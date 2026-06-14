package com.ruoyi.ai.tool;

public class AiToolExecutionResult
{
    private String toolName;
    private String summary;
    private Object data;

    public AiToolExecutionResult()
    {
    }

    public AiToolExecutionResult(String toolName, String summary, Object data)
    {
        this.toolName = toolName;
        this.summary = summary;
        this.data = data;
    }

    public String getToolName()
    {
        return toolName;
    }

    public void setToolName(String toolName)
    {
        this.toolName = toolName;
    }

    public String getSummary()
    {
        return summary;
    }

    public void setSummary(String summary)
    {
        this.summary = summary;
    }

    public Object getData()
    {
        return data;
    }

    public void setData(Object data)
    {
        this.data = data;
    }
}
