package com.ruoyi.ai.service;

import java.util.List;
import java.util.function.Consumer;
import com.ruoyi.ai.domain.AiConversation;
import com.ruoyi.ai.domain.AiMessage;
import com.ruoyi.ai.domain.bo.AiChatRequest;
import com.ruoyi.ai.domain.vo.AiChatEvent;
import com.ruoyi.ai.domain.vo.AiChatResult;

public interface IAiChatService
{
    AiChatResult chat(AiChatRequest request);

    AiChatResult streamChat(AiChatRequest request, Consumer<String> contentConsumer,
            Consumer<AiChatEvent> eventConsumer);

    List<AiConversation> selectConversationList();

    List<AiMessage> selectMessageList(Long conversationId);

    AiConversation createConversation(AiChatRequest request);

    int deleteConversation(Long conversationId);
}
