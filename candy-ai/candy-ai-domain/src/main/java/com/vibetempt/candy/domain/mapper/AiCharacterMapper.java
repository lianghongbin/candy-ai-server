package com.vibetempt.candy.domain.mapper;

import com.vibetempt.candy.domain.AiCharacter;
import org.apache.ibatis.annotations.Mapper;
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
     * 根据用户查询 AI 角色列表
     * 
     * @param userId 用户ID
     * @return AI 角色集合
     */
    List<AiCharacter> selectAiCharacterListByUserId(Long userId);
    
    /**
     * 根据用户查询 AI 角色列表（带条件）
     * 
     * @param aiCharacter AI 角色信息（包含用户ID）
     * @return AI 角色集合
     */
    List<AiCharacter> selectAiCharacterListByUser(AiCharacter aiCharacter);
    
    /**
     * 根据拥有者查询角色实例列表
     * 
     * @param ownerUserId 拥有者用户ID
     * @return 角色实例集合
     */
    List<AiCharacter> selectCharacterInstancesByOwner(Long ownerUserId);
    
    /**
     * 根据模板ID查询角色实例列表
     * 
     * @param templateId 模板ID
     * @return 角色实例集合
     */
    List<AiCharacter> selectCharacterInstancesByTemplate(Long templateId);
    
    /**
     * 根据 ID 查询 AI 角色
     * 
     * @param characterId AI 角色 ID
     * @return AI 角色信息
     */
    AiCharacter selectAiCharacterById(Long characterId);
    
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
     * @param characterId AI 角色 ID
     * @return 结果
     */
    int deleteAiCharacterById(Long characterId);
    
    /**
     * 批量删除 AI 角色
     * 
     * @param characterIds 需要删除的 AI 角色 ID 数组
     * @return 结果
     */
    int deleteAiCharacterByIds(Long[] characterIds);
} 