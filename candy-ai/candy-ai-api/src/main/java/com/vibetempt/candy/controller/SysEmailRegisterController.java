package com.vibetempt.candy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.service.ISysConfigService;
import com.vibetempt.candy.domain.model.EmailRegisterBody;
import com.vibetempt.candy.service.ISysEmailRegisterService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 邮箱注册Controller
 * 
 * @author candy
 */
@Tag(name = "邮箱注册管理")
@RestController
@RequestMapping("/auth/email")
public class SysEmailRegisterController extends BaseController {

    @Autowired
    private ISysEmailRegisterService emailRegisterService;

    @Autowired
    private ISysConfigService configService;

    /**
     * 邮箱注册
     */
    @Operation(summary = "邮箱注册", description = "通过邮箱和密码进行用户注册")
    @Log(title = "邮箱注册", businessType = BusinessType.INSERT)
    @PostMapping("/register")
    public AjaxResult register(@RequestBody EmailRegisterBody registerBody) {
        String msg = emailRegisterService.register(registerBody);
        if (msg.isEmpty()) {
            return success("注册成功");
        } else {
            return error(msg);
        }
    }

    /**
     * 发送邮箱验证码
     */
    @Operation(summary = "发送邮箱验证码", description = "向指定邮箱发送验证码")
    @Log(title = "发送邮箱验证码", businessType = BusinessType.OTHER)
    @PostMapping("/sendVerifyCode")
    public AjaxResult sendVerifyCode(
            @Parameter(description = "邮箱地址") @RequestParam String email,
            @Parameter(description = "验证类型") @RequestParam String verifyType) {
        
        String msg = emailRegisterService.sendEmailVerifyCode(email, verifyType);
        if (msg.isEmpty()) {
            return success("验证码发送成功");
        } else {
            return error(msg);
        }
    }

    /**
     * 验证邮箱验证码
     */
    @Operation(summary = "验证邮箱验证码", description = "验证邮箱验证码是否正确")
    @Log(title = "验证邮箱验证码", businessType = BusinessType.OTHER)
    @PostMapping("/verifyCode")
    public AjaxResult verifyCode(
            @Parameter(description = "邮箱地址") @RequestParam String email,
            @Parameter(description = "验证码") @RequestParam String verifyCode,
            @Parameter(description = "验证类型") @RequestParam String verifyType) {
        
        boolean isValid = emailRegisterService.verifyEmailCode(email, verifyCode, verifyType);
        if (isValid) {
            return success("验证码验证成功");
        } else {
            return error("验证码验证失败");
        }
    }

    /**
     * 检查邮箱是否已注册
     */
    @Operation(summary = "检查邮箱是否已注册", description = "检查指定邮箱是否已经被注册")
    @Log(title = "检查邮箱注册状态", businessType = BusinessType.OTHER)
    @PostMapping("/checkEmail")
    public AjaxResult checkEmail(@Parameter(description = "邮箱地址") @RequestParam String email) {
        boolean isRegistered = emailRegisterService.isEmailRegistered(email);
        return success().put("isRegistered", isRegistered);
    }
} 