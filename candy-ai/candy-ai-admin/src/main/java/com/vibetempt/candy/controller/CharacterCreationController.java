package com.vibetempt.candy.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.vibetempt.candy.domain.AiCharacter;
import com.vibetempt.candy.domain.AiCharacterCreationSession;
import com.vibetempt.candy.domain.dto.CreationProgress;
import com.vibetempt.candy.domain.dto.StepOptions;
import com.vibetempt.candy.service.CharacterCreationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 角色创建控制器
 * 
 * @author vibetempt
 */
@RestController
@RequestMapping("/api/character/creation")
public class CharacterCreationController extends BaseController {
    
    @Autowired
    private CharacterCreationService characterCreationService;
    
    /**
     * 获取创建流程配置
     */
    @GetMapping("/config")
    public AjaxResult getCreationConfig() {
        return success(characterCreationService.getCreationConfig());
    }
    
    /**
     * 开始创建角色
     */
    @PostMapping("/start")
    public AjaxResult startCreation() {
        Long userId = SecurityUtils.getUserId();
        AiCharacterCreationSession session = characterCreationService.startCreation(userId);
        return success(session);
    }
    
    /**
     * 获取步骤选项
     */
    @GetMapping("/step/{stepCode}")
    public AjaxResult getStepOptions(@PathVariable String stepCode) {
        Long userId = SecurityUtils.getUserId();
        AiCharacterCreationSession session = characterCreationService.getActiveSessionByUserId(userId);
        if (session == null) {
            return error("没有活跃的创建会话，请先开始创建");
        }
        
        StepOptions stepOptions = characterCreationService.getStepOptions(stepCode, session);
        return success(stepOptions);
    }
    
    /**
     * 保存步骤选择
     */
    @PostMapping("/step/{stepCode}")
    public AjaxResult saveStepSelection(@PathVariable String stepCode, @RequestBody Map<String, Object> selections) {
        Long userId = SecurityUtils.getUserId();
        AiCharacterCreationSession session = characterCreationService.getActiveSessionByUserId(userId);
        if (session == null) {
            return error("没有活跃的创建会话，请先开始创建");
        }
        
        characterCreationService.saveStepSelection(stepCode, selections, session);
        return success();
    }
    
    /**
     * 获取创建进度
     */
    @GetMapping("/progress")
    public AjaxResult getProgress() {
        Long userId = SecurityUtils.getUserId();
        AiCharacterCreationSession session = characterCreationService.getActiveSessionByUserId(userId);
        if (session == null) {
            return error("没有活跃的创建会话，请先开始创建");
        }
        
        CreationProgress progress = characterCreationService.getProgress(session);
        return success(progress);
    }
    
    /**
     * 获取下一步骤
     */
    @GetMapping("/next")
    public AjaxResult getNextStep() {
        Long userId = SecurityUtils.getUserId();
        AiCharacterCreationSession session = characterCreationService.getActiveSessionByUserId(userId);
        if (session == null) {
            return error("没有活跃的创建会话，请先开始创建");
        }
        
        String nextStep = characterCreationService.getNextStep(session.getCurrentStep(), session);
        return success(nextStep);
    }
    
    /**
     * 获取上一步骤
     */
    @GetMapping("/previous")
    public AjaxResult getPreviousStep() {
        Long userId = SecurityUtils.getUserId();
        AiCharacterCreationSession session = characterCreationService.getActiveSessionByUserId(userId);
        if (session == null) {
            return error("没有活跃的创建会话，请先开始创建");
        }
        
        String previousStep = characterCreationService.getPreviousStep(session.getCurrentStep(), session);
        return success(previousStep);
    }
    
    /**
     * 完成创建
     */
    @PostMapping("/complete")
    public AjaxResult completeCreation() {
        Long userId = SecurityUtils.getUserId();
        AiCharacterCreationSession session = characterCreationService.getActiveSessionByUserId(userId);
        if (session == null) {
            return error("没有活跃的创建会话，请先开始创建");
        }
        
        AiCharacter character = characterCreationService.completeCreation(session);
        return success(character);
    }
    
    /**
     * 取消创建
     */
    @PostMapping("/cancel")
    public AjaxResult cancelCreation() {
        Long userId = SecurityUtils.getUserId();
        AiCharacterCreationSession session = characterCreationService.getActiveSessionByUserId(userId);
        if (session == null) {
            return error("没有活跃的创建会话，请先开始创建");
        }
        
        characterCreationService.cancelCreation(session);
        return success();
    }
    
    /**
     * 获取当前会话
     */
    @GetMapping("/session")
    public AjaxResult getCurrentSession() {
        Long userId = SecurityUtils.getUserId();
        AiCharacterCreationSession session = characterCreationService.getActiveSessionByUserId(userId);
        return success(session);
    }
} 