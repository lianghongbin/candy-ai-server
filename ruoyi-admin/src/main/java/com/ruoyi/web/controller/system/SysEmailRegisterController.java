package com.ruoyi.web.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.EmailRegisterBody;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.service.ISysEmailRegisterService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 邮箱注册验证
 * 
 * @author ruoyi
 */
@Tag(name = "邮箱注册管理")
@RestController
@RequestMapping("/auth/email")
public class SysEmailRegisterController extends BaseController
{
    @Autowired
    private ISysEmailRegisterService emailRegisterService;

    @Autowired
    private ISysConfigService configService;

    /**
     * 邮箱注册
     */
    @Operation(summary = "邮箱注册")
    @Log(title = "邮箱注册", businessType = BusinessType.INSERT)
    @PostMapping("/register")
    public AjaxResult register(@Validated @RequestBody EmailRegisterBody registerBody)
    {
        if (!("true".equals(configService.selectConfigByKey("sys.account.registerUser"))))
        {
            return error("当前系统没有开启注册功能！");
        }
        String msg = emailRegisterService.register(registerBody);
        return StringUtils.isEmpty(msg) ? success() : error(msg);
    }

    /**
     * 发送邮箱验证码
     */
    @Operation(summary = "发送邮箱验证码")
    @PostMapping("/sendVerifyCode")
    public AjaxResult sendVerifyCode(@RequestParam String email, @RequestParam String verifyType)
    {
        if (!("true".equals(configService.selectConfigByKey("sys.account.emailVerify"))))
        {
            return error("当前系统没有开启邮箱验证功能！");
        }
        String msg = emailRegisterService.sendEmailVerifyCode(email, verifyType);
        return StringUtils.isEmpty(msg) ? success("验证码发送成功") : error(msg);
    }

    /**
     * 验证邮箱验证码
     */
    @Operation(summary = "验证邮箱验证码")
    @PostMapping("/verifyCode")
    public AjaxResult verifyCode(@RequestParam String email, @RequestParam String verifyCode, @RequestParam String verifyType)
    {
        boolean isValid = emailRegisterService.verifyEmailCode(email, verifyCode, verifyType);
        return isValid ? success("验证码验证成功") : error("验证码验证失败");
    }

    /**
     * 检查邮箱是否已注册
     */
    @Operation(summary = "检查邮箱是否已注册")
    @PostMapping("/checkEmail")
    public AjaxResult checkEmail(@RequestParam String email)
    {
        boolean isRegistered = emailRegisterService.isEmailRegistered(email);
        return success().put("isRegistered", isRegistered);
    }
} 