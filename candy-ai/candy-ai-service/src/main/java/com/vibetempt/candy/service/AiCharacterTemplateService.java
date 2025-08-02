package com.vibetempt.candy.service;

import com.vibetempt.candy.domain.AiCharacterTemplate;
import java.util.List;

/**
 * AI 角色模板 业务层
 * 
 * @author candy-ai
 */
public interface AiCharacterTemplateService {
    
    /**
     * 根据条件分页查询 AI 角色模板列表
     * 
     * @param aiCharacterTemplate AI 角色模板信息
     * @return AI 角色模板集合
     */
    List<AiCharacterTemplate> selectAiCharacterTemplateList(AiCharacterTemplate aiCharacterTemplate);
    
    /**
     * 通过角色ID查询 AI 角色模板
     * 
     * @param id AI 角色模板 ID
     * @return AI 角色模板信息
     */
    AiCharacterTemplate selectAiCharacterTemplateById(Long id);
    
    /**
     * 新增 AI 角色模板信息
     * 
     * @param aiCharacterTemplate AI 角色模板信息
     * @return 结果
     */
    int insertAiCharacterTemplate(AiCharacterTemplate aiCharacterTemplate);
    
    /**
     * 修改 AI 角色模板信息
     * 
     * @param aiCharacterTemplate AI 角色模板信息
     * @return 结果
     */
    int updateAiCharacterTemplate(AiCharacterTemplate aiCharacterTemplate);
    
    /**
     * 通过角色ID删除 AI 角色模板信息
     * 
     * @param id AI 角色模板 ID
     * @return 结果
     */
    int deleteAiCharacterTemplateById(Long id);
    
    /**
     * 批量删除 AI 角色模板信息
     * 
     * @param ids 需要删除的 AI 角色模板 ID 数组
     * @return 结果
     */
    int deleteAiCharacterTemplateByIds(Long[] ids);
    
    /**
     * 复制AI角色模板
     * 
     * @param id 要复制的角色ID
     * @return 结果
     */
    int copyAiCharacterTemplate(Long id);
} 