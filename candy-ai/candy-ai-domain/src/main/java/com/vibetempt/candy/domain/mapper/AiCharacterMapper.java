package com.vibetempt.candy.domain.mapper;

import com.vibetempt.candy.domain.AiCharacter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * AI 角色 Mapper 接口
 * 
 * @author vibetempt
 */
@Mapper
public interface AiCharacterMapper {
    
    /**
     * 查询 AI 角色列表
     * 
     * @param aiCharacter AI 角色信息
     * @return AI 角色集合
     */
    List<AiCharacter> selectAiCharacterList(AiCharacter aiCharacter);
    
    /**
     * 根据创建者查询 AI 角色列表
     * 
     * @param creatorId 创建者ID
     * @return AI 角色集合
     */
    List<AiCharacter> selectAiCharacterListByCreator(Long creatorId);
    
    /**
     * 根据风格查询 AI 角色列表
     * 
     * @param style 风格
     * @return AI 角色集合
     */
    List<AiCharacter> selectAiCharacterListByStyle(String style);
    
    /**
     * 查询系统预设角色列表
     * 
     * @return AI 角色集合
     */
    List<AiCharacter> selectSystemCharacters();
    
    /**
     * 根据 ID 查询 AI 角色
     * 
     * @param id AI 角色 ID
     * @return AI 角色信息
     */
    AiCharacter selectAiCharacterById(Long id);
    
    /**
     * 新增 AI 角色
     * 
     * @param aiCharacter AI 角色信息
     * @return 结果
     */
    int insertAiCharacter(AiCharacter aiCharacter);
    
    /**
     * 修改 AI 角色
     * 
     * @param aiCharacter AI 角色信息
     * @return 结果
     */
    int updateAiCharacter(AiCharacter aiCharacter);
    
    /**
     * 删除 AI 角色
     * 
     * @param id AI 角色 ID
     * @return 结果
     */
    int deleteAiCharacterById(Long id);
    
    /**
     * 批量删除 AI 角色
     * 
     * @param ids 需要删除的 AI 角色 ID 数组
     * @return 结果
     */
    int deleteAiCharacterByIds(Long[] ids);
    
    /**
     * 更新角色统计信息
     * 
     * @param id 角色ID
     * @param totalUsers 使用用户数
     * @param totalConversations 总对话数
     * @param totalMessages 总消息数
     * @return 结果
     */
    int updateCharacterStats(@Param("id") Long id, 
                           @Param("totalUsers") Integer totalUsers,
                           @Param("totalConversations") Integer totalConversations,
                           @Param("totalMessages") Integer totalMessages);
} 