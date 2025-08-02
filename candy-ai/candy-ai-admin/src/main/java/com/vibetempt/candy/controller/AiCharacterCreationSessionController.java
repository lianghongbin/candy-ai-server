package com.vibetempt.candy.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.vibetempt.candy.domain.AiCharacterCreationSession;
import com.vibetempt.candy.service.CharacterCreationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * AI角色创建会话管理 Controller
 * 
 * @author vibetempt
 */
@RestController
@RequestMapping("/api/character/session")
public class AiCharacterCreationSessionController extends BaseController {
    
    @Autowired
    private CharacterCreationService characterCreationService;
    
    /**
     * 查询角色创建会话列表
     */
    @PreAuthorize("@ss.hasPermi('candy:session:list')")
    @GetMapping("/list")
    public TableDataInfo list(AiCharacterCreationSession session) {
        startPage();
        // 这里需要添加会话列表查询方法到服务层
        // List<AiCharacterCreationSession> list = sessionService.selectSessionList(session);
        // return getDataTable(list);
        return getDataTable(null);
    }
    
    /**
     * 获取会话详情
     */
    @PreAuthorize("@ss.hasPermi('candy:session:query')")
    @GetMapping(value = "/{sessionId}")
    public AjaxResult getInfo(@PathVariable("sessionId") String sessionId) {
        AiCharacterCreationSession session = characterCreationService.getSessionBySessionId(sessionId);
        return success(session);
    }
    
    /**
     * 获取当前用户的活跃会话
     */
    @GetMapping("/active")
    public AjaxResult getActiveSession() {
        Long userId = SecurityUtils.getUserId();
        AiCharacterCreationSession session = characterCreationService.getActiveSessionByUserId(userId);
        return success(session);
    }
    
    /**
     * 取消会话
     */
    @PreAuthorize("@ss.hasPermi('candy:session:cancel')")
    @Log(title = "角色创建会话管理", businessType = BusinessType.UPDATE)
    @PostMapping("/cancel/{sessionId}")
    public AjaxResult cancelSession(@PathVariable String sessionId) {
        AiCharacterCreationSession session = characterCreationService.getSessionBySessionId(sessionId);
        if (session == null) {
            return error("会话不存在");
        }
        
        // 检查权限：只能取消自己的会话
        if (!session.getUserId().equals(SecurityUtils.getUserId())) {
            return error("只能取消自己的会话");
        }
        
        characterCreationService.cancelCreation(session);
        return success();
    }
    
    /**
     * 删除会话
     */
    @PreAuthorize("@ss.hasPermi('candy:session:remove')")
    @Log(title = "角色创建会话管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{sessionIds}")
    public AjaxResult remove(@PathVariable String[] sessionIds) {
        // 这里需要添加会话删除方法到服务层
        // return toAjax(sessionService.deleteSessionByIds(sessionIds));
        return success();
    }
    
    /**
     * 清理过期会话
     */
    @PreAuthorize("@ss.hasPermi('candy:session:clean')")
    @Log(title = "角色创建会话管理", businessType = BusinessType.CLEAN)
    @PostMapping("/clean")
    public AjaxResult cleanExpiredSessions() {
        // 这里需要添加清理过期会话方法到服务层
        // int result = sessionService.cleanExpiredSessions();
        // return toAjax(result);
        return success();
    }
} 