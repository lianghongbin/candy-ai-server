package com.ruoyi.system.service.impl;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.entity.SysEmailVerify;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.EmailRegisterBody;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.exception.user.CaptchaException;
import com.ruoyi.common.exception.user.CaptchaExpireException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.MessageUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
// import com.ruoyi.framework.manager.AsyncManager;
// import com.ruoyi.framework.manager.factory.AsyncFactory;
import com.ruoyi.system.mapper.SysEmailVerifyMapper;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.service.ISysEmailRegisterService;
import com.ruoyi.system.service.ISysUserService;

/**
 * 邮箱注册服务实现类
 * 
 * @author ruoyi
 */
@Service
public class SysEmailRegisterServiceImpl implements ISysEmailRegisterService
{
    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private SysEmailVerifyMapper emailVerifyMapper;

    @Autowired
    private RedisCache redisCache;

    /**
     * 邮箱注册
     */
    @Override
    @Transactional
    public String register(EmailRegisterBody registerBody)
    {
        String msg = "", email = registerBody.getEmail(), password = registerBody.getPassword();
        SysUser sysUser = new SysUser();
        sysUser.setEmail(email);

        // 验证码开关
        boolean captchaEnabled = configService.selectCaptchaEnabled();
        if (captchaEnabled)
        {
            validateCaptcha(email, registerBody.getCode(), registerBody.getUuid());
        }

        if (StringUtils.isEmpty(email))
        {
            msg = "邮箱不能为空";
        }
        else if (StringUtils.isEmpty(password))
        {
            msg = "用户密码不能为空";
        }
        else if (password.length() < 6 || password.length() > 20)
        {
            msg = "密码长度必须在6到20个字符之间";
        }
        else if (!userService.checkEmailUnique(sysUser))
        {
            msg = "保存用户'" + email + "'失败，注册邮箱已存在";
        }
        else
        {
            // 生成用户名（使用邮箱前缀）
            String userName = email.substring(0, email.indexOf("@"));
            if (userName.length() > 20)
            {
                userName = userName.substring(0, 20);
            }
            
            // 检查用户名是否已存在
            SysUser checkUser = new SysUser();
            checkUser.setUserName(userName);
            int suffix = 1;
            while (!userService.checkUserNameUnique(checkUser))
            {
                checkUser.setUserName(userName + suffix);
                suffix++;
            }
            userName = checkUser.getUserName();

            sysUser.setUserName(userName);
            sysUser.setNickName(StringUtils.isNotEmpty(registerBody.getNickName()) ? 
                registerBody.getNickName() : userName);
            sysUser.setPwdUpdateDate(DateUtils.getNowDate());
            sysUser.setPassword(SecurityUtils.encryptPassword(password));
            sysUser.setRegisterSource("email");
            sysUser.setEmailVerified(1); // 邮箱已验证
            sysUser.setStatus("0"); // 正常状态

            boolean regFlag = userService.registerUser(sysUser);
            if (!regFlag)
            {
                msg = "注册失败,请联系系统管理人员";
            }
            else
            {
                // TODO: 记录登录日志
                // AsyncManager.me().execute(AsyncFactory.recordLogininfor(userName, Constants.REGISTER, 
                //     MessageUtils.message("user.register.success")));
            }
        }
        return msg;
    }

    /**
     * 发送邮箱验证码
     */
    @Override
    public String sendEmailVerifyCode(String email, String verifyType)
    {
        if (StringUtils.isEmpty(email))
        {
            return "邮箱地址不能为空";
        }

        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"))
        {
            return "邮箱格式不正确";
        }

        // 检查邮箱是否已注册
        if ("register".equals(verifyType) && isEmailRegistered(email))
        {
            return "该邮箱已注册";
        }

        // 生成6位数字验证码
        String verifyCode = String.valueOf((int)((Math.random() * 9 + 1) * 100000));

        // 保存验证码到数据库
        SysEmailVerify emailVerify = new SysEmailVerify();
        emailVerify.setEmail(email);
        emailVerify.setVerifyCode(verifyCode);
        emailVerify.setVerifyType(verifyType);
        emailVerify.setExpireTime(DateUtils.addMinutes(new Date(), 10)); // 10分钟过期
        emailVerify.setUsed(0);

        emailVerifyMapper.insertSysEmailVerify(emailVerify);

        // TODO: 发送邮件逻辑（需要配置邮件服务）
        // 这里先模拟发送成功
        System.out.println("发送验证码到邮箱: " + email + ", 验证码: " + verifyCode);

        return "";
    }

    /**
     * 验证邮箱验证码
     */
    @Override
    public boolean verifyEmailCode(String email, String verifyCode, String verifyType)
    {
        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(verifyCode))
        {
            return false;
        }

        SysEmailVerify emailVerify = emailVerifyMapper.selectLatestValidVerifyCode(email, verifyType);
        if (emailVerify == null)
        {
            return false;
        }

        if (!verifyCode.equals(emailVerify.getVerifyCode()))
        {
            return false;
        }

        // 标记验证码为已使用
        emailVerifyMapper.markVerifyCodeAsUsed(emailVerify.getId());

        return true;
    }

    /**
     * 检查邮箱是否已注册
     */
    @Override
    public boolean isEmailRegistered(String email)
    {
        if (StringUtils.isEmpty(email))
        {
            return false;
        }

        SysUser user = new SysUser();
        user.setEmail(email);
        return !userService.checkEmailUnique(user);
    }

    /**
     * 校验验证码
     */
    private void validateCaptcha(String email, String code, String uuid)
    {
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + StringUtils.nvl(uuid, "");
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);
        if (captcha == null)
        {
            throw new CaptchaExpireException();
        }
        if (!code.equalsIgnoreCase(captcha))
        {
            throw new CaptchaException();
        }
    }
} 