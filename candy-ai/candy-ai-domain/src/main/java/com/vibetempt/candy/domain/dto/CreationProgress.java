package com.vibetempt.candy.domain.dto;

import lombok.Data;
import java.util.List;
import java.util.Map;

/**
 * 创建进度
 * 
 * @author vibetempt
 */
@Data
public class CreationProgress {
    
    /** 会话ID */
    private String sessionId;
    
    /** 当前步骤 */
    private String currentStep;
    
    /** 总步骤数 */
    private int totalSteps;
    
    /** 已完成步骤数 */
    private int completedSteps;
    
    /** 进度百分比 */
    private int progressPercentage;
    
    /** 步骤列表 */
    private List<StepProgress> steps;
    
    /** 已选择的选项 */
    private Map<String, Object> selections;
    
    /** 步骤进度 */
    @Data
    public static class StepProgress {
        /** 步骤代码 */
        private String step;
        
        /** 步骤标题 */
        private String title;
        
        /** 步骤名称 */
        private String stepName;
        
        /** 步骤代码 */
        private String stepCode;
        
        /** 是否完成 */
        private boolean completed;
        
        /** 是否当前步骤 */
        private boolean current;
        
        /** 是否必填 */
        private boolean required;
        
        /** 是否跳过 */
        private boolean skipped;
    }
} 