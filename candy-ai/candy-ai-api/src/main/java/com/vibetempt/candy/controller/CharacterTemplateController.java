package com.vibetempt.candy.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.vibetempt.candy.domain.CharacterTemplate;
import com.vibetempt.candy.service.CharacterTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 角色模板 Controller
 * 
 * @author vibetempt
 */
@RestController
@RequestMapping("/candy/template")
public class CharacterTemplateController extends BaseController {
    
    @Autowired
    private CharacterTemplateService characterTemplateService;
    
    /**
     * 查询角色模板列表
     */
    @PreAuthorize("@ss.hasPermi('candy:template:list')")
    @GetMapping("/list")
    public TableDataInfo list(CharacterTemplate characterTemplate) {
        startPage();
        List<CharacterTemplate> list = characterTemplateService.selectCharacterTemplateList(characterTemplate);
        return getDataTable(list);
    }
    
    /**
     * 查询可用的角色模板列表
     */
    @GetMapping("/available")
    public AjaxResult availableTemplates() {
        List<CharacterTemplate> list = characterTemplateService.selectAvailableTemplateList();
        return success(list);
    }
    
    /**
     * 查询我创建的角色模板列表
     */
    @GetMapping("/my")
    public TableDataInfo myTemplates(CharacterTemplate characterTemplate) {
        startPage();
        characterTemplate.setCreateBy(getUsername());
        List<CharacterTemplate> list = characterTemplateService.selectCharacterTemplateList(characterTemplate);
        return getDataTable(list);
    }
    
    /**
     * 查询用户可用的角色模板列表（系统模板 + 用户创建的模板）
     */
    @GetMapping("/available-for-user")
    public AjaxResult availableTemplatesForUser() {
        List<CharacterTemplate> list = characterTemplateService.selectAvailableTemplatesForUser(getUserId());
        return success(list);
    }
    
    /**
     * 导出角色模板列表
     */
    @Log(title = "角色模板管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('candy:template:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, CharacterTemplate characterTemplate) {
        List<CharacterTemplate> list = characterTemplateService.selectCharacterTemplateList(characterTemplate);
        ExcelUtil<CharacterTemplate> util = new ExcelUtil<CharacterTemplate>(CharacterTemplate.class);
        util.exportExcel(response, list, "角色模板数据");
    }
    
    /**
     * 根据 ID 获取角色模板详细信息
     */
    @PreAuthorize("@ss.hasPermi('candy:template:query')")
    @GetMapping(value = "/{templateId}")
    public AjaxResult getInfo(@PathVariable("templateId") Long templateId) {
        return success(characterTemplateService.selectCharacterTemplateByTemplateId(templateId));
    }
    
    /**
     * 新增角色模板
     */
    @PreAuthorize("@ss.hasPermi('candy:template:add')")
    @Log(title = "角色模板管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CharacterTemplate characterTemplate) {
        characterTemplate.setCreateBy(getUsername());
        return toAjax(characterTemplateService.insertCharacterTemplate(characterTemplate));
    }
    
    /**
     * 修改角色模板
     */
    @PreAuthorize("@ss.hasPermi('candy:template:edit')")
    @Log(title = "角色模板管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CharacterTemplate characterTemplate) {
        characterTemplate.setUpdateBy(getUsername());
        return toAjax(characterTemplateService.updateCharacterTemplate(characterTemplate));
    }
    
    /**
     * 删除角色模板
     */
    @PreAuthorize("@ss.hasPermi('candy:template:remove')")
    @Log(title = "角色模板管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{templateIds}")
    public AjaxResult remove(@PathVariable Long[] templateIds) {
        return toAjax(characterTemplateService.deleteCharacterTemplateByTemplateIds(templateIds));
    }
} 