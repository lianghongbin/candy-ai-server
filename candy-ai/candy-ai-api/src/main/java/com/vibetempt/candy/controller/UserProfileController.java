package com.vibetempt.candy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.vibetempt.candy.service.UserProfileService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 用户 Profile 管理Controller
 * 
 * @author candy
 */
@Tag(name = "用户 Profile 管理")
@RestController
@RequestMapping("/api/user/profile")
public class UserProfileController extends BaseController {

    @Autowired
    private UserProfileService userProfileService;

    /**
     * 获取用户 Profile 信息
     */
    @Operation(summary = "获取用户 Profile 信息", description = "获取当前登录用户的详细信息")
    @Log(title = "获取用户 Profile", businessType = BusinessType.OTHER)
    @GetMapping("/info")
    public AjaxResult getUserProfile() {
        try {
            Long userId = SecurityUtils.getUserId();
            SysUser user = userProfileService.getUserProfile(userId);
            if (user != null) {
                // 隐藏敏感信息
                user.setPassword(null);
                // TODO: 隐藏验证码信息，需要数据库字段支持
                return success(user);
            } else {
                return error("用户不存在");
            }
        } catch (Exception e) {
            return error("获取用户信息失败: " + e.getMessage());
        }
    }

    /**
     * 更新用户基本信息
     */
    @Operation(summary = "更新用户基本信息", description = "更新用户昵称、性别等基本信息")
    @Log(title = "更新用户基本信息", businessType = BusinessType.UPDATE)
    @PutMapping("/update")
    public AjaxResult updateUserProfile(@RequestBody SysUser user) {
        try {
            Long userId = SecurityUtils.getUserId();
            user.setUserId(userId);
            
            String result = userProfileService.updateUserProfile(user);
            if (result.isEmpty()) {
                return success("更新成功");
            } else {
                return error(result);
            }
        } catch (Exception e) {
            return error("更新失败: " + e.getMessage());
        }
    }

    /**
     * 发送原邮箱验证码（用于换绑邮箱）
     */
    @Operation(summary = "发送原邮箱验证码", description = "发送验证码到原邮箱地址")
    @Log(title = "发送原邮箱验证码", businessType = BusinessType.OTHER)
    @PostMapping("/sendOldEmailVerifyCode")
    public AjaxResult sendOldEmailVerifyCode() {
        try {
            Long userId = SecurityUtils.getUserId();
            String result = userProfileService.sendOldEmailVerifyCode(userId);
            if (result.isEmpty()) {
                return success("验证码发送成功");
            } else {
                return error(result);
            }
        } catch (Exception e) {
            return error("发送验证码失败: " + e.getMessage());
        }
    }

    /**
     * 发送新邮箱验证码（用于换绑邮箱）
     */
    @Operation(summary = "发送新邮箱验证码", description = "发送验证码到新邮箱地址")
    @Log(title = "发送新邮箱验证码", businessType = BusinessType.OTHER)
    @PostMapping("/sendNewEmailVerifyCode")
    public AjaxResult sendNewEmailVerifyCode(
            @Parameter(description = "新邮箱地址") @RequestParam String newEmail) {
        try {
            Long userId = SecurityUtils.getUserId();
            String result = userProfileService.sendNewEmailVerifyCode(userId, newEmail);
            if (result.isEmpty()) {
                return success("验证码发送成功");
            } else {
                return error(result);
            }
        } catch (Exception e) {
            return error("发送验证码失败: " + e.getMessage());
        }
    }

    /**
     * 验证原邮箱验证码
     */
    @Operation(summary = "验证原邮箱验证码", description = "验证原邮箱验证码")
    @Log(title = "验证原邮箱验证码", businessType = BusinessType.OTHER)
    @PostMapping("/verifyOldEmailCode")
    public AjaxResult verifyOldEmailCode(
            @Parameter(description = "验证码") @RequestParam String verifyCode) {
        try {
            Long userId = SecurityUtils.getUserId();
            String result = userProfileService.verifyOldEmailCode(userId, verifyCode);
            if (result.isEmpty()) {
                return success("原邮箱验证成功");
            } else {
                return error(result);
            }
        } catch (Exception e) {
            return error("验证失败: " + e.getMessage());
        }
    }

    /**
     * 验证新邮箱验证码并完成换绑
     */
    @Operation(summary = "验证新邮箱验证码", description = "验证新邮箱验证码并完成换绑")
    @Log(title = "验证新邮箱验证码", businessType = BusinessType.UPDATE)
    @PostMapping("/verifyNewEmailAndUpdate")
    public AjaxResult verifyNewEmailAndUpdate(
            @Parameter(description = "新邮箱地址") @RequestParam String newEmail,
            @Parameter(description = "验证码") @RequestParam String verifyCode) {
        try {
            Long userId = SecurityUtils.getUserId();
            String result = userProfileService.verifyNewEmailAndUpdate(userId, newEmail, verifyCode);
            if (result.isEmpty()) {
                return success("邮箱换绑成功");
            } else {
                return error(result);
            }
        } catch (Exception e) {
            return error("验证失败: " + e.getMessage());
        }
    }

    /**
     * 解绑邮箱
     */
    @Operation(summary = "解绑邮箱", description = "解绑当前邮箱")
    @Log(title = "解绑邮箱", businessType = BusinessType.UPDATE)
    @PostMapping("/unbindEmail")
    public AjaxResult unbindEmail() {
        try {
            Long userId = SecurityUtils.getUserId();
            String result = userProfileService.unbindEmail(userId);
            if (result.isEmpty()) {
                return success("邮箱解绑成功");
            } else {
                return error(result);
            }
        } catch (Exception e) {
            return error("解绑失败: " + e.getMessage());
        }
    }

    /**
     * 发送手机验证码（用于绑定手机）
     */
    @Operation(summary = "发送手机验证码", description = "发送验证码到手机号码")
    @Log(title = "发送手机验证码", businessType = BusinessType.OTHER)
    @PostMapping("/sendPhoneVerifyCode")
    public AjaxResult sendPhoneVerifyCode(
            @Parameter(description = "手机号码") @RequestParam String phoneNumber) {
        try {
            Long userId = SecurityUtils.getUserId();
            String result = userProfileService.sendPhoneVerifyCode(userId, phoneNumber);
            if (result.isEmpty()) {
                return success("验证码发送成功");
            } else {
                return error(result);
            }
        } catch (Exception e) {
            return error("发送验证码失败: " + e.getMessage());
        }
    }

    /**
     * 验证手机验证码并绑定手机
     */
    @Operation(summary = "验证手机验证码", description = "验证手机验证码并绑定手机号")
    @Log(title = "验证手机验证码", businessType = BusinessType.UPDATE)
    @PostMapping("/verifyPhoneAndBind")
    public AjaxResult verifyPhoneAndBind(
            @Parameter(description = "手机号码") @RequestParam String phoneNumber,
            @Parameter(description = "验证码") @RequestParam String verifyCode) {
        try {
            Long userId = SecurityUtils.getUserId();
            String result = userProfileService.verifyPhoneAndBind(userId, phoneNumber, verifyCode);
            if (result.isEmpty()) {
                return success("手机号绑定成功");
            } else {
                return error(result);
            }
        } catch (Exception e) {
            return error("验证失败: " + e.getMessage());
        }
    }

    /**
     * 发送手机验证码（用于换绑手机）
     */
    @Operation(summary = "发送手机换绑验证码", description = "发送验证码到新手机号码")
    @Log(title = "发送手机换绑验证码", businessType = BusinessType.OTHER)
    @PostMapping("/sendPhoneChangeVerifyCode")
    public AjaxResult sendPhoneChangeVerifyCode(
            @Parameter(description = "新手机号码") @RequestParam String newPhoneNumber) {
        try {
            Long userId = SecurityUtils.getUserId();
            String result = userProfileService.sendPhoneChangeVerifyCode(userId, newPhoneNumber);
            if (result.isEmpty()) {
                return success("验证码发送成功");
            } else {
                return error(result);
            }
        } catch (Exception e) {
            return error("发送验证码失败: " + e.getMessage());
        }
    }

    /**
     * 验证手机验证码并换绑手机
     */
    @Operation(summary = "验证手机验证码并换绑", description = "验证手机验证码并换绑手机号")
    @Log(title = "验证手机验证码并换绑", businessType = BusinessType.UPDATE)
    @PostMapping("/verifyPhoneAndChange")
    public AjaxResult verifyPhoneAndChange(
            @Parameter(description = "新手机号码") @RequestParam String newPhoneNumber,
            @Parameter(description = "验证码") @RequestParam String verifyCode) {
        try {
            Long userId = SecurityUtils.getUserId();
            String result = userProfileService.verifyPhoneAndChange(userId, newPhoneNumber, verifyCode);
            if (result.isEmpty()) {
                return success("手机号换绑成功");
            } else {
                return error(result);
            }
        } catch (Exception e) {
            return error("验证失败: " + e.getMessage());
        }
    }

    /**
     * 解绑手机
     */
    @Operation(summary = "解绑手机", description = "解绑当前手机号")
    @Log(title = "解绑手机", businessType = BusinessType.UPDATE)
    @PostMapping("/unbindPhone")
    public AjaxResult unbindPhone() {
        try {
            Long userId = SecurityUtils.getUserId();
            String result = userProfileService.unbindPhone(userId);
            if (result.isEmpty()) {
                return success("手机号解绑成功");
            } else {
                return error(result);
            }
        } catch (Exception e) {
            return error("解绑失败: " + e.getMessage());
        }
    }

    /**
     * 更新用户头像
     */
    @Operation(summary = "更新用户头像", description = "更新用户头像URL")
    @Log(title = "更新用户头像", businessType = BusinessType.UPDATE)
    @PostMapping("/updateAvatar")
    public AjaxResult updateUserAvatar(
            @Parameter(description = "头像URL") @RequestParam String avatarUrl) {
        try {
            Long userId = SecurityUtils.getUserId();
            String result = userProfileService.updateUserAvatar(userId, avatarUrl);
            if (result.isEmpty()) {
                return success("头像更新成功");
            } else {
                return error(result);
            }
        } catch (Exception e) {
            return error("更新头像失败: " + e.getMessage());
        }
    }

    /**
     * 检查邮箱是否可用
     */
    @Operation(summary = "检查邮箱是否可用", description = "检查邮箱是否已被其他用户使用")
    @GetMapping("/checkEmail")
    public AjaxResult checkEmailAvailable(
            @Parameter(description = "邮箱地址") @RequestParam String email) {
        try {
            Long userId = SecurityUtils.getUserId();
            boolean available = userProfileService.isEmailAvailable(email, userId);
            return success().put("available", available);
        } catch (Exception e) {
            return error("检查失败: " + e.getMessage());
        }
    }

    /**
     * 检查手机号是否可用
     */
    @Operation(summary = "检查手机号是否可用", description = "检查手机号是否已被其他用户使用")
    @GetMapping("/checkPhone")
    public AjaxResult checkPhoneAvailable(
            @Parameter(description = "手机号码") @RequestParam String phoneNumber) {
        try {
            Long userId = SecurityUtils.getUserId();
            boolean available = userProfileService.isPhoneAvailable(phoneNumber, userId);
            return success().put("available", available);
        } catch (Exception e) {
            return error("检查失败: " + e.getMessage());
        }
    }
} 