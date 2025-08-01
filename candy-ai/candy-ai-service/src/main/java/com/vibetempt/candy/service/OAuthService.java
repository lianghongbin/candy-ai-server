package com.vibetempt.candy.service;

import com.vibetempt.candy.domain.model.OAuthLoginBody;

/**
 * 第三方登录服务接口
 * 
 * @author candy
 */
public interface OAuthService {

    /**
     * Google 登录
     * 
     * @param loginBody 登录信息
     * @return 登录结果
     */
    String googleLogin(OAuthLoginBody loginBody);

    /**
     * GitHub 登录
     * 
     * @param loginBody 登录信息
     * @return 登录结果
     */
    String githubLogin(OAuthLoginBody loginBody);

    /**
     * Discord 登录
     * 
     * @param loginBody 登录信息
     * @return 登录结果
     */
    String discordLogin(OAuthLoginBody loginBody);

    /**
     * Apple 登录
     * 
     * @param loginBody 登录信息
     * @return 登录结果
     */
    String appleLogin(OAuthLoginBody loginBody);

    /**
     * Facebook 登录
     * 
     * @param loginBody 登录信息
     * @return 登录结果
     */
    String facebookLogin(OAuthLoginBody loginBody);

    /**
     * 处理 OAuth 回调
     * 
     * @param code 授权码
     * @param state 状态码
     * @param provider 提供商 (google, github)
     * @return 处理结果
     */
    String handleOAuthCallback(String code, String state, String provider);

    /**
     * 获取 OAuth 授权 URL
     * 
     * @param provider 提供商 (google, github)
     * @return 授权 URL
     */
    String getOAuthUrl(String provider);
} 