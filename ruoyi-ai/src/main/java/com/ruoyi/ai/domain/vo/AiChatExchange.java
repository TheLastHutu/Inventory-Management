package com.ruoyi.ai.domain.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * Full AI chat exchange, including final content and internal events.
 */
public class AiChatExchange
{
    private String content;
    private List<AiChatEvent> events = new ArrayList<AiChatEvent>();

    public AiChatExchange()
    {
    }

    public AiChatExchange(String content)
    {
        this.content = content;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public List<AiChatEvent> getEvents()
    {
        return events;
    }

    public void setEvents(List<AiChatEvent> events)
    {
        this.events = events;
    }

    public void addEvent(AiChatEvent event)
    {
        this.events.add(event);
    }
}
