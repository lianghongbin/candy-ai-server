package com.vibetempt.candy.service;

import com.vibetempt.candy.domain.AiCharacter;
import java.util.List;

/**
 * AI 角色 业务层
 * 
 * @author vibetempt
 */
public interface IAiCharacterService {
    
    /**
     * 根据条件分页查询 AI 角色列表
     * 
     * @param aiCharacter AI 角色信息
     * @return AI 角色集合
     */
    List<AiCharacter> selectAiCharacterList(AiCharacter aiCharacter);
    
    /**
     * 通过角色ID查询 AI 角色
     * 
     * @param characterId AI 角色 ID
     * @return AI 角色信息
     */
    AiCharacter selectAiCharacterById(Long characterId);
    
    /**
     * 新增 AI 角色信息
     * 
     * @param aiCharacter AI 角色信息
     * @return 结果
     */
    int insertAiCharacter(AiCharacter aiCharacter);
    
    /**
     * 修改 AI 角色信息
     * 
     * @param aiCharacter AI 角色信息
     * @return 结果
     */
    int updateAiCharacter(AiCharacter aiCharacter);
    
    /**
     * 通过角色ID删除 AI 角色信息
     * 
     * @param characterId AI 角色 ID
     * @return 结果
     */
    int deleteAiCharacterById(Long characterId);
    
    /**
     * 批量删除 AI 角色信息
     * 
     * @param characterIds 需要删除的 AI 角色 ID 数组
     * @return 结果
     */
    int deleteAiCharacterByIds(Long[] characterIds);
    
    /**
     * 根据条件分页查询我的角色实例列表
     * 
     * @param aiCharacter 查询条件
     * @return 角色实例集合
     */
    List<AiCharacter> selectMyCharacterList(AiCharacter aiCharacter);
    
    /**
     * 根据拥有者查询角色实例列表
     * 
     * @param ownerUserId 拥有者用户ID
     * @return 角色实例集合
     */
    List<AiCharacter> selectCharacterListByOwner(Long ownerUserId);
    
    /**
     * 根据模板ID查询角色实例列表
     * 
     * @param templateId 模板ID
     * @return 角色实例集合
     */
    List<AiCharacter> selectCharacterListByTemplate(Long templateId);
    
    /**
     * 根据模板创建角色实例
     * 
     * @param templateId 模板ID
     * @param characterName 角色实例名称
     * @param ownerUserId 实例拥有者用户ID
     * @return 结果
     */
    int createCharacterInstanceFromTemplate(Long templateId, String characterName, Long ownerUserId);
} 