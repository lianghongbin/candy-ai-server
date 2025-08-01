package com.vibetempt.candy.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.entity.SysUser;

import java.util.Date;

/**
 * Candy AI 用户扩展信息
 * 
 * @author candy-ai
 */
@TableName("candy_user_extension")
public class CandyUserExtension
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 用户ID（关联sys_user.user_id） */
    private Long userId;

    /** 邮箱验证状态（0未验证 1已验证） */
    private Integer emailVerified;

    /** 邮箱验证码 */
    private String emailVerifyCode;

    /** 邮箱验证码过期时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date emailVerifyExpire;

    /** 注册来源（email/google/discord） */
    private String registerSource;

    /** 头像URL */
    private String avatarUrl;

    /** 最后登录来源 */
    private String lastLoginSource;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    public CandyUserExtension() {}

    public CandyUserExtension(Long userId) {
        this.userId = userId;
        this.emailVerified = 0; // 默认未验证
        this.createTime = new Date();
        this.updateTime = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Integer emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getEmailVerifyCode() {
        return emailVerifyCode;
    }

    public void setEmailVerifyCode(String emailVerifyCode) {
        this.emailVerifyCode = emailVerifyCode;
    }

    public Date getEmailVerifyExpire() {
        return emailVerifyExpire;
    }

    public void setEmailVerifyExpire(Date emailVerifyExpire) {
        this.emailVerifyExpire = emailVerifyExpire;
    }

    public String getRegisterSource() {
        return registerSource;
    }

    public void setRegisterSource(String registerSource) {
        this.registerSource = registerSource;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getLastLoginSource() {
        return lastLoginSource;
    }

    public void setLastLoginSource(String lastLoginSource) {
        this.lastLoginSource = lastLoginSource;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 从 SysUser 创建扩展信息
     */
    public static CandyUserExtension fromSysUser(SysUser user) {
        CandyUserExtension extension = new CandyUserExtension(user.getUserId());
        extension.setRegisterSource("email"); // 默认来源
        return extension;
    }

    /**
     * 检查邮箱是否已验证
     */
    public boolean isEmailVerified() {
        return emailVerified != null && emailVerified == 1;
    }

    /**
     * 检查验证码是否过期
     */
    public boolean isVerifyCodeExpired() {
        return emailVerifyExpire != null && emailVerifyExpire.before(new Date());
    }
} 