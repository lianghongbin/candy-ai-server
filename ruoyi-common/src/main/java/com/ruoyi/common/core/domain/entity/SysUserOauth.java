package com.ruoyi.common.core.domain.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 用户第三方登录关联对象 sys_user_oauth
 * 
 * @author ruoyi
 */
public class SysUserOauth extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 第三方平台类型 */
    @Excel(name = "第三方平台类型")
    private String oauthType;

    /** 第三方平台用户ID */
    @Excel(name = "第三方平台用户ID")
    private String oauthUserId;

    /** 第三方平台用户名 */
    @Excel(name = "第三方平台用户名")
    private String oauthUsername;

    /** 第三方平台邮箱 */
    @Excel(name = "第三方平台邮箱")
    private String oauthEmail;

    /** 第三方平台头像 */
    private String oauthAvatar;

    /** 访问令牌 */
    private String accessToken;

    /** 刷新令牌 */
    private String refreshToken;

    /** 令牌过期时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "令牌过期时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date tokenExpireTime;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }

    public void setOauthType(String oauthType) 
    {
        this.oauthType = oauthType;
    }

    public String getOauthType() 
    {
        return oauthType;
    }

    public void setOauthUserId(String oauthUserId) 
    {
        this.oauthUserId = oauthUserId;
    }

    public String getOauthUserId() 
    {
        return oauthUserId;
    }

    public void setOauthUsername(String oauthUsername) 
    {
        this.oauthUsername = oauthUsername;
    }

    public String getOauthUsername() 
    {
        return oauthUsername;
    }

    public void setOauthEmail(String oauthEmail) 
    {
        this.oauthEmail = oauthEmail;
    }

    public String getOauthEmail() 
    {
        return oauthEmail;
    }

    public void setOauthAvatar(String oauthAvatar) 
    {
        this.oauthAvatar = oauthAvatar;
    }

    public String getOauthAvatar() 
    {
        return oauthAvatar;
    }

    public void setAccessToken(String accessToken) 
    {
        this.accessToken = accessToken;
    }

    public String getAccessToken() 
    {
        return accessToken;
    }

    public void setRefreshToken(String refreshToken) 
    {
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() 
    {
        return refreshToken;
    }

    public void setTokenExpireTime(Date tokenExpireTime) 
    {
        this.tokenExpireTime = tokenExpireTime;
    }

    public Date getTokenExpireTime() 
    {
        return tokenExpireTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("oauthType", getOauthType())
            .append("oauthUserId", getOauthUserId())
            .append("oauthUsername", getOauthUsername())
            .append("oauthEmail", getOauthEmail())
            .append("oauthAvatar", getOauthAvatar())
            .append("accessToken", getAccessToken())
            .append("refreshToken", getRefreshToken())
            .append("tokenExpireTime", getTokenExpireTime())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
} 