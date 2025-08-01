package com.vibetempt.candy.service;

import com.ruoyi.common.core.domain.entity.SysUser;

/**
 * 用户 Profile 服务接口
 * 
 * @author candy
 */
public interface UserProfileService {
    
    /**
     * 获取用户 Profile 信息
     * 
     * @param userId 用户ID
     * @return 用户信息
     */
    SysUser getUserProfile(Long userId);
    
    /**
     * 更新用户基本信息
     * 
     * @param user 用户信息
     * @return 更新结果
     */
    String updateUserProfile(SysUser user);
    
    /**
     * 发送原邮箱验证码（用于换绑邮箱）
     * 
     * @param userId 用户ID
     * @return 发送结果
     */
    String sendOldEmailVerifyCode(Long userId);
    
    /**
     * 发送新邮箱验证码（用于换绑邮箱）
     * 
     * @param userId 用户ID
     * @param newEmail 新邮箱地址
     * @return 发送结果
     */
    String sendNewEmailVerifyCode(Long userId, String newEmail);
    
    /**
     * 验证原邮箱验证码
     * 
     * @param userId 用户ID
     * @param verifyCode 验证码
     * @return 验证结果
     */
    String verifyOldEmailCode(Long userId, String verifyCode);
    
    /**
     * 验证新邮箱验证码并完成换绑
     * 
     * @param userId 用户ID
     * @param newEmail 新邮箱地址
     * @param verifyCode 验证码
     * @return 验证结果
     */
    String verifyNewEmailAndUpdate(Long userId, String newEmail, String verifyCode);
    
    /**
     * 解绑邮箱
     * 
     * @param userId 用户ID
     * @return 解绑结果
     */
    String unbindEmail(Long userId);
    
    /**
     * 发送手机验证码（用于绑定手机）
     * 
     * @param userId 用户ID
     * @param phoneNumber 手机号码
     * @return 发送结果
     */
    String sendPhoneVerifyCode(Long userId, String phoneNumber);
    
    /**
     * 验证手机验证码并绑定手机
     * 
     * @param userId 用户ID
     * @param phoneNumber 手机号码
     * @param verifyCode 验证码
     * @return 验证结果
     */
    String verifyPhoneAndBind(Long userId, String phoneNumber, String verifyCode);
    
    /**
     * 发送手机验证码（用于换绑手机）
     * 
     * @param userId 用户ID
     * @param newPhoneNumber 新手机号码
     * @return 发送结果
     */
    String sendPhoneChangeVerifyCode(Long userId, String newPhoneNumber);
    
    /**
     * 验证手机验证码并换绑手机
     * 
     * @param userId 用户ID
     * @param newPhoneNumber 新手机号码
     * @param verifyCode 验证码
     * @return 验证结果
     */
    String verifyPhoneAndChange(Long userId, String newPhoneNumber, String verifyCode);
    
    /**
     * 解绑手机
     * 
     * @param userId 用户ID
     * @return 解绑结果
     */
    String unbindPhone(Long userId);
    
    /**
     * 更新用户头像
     * 
     * @param userId 用户ID
     * @param avatarUrl 头像URL
     * @return 更新结果
     */
    String updateUserAvatar(Long userId, String avatarUrl);
    
    /**
     * 检查邮箱是否已被其他用户使用
     * 
     * @param email 邮箱地址
     * @param excludeUserId 排除的用户ID
     * @return 是否可用
     */
    boolean isEmailAvailable(String email, Long excludeUserId);
    
    /**
     * 检查手机号是否已被其他用户使用
     * 
     * @param phoneNumber 手机号码
     * @param excludeUserId 排除的用户ID
     * @return 是否可用
     */
    boolean isPhoneAvailable(String phoneNumber, Long excludeUserId);
} 