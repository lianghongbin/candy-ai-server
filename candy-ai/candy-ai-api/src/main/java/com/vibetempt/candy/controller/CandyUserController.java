package com.vibetempt.candy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.service.ISysUserService;
import org.apache.commons.lang3.ArrayUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Candy AI 用户管理Controller
 * 
 * @author candy
 */
@Tag(name = "Candy AI 用户管理")
@RestController
@RequestMapping("/candy/user")
public class CandyUserController extends BaseController {

    @Autowired
    private ISysUserService userService;

    /**
     * 获取Candy用户列表
     */
    @PreAuthorize("@ss.hasPermi('candy:user:list')")
    @GetMapping("/list")
    @Operation(summary = "获取Candy用户列表", description = "获取通过注册功能创建的用户列表")
    public TableDataInfo list(SysUser user) {
        startPage();
        // 查询所有用户，前端可以过滤显示注册用户
        List<SysUser> list = userService.selectUserList(user);
        return getDataTable(list);
    }

    /**
     * 根据用户编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('candy:user:query')")
    @GetMapping(value = { "/", "/{userId}" })
    @Operation(summary = "获取Candy用户详细信息", description = "根据用户ID获取详细信息")
    public AjaxResult getInfo(@PathVariable(value = "userId", required = false) Long userId) {
        if (StringUtils.isNotNull(userId)) {
            SysUser sysUser = userService.selectUserById(userId);
            if (sysUser != null) {
                // 隐藏敏感信息
                sysUser.setPassword(null);
                return success(sysUser);
            } else {
                return error("用户不存在");
            }
        }
        return error("用户ID不能为空");
    }

    /**
     * 新增Candy用户
     */
    @PreAuthorize("@ss.hasPermi('candy:user:add')")
    @Log(title = "Candy用户管理", businessType = BusinessType.INSERT)
    @PostMapping
    @Operation(summary = "新增Candy用户", description = "新增Candy AI用户")
    public AjaxResult add(@Validated @RequestBody SysUser user) {
        if (!userService.checkUserNameUnique(user)) {
            return error("新增用户'" + user.getUserName() + "'失败，登录账号已存在");
        } else if (StringUtils.isNotEmpty(user.getPhonenumber()) && !userService.checkPhoneUnique(user)) {
            return error("新增用户'" + user.getUserName() + "'失败，手机号码已存在");
        } else if (StringUtils.isNotEmpty(user.getEmail()) && !userService.checkEmailUnique(user)) {
            return error("新增用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        user.setCreateBy(getUsername());
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        return toAjax(userService.insertUser(user));
    }

    /**
     * 修改Candy用户
     */
    @PreAuthorize("@ss.hasPermi('candy:user:edit')")
    @Log(title = "Candy用户管理", businessType = BusinessType.UPDATE)
    @PutMapping
    @Operation(summary = "修改Candy用户", description = "修改Candy AI用户信息")
    public AjaxResult edit(@Validated @RequestBody SysUser user) {
        userService.checkUserAllowed(user);
        if (!userService.checkUserNameUnique(user)) {
            return error("修改用户'" + user.getUserName() + "'失败，登录账号已存在");
        } else if (StringUtils.isNotEmpty(user.getPhonenumber()) && !userService.checkPhoneUnique(user)) {
            return error("修改用户'" + user.getUserName() + "'失败，手机号码已存在");
        } else if (StringUtils.isNotEmpty(user.getEmail()) && !userService.checkEmailUnique(user)) {
            return error("修改用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        user.setUpdateBy(getUsername());
        return toAjax(userService.updateUser(user));
    }

    /**
     * 删除Candy用户
     */
    @PreAuthorize("@ss.hasPermi('candy:user:remove')")
    @Log(title = "Candy用户管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{userIds}")
    @Operation(summary = "删除Candy用户", description = "删除Candy AI用户")
    public AjaxResult remove(@PathVariable Long[] userIds) {
        if (ArrayUtils.contains(userIds, getUserId())) {
            return error("当前用户不能删除");
        }
        return toAjax(userService.deleteUserByIds(userIds));
    }

    /**
     * 重置Candy用户密码
     */
    @PreAuthorize("@ss.hasPermi('candy:user:resetPwd')")
    @Log(title = "Candy用户管理", businessType = BusinessType.UPDATE)
    @PutMapping("/resetPwd")
    @Operation(summary = "重置Candy用户密码", description = "重置Candy AI用户密码")
    public AjaxResult resetPwd(@RequestBody SysUser user) {
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        user.setUpdateBy(getUsername());
        return toAjax(userService.resetUserPwd(user.getUserId(), user.getPassword()));
    }

    /**
     * 获取Candy用户统计信息
     */
    @PreAuthorize("@ss.hasPermi('candy:user:query')")
    @GetMapping("/stats")
    @Operation(summary = "获取Candy用户统计信息", description = "获取Candy AI用户统计信息")
    public AjaxResult getStats() {
        try {
            SysUser queryUser = new SysUser();
            List<SysUser> allUsers = userService.selectUserList(queryUser);
            
            // 统计总用户数
            long totalUsers = allUsers.size();
            
            // 统计有邮箱的用户数
            long emailUsers = allUsers.stream()
                    .filter(u -> StringUtils.isNotEmpty(u.getEmail()))
                    .count();
            
            // 统计有手机的用户数
            long phoneUsers = allUsers.stream()
                    .filter(u -> StringUtils.isNotEmpty(u.getPhonenumber()))
                    .count();
            
            return success()
                    .put("totalUsers", totalUsers)
                    .put("emailUsers", emailUsers)
                    .put("phoneUsers", phoneUsers);
        } catch (Exception e) {
            return error("获取统计信息失败: " + e.getMessage());
        }
    }
} 