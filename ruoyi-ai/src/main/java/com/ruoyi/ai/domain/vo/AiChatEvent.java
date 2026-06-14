package com.ruoyi.ai.domain.vo;

/**
 * Event generated during an AI chat exchange.
 */
public class AiChatEvent
{
    private String type;
    private String toolName;
    private String content;

    public AiChatEvent()
    {
    }

    public AiChatEvent(String type, String toolName, String content)
    {
        this.type = type;
        this.toolName = toolName;
        this.content = content;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getToolName()
    {
        return toolName;
    }

    public void setToolName(String toolName)
    {
        this.toolName = toolName;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }
}
