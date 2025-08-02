package com.vibetempt.candy.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.vibetempt.candy.domain.AiCharacter;
import com.vibetempt.candy.service.AiCharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * AI 角色 Controller
 * 
 * @author vibetempt
 */
@RestController
@RequestMapping("/api/character")
public class AiCharacterController extends BaseController {
    
    @Autowired
    private AiCharacterService aiCharacterService;
    
    /**
     * 查询 AI 角色列表
     */
    @PreAuthorize("@ss.hasPermi('candy:character:list')")
    @GetMapping("/list")
    public TableDataInfo list(AiCharacter aiCharacter) {
        startPage();
        List<AiCharacter> list = aiCharacterService.getCharacterList(aiCharacter);
        return getDataTable(list);
    }
    
    /**
     * 获取角色详情
     */
    @PreAuthorize("@ss.hasPermi('candy:character:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(aiCharacterService.getCharacterById(id));
    }
    
    /**
     * 新增 AI 角色
     */
    @PreAuthorize("@ss.hasPermi('candy:character:add')")
    @Log(title = "AI 角色管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody AiCharacter aiCharacter) {
        aiCharacter.setCreatorId(SecurityUtils.getUserId());
        aiCharacter.setCreateBy(SecurityUtils.getUsername());
        AiCharacter result = aiCharacterService.createCharacter(aiCharacter);
        return success(result);
    }
    
    /**
     * 修改 AI 角色
     */
    @PreAuthorize("@ss.hasPermi('candy:character:edit')")
    @Log(title = "AI 角色管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody AiCharacter aiCharacter) {
        aiCharacter.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(aiCharacterService.updateCharacter(aiCharacter));
    }
    
    /**
     * 删除 AI 角色
     */
    @PreAuthorize("@ss.hasPermi('candy:character:remove')")
    @Log(title = "AI 角色管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(aiCharacterService.deleteCharacter(ids[0]));
    }
    
    /**
     * 复制角色
     */
    @PreAuthorize("@ss.hasPermi('candy:character:add')")
    @Log(title = "AI 角色管理", businessType = BusinessType.INSERT)
    @PostMapping("/copy/{id}")
    public AjaxResult copyCharacter(@PathVariable Long id) {
        Long userId = SecurityUtils.getUserId();
        AiCharacter result = aiCharacterService.copyCharacter(id, userId);
        return success(result);
    }
    
    /**
     * 获取用户角色
     */
    @GetMapping("/user")
    public AjaxResult getUserCharacters() {
        Long userId = SecurityUtils.getUserId();
        List<AiCharacter> list = aiCharacterService.getUserCharacters(userId);
        return success(list);
    }
    
    /**
     * 获取系统角色
     */
    @GetMapping("/system")
    public AjaxResult getSystemCharacters() {
        List<AiCharacter> list = aiCharacterService.getSystemCharacters();
        return success(list);
    }
    
    /**
     * 导出 AI 角色列表
     */
    @Log(title = "AI 角色管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('candy:character:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, AiCharacter aiCharacter) {
        List<AiCharacter> list = aiCharacterService.getCharacterList(aiCharacter);
        ExcelUtil<AiCharacter> util = new ExcelUtil<AiCharacter>(AiCharacter.class);
        util.exportExcel(response, list, "AI角色数据");
    }
    
    // 保留原有方法以兼容现有代码
    /**
     * 查询 AI 角色列表 (兼容旧接口)
     */
    @PreAuthorize("@ss.hasPermi('candy:character:list')")
    @GetMapping("/list/old")
    public TableDataInfo listOld(AiCharacter aiCharacter) {
        startPage();
        List<AiCharacter> list = aiCharacterService.selectAiCharacterList(aiCharacter);
        return getDataTable(list);
    }
    
    /**
     * 根据 ID 获取 AI 角色详细信息 (兼容旧接口)
     */
    @PreAuthorize("@ss.hasPermi('candy:character:query')")
    @GetMapping(value = "/old/{characterId}")
    public AjaxResult getInfoOld(@PathVariable("characterId") Long characterId) {
        return success(aiCharacterService.selectAiCharacterById(characterId));
    }
    
    /**
     * 新增 AI 角色 (兼容旧接口)
     */
    @PreAuthorize("@ss.hasPermi('candy:character:add')")
    @Log(title = "AI 角色管理", businessType = BusinessType.INSERT)
    @PostMapping("/old")
    public AjaxResult addOld(@RequestBody AiCharacter aiCharacter) {
        aiCharacter.setCreateBy(getUsername());
        return toAjax(aiCharacterService.insertAiCharacter(aiCharacter));
    }
    
    /**
     * 修改 AI 角色 (兼容旧接口)
     */
    @PreAuthorize("@ss.hasPermi('candy:character:edit')")
    @Log(title = "AI 角色管理", businessType = BusinessType.UPDATE)
    @PutMapping("/old")
    public AjaxResult editOld(@RequestBody AiCharacter aiCharacter) {
        aiCharacter.setUpdateBy(getUsername());
        return toAjax(aiCharacterService.updateAiCharacter(aiCharacter));
    }
    
    /**
     * 删除 AI 角色 (兼容旧接口)
     */
    @PreAuthorize("@ss.hasPermi('candy:character:remove')")
    @Log(title = "AI 角色管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/old/{characterIds}")
    public AjaxResult removeOld(@PathVariable Long[] characterIds) {
        return toAjax(aiCharacterService.deleteAiCharacterByIds(characterIds));
    }
} 