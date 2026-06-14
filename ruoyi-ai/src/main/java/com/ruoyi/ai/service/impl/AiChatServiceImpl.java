package com.ruoyi.ai.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.ai.domain.AiConversation;
import com.ruoyi.ai.domain.AiMessage;
import com.ruoyi.ai.domain.bo.AiChatRequest;
import com.ruoyi.ai.domain.vo.AiChatExchange;
import com.ruoyi.ai.domain.vo.AiChatEvent;
import com.ruoyi.ai.domain.vo.AiChatResult;
import com.ruoyi.ai.mapper.AiConversationMapper;
import com.ruoyi.ai.mapper.AiMessageMapper;
import com.ruoyi.ai.service.IAiChatService;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;

@Service
public class AiChatServiceImpl implements IAiChatService
{
    @Autowired
    private AiConversationMapper conversationMapper;

    @Autowired
    private AiMessageMapper messageMapper;

    @Autowired
    private LocalAiChatProvider localAiChatProvider;

    @Autowired
    private Optional<SpringAiChatProvider> springAiChatProvider;

    @Value("${ai.chat.provider:local}")
    private String provider;

    @Value("${ai.chat.max-history:20}")
    private Integer maxHistory;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AiChatResult chat(AiChatRequest request)
    {
        if (request == null || StringUtils.isEmpty(request.getMessage()))
        {
            throw new ServiceException("消息内容不能为空");
        }

        Long userId = SecurityUtils.getUserId();
        AiConversation conversation = getOrCreateConversation(request, userId);
        request.setConversationId(conversation.getConversationId());

        AiMessage userMessage = buildMessage(conversation.getConversationId(), userId, "user", request.getMessage(),
                request.getRoute(), request.getPageTitle(), buildMetadata(request));
        messageMapper.insertAiMessage(userMessage);

        List<AiMessage> history = messageMapper.selectRecentAiMessageList(conversation.getConversationId(), userId, maxHistory);
        AiChatExchange exchange = selectChatProvider().exchange(request, history);
        String answer = exchange.getContent();

        AiMessage assistantMessage = buildMessage(conversation.getConversationId(), userId, "assistant", answer,
                request.getRoute(), request.getPageTitle(), null);
        messageMapper.insertAiMessage(assistantMessage);

        conversation.setLastRoute(request.getRoute());
        conversation.setLastPageTitle(request.getPageTitle());
        conversation.setUpdateTime(new Date());
        conversationMapper.updateAiConversation(conversation);

        AiChatResult result = new AiChatResult(conversation.getConversationId(), answer);
        result.setEvents(exchange.getEvents());
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AiChatResult streamChat(AiChatRequest request, Consumer<String> contentConsumer,
            Consumer<AiChatEvent> eventConsumer)
    {
        if (request == null || StringUtils.isEmpty(request.getMessage()))
        {
            throw new ServiceException("娑堟伅鍐呭涓嶈兘涓虹┖");
        }

        Long userId = SecurityUtils.getUserId();
        AiConversation conversation = getOrCreateConversation(request, userId);
        request.setConversationId(conversation.getConversationId());

        AiMessage userMessage = buildMessage(conversation.getConversationId(), userId, "user", request.getMessage(),
                request.getRoute(), request.getPageTitle(), buildMetadata(request));
        messageMapper.insertAiMessage(userMessage);

        List<AiMessage> history = messageMapper.selectRecentAiMessageList(conversation.getConversationId(), userId, maxHistory);
        StringBuilder answerBuilder = new StringBuilder();
        AiChatExchange exchange = selectChatProvider().streamExchange(request, history, chunk -> {
            if (chunk != null)
            {
                answerBuilder.append(chunk);
                contentConsumer.accept(chunk);
            }
        }, eventConsumer);

        String answer = answerBuilder.length() > 0 ? answerBuilder.toString() : exchange.getContent();
        if (answer == null)
        {
            answer = "";
        }

        AiMessage assistantMessage = buildMessage(conversation.getConversationId(), userId, "assistant", answer,
                request.getRoute(), request.getPageTitle(), null);
        messageMapper.insertAiMessage(assistantMessage);

        conversation.setLastRoute(request.getRoute());
        conversation.setLastPageTitle(request.getPageTitle());
        conversation.setUpdateTime(new Date());
        conversationMapper.updateAiConversation(conversation);

        AiChatResult result = new AiChatResult(conversation.getConversationId(), answer);
        result.setEvents(exchange.getEvents());
        return result;
    }

    @Override
    public List<AiConversation> selectConversationList()
    {
        return conversationMapper.selectAiConversationList(SecurityUtils.getUserId());
    }

    @Override
    public List<AiMessage> selectMessageList(Long conversationId)
    {
        return messageMapper.selectAiMessageList(conversationId, SecurityUtils.getUserId());
    }

    @Override
    public AiConversation createConversation(AiChatRequest request)
    {
        return createConversationInternal(request, SecurityUtils.getUserId(), SecurityUtils.getUsername());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteConversation(Long conversationId)
    {
        Long userId = SecurityUtils.getUserId();
        messageMapper.deleteAiMessagesByConversationId(conversationId, userId);
        return conversationMapper.deleteAiConversationById(conversationId, userId);
    }

    private AiConversation getOrCreateConversation(AiChatRequest request, Long userId)
    {
        if (request.getConversationId() != null)
        {
            AiConversation conversation = conversationMapper.selectAiConversationById(request.getConversationId(), userId);
            if (conversation == null)
            {
                throw new ServiceException("会话不存在或无权访问");
            }
            return conversation;
        }
        return createConversationInternal(request, userId, SecurityUtils.getUsername());
    }

    private com.ruoyi.ai.service.AiChatProvider selectChatProvider()
    {
        if ("spring".equalsIgnoreCase(provider) && springAiChatProvider.isPresent())
        {
            return springAiChatProvider.get();
        }
        return localAiChatProvider;
    }

    private AiConversation createConversationInternal(AiChatRequest request, Long userId, String username)
    {
        AiConversation conversation = new AiConversation();
        Date now = new Date();
        conversation.setUserId(userId);
        conversation.setUsername(username);
        conversation.setTitle(buildTitle(request == null ? null : request.getMessage()));
        conversation.setSummary("");
        conversation.setLastRoute(request == null ? null : request.getRoute());
        conversation.setLastPageTitle(request == null ? null : request.getPageTitle());
        conversation.setStatus("0");
        conversation.setCreateTime(now);
        conversation.setUpdateTime(now);
        conversationMapper.insertAiConversation(conversation);
        return conversation;
    }

    private AiMessage buildMessage(Long conversationId, Long userId, String role, String content, String route,
            String pageTitle, String metadata)
    {
        AiMessage message = new AiMessage();
        message.setConversationId(conversationId);
        message.setUserId(userId);
        message.setRole(role);
        message.setContent(content);
        message.setRoute(route);
        message.setPageTitle(pageTitle);
        message.setMetadata(metadata);
        message.setCreateTime(new Date());
        return message;
    }

    private String buildTitle(String message)
    {
        if (StringUtils.isEmpty(message))
        {
            return "新的聊天";
        }
        String clean = message.replaceAll("\\s+", " ").trim();
        return clean.length() > 30 ? clean.substring(0, 30) : clean;
    }

    private String buildMetadata(AiChatRequest request)
    {
        if (StringUtils.isEmpty(request.getQueryParams()) && StringUtils.isEmpty(request.getSelectedRows()))
        {
            return null;
        }
        return "{\"queryParams\":" + safeJson(request.getQueryParams()) + ",\"selectedRows\":"
                + safeJson(request.getSelectedRows()) + "}";
    }

    private String safeJson(String value)
    {
        return StringUtils.isEmpty(value) ? "null" : value;
    }
}
