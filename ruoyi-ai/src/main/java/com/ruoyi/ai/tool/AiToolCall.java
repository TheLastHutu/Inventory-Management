package com.ruoyi.ai.tool;

import java.util.Map;

public class AiToolCall
{
    private String type;
    private String tool;
    private Map<String, Object> arguments;
    private String reason;

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getTool()
    {
        return tool;
    }

    public void setTool(String tool)
    {
        this.tool = tool;
    }

    public Map<String, Object> getArguments()
    {
        return arguments;
    }

    public void setArguments(Map<String, Object> arguments)
    {
        this.arguments = arguments;
    }

    public String getReason()
    {
        return reason;
    }

    public void setReason(String reason)
    {
        this.reason = reason;
    }
}
