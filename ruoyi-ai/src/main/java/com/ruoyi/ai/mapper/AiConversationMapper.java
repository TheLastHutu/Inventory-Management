package com.ruoyi.ai.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.ai.domain.AiConversation;

public interface AiConversationMapper
{
    AiConversation selectAiConversationById(@Param("conversationId") Long conversationId, @Param("userId") Long userId);

    List<AiConversation> selectAiConversationList(@Param("userId") Long userId);

    int insertAiConversation(AiConversation conversation);

    int updateAiConversation(AiConversation conversation);

    int deleteAiConversationById(@Param("conversationId") Long conversationId, @Param("userId") Long userId);
}
