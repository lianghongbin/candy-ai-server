package com.ruoyi.common.core.domain.entity;

import java.util.Date;
import java.util.List;
import jakarta.validation.constraints.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Excel.ColumnType;
import com.ruoyi.common.annotation.Excel.Type;
import com.ruoyi.common.annotation.Excels;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.xss.Xss;

/**
 * 用户对象 sys_user
 * 
 * @author ruoyi
 */
public class SysUser extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 用户ID */
    @Excel(name = "用户序号", type = Type.EXPORT, cellType = ColumnType.NUMERIC, prompt = "用户编号")
    private Long userId;

    /** 部门ID */
    @Excel(name = "部门编号", type = Type.IMPORT)
    private Long deptId;

    /** 用户账号 */
    @Excel(name = "登录名称")
    private String userName;

    /** 用户昵称 */
    @Excel(name = "用户名称")
    private String nickName;

    /** 用户邮箱 */
    @Excel(name = "用户邮箱")
    private String email;

    /** 手机号码 */
    @Excel(name = "手机号码", cellType = ColumnType.TEXT)
    private String phonenumber;

    /** 用户性别 */
    @Excel(name = "用户性别", readConverterExp = "0=男,1=女,2=未知")
    private String sex;

    /** 用户头像 */
    private String avatar;

    /** 密码 */
    private String password;

    /** 账号状态（0正常 1停用） */
    @Excel(name = "账号状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /** 最后登录IP */
    @Excel(name = "最后登录IP", type = Type.EXPORT)
    private String loginIp;

    /** 最后登录时间 */
    @Excel(name = "最后登录时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Type.EXPORT)
    private Date loginDate;

    /** 密码最后更新时间 */
    private Date pwdUpdateDate;

    /** 邮箱验证状态（0未验证 1已验证） */
    private Integer emailVerified;

    /** 邮箱验证码 */
    private String emailVerifyCode;

    /** 邮箱验证码过期时间 */
    private Date emailVerifyExpire;

    /** 手机验证状态（0未验证 1已验证） */
    private Integer phoneVerified;

    /** 手机验证码 */
    private String phoneVerifyCode;

    /** 手机验证码过期时间 */
    private Date phoneVerifyExpire;

    /** 原邮箱验证码（用于换绑验证） */
    private String oldEmailVerifyCode;

    /** 原邮箱验证码过期时间 */
    private Date oldEmailVerifyExpire;

    /** 新邮箱验证码（用于换绑验证） */
    private String newEmailVerifyCode;

    /** 新邮箱验证码过期时间 */
    private Date newEmailVerifyExpire;

    /** 注册来源（email/google/discord） */
    private String registerSource;

    /** 头像URL */
    private String avatarUrl;

    /** 最后登录来源 */
    private String lastLoginSource;

    /** 部门对象 */
    @Excels({
        @Excel(name = "部门名称", targetAttr = "deptName", type = Type.EXPORT),
        @Excel(name = "部门负责人", targetAttr = "leader", type = Type.EXPORT)
    })
    private SysDept dept;

    /** 角色对象 */
    private List<SysRole> roles;

    /** 角色组 */
    private Long[] roleIds;

    /** 岗位组 */
    private Long[] postIds;

    /** 角色ID */
    private Long roleId;

    public SysUser()
    {

    }

    public SysUser(Long userId)
    {
        this.userId = userId;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public boolean isAdmin()
    {
        return isAdmin(this.userId);
    }

    public static boolean isAdmin(Long userId)
    {
        return userId != null && 1L == userId;
    }

    public Long getDeptId()
    {
        return deptId;
    }

    public void setDeptId(Long deptId)
    {
        this.deptId = deptId;
    }

    @Xss(message = "用户昵称不能包含脚本字符")
    @Size(min = 0, max = 30, message = "用户昵称长度不能超过30个字符")
    public String getNickName()
    {
        return nickName;
    }

    public void setNickName(String nickName)
    {
        this.nickName = nickName;
    }

    @Xss(message = "用户账号不能包含脚本字符")
    @NotBlank(message = "用户账号不能为空")
    @Size(min = 0, max = 30, message = "用户账号长度不能超过30个字符")
    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    @Email(message = "邮箱格式不正确")
    @Size(min = 0, max = 50, message = "邮箱长度不能超过50个字符")
    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    @Size(min = 0, max = 11, message = "手机号码长度不能超过11个字符")
    public String getPhonenumber()
    {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber)
    {
        this.phonenumber = phonenumber;
    }

    public String getSex()
    {
        return sex;
    }

    public void setSex(String sex)
    {
        this.sex = sex;
    }

    public String getAvatar()
    {
        return avatar;
    }

    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getLoginIp()
    {
        return loginIp;
    }

    public void setLoginIp(String loginIp)
    {
        this.loginIp = loginIp;
    }

    public Date getLoginDate()
    {
        return loginDate;
    }

    public void setLoginDate(Date loginDate)
    {
        this.loginDate = loginDate;
    }

    public Date getPwdUpdateDate()
    {
        return pwdUpdateDate;
    }

    public void setPwdUpdateDate(Date pwdUpdateDate)
    {
        this.pwdUpdateDate = pwdUpdateDate;
    }

    public Integer getEmailVerified()
    {
        return emailVerified;
    }

    public void setEmailVerified(Integer emailVerified)
    {
        this.emailVerified = emailVerified;
    }

    public String getEmailVerifyCode()
    {
        return emailVerifyCode;
    }

    public void setEmailVerifyCode(String emailVerifyCode)
    {
        this.emailVerifyCode = emailVerifyCode;
    }

    public Date getEmailVerifyExpire()
    {
        return emailVerifyExpire;
    }

    public void setEmailVerifyExpire(Date emailVerifyExpire)
    {
        this.emailVerifyExpire = emailVerifyExpire;
    }

    public Integer getPhoneVerified()
    {
        return phoneVerified;
    }

    public void setPhoneVerified(Integer phoneVerified)
    {
        this.phoneVerified = phoneVerified;
    }

    public String getPhoneVerifyCode()
    {
        return phoneVerifyCode;
    }

    public void setPhoneVerifyCode(String phoneVerifyCode)
    {
        this.phoneVerifyCode = phoneVerifyCode;
    }

    public Date getPhoneVerifyExpire()
    {
        return phoneVerifyExpire;
    }

    public void setPhoneVerifyExpire(Date phoneVerifyExpire)
    {
        this.phoneVerifyExpire = phoneVerifyExpire;
    }

    public String getOldEmailVerifyCode()
    {
        return oldEmailVerifyCode;
    }

    public void setOldEmailVerifyCode(String oldEmailVerifyCode)
    {
        this.oldEmailVerifyCode = oldEmailVerifyCode;
    }

    public Date getOldEmailVerifyExpire()
    {
        return oldEmailVerifyExpire;
    }

    public void setOldEmailVerifyExpire(Date oldEmailVerifyExpire)
    {
        this.oldEmailVerifyExpire = oldEmailVerifyExpire;
    }

    public String getNewEmailVerifyCode()
    {
        return newEmailVerifyCode;
    }

    public void setNewEmailVerifyCode(String newEmailVerifyCode)
    {
        this.newEmailVerifyCode = newEmailVerifyCode;
    }

    public Date getNewEmailVerifyExpire()
    {
        return newEmailVerifyExpire;
    }

    public void setNewEmailVerifyExpire(Date newEmailVerifyExpire)
    {
        this.newEmailVerifyExpire = newEmailVerifyExpire;
    }

    public String getRegisterSource()
    {
        return registerSource;
    }

    public void setRegisterSource(String registerSource)
    {
        this.registerSource = registerSource;
    }

    public String getAvatarUrl()
    {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl)
    {
        this.avatarUrl = avatarUrl;
    }

    public String getLastLoginSource()
    {
        return lastLoginSource;
    }

    public void setLastLoginSource(String lastLoginSource)
    {
        this.lastLoginSource = lastLoginSource;
    }

    public SysDept getDept()
    {
        return dept;
    }

    public void setDept(SysDept dept)
    {
        this.dept = dept;
    }

    public List<SysRole> getRoles()
    {
        return roles;
    }

    public void setRoles(List<SysRole> roles)
    {
        this.roles = roles;
    }

    public Long[] getRoleIds()
    {
        return roleIds;
    }

    public void setRoleIds(Long[] roleIds)
    {
        this.roleIds = roleIds;
    }

    public Long[] getPostIds()
    {
        return postIds;
    }

    public void setPostIds(Long[] postIds)
    {
        this.postIds = postIds;
    }

    public Long getRoleId()
    {
        return roleId;
    }

    public void setRoleId(Long roleId)
    {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("userId", getUserId())
            .append("deptId", getDeptId())
            .append("userName", getUserName())
            .append("nickName", getNickName())
            .append("email", getEmail())
            .append("phonenumber", getPhonenumber())
            .append("sex", getSex())
            .append("avatar", getAvatar())
            .append("password", getPassword())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .append("loginIp", getLoginIp())
            .append("loginDate", getLoginDate())
            .append("pwdUpdateDate", getPwdUpdateDate())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("dept", getDept())
            .toString();
    }
}
