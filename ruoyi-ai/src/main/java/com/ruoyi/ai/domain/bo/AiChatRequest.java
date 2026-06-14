package com.ruoyi.ai.domain.bo;

/**
 * Chat request from AI floating window.
 */
public class AiChatRequest
{
    private Long conversationId;
    private String message;
    private String route;
    private String pageTitle;
    private String queryParams;
    private String selectedRows;

    public Long getConversationId()
    {
        return conversationId;
    }

    public void setConversationId(Long conversationId)
    {
        this.conversationId = conversationId;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getRoute()
    {
        return route;
    }

    public void setRoute(String route)
    {
        this.route = route;
    }

    public String getPageTitle()
    {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle)
    {
        this.pageTitle = pageTitle;
    }

    public String getQueryParams()
    {
        return queryParams;
    }

    public void setQueryParams(String queryParams)
    {
        this.queryParams = queryParams;
    }

    public String getSelectedRows()
    {
        return selectedRows;
    }

    public void setSelectedRows(String selectedRows)
    {
        this.selectedRows = selectedRows;
    }
}
