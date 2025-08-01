package com.vibetempt.candy.domain.entity;

import java.util.Date;

/**
 * 用户第三方登录关联对象 sys_user_oauth
 * 
 * @author candy
 */
public class SysUserOauth {
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 用户ID */
    private Long userId;

    /** 第三方平台类型（google/discord） */
    private String oauthType;

    /** 第三方平台用户ID */
    private String oauthUserId;

    /** 第三方平台用户名 */
    private String oauthUsername;

    /** 第三方平台邮箱 */
    private String oauthEmail;

    /** 第三方平台头像URL */
    private String oauthAvatarUrl;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;

    public SysUserOauth() {
    }

    public SysUserOauth(Long userId, String oauthType, String oauthUserId) {
        this.userId = userId;
        this.oauthType = oauthType;
        this.oauthUserId = oauthUserId;
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

    public String getOauthType() {
        return oauthType;
    }

    public void setOauthType(String oauthType) {
        this.oauthType = oauthType;
    }

    public String getOauthUserId() {
        return oauthUserId;
    }

    public void setOauthUserId(String oauthUserId) {
        this.oauthUserId = oauthUserId;
    }

    public String getOauthUsername() {
        return oauthUsername;
    }

    public void setOauthUsername(String oauthUsername) {
        this.oauthUsername = oauthUsername;
    }

    public String getOauthEmail() {
        return oauthEmail;
    }

    public void setOauthEmail(String oauthEmail) {
        this.oauthEmail = oauthEmail;
    }

    public String getOauthAvatarUrl() {
        return oauthAvatarUrl;
    }

    public void setOauthAvatarUrl(String oauthAvatarUrl) {
        this.oauthAvatarUrl = oauthAvatarUrl;
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

    @Override
    public String toString() {
        return "SysUserOauth{" +
                "id=" + id +
                ", userId=" + userId +
                ", oauthType='" + oauthType + '\'' +
                ", oauthUserId='" + oauthUserId + '\'' +
                ", oauthUsername='" + oauthUsername + '\'' +
                ", oauthEmail='" + oauthEmail + '\'' +
                ", oauthAvatarUrl='" + oauthAvatarUrl + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
} 