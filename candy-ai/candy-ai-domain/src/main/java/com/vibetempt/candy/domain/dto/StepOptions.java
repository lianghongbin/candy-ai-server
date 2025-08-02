package com.vibetempt.candy.domain.dto;

import lombok.Data;
import java.util.List;
import java.util.Map;

/**
 * 步骤选项
 * 
 * @author vibetempt
 */
@Data
public class StepOptions {
    
    /** 步骤代码 */
    private String stepCode;
    
    /** 步骤标题 */
    private String title;
    
    /** 步骤描述 */
    private String description;
    
    /** 当前选择 */
    private Map<String, Object> currentSelections;
    
    /** 选项列表 */
    private List<CharacterCreationConfig.OptionConfig> options;
    
    /** 分组配置 */
    private List<CharacterCreationConfig.SectionConfig> sections;
    
    /** 是否必填 */
    private boolean required;
    
    /** 最大选择数量 */
    private Integer maxSelect;
} 