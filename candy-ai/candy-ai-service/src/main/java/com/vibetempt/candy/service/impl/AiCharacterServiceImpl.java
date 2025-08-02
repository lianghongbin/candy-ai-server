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
    
    // 新接口方法实现
    @Override
    public AiCharacter createCharacter(AiCharacter character) {
        aiCharacterMapper.insertAiCharacter(character);
        return character;
    }
    
    @Override
    public int updateCharacter(AiCharacter character) {
        character.setUpdateBy(SecurityUtils.getUsername());
        return aiCharacterMapper.updateAiCharacter(character);
    }
    
    @Override
    public int deleteCharacter(Long id) {
        return aiCharacterMapper.deleteAiCharacterById(id);
    }
    
    @Override
    public List<AiCharacter> getCharacterList(AiCharacter query) {
        return aiCharacterMapper.selectAiCharacterList(query);
    }
    
    @Override
    public AiCharacter getCharacterById(Long id) {
        return aiCharacterMapper.selectAiCharacterById(id);
    }
    
    @Override
    public List<AiCharacter> getUserCharacters(Long userId) {
        return aiCharacterMapper.selectAiCharacterListByCreator(userId);
    }
    
    @Override
    public List<AiCharacter> getSystemCharacters() {
        return aiCharacterMapper.selectSystemCharacters();
    }
    
    @Override
    public AiCharacter copyCharacter(Long sourceId, Long userId) {
        // 获取原角色信息
        AiCharacter originalCharacter = aiCharacterMapper.selectAiCharacterById(sourceId);
        if (originalCharacter == null) {
            return null;
        }
        
        // 创建新角色，复制原角色的信息
        AiCharacter newCharacter = new AiCharacter();
        newCharacter.setName(originalCharacter.getName() + "_副本");
        newCharacter.setDescription(originalCharacter.getDescription());
        newCharacter.setAvatarUrl(originalCharacter.getAvatarUrl());
        newCharacter.setCreatorId(userId);
        newCharacter.setCreateBy(SecurityUtils.getUsername());
        newCharacter.setStatus("0");
        
        // 复制角色属性
        newCharacter.setStyle(originalCharacter.getStyle());
        newCharacter.setEthnicity(originalCharacter.getEthnicity());
        newCharacter.setAge(originalCharacter.getAge());
        newCharacter.setEyeColor(originalCharacter.getEyeColor());
        newCharacter.setHairStyle(originalCharacter.getHairStyle());
        newCharacter.setHairColor(originalCharacter.getHairColor());
        newCharacter.setBodyType(originalCharacter.getBodyType());
        newCharacter.setBreastSize(originalCharacter.getBreastSize());
        newCharacter.setButtSize(originalCharacter.getButtSize());
        newCharacter.setPersonality(originalCharacter.getPersonality());
        newCharacter.setOccupation(originalCharacter.getOccupation());
        newCharacter.setHobbies(originalCharacter.getHobbies());
        newCharacter.setRelationship(originalCharacter.getRelationship());
        
        aiCharacterMapper.insertAiCharacter(newCharacter);
        return newCharacter;
    }
    
    // 保留原有方法以兼容现有代码
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
     * @param id AI 角色 ID
     * @return AI 角色信息
     */
    @Override
    public AiCharacter selectAiCharacterById(Long id) {
        return aiCharacterMapper.selectAiCharacterById(id);
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
     * @param id AI 角色 ID
     * @return 结果
     */
    @Override
    public int deleteAiCharacterById(Long id) {
        return aiCharacterMapper.deleteAiCharacterById(id);
    }
    
    /**
     * 批量删除 AI 角色
     * 
     * @param ids 需要删除的 AI 角色 ID 数组
     * @return 结果
     */
    @Override
    public int deleteAiCharacterByIds(Long[] ids) {
        return aiCharacterMapper.deleteAiCharacterByIds(ids);
    }
} 