package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.model.EmailRegisterBody;

/**
 * 邮箱注册服务接口
 * 
 * @author ruoyi
 */
public interface ISysEmailRegisterService 
{
    /**
     * 邮箱注册
     * 
     * @param registerBody 注册信息
     * @return 注册结果
     */
    String register(EmailRegisterBody registerBody);

    /**
     * 发送邮箱验证码
     * 
     * @param email 邮箱地址
     * @param verifyType 验证类型（register/reset）
     * @return 发送结果
     */
    String sendEmailVerifyCode(String email, String verifyType);

    /**
     * 验证邮箱验证码
     * 
     * @param email 邮箱地址
     * @param verifyCode 验证码
     * @param verifyType 验证类型
     * @return 验证结果
     */
    boolean verifyEmailCode(String email, String verifyCode, String verifyType);

    /**
     * 检查邮箱是否已注册
     * 
     * @param email 邮箱地址
     * @return 是否已注册
     */
    boolean isEmailRegistered(String email);
} 