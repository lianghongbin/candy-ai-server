package com.vibetempt.candy.service.impl;

import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.service.ISysUserService;
import com.vibetempt.candy.domain.entity.SysEmailVerify;
import com.vibetempt.candy.domain.model.EmailRegisterBody;
import com.vibetempt.candy.service.ISysEmailRegisterService;
import com.vibetempt.candy.service.mapper.SysEmailVerifyMapper;

/**
 * 邮箱注册服务实现类
 * 
 * @author candy
 */
@Service
public class SysEmailRegisterServiceImpl implements ISysEmailRegisterService {

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private SysEmailVerifyMapper emailVerifyMapper;

    @Autowired
    private RedisCache redisCache;

    @Override
    public String register(EmailRegisterBody registerBody) {
        // 验证邮箱
        if (registerBody.getEmail() == null || registerBody.getEmail().trim().isEmpty()) {
            return "邮箱不能为空";
        }

        // 验证密码
        if (registerBody.getPassword() == null || registerBody.getPassword().trim().isEmpty()) {
            return "用户密码不能为空";
        }

        if (registerBody.getPassword().length() < 6 || registerBody.getPassword().length() > 20) {
            return "密码长度必须在6到20个字符之间";
        }

        // 验证验证码（如果启用）
        if (configService.selectCaptchaEnabled()) {
            String verifyKey = "captcha_codes:" + registerBody.getUuid();
            String captcha = redisCache.getCacheObject(verifyKey);
            redisCache.deleteObject(verifyKey);
            if (captcha == null) {
                return "验证码失效";
            }
            if (!registerBody.getCode().equalsIgnoreCase(captcha)) {
                return "验证码错误";
            }
        }

        // 检查邮箱是否已注册
        SysUser user = new SysUser();
        user.setEmail(registerBody.getEmail());
        if (!userService.checkEmailUnique(user)) {
            return "保存用户'" + registerBody.getEmail() + "'失败，注册邮箱已存在";
        }

        // 创建用户
        SysUser newUser = new SysUser();
        newUser.setUserName(registerBody.getEmail()); // 使用邮箱作为用户名
        newUser.setNickName(registerBody.getNickName() != null ? registerBody.getNickName() : "用户");
        newUser.setEmail(registerBody.getEmail());
        newUser.setPassword(registerBody.getPassword());
        newUser.setStatus("0"); // 正常状态
        newUser.setRegisterSource("email");
        newUser.setEmailVerified(0); // 未验证

        // 检查用户名唯一性
        if (!userService.checkUserNameUnique(newUser)) {
            return "保存用户'" + registerBody.getEmail() + "'失败，登录账号已存在";
        }

        // 注册用户
        boolean regFlag = userService.registerUser(newUser);
        if (!regFlag) {
            return "注册失败,请联系系统管理人员";
        }

        return "";
    }

    @Override
    public String sendEmailVerifyCode(String email, String verifyType) {
        // 验证邮箱
        if (email == null || email.trim().isEmpty()) {
            return "邮箱地址不能为空";
        }

        // 验证邮箱格式
        if (!isValidEmail(email)) {
            return "邮箱格式不正确";
        }

        // 检查邮箱是否已注册（注册时）
        if ("register".equals(verifyType)) {
            SysUser user = new SysUser();
            user.setEmail(email);
            if (!userService.checkEmailUnique(user)) {
                return "该邮箱已注册";
            }
        }

        // 生成验证码
        String verifyCode = generateVerifyCode();

        // 创建验证码记录
        SysEmailVerify emailVerify = new SysEmailVerify(email, verifyCode, verifyType);
        emailVerify.setExpireTime(new Date(System.currentTimeMillis() + 10 * 60 * 1000)); // 10分钟过期
        emailVerify.setCreateTime(new Date());

        // 保存到数据库
        emailVerifyMapper.insertSysEmailVerify(emailVerify);

        // TODO: 发送邮件
        // 这里需要集成邮件服务，暂时只保存到数据库
        System.out.println("发送验证码到邮箱: " + email + ", 验证码: " + verifyCode);

        return "";
    }

    @Override
    public boolean verifyEmailCode(String email, String verifyCode, String verifyType) {
        // 验证参数
        if (email == null || email.trim().isEmpty()) {
            return false;
        }

        if (verifyCode == null || verifyCode.trim().isEmpty()) {
            return false;
        }

        // 查询最新的有效验证码
        SysEmailVerify emailVerify = emailVerifyMapper.selectLatestValidVerifyCode(email, verifyType);
        if (emailVerify == null) {
            return false;
        }

        // 验证验证码
        if (!verifyCode.equals(emailVerify.getVerifyCode())) {
            return false;
        }

        // 标记验证码为已使用
        emailVerifyMapper.markVerifyCodeAsUsed(emailVerify.getId());

        return true;
    }

    @Override
    public boolean isEmailRegistered(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }

        SysUser user = new SysUser();
        user.setEmail(email);
        return !userService.checkEmailUnique(user);
    }

    /**
     * 验证邮箱格式
     */
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    /**
     * 生成6位数字验证码
     */
    private String generateVerifyCode() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
} 