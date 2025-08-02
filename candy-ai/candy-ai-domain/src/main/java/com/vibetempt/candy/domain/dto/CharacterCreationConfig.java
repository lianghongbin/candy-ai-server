package com.vibetempt.candy.domain.dto;

import lombok.Data;
import java.util.List;
import java.util.Map;

/**
 * 角色创建配置
 * 
 * @author vibetempt
 */
@Data
public class CharacterCreationConfig {
    
    /** 步骤列表 */
    private List<StepConfig> steps;
    
    /** 步骤配置 */
    @Data
    public static class StepConfig {
        /** 步骤代码 */
        private String step;
        
        /** 步骤标题 */
        private String title;
        
        /** 步骤描述 */
        private String description;
        
        /** 是否必填 */
        private boolean required;
        
        /** 选项列表 */
        private List<OptionConfig> options;
        
        /** 分组配置 */
        private List<SectionConfig> sections;
    }
    
    /** 选项配置 */
    @Data
    public static class OptionConfig {
        /** 选项值 */
        private String value;
        
        /** 选项标签 */
        private String label;
        
        /** 选项描述 */
        private String description;
        
        /** 选项图标 */
        private String icon;
        
        /** 图片URL */
        private String imageUrl;
        
        /** 是否默认选中 */
        private boolean defaultSelected;
        
        /** 跳过的步骤 */
        private List<String> skipSteps;
        
        /** 是否锁定 */
        private boolean locked;
    }
    
    /** 分组配置 */
    @Data
    public static class SectionConfig {
        /** 分组标题 */
        private String title;
        
        /** 分组名称 */
        private String name;
        
        /** 分组描述 */
        private String description;
        
        /** 选项列表 */
        private List<OptionConfig> options;
        
        /** 最大选择数量 */
        private Integer maxSelect;
    }
} 