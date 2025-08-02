package com.vibetempt.candy.service;

import com.vibetempt.candy.domain.AiCharacter;
import java.util.List;

/**
 * AI 角色 业务层
 * 
 * @author vibetempt
 */
public interface AiCharacterService {
    
    /**
     * 创建角色
     */
    AiCharacter createCharacter(AiCharacter character);
    
    /**
     * 更新角色
     */
    int updateCharacter(AiCharacter character);
    
    /**
     * 删除角色
     */
    int deleteCharacter(Long id);
    
    /**
     * 获取角色列表
     */
    List<AiCharacter> getCharacterList(AiCharacter query);
    
    /**
     * 获取角色详情
     */
    AiCharacter getCharacterById(Long id);
    
    /**
     * 获取用户创建的角色
     */
    List<AiCharacter> getUserCharacters(Long userId);
    
    /**
     * 获取系统预设角色
     */
    List<AiCharacter> getSystemCharacters();
    
    /**
     * 复制角色
     */
    AiCharacter copyCharacter(Long sourceId, Long userId);
    
    // 保留原有方法以兼容现有代码
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
     * @param id AI 角色 ID
     * @return AI 角色信息
     */
    AiCharacter selectAiCharacterById(Long id);
    
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
     * @param id AI 角色 ID
     * @return 结果
     */
    int deleteAiCharacterById(Long id);
    
    /**
     * 批量删除 AI 角色信息
     * 
     * @param ids 需要删除的 AI 角色 ID 数组
     * @return 结果
     */
    int deleteAiCharacterByIds(Long[] ids);
} 