package com.vibetempt.candy.domain.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 邮箱注册请求体
 * 
 * @author candy
 */
public class EmailRegisterBody {
    
    /** 邮箱地址 */
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;

    /** 密码 */
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度必须在6到20个字符之间")
    private String password;

    /** 验证码 */
    private String code;

    /** 验证码UUID */
    private String uuid;

    /** 昵称 */
    private String nickName;

    public EmailRegisterBody() {
    }

    public EmailRegisterBody(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "EmailRegisterBody{" +
                "email='" + email + '\'' +
                ", password='[PROTECTED]'" +
                ", code='" + code + '\'' +
                ", uuid='" + uuid + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }
} 