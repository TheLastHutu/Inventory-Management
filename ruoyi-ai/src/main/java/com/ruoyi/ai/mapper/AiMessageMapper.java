package com.ruoyi.ai.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.ai.domain.AiMessage;

public interface AiMessageMapper
{
    List<AiMessage> selectAiMessageList(@Param("conversationId") Long conversationId, @Param("userId") Long userId);

    List<AiMessage> selectRecentAiMessageList(@Param("conversationId") Long conversationId,
            @Param("userId") Long userId, @Param("limit") Integer limit);

    int insertAiMessage(AiMessage message);

    int deleteAiMessagesByConversationId(@Param("conversationId") Long conversationId, @Param("userId") Long userId);
}
