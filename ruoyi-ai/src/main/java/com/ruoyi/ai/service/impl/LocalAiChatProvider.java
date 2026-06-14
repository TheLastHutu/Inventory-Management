package com.ruoyi.ai.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import com.ruoyi.ai.domain.AiMessage;
import com.ruoyi.ai.domain.bo.AiChatRequest;
import com.ruoyi.ai.service.AiChatProvider;
import com.ruoyi.common.utils.StringUtils;

/**
 * Local fallback provider used before a real model key is configured.
 */
@Service
public class LocalAiChatProvider implements AiChatProvider
{
    @Override
    public String chat(AiChatRequest request, List<AiMessage> history)
    {
        int rounds = Math.max(0, history.size() / 2);
        StringBuilder reply = new StringBuilder();
        reply.append("我已经收到你的问题：**").append(request.getMessage()).append("**\n\n");
        reply.append("当前聊天上下文中已有 ").append(rounds).append(" 轮历史对话。");
        if (StringUtils.isNotEmpty(request.getPageTitle()) || StringUtils.isNotEmpty(request.getRoute()))
        {
            reply.append("你现在位于");
            if (StringUtils.isNotEmpty(request.getPageTitle()))
            {
                reply.append(" **").append(request.getPageTitle()).append("**");
            }
            if (StringUtils.isNotEmpty(request.getRoute()))
            {
                reply.append("（`").append(request.getRoute()).append("`）");
            }
            reply.append("。");
        }
        reply.append("\n\n");
        reply.append("当前版本已经打通聊天、SSE 流式输出和会话持久化。业务工具调用还未启用，所以我不会查询或修改任何业务数据。");
        reply.append("\n\n");
        reply.append("你可以继续追问上一条内容，我会通过会话记录保持基本上下文。");
        return reply.toString();
    }
}
