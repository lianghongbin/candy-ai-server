package com.vibetempt.candy.domain.entity;

import java.util.Date;

/**
 * 邮箱验证码对象 sys_email_verify
 * 
 * @author candy
 */
public class SysEmailVerify {
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 邮箱地址 */
    private String email;

    /** 验证码 */
    private String verifyCode;

    /** 验证类型（register/reset） */
    private String verifyType;

    /** 是否已使用（0未使用 1已使用） */
    private Integer used;

    /** 过期时间 */
    private Date expireTime;

    /** 创建时间 */
    private Date createTime;

    /** 使用时间 */
    private Date usedTime;

    public SysEmailVerify() {
    }

    public SysEmailVerify(String email, String verifyCode, String verifyType) {
        this.email = email;
        this.verifyCode = verifyCode;
        this.verifyType = verifyType;
        this.used = 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getVerifyType() {
        return verifyType;
    }

    public void setVerifyType(String verifyType) {
        this.verifyType = verifyType;
    }

    public Integer getUsed() {
        return used;
    }

    public void setUsed(Integer used) {
        this.used = used;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(Date usedTime) {
        this.usedTime = usedTime;
    }

    @Override
    public String toString() {
        return "SysEmailVerify{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", verifyCode='" + verifyCode + '\'' +
                ", verifyType='" + verifyType + '\'' +
                ", used=" + used +
                ", expireTime=" + expireTime +
                ", createTime=" + createTime +
                ", usedTime=" + usedTime +
                '}';
    }
} 