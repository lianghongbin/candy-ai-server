package com.vibetempt.candy.service.impl;

import com.ruoyi.common.utils.SecurityUtils;
import com.vibetempt.candy.domain.AiCharacterTemplate;
import com.vibetempt.candy.domain.mapper.AiCharacterTemplateMapper;
import com.vibetempt.candy.service.AiCharacterTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * AI 角色模板 业务层处理
 * 
 * @author candy-ai
 */
@Service
public class AiCharacterTemplateServiceImpl implements AiCharacterTemplateService {
    
    @Autowired
    private AiCharacterTemplateMapper aiCharacterTemplateMapper;
    
    /**
     * 根据条件分页查询 AI 角色模板列表
     * 
     * @param aiCharacterTemplate AI 角色模板信息
     * @return AI 角色模板集合
     */
    @Override
    public List<AiCharacterTemplate> selectAiCharacterTemplateList(AiCharacterTemplate aiCharacterTemplate) {
        return aiCharacterTemplateMapper.selectAiCharacterTemplateList(aiCharacterTemplate);
    }
    
    /**
     * 通过角色ID查询 AI 角色模板
     * 
     * @param id AI 角色模板 ID
     * @return AI 角色模板信息
     */
    @Override
    public AiCharacterTemplate selectAiCharacterTemplateById(Long id) {
        return aiCharacterTemplateMapper.selectAiCharacterTemplateById(id);
    }
    
    /**
     * 新增 AI 角色模板信息
     * 
     * @param aiCharacterTemplate AI 角色模板信息
     * @return 结果
     */
    @Override
    public int insertAiCharacterTemplate(AiCharacterTemplate aiCharacterTemplate) {
        aiCharacterTemplate.setCreateBy(SecurityUtils.getUsername());
        return aiCharacterTemplateMapper.insertAiCharacterTemplate(aiCharacterTemplate);
    }
    
    /**
     * 修改 AI 角色模板信息
     * 
     * @param aiCharacterTemplate AI 角色模板信息
     * @return 结果
     */
    @Override
    public int updateAiCharacterTemplate(AiCharacterTemplate aiCharacterTemplate) {
        aiCharacterTemplate.setUpdateBy(SecurityUtils.getUsername());
        return aiCharacterTemplateMapper.updateAiCharacterTemplate(aiCharacterTemplate);
    }
    
    /**
     * 通过角色ID删除 AI 角色模板信息
     * 
     * @param id AI 角色模板 ID
     * @return 结果
     */
    @Override
    public int deleteAiCharacterTemplateById(Long id) {
        return aiCharacterTemplateMapper.deleteAiCharacterTemplateById(id);
    }
    
    /**
     * 批量删除 AI 角色模板信息
     * 
     * @param ids 需要删除的 AI 角色模板 ID 数组
     * @return 结果
     */
    @Override
    public int deleteAiCharacterTemplateByIds(Long[] ids) {
        return aiCharacterTemplateMapper.deleteAiCharacterTemplateByIds(ids);
    }
    
    /**
     * 复制AI角色模板
     * 
     * @param id 要复制的角色ID
     * @return 结果
     */
    @Override
    public int copyAiCharacterTemplate(Long id) {
        // 获取原角色模板信息
        AiCharacterTemplate originalTemplate = aiCharacterTemplateMapper.selectAiCharacterTemplateById(id);
        if (originalTemplate == null) {
            return 0;
        }
        
        // 创建新角色模板，复制原模板的信息
        AiCharacterTemplate newTemplate = new AiCharacterTemplate();
        newTemplate.setName(originalTemplate.getName() + "_副本");
        newTemplate.setDescription(originalTemplate.getDescription());
        newTemplate.setPersonality(originalTemplate.getPersonality());
        newTemplate.setAvatarUrl(originalTemplate.getAvatarUrl());
        newTemplate.setCharacterType(originalTemplate.getCharacterType());
        newTemplate.setCreatorId(SecurityUtils.getUserId());
        newTemplate.setIsActive(originalTemplate.getIsActive());
        newTemplate.setIsPublic(originalTemplate.getIsPublic());
        newTemplate.setGender(originalTemplate.getGender());
        newTemplate.setAge(originalTemplate.getAge());
        newTemplate.setCreateBy(SecurityUtils.getUsername());
        
        return aiCharacterTemplateMapper.insertAiCharacterTemplate(newTemplate);
    }
} 