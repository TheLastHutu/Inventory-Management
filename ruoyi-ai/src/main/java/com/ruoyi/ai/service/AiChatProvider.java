package com.ruoyi.ai.service;

import java.util.List;
import java.util.function.Consumer;
import com.ruoyi.ai.domain.AiMessage;
import com.ruoyi.ai.domain.bo.AiChatRequest;
import com.ruoyi.ai.domain.vo.AiChatEvent;
import com.ruoyi.ai.domain.vo.AiChatExchange;

public interface AiChatProvider
{
    String chat(AiChatRequest request, List<AiMessage> history);

    default AiChatExchange exchange(AiChatRequest request, List<AiMessage> history)
    {
        return new AiChatExchange(chat(request, history));
    }

    default AiChatExchange streamExchange(AiChatRequest request, List<AiMessage> history,
            Consumer<String> contentConsumer, Consumer<AiChatEvent> eventConsumer)
    {
        AiChatExchange exchange = exchange(request, history);
        if (exchange.getEvents() != null)
        {
            for (AiChatEvent event : exchange.getEvents())
            {
                eventConsumer.accept(event);
            }
        }
        if (exchange.getContent() != null)
        {
            contentConsumer.accept(exchange.getContent());
        }
        return exchange;
    }
}
