package com.vibetempt.candy.domain.mapper;

import com.vibetempt.candy.domain.CharacterTemplate;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 角色模板 Mapper 接口
 * 
 * @author vibetempt
 */
@Mapper
public interface CharacterTemplateMapper {
    
    /**
     * 查询角色模板
     * 
     * @param templateId 角色模板主键
     * @return 角色模板
     */
    CharacterTemplate selectCharacterTemplateByTemplateId(Long templateId);
    
    /**
     * 查询角色模板列表
     * 
     * @param characterTemplate 角色模板
     * @return 角色模板集合
     */
    List<CharacterTemplate> selectCharacterTemplateList(CharacterTemplate characterTemplate);
    
    /**
     * 新增角色模板
     * 
     * @param characterTemplate 角色模板
     * @return 结果
     */
    int insertCharacterTemplate(CharacterTemplate characterTemplate);
    
    /**
     * 修改角色模板
     * 
     * @param characterTemplate 角色模板
     * @return 结果
     */
    int updateCharacterTemplate(CharacterTemplate characterTemplate);
    
    /**
     * 删除角色模板
     * 
     * @param templateId 角色模板主键
     * @return 结果
     */
    int deleteCharacterTemplateByTemplateId(Long templateId);
    
    /**
     * 批量删除角色模板
     * 
     * @param templateIds 需要删除的数据主键集合
     * @return 结果
     */
    int deleteCharacterTemplateByTemplateIds(Long[] templateIds);
    
    /**
     * 查询可用的角色模板列表
     * 
     * @return 角色模板集合
     */
    List<CharacterTemplate> selectAvailableTemplateList();
    
    /**
     * 查询用户可用的角色模板列表（系统模板 + 用户创建的模板）
     * 
     * @param userId 用户ID
     * @return 角色模板集合
     */
    List<CharacterTemplate> selectAvailableTemplatesForUser(Long userId);
} 