package com.vibetempt.candy.domain.enums;

/**
 * 角色创建步骤枚举
 * 
 * @author vibetempt
 */
public enum CharacterCreationStep {
    
    STEP_1_STYLE("style", "选择风格", true),
    STEP_2_BASIC_ATTRIBUTES("basic_attributes", "基础属性", true),
    STEP_3_HAIR("hair", "发型发色", true),
    STEP_4_BODY("body", "体型特征", true),
    STEP_5_PERSONALITY("personality", "个性选择", true),
    STEP_6_OCCUPATION_HOBBIES("occupation_hobbies", "职业爱好", false),
    STEP_7_RELATIONSHIP("relationship", "关系选择", true),
    STEP_8_SUMMARY("summary", "确认信息", true);
    
    private final String code;
    private final String name;
    private final boolean required;
    
    CharacterCreationStep(String code, String name, boolean required) {
        this.code = code;
        this.name = name;
        this.required = required;
    }
    
    public String getCode() {
        return code;
    }
    
    public String getName() {
        return name;
    }
    
    public boolean isRequired() {
        return required;
    }
    
    /**
     * 根据代码获取步骤
     */
    public static CharacterCreationStep getByCode(String code) {
        for (CharacterCreationStep step : values()) {
            if (step.getCode().equals(code)) {
                return step;
            }
        }
        return null;
    }
    
    /**
     * 获取下一步骤
     */
    public CharacterCreationStep getNextStep() {
        CharacterCreationStep[] steps = values();
        for (int i = 0; i < steps.length - 1; i++) {
            if (steps[i] == this) {
                return steps[i + 1];
            }
        }
        return null;
    }
    
    /**
     * 获取上一步骤
     */
    public CharacterCreationStep getPreviousStep() {
        CharacterCreationStep[] steps = values();
        for (int i = 1; i < steps.length; i++) {
            if (steps[i] == this) {
                return steps[i - 1];
            }
        }
        return null;
    }
} 