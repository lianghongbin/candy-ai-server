package com.vibetempt.candy.service;

import com.vibetempt.candy.domain.AiCharacter;
import com.vibetempt.candy.domain.AiCharacterCreationSession;
import com.vibetempt.candy.domain.dto.CharacterCreationConfig;
import com.vibetempt.candy.domain.dto.CreationProgress;
import com.vibetempt.candy.domain.dto.StepOptions;

import java.util.Map;

/**
 * 角色创建服务接口
 * 
 * @author vibetempt
 */
public interface CharacterCreationService {
    
    /**
     * 获取角色创建流程配置
     */
    CharacterCreationConfig getCreationConfig();
    
    /**
     * 开始创建角色
     */
    AiCharacterCreationSession startCreation(Long userId);
    
    /**
     * 获取当前步骤的选项
     */
    StepOptions getStepOptions(String stepCode, AiCharacterCreationSession session);
    
    /**
     * 保存步骤选择
     */
    void saveStepSelection(String stepCode, Map<String, Object> selections, AiCharacterCreationSession session);
    
    /**
     * 获取下一步骤
     */
    String getNextStep(String currentStep, AiCharacterCreationSession session);
    
    /**
     * 获取上一步骤
     */
    String getPreviousStep(String currentStep, AiCharacterCreationSession session);
    
    /**
     * 完成角色创建
     */
    AiCharacter completeCreation(AiCharacterCreationSession session);
    
    /**
     * 获取创建进度
     */
    CreationProgress getProgress(AiCharacterCreationSession session);
    
    /**
     * 取消创建
     */
    void cancelCreation(AiCharacterCreationSession session);
    
    /**
     * 根据会话ID获取会话
     */
    AiCharacterCreationSession getSessionBySessionId(String sessionId);
    
    /**
     * 根据用户ID获取活跃会话
     */
    AiCharacterCreationSession getActiveSessionByUserId(Long userId);
} 