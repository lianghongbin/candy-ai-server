package com.vibetempt.candy.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.SecurityUtils;
import com.vibetempt.candy.domain.AiCharacter;
import com.vibetempt.candy.service.AiCharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AI角色统计 Controller
 * 
 * @author vibetempt
 */
@RestController
@RequestMapping("/api/character/stats")
public class AiCharacterStatsController extends BaseController {
    
    @Autowired
    private AiCharacterService aiCharacterService;
    
    /**
     * 获取角色统计概览
     */
    @GetMapping("/overview")
    public AjaxResult getOverview() {
        Long userId = SecurityUtils.getUserId();
        
        // 获取用户角色列表
        List<AiCharacter> userCharacters = aiCharacterService.getUserCharacters(userId);
        
        // 获取系统角色列表
        List<AiCharacter> systemCharacters = aiCharacterService.getSystemCharacters();
        
        // 计算统计信息
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUserCharacters", userCharacters.size());
        stats.put("totalSystemCharacters", systemCharacters.size());
        stats.put("totalCharacters", userCharacters.size() + systemCharacters.size());
        
        // 按风格统计
        Map<String, Long> styleStats = userCharacters.stream()
            .collect(java.util.stream.Collectors.groupingBy(
                character -> character.getStyle() != null ? character.getStyle() : "unknown",
                java.util.stream.Collectors.counting()
            ));
        stats.put("styleStats", styleStats);
        
        // 按个性统计
        Map<String, Long> personalityStats = userCharacters.stream()
            .collect(java.util.stream.Collectors.groupingBy(
                character -> character.getPersonality() != null ? character.getPersonality() : "unknown",
                java.util.stream.Collectors.counting()
            ));
        stats.put("personalityStats", personalityStats);
        
        // 计算总使用量
        int totalUsers = userCharacters.stream()
            .mapToInt(character -> character.getTotalUsers() != null ? character.getTotalUsers() : 0)
            .sum();
        int totalConversations = userCharacters.stream()
            .mapToInt(character -> character.getTotalConversations() != null ? character.getTotalConversations() : 0)
            .sum();
        int totalMessages = userCharacters.stream()
            .mapToInt(character -> character.getTotalMessages() != null ? character.getTotalMessages() : 0)
            .sum();
        
        stats.put("totalUsers", totalUsers);
        stats.put("totalConversations", totalConversations);
        stats.put("totalMessages", totalMessages);
        
        return success(stats);
    }
    
    /**
     * 获取角色使用排行
     */
    @GetMapping("/ranking")
    public AjaxResult getRanking(@RequestParam(defaultValue = "10") int limit) {
        Long userId = SecurityUtils.getUserId();
        List<AiCharacter> userCharacters = aiCharacterService.getUserCharacters(userId);
        
        // 按使用量排序
        List<AiCharacter> ranking = userCharacters.stream()
            .sorted((c1, c2) -> {
                int usage1 = c1.getTotalUsers() != null ? c1.getTotalUsers() : 0;
                int usage2 = c2.getTotalUsers() != null ? c2.getTotalUsers() : 0;
                return Integer.compare(usage2, usage1); // 降序
            })
            .limit(limit)
            .toList();
        
        return success(ranking);
    }
    
    /**
     * 获取角色风格分布
     */
    @GetMapping("/style-distribution")
    public AjaxResult getStyleDistribution() {
        Long userId = SecurityUtils.getUserId();
        List<AiCharacter> userCharacters = aiCharacterService.getUserCharacters(userId);
        
        Map<String, Long> distribution = userCharacters.stream()
            .collect(java.util.stream.Collectors.groupingBy(
                character -> character.getStyle() != null ? character.getStyle() : "unknown",
                java.util.stream.Collectors.counting()
            ));
        
        return success(distribution);
    }
    
    /**
     * 获取角色个性分布
     */
    @GetMapping("/personality-distribution")
    public AjaxResult getPersonalityDistribution() {
        Long userId = SecurityUtils.getUserId();
        List<AiCharacter> userCharacters = aiCharacterService.getUserCharacters(userId);
        
        Map<String, Long> distribution = userCharacters.stream()
            .collect(java.util.stream.Collectors.groupingBy(
                character -> character.getPersonality() != null ? character.getPersonality() : "unknown",
                java.util.stream.Collectors.counting()
            ));
        
        return success(distribution);
    }
    
    /**
     * 获取角色创建趋势
     */
    @GetMapping("/creation-trend")
    public AjaxResult getCreationTrend(@RequestParam(defaultValue = "30") int days) {
        Long userId = SecurityUtils.getUserId();
        List<AiCharacter> userCharacters = aiCharacterService.getUserCharacters(userId);
        
        // 这里可以实现按时间统计创建趋势的逻辑
        // 暂时返回简单的统计信息
        Map<String, Object> trend = new HashMap<>();
        trend.put("totalCreated", userCharacters.size());
        trend.put("days", days);
        trend.put("averagePerDay", userCharacters.size() / (double) days);
        
        return success(trend);
    }
} 