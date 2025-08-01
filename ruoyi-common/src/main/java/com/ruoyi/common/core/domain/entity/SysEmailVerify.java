package com.ruoyi.common.core.domain.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 邮箱验证码对象 sys_email_verify
 * 
 * @author ruoyi
 */
public class SysEmailVerify extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 邮箱地址 */
    @Excel(name = "邮箱地址")
    private String email;

    /** 验证码 */
    @Excel(name = "验证码")
    private String verifyCode;

    /** 验证类型 */
    @Excel(name = "验证类型")
    private String verifyType;

    /** 过期时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "过期时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date expireTime;

    /** 是否已使用 */
    @Excel(name = "是否已使用", readConverterExp = "0=未使用,1=已使用")
    private Integer used;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setEmail(String email) 
    {
        this.email = email;
    }

    public String getEmail() 
    {
        return email;
    }

    public void setVerifyCode(String verifyCode) 
    {
        this.verifyCode = verifyCode;
    }

    public String getVerifyCode() 
    {
        return verifyCode;
    }

    public void setVerifyType(String verifyType) 
    {
        this.verifyType = verifyType;
    }

    public String getVerifyType() 
    {
        return verifyType;
    }

    public void setExpireTime(Date expireTime) 
    {
        this.expireTime = expireTime;
    }

    public Date getExpireTime() 
    {
        return expireTime;
    }

    public void setUsed(Integer used) 
    {
        this.used = used;
    }

    public Integer getUsed() 
    {
        return used;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("email", getEmail())
            .append("verifyCode", getVerifyCode())
            .append("verifyType", getVerifyType())
            .append("expireTime", getExpireTime())
            .append("used", getUsed())
            .append("createTime", getCreateTime())
            .toString();
    }
} 