package com.vibetempt.candy.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.service.ISysUserService;
import com.vibetempt.candy.service.UserProfileService;

/**
 * 用户 Profile 服务实现类
 * 
 * @author candy
 */
@Service
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    private ISysUserService userService;

    @Override
    public SysUser getUserProfile(Long userId) {
        return userService.selectUserById(userId);
    }

    @Override
    @Transactional
    public String updateUserProfile(SysUser user) {
        try {
            // 验证用户是否存在
            SysUser existingUser = userService.selectUserById(user.getUserId());
            if (existingUser == null) {
                return "用户不存在";
            }

            // 只允许更新基本信息
            existingUser.setNickName(user.getNickName());
            existingUser.setSex(user.getSex());
            existingUser.setAvatar(user.getAvatar());

            int result = userService.updateUser(existingUser);
            if (result > 0) {
                return "";
            } else {
                return "更新失败";
            }
        } catch (Exception e) {
            return "更新失败: " + e.getMessage();
        }
    }

    @Override
    public String sendOldEmailVerifyCode(Long userId) {
        try {
            // 验证用户是否存在
            SysUser user = userService.selectUserById(userId);
            if (user == null) {
                return "用户不存在";
            }

            // 检查用户是否有邮箱
            if (StringUtils.isEmpty(user.getEmail())) {
                return "用户未绑定邮箱";
            }

            // 生成验证码
            String verifyCode = generateVerifyCode();
            Date expireTime = new Date(System.currentTimeMillis() + 10 * 60 * 1000); // 10分钟过期

            // 更新用户验证码信息 - 使用临时字段存储
            // TODO: 需要数据库字段支持
            System.out.println("发送验证码到原邮箱: " + user.getEmail() + ", 验证码: " + verifyCode + ", 过期时间: " + expireTime);

            return "";
        } catch (Exception e) {
            return "发送验证码失败: " + e.getMessage();
        }
    }

    @Override
    public String sendNewEmailVerifyCode(Long userId, String newEmail) {
        try {
            // 验证用户是否存在
            SysUser user = userService.selectUserById(userId);
            if (user == null) {
                return "用户不存在";
            }

            // 验证邮箱格式
            if (!isValidEmail(newEmail)) {
                return "邮箱格式不正确";
            }

            // 检查邮箱是否已被其他用户使用
            if (!isEmailAvailable(newEmail, userId)) {
                return "该邮箱已被其他用户使用";
            }

            // 生成验证码
            String verifyCode = generateVerifyCode();
            Date expireTime = new Date(System.currentTimeMillis() + 10 * 60 * 1000); // 10分钟过期

            // TODO: 需要数据库字段支持
            System.out.println("发送验证码到新邮箱: " + newEmail + ", 验证码: " + verifyCode + ", 过期时间: " + expireTime);

            return "";
        } catch (Exception e) {
            return "发送验证码失败: " + e.getMessage();
        }
    }

    @Override
    public String verifyOldEmailCode(Long userId, String verifyCode) {
        try {
            // 验证用户是否存在
            SysUser user = userService.selectUserById(userId);
            if (user == null) {
                return "用户不存在";
            }

            // TODO: 需要数据库字段支持，暂时返回成功
            System.out.println("验证原邮箱验证码: " + verifyCode);

            return "";
        } catch (Exception e) {
            return "验证失败: " + e.getMessage();
        }
    }

    @Override
    @Transactional
    public String verifyNewEmailAndUpdate(Long userId, String newEmail, String verifyCode) {
        try {
            // 验证用户是否存在
            SysUser user = userService.selectUserById(userId);
            if (user == null) {
                return "用户不存在";
            }

            // TODO: 需要数据库字段支持，暂时直接更新邮箱
            System.out.println("验证新邮箱验证码并换绑: " + newEmail + ", 验证码: " + verifyCode);

            // 更新邮箱
            user.setEmail(newEmail);
            // TODO: 设置邮箱验证状态

            int result = userService.updateUser(user);
            if (result > 0) {
                return "";
            } else {
                return "更新邮箱失败";
            }
        } catch (Exception e) {
            return "验证失败: " + e.getMessage();
        }
    }

    @Override
    @Transactional
    public String unbindEmail(Long userId) {
        try {
            // 验证用户是否存在
            SysUser user = userService.selectUserById(userId);
            if (user == null) {
                return "用户不存在";
            }

            // 清除邮箱信息
            user.setEmail(null);
            // TODO: 清除邮箱验证相关字段

            int result = userService.updateUser(user);
            if (result > 0) {
                return "";
            } else {
                return "解绑邮箱失败";
            }
        } catch (Exception e) {
            return "解绑邮箱失败: " + e.getMessage();
        }
    }

    @Override
    public String sendPhoneVerifyCode(Long userId, String phoneNumber) {
        try {
            // 验证用户是否存在
            SysUser user = userService.selectUserById(userId);
            if (user == null) {
                return "用户不存在";
            }

            // 验证手机号格式
            if (!isValidPhone(phoneNumber)) {
                return "手机号格式不正确";
            }

            // 检查手机号是否已被其他用户使用
            if (!isPhoneAvailable(phoneNumber, userId)) {
                return "该手机号已被其他用户使用";
            }

            // 生成验证码
            String verifyCode = generateVerifyCode();
            Date expireTime = new Date(System.currentTimeMillis() + 5 * 60 * 1000); // 5分钟过期

            // TODO: 需要数据库字段支持
            System.out.println("发送短信验证码到: " + phoneNumber + ", 验证码: " + verifyCode + ", 过期时间: " + expireTime);

            return "";
        } catch (Exception e) {
            return "发送验证码失败: " + e.getMessage();
        }
    }

    @Override
    @Transactional
    public String verifyPhoneAndBind(Long userId, String phoneNumber, String verifyCode) {
        try {
            // 验证用户是否存在
            SysUser user = userService.selectUserById(userId);
            if (user == null) {
                return "用户不存在";
            }

            // TODO: 需要数据库字段支持，暂时直接绑定手机号
            System.out.println("验证手机验证码并绑定: " + phoneNumber + ", 验证码: " + verifyCode);

            // 绑定手机号
            user.setPhonenumber(phoneNumber);
            // TODO: 设置手机验证状态

            int result = userService.updateUser(user);
            if (result > 0) {
                return "";
            } else {
                return "绑定手机号失败";
            }
        } catch (Exception e) {
            return "验证失败: " + e.getMessage();
        }
    }

    @Override
    public String sendPhoneChangeVerifyCode(Long userId, String newPhoneNumber) {
        try {
            // 验证用户是否存在
            SysUser user = userService.selectUserById(userId);
            if (user == null) {
                return "用户不存在";
            }

            // 验证手机号格式
            if (!isValidPhone(newPhoneNumber)) {
                return "手机号格式不正确";
            }

            // 检查手机号是否已被其他用户使用
            if (!isPhoneAvailable(newPhoneNumber, userId)) {
                return "该手机号已被其他用户使用";
            }

            // 生成验证码
            String verifyCode = generateVerifyCode();
            Date expireTime = new Date(System.currentTimeMillis() + 5 * 60 * 1000); // 5分钟过期

            // TODO: 需要数据库字段支持
            System.out.println("发送验证码到新手机号: " + newPhoneNumber + ", 验证码: " + verifyCode + ", 过期时间: " + expireTime);

            return "";
        } catch (Exception e) {
            return "发送验证码失败: " + e.getMessage();
        }
    }

    @Override
    @Transactional
    public String verifyPhoneAndChange(Long userId, String newPhoneNumber, String verifyCode) {
        try {
            // 验证用户是否存在
            SysUser user = userService.selectUserById(userId);
            if (user == null) {
                return "用户不存在";
            }

            // TODO: 需要数据库字段支持，暂时直接换绑手机号
            System.out.println("验证手机验证码并换绑: " + newPhoneNumber + ", 验证码: " + verifyCode);

            // 换绑手机号
            user.setPhonenumber(newPhoneNumber);
            // TODO: 设置手机验证状态

            int result = userService.updateUser(user);
            if (result > 0) {
                return "";
            } else {
                return "换绑手机号失败";
            }
        } catch (Exception e) {
            return "验证失败: " + e.getMessage();
        }
    }

    @Override
    @Transactional
    public String unbindPhone(Long userId) {
        try {
            // 验证用户是否存在
            SysUser user = userService.selectUserById(userId);
            if (user == null) {
                return "用户不存在";
            }

            // 清除手机号信息
            user.setPhonenumber(null);
            // TODO: 清除手机验证相关字段

            int result = userService.updateUser(user);
            if (result > 0) {
                return "";
            } else {
                return "解绑手机号失败";
            }
        } catch (Exception e) {
            return "解绑手机号失败: " + e.getMessage();
        }
    }

    @Override
    @Transactional
    public String updateUserAvatar(Long userId, String avatarUrl) {
        try {
            // 验证用户是否存在
            SysUser user = userService.selectUserById(userId);
            if (user == null) {
                return "用户不存在";
            }

            // 更新头像
            user.setAvatar(avatarUrl);

            int result = userService.updateUser(user);
            if (result > 0) {
                return "";
            } else {
                return "更新头像失败";
            }
        } catch (Exception e) {
            return "更新头像失败: " + e.getMessage();
        }
    }

    @Override
    public boolean isEmailAvailable(String email, Long excludeUserId) {
        try {
            // 查询所有用户
            List<SysUser> allUsers = userService.selectUserList(new SysUser());
            
            // 检查邮箱是否已被其他用户使用
            for (SysUser user : allUsers) {
                if (email.equals(user.getEmail()) && !user.getUserId().equals(excludeUserId)) {
                    return false; // 邮箱已被其他用户使用
                }
            }
            
            return true; // 邮箱可用
        } catch (Exception e) {
            return false; // 出错时返回不可用
        }
    }

    @Override
    public boolean isPhoneAvailable(String phoneNumber, Long excludeUserId) {
        try {
            // 查询所有用户
            List<SysUser> allUsers = userService.selectUserList(new SysUser());
            
            // 检查手机号是否已被其他用户使用
            for (SysUser user : allUsers) {
                if (phoneNumber.equals(user.getPhonenumber()) && !user.getUserId().equals(excludeUserId)) {
                    return false; // 手机号已被其他用户使用
                }
            }
            
            return true; // 手机号可用
        } catch (Exception e) {
            return false; // 出错时返回不可用
        }
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

    /**
     * 验证邮箱格式
     */
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    /**
     * 验证手机号格式
     */
    private boolean isValidPhone(String phone) {
        String phoneRegex = "^1[3-9]\\d{9}$";
        Pattern pattern = Pattern.compile(phoneRegex);
        return pattern.matcher(phone).matches();
    }
} 