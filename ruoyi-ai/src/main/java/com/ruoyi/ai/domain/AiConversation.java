package com.ruoyi.ai.domain;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * AI conversation.
 */
public class AiConversation extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long conversationId;
    private Long userId;
    private String username;
    private String title;
    private String summary;
    private String lastRoute;
    private String lastPageTitle;
    private String status;

    public Long getConversationId()
    {
        return conversationId;
    }

    public void setConversationId(Long conversationId)
    {
        this.conversationId = conversationId;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getSummary()
    {
        return summary;
    }

    public void setSummary(String summary)
    {
        this.summary = summary;
    }

    public String getLastRoute()
    {
        return lastRoute;
    }

    public void setLastRoute(String lastRoute)
    {
        this.lastRoute = lastRoute;
    }

    public String getLastPageTitle()
    {
        return lastPageTitle;
    }

    public void setLastPageTitle(String lastPageTitle)
    {
        this.lastPageTitle = lastPageTitle;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }
}
