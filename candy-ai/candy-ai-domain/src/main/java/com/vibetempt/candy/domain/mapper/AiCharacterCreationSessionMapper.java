package com.vibetempt.candy.domain.mapper;

import com.vibetempt.candy.domain.AiCharacterCreationSession;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * AI角色创建会话Mapper接口
 * 
 * @author vibetempt
 */
@Mapper
public interface AiCharacterCreationSessionMapper {
    
    /**
     * 查询角色创建会话列表
     * 
     * @param session 查询条件
     * @return 会话列表
     */
    List<AiCharacterCreationSession> selectAiCharacterCreationSessionList(AiCharacterCreationSession session);
    
    /**
     * 根据ID查询角色创建会话
     * 
     * @param id 会话ID
     * @return 会话信息
     */
    AiCharacterCreationSession selectAiCharacterCreationSessionById(Long id);
    
    /**
     * 根据会话ID查询角色创建会话
     * 
     * @param sessionId 会话ID
     * @return 会话信息
     */
    AiCharacterCreationSession selectAiCharacterCreationSessionBySessionId(String sessionId);
    
    /**
     * 根据用户ID查询活跃会话
     * 
     * @param userId 用户ID
     * @return 会话信息
     */
    AiCharacterCreationSession selectActiveSessionByUserId(Long userId);
    
    /**
     * 新增角色创建会话
     * 
     * @param session 会话信息
     * @return 结果
     */
    int insertAiCharacterCreationSession(AiCharacterCreationSession session);
    
    /**
     * 修改角色创建会话
     * 
     * @param session 会话信息
     * @return 结果
     */
    int updateAiCharacterCreationSession(AiCharacterCreationSession session);
    
    /**
     * 删除角色创建会话
     * 
     * @param id 会话ID
     * @return 结果
     */
    int deleteAiCharacterCreationSessionById(Long id);
    
    /**
     * 批量删除角色创建会话
     * 
     * @param ids 需要删除的会话ID数组
     * @return 结果
     */
    int deleteAiCharacterCreationSessionByIds(Long[] ids);
    
    /**
     * 更新会话状态
     * 
     * @param sessionId 会话ID
     * @param status 状态
     * @return 结果
     */
    int updateSessionStatus(@Param("sessionId") String sessionId, @Param("status") String status);
    
    /**
     * 清理过期会话
     * 
     * @param expireTime 过期时间
     * @return 结果
     */
    int cleanExpiredSessions(String expireTime);
} 