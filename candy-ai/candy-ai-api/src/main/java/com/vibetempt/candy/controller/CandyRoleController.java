package com.vibetempt.candy.controller;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.vibetempt.candy.domain.AiCharacterTemplate;
import com.vibetempt.candy.service.AiCharacterTemplateService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * Candy AI 角色管理Controller
 * 
 * @author candy-ai
 */
@RestController
@RequestMapping("/candy/role")
public class CandyRoleController extends BaseController
{
    @Autowired
    private AiCharacterTemplateService aiCharacterTemplateService;

    /**
     * 查询Candy AI角色列表
     */
    @PreAuthorize("@ss.hasPermi('candy:role:list')")
    @GetMapping("/list")
    public TableDataInfo list(AiCharacterTemplate aiCharacterTemplate)
    {
        startPage();
        List<AiCharacterTemplate> list = aiCharacterTemplateService.selectAiCharacterTemplateList(aiCharacterTemplate);
        return getDataTable(list);
    }

    /**
     * 导出Candy AI角色列表
     */
    @PreAuthorize("@ss.hasPermi('candy:role:export')")
    @Log(title = "Candy AI角色", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(AiCharacterTemplate aiCharacterTemplate)
    {
        List<AiCharacterTemplate> list = aiCharacterTemplateService.selectAiCharacterTemplateList(aiCharacterTemplate);
        ExcelUtil<AiCharacterTemplate> util = new ExcelUtil<AiCharacterTemplate>(AiCharacterTemplate.class);
        return util.exportExcel(list, "candy_role");
    }

    /**
     * 获取Candy AI角色详细信息
     */
    @PreAuthorize("@ss.hasPermi('candy:role:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(aiCharacterTemplateService.selectAiCharacterTemplateById(id));
    }

    /**
     * 新增Candy AI角色
     */
    @PreAuthorize("@ss.hasPermi('candy:role:add')")
    @Log(title = "Candy AI角色", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AiCharacterTemplate aiCharacterTemplate)
    {
        return toAjax(aiCharacterTemplateService.insertAiCharacterTemplate(aiCharacterTemplate));
    }

    /**
     * 修改Candy AI角色
     */
    @PreAuthorize("@ss.hasPermi('candy:role:edit')")
    @Log(title = "Candy AI角色", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AiCharacterTemplate aiCharacterTemplate)
    {
        return toAjax(aiCharacterTemplateService.updateAiCharacterTemplate(aiCharacterTemplate));
    }

    /**
     * 删除Candy AI角色
     */
    @PreAuthorize("@ss.hasPermi('candy:role:remove')")
    @Log(title = "Candy AI角色", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(aiCharacterTemplateService.deleteAiCharacterTemplateByIds(ids));
    }

    /**
     * 复制Candy AI角色
     */
    @PreAuthorize("@ss.hasPermi('candy:role:add')")
    @Log(title = "复制Candy AI角色", businessType = BusinessType.INSERT)
    @PostMapping("/copy/{id}")
    public AjaxResult copy(@PathVariable("id") Long id)
    {
        return toAjax(aiCharacterTemplateService.copyAiCharacterTemplate(id));
    }
} 