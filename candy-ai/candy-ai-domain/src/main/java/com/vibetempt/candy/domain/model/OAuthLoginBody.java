package com.vibetempt.candy.domain.model;

import jakarta.validation.constraints.NotBlank;

/**
 * 第三方登录请求体
 * 
 * @author candy
 */
public class OAuthLoginBody {
    
    /** 第三方平台类型 */
    @NotBlank(message = "第三方平台类型不能为空")
    private String oauthType;

    /** 授权码 */
    @NotBlank(message = "授权码不能为空")
    private String code;

    /** 状态码（CSRF保护） */
    private String state;

    /** 重定向URI */
    private String redirectUri;

    public OAuthLoginBody() {
    }

    public OAuthLoginBody(String oauthType, String code) {
        this.oauthType = oauthType;
        this.code = code;
    }

    public String getOauthType() {
        return oauthType;
    }

    public void setOauthType(String oauthType) {
        this.oauthType = oauthType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    @Override
    public String toString() {
        return "OAuthLoginBody{" +
                "oauthType='" + oauthType + '\'' +
                ", code='" + code + '\'' +
                ", state='" + state + '\'' +
                ", redirectUri='" + redirectUri + '\'' +
                '}';
    }
} 