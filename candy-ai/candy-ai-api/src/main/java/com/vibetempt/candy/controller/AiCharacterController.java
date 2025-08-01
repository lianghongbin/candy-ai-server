package com.vibetempt.candy.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.vibetempt.candy.domain.AiCharacter;
import com.vibetempt.candy.service.AiCharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * AI 角色 Controller
 * 
 * @author vibetempt
 */
@RestController
@RequestMapping("/candy/character")
public class AiCharacterController extends BaseController {
    
    @Autowired
    private IAiCharacterService aiCharacterService;
    
    /**
     * 查询 AI 角色列表
     */
    @PreAuthorize("@ss.hasPermi('candy:character:list')")
    @GetMapping("/list")
    public TableDataInfo list(AiCharacter aiCharacter) {
        startPage();
        List<AiCharacter> list = aiCharacterService.selectAiCharacterList(aiCharacter);
        return getDataTable(list);
    }
    
    /**
     * 获取我的角色实例列表
     */
    @GetMapping("/my")
    public TableDataInfo myCharacters(AiCharacter aiCharacter) {
        startPage();
        List<AiCharacter> list = aiCharacterService.selectMyCharacterList(aiCharacter);
        return getDataTable(list);
    }
    
    /**
     * 导出 AI 角色列表
     */
    @Log(title = "AI 角色管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('candy:character:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, AiCharacter aiCharacter) {
        List<AiCharacter> list = aiCharacterService.selectAiCharacterList(aiCharacter);
        ExcelUtil<AiCharacter> util = new ExcelUtil<AiCharacter>(AiCharacter.class);
        util.exportExcel(response, list, "AI角色数据");
    }
    
    /**
     * 根据 ID 获取 AI 角色详细信息
     */
    @PreAuthorize("@ss.hasPermi('candy:character:query')")
    @GetMapping(value = "/{characterId}")
    public AjaxResult getInfo(@PathVariable("characterId") Long characterId) {
        return success(aiCharacterService.selectAiCharacterById(characterId));
    }
    
    /**
     * 新增 AI 角色
     */
    @PreAuthorize("@ss.hasPermi('candy:character:add')")
    @Log(title = "AI 角色管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AiCharacter aiCharacter) {
        aiCharacter.setCreateBy(getUsername());
        return toAjax(aiCharacterService.insertAiCharacter(aiCharacter));
    }
    
    /**
     * 修改 AI 角色
     */
    @PreAuthorize("@ss.hasPermi('candy:character:edit')")
    @Log(title = "AI 角色管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AiCharacter aiCharacter) {
        aiCharacter.setUpdateBy(getUsername());
        return toAjax(aiCharacterService.updateAiCharacter(aiCharacter));
    }
    
    /**
     * 删除 AI 角色
     */
    @PreAuthorize("@ss.hasPermi('candy:character:remove')")
    @Log(title = "AI 角色管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{characterIds}")
    public AjaxResult remove(@PathVariable Long[] characterIds) {
        return toAjax(aiCharacterService.deleteAiCharacterByIds(characterIds));
    }
    
    /**
     * 根据模板创建角色实例
     */
    @PostMapping("/create-from-template")
    public AjaxResult createFromTemplate(@RequestParam Long templateId, 
                                       @RequestParam String characterName) {
        return toAjax(aiCharacterService.createCharacterInstanceFromTemplate(
            templateId, characterName, getUserId()));
    }
} 