package com.vibetempt.candy.service.impl;

import com.ruoyi.common.utils.SecurityUtils;
import com.vibetempt.candy.domain.CharacterTemplate;
import com.vibetempt.candy.domain.mapper.CharacterTemplateMapper;
import com.vibetempt.candy.service.CharacterTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色模板 业务层处理
 * 
 * @author vibetempt
 */
@Service
public class CharacterTemplateServiceImpl implements CharacterTemplateService {
    
    @Autowired
    private CharacterTemplateMapper characterTemplateMapper;
    
    /**
     * 查询角色模板
     * 
     * @param templateId 角色模板主键
     * @return 角色模板
     */
    @Override
    public CharacterTemplate selectCharacterTemplateByTemplateId(Long templateId) {
        return characterTemplateMapper.selectCharacterTemplateByTemplateId(templateId);
    }
    
    /**
     * 根据条件分页查询角色模板列表
     * 
     * @param characterTemplate 角色模板
     * @return 角色模板集合
     */
    @Override
    public List<CharacterTemplate> selectCharacterTemplateList(CharacterTemplate characterTemplate) {
        return characterTemplateMapper.selectCharacterTemplateList(characterTemplate);
    }
    
    /**
     * 新增角色模板
     * 
     * @param characterTemplate 角色模板
     * @return 结果
     */
    @Override
    public int insertCharacterTemplate(CharacterTemplate characterTemplate) {
        return characterTemplateMapper.insertCharacterTemplate(characterTemplate);
    }
    
    /**
     * 修改角色模板
     * 
     * @param characterTemplate 角色模板
     * @return 结果
     */
    @Override
    public int updateCharacterTemplate(CharacterTemplate characterTemplate) {
        return characterTemplateMapper.updateCharacterTemplate(characterTemplate);
    }
    
    /**
     * 批量删除角色模板
     * 
     * @param templateIds 需要删除的角色模板主键集合
     * @return 结果
     */
    @Override
    public int deleteCharacterTemplateByTemplateIds(Long[] templateIds) {
        return characterTemplateMapper.deleteCharacterTemplateByTemplateIds(templateIds);
    }
    
    /**
     * 删除角色模板信息
     * 
     * @param templateId 角色模板主键
     * @return 结果
     */
    @Override
    public int deleteCharacterTemplateByTemplateId(Long templateId) {
        return characterTemplateMapper.deleteCharacterTemplateByTemplateId(templateId);
    }
    
    /**
     * 查询可用的角色模板列表
     * 
     * @return 角色模板集合
     */
    @Override
    public List<CharacterTemplate> selectAvailableTemplateList() {
        CharacterTemplate query = new CharacterTemplate();
        query.setStatus("0"); // 只查询正常状态的模板
        return characterTemplateMapper.selectCharacterTemplateList(query);
    }
    
    /**
     * 根据用户ID查询可用的角色模板列表（系统模板 + 用户创建的模板）
     * 
     * @param userId 用户ID
     * @return 角色模板集合
     */
    @Override
    public List<CharacterTemplate> selectAvailableTemplatesForUser(Long userId) {
        // 这里需要根据业务逻辑查询系统模板和用户创建的模板
        // 可以通过 Mapper 的 SQL 来实现
        return characterTemplateMapper.selectAvailableTemplatesForUser(userId);
    }
    
    /**
     * 根据创建者查询角色模板列表
     * 
     * @param createBy 创建者
     * @return 角色模板集合
     */
    @Override
    public List<CharacterTemplate> selectTemplateListByCreator(String createBy) {
        CharacterTemplate query = new CharacterTemplate();
        query.setCreateBy(createBy);
        return characterTemplateMapper.selectCharacterTemplateList(query);
    }
    
    /**
     * 根据模板类型查询角色模板列表
     * 
     * @param templateType 模板类型
     * @return 角色模板集合
     */
    @Override
    public List<CharacterTemplate> selectTemplateListByType(String templateType) {
        CharacterTemplate query = new CharacterTemplate();
        query.setTemplateType(templateType);
        return characterTemplateMapper.selectCharacterTemplateList(query);
    }
} 