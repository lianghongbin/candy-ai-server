package com.vibetempt.candy.service.impl;

import com.ruoyi.common.utils.SecurityUtils;
import com.vibetempt.candy.domain.AiCharacter;
import com.vibetempt.candy.domain.mapper.AiCharacterMapper;
import com.vibetempt.candy.service.AiCharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * AI 角色 业务层处理
 * 
 * @author vibetempt
 */
@Service
public class AiCharacterServiceImpl implements AiCharacterService {
    
    @Autowired
    private AiCharacterMapper aiCharacterMapper;
    
    /**
     * 根据条件分页查询 AI 角色列表
     * 
     * @param aiCharacter AI 角色信息
     * @return AI 角色集合
     */
    @Override
    public List<AiCharacter> selectAiCharacterList(AiCharacter aiCharacter) {
        return aiCharacterMapper.selectAiCharacterList(aiCharacter);
    }
    
    /**
     * 根据 ID 查询 AI 角色
     * 
     * @param characterId AI 角色 ID
     * @return AI 角色信息
     */
    @Override
    public AiCharacter selectAiCharacterById(Long characterId) {
        return aiCharacterMapper.selectAiCharacterById(characterId);
    }
    
    /**
     * 新增 AI 角色
     * 
     * @param aiCharacter AI 角色信息
     * @return 结果
     */
    @Override
    public int insertAiCharacter(AiCharacter aiCharacter) {
        return aiCharacterMapper.insertAiCharacter(aiCharacter);
    }
    
    /**
     * 修改 AI 角色
     * 
     * @param aiCharacter AI 角色信息
     * @return 结果
     */
    @Override
    public int updateAiCharacter(AiCharacter aiCharacter) {
        return aiCharacterMapper.updateAiCharacter(aiCharacter);
    }
    
    /**
     * 删除 AI 角色信息
     * 
     * @param characterId AI 角色 ID
     * @return 结果
     */
    @Override
    public int deleteAiCharacterById(Long characterId) {
        return aiCharacterMapper.deleteAiCharacterById(characterId);
    }
    
    /**
     * 批量删除 AI 角色
     * 
     * @param characterIds 需要删除的 AI 角色 ID 数组
     * @return 结果
     */
    @Override
    public int deleteAiCharacterByIds(Long[] characterIds) {
        return aiCharacterMapper.deleteAiCharacterByIds(characterIds);
    }
    
    /**
     * 根据条件分页查询我的角色实例列表
     * 
     * @param aiCharacter 查询条件
     * @return 角色实例集合
     */
    @Override
    public List<AiCharacter> selectMyCharacterList(AiCharacter aiCharacter) {
        // 设置当前用户ID作为拥有者条件
        aiCharacter.setOwnerUserId(SecurityUtils.getUserId());
        return aiCharacterMapper.selectAiCharacterList(aiCharacter);
    }
    
    /**
     * 根据拥有者查询角色实例列表
     * 
     * @param ownerUserId 拥有者用户ID
     * @return 角色实例集合
     */
    @Override
    public List<AiCharacter> selectCharacterListByOwner(Long ownerUserId) {
        return aiCharacterMapper.selectCharacterInstancesByOwner(ownerUserId);
    }
    
    /**
     * 根据模板ID查询角色实例列表
     * 
     * @param templateId 模板ID
     * @return 角色实例集合
     */
    @Override
    public List<AiCharacter> selectCharacterListByTemplate(Long templateId) {
        AiCharacter query = new AiCharacter();
        query.setTemplateId(templateId);
        return aiCharacterMapper.selectAiCharacterList(query);
    }
    
    /**
     * 根据模板创建角色实例
     * 
     * @param templateId 模板ID
     * @param characterName 角色实例名称
     * @param ownerUserId 实例拥有者用户ID
     * @return 结果
     */
    @Override
    public int createCharacterInstanceFromTemplate(Long templateId, String characterName, Long ownerUserId) {
        AiCharacter aiCharacter = new AiCharacter();
        aiCharacter.setTemplateId(templateId);
        aiCharacter.setCharacterName(characterName);
        aiCharacter.setOwnerUserId(ownerUserId);
        aiCharacter.setCreateBy(SecurityUtils.getUsername());
        aiCharacter.setStatus("0"); // 正常状态
        
        return aiCharacterMapper.insertAiCharacter(aiCharacter);
    }
} 