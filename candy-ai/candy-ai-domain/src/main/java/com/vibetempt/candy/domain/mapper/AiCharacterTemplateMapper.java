package com.vibetempt.candy.domain.mapper;

import com.vibetempt.candy.domain.AiCharacterTemplate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * AI 角色模板 数据层
 * 
 * @author candy-ai
 */
@Mapper
public interface AiCharacterTemplateMapper {
    
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
    int deleteAiCharacterTemplateByIds(@Param("ids") Long[] ids);
} 