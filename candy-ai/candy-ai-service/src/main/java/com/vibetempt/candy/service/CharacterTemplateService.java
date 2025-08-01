package com.vibetempt.candy.service;

import com.vibetempt.candy.domain.CharacterTemplate;
import java.util.List;

/**
 * 角色模板 业务层
 * 
 * @author vibetempt
 */
public interface CharacterTemplateService {
    
    /**
     * 通过模板ID查询角色模板
     * 
     * @param templateId 角色模板主键
     * @return 角色模板
     */
    CharacterTemplate selectCharacterTemplateByTemplateId(Long templateId);
    
    /**
     * 根据条件分页查询角色模板列表
     * 
     * @param characterTemplate 角色模板
     * @return 角色模板集合
     */
    List<CharacterTemplate> selectCharacterTemplateList(CharacterTemplate characterTemplate);
    
    /**
     * 新增角色模板信息
     * 
     * @param characterTemplate 角色模板
     * @return 结果
     */
    int insertCharacterTemplate(CharacterTemplate characterTemplate);
    
    /**
     * 修改角色模板信息
     * 
     * @param characterTemplate 角色模板
     * @return 结果
     */
    int updateCharacterTemplate(CharacterTemplate characterTemplate);
    
    /**
     * 批量删除角色模板信息
     * 
     * @param templateIds 需要删除的角色模板主键集合
     * @return 结果
     */
    int deleteCharacterTemplateByTemplateIds(Long[] templateIds);
    
    /**
     * 通过模板ID删除角色模板信息
     * 
     * @param templateId 角色模板主键
     * @return 结果
     */
    int deleteCharacterTemplateByTemplateId(Long templateId);
    
    /**
     * 查询可用的角色模板列表
     * 
     * @return 角色模板集合
     */
    List<CharacterTemplate> selectAvailableTemplateList();
    
    /**
     * 根据用户ID查询可用的角色模板列表（系统模板 + 用户创建的模板）
     * 
     * @param userId 用户ID
     * @return 角色模板集合
     */
    List<CharacterTemplate> selectAvailableTemplatesForUser(Long userId);
    
    /**
     * 根据创建者查询角色模板列表
     * 
     * @param createBy 创建者
     * @return 角色模板集合
     */
    List<CharacterTemplate> selectTemplateListByCreator(String createBy);
    
    /**
     * 根据模板类型查询角色模板列表
     * 
     * @param templateType 模板类型
     * @return 角色模板集合
     */
    List<CharacterTemplate> selectTemplateListByType(String templateType);
} 