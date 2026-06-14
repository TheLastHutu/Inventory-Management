package com.ruoyi.ai.controller;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import com.alibaba.fastjson2.JSON;
import com.ruoyi.ai.domain.AiConversation;
import com.ruoyi.ai.domain.AiMessage;
import com.ruoyi.ai.domain.bo.AiChatRequest;
import com.ruoyi.ai.domain.vo.AiChatResult;
import com.ruoyi.ai.service.IAiChatService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.framework.web.service.TokenService;

@RestController
@RequestMapping("/ai")
public class AiChatController extends BaseController
{
    @Autowired
    private IAiChatService aiChatService;

    @Autowired
    private TokenService tokenService;

    @PostMapping(value = "/chat/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter stream(@RequestBody AiChatRequest request, HttpServletRequest httpRequest)
    {
        SseEmitter emitter = new SseEmitter(120000L);
        try
        {
            Authentication authentication = ensureLogin(httpRequest);
            Thread worker = new Thread(() -> {
                SecurityContextHolder.getContext().setAuthentication(authentication);
                try
                {
                    AiChatResult result = aiChatService.streamChat(request,
                            chunk -> sendEvent(emitter, "message", new AiChatResult(request.getConversationId(), chunk)),
                            event -> sendEvent(emitter, event.getType(), event));
                    sendEvent(emitter, "done", result);
                    emitter.complete();
                }
                catch (Exception e)
                {
                    sendError(emitter, e);
                    emitter.completeWithError(e);
                }
                finally
                {
                    SecurityContextHolder.clearContext();
                }
            }, "ai-chat-stream");
            worker.start();
        }
        catch (Exception e)
        {
            sendError(emitter, e);
            emitter.completeWithError(e);
        }
        return emitter;
    }

    @GetMapping("/conversations")
    public AjaxResult conversations()
    {
        List<AiConversation> list = aiChatService.selectConversationList();
        return success(list);
    }

    @PostMapping("/conversations")
    public AjaxResult createConversation(@RequestBody(required = false) AiChatRequest request)
    {
        return success(aiChatService.createConversation(request));
    }

    @GetMapping("/conversations/{conversationId}/messages")
    public AjaxResult messages(@PathVariable Long conversationId)
    {
        List<AiMessage> list = aiChatService.selectMessageList(conversationId);
        return success(list);
    }

    @DeleteMapping("/conversations/{conversationId}")
    public AjaxResult deleteConversation(@PathVariable Long conversationId)
    {
        return toAjax(aiChatService.deleteConversation(conversationId));
    }

    private void sendEvent(SseEmitter emitter, String name, Object data)
    {
        try
        {
            emitter.send(SseEmitter.event().name(name).data(JSON.toJSONString(data)));
        }
        catch (IOException e)
        {
            throw new IllegalStateException("AI SSE send failed", e);
        }
    }

    private void sendError(SseEmitter emitter, Exception e)
    {
        try
        {
            emitter.send(SseEmitter.event().name("error").data(JSON.toJSONString(AjaxResult.error(e.getMessage()))));
        }
        catch (IOException ignored)
        {
        }
    }

    private Authentication ensureLogin(HttpServletRequest request)
    {
        LoginUser loginUser = tokenService.getLoginUser(request);
        if (loginUser == null)
        {
            throw new ServiceException("登录状态已过期，请重新登录");
        }
        tokenService.verifyToken(loginUser);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        return authenticationToken;
    }
}
