package com.vibetempt.candy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.vibetempt.candy.domain.model.OAuthLoginBody;
import com.vibetempt.candy.service.OAuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 第三方登录Controller
 * 
 * @author candy
 */
@Tag(name = "第三方登录管理")
@Anonymous
@RestController
@RequestMapping("/auth/oauth")
public class OAuthController extends BaseController {

    @Autowired
    private OAuthService oauthService;

    /**
     * Google 登录
     */
    @Operation(summary = "Google 登录", description = "通过 Google OAuth 进行登录")
    @Log(title = "Google 登录", businessType = BusinessType.OTHER)
    @PostMapping("/google")
    public AjaxResult googleLogin(@RequestBody OAuthLoginBody loginBody) {
        String msg = oauthService.googleLogin(loginBody);
        if (msg.isEmpty()) {
            return success("Google 登录成功");
        } else {
            return error(msg);
        }
    }

    /**
     * GitHub 登录
     */
    @Operation(summary = "GitHub 登录", description = "通过 GitHub OAuth 进行登录")
    @Log(title = "GitHub 登录", businessType = BusinessType.OTHER)
    @PostMapping("/github")
    public AjaxResult githubLogin(@RequestBody OAuthLoginBody loginBody) {
        String msg = oauthService.githubLogin(loginBody);
        if (msg.isEmpty()) {
            return success("GitHub 登录成功");
        } else {
            return error(msg);
        }
    }

    /**
     * Discord 登录
     */
    @Operation(summary = "Discord 登录", description = "通过 Discord OAuth 进行登录")
    @Log(title = "Discord 登录", businessType = BusinessType.OTHER)
    @PostMapping("/discord")
    public AjaxResult discordLogin(@RequestBody OAuthLoginBody loginBody) {
        String msg = oauthService.discordLogin(loginBody);
        if (msg.isEmpty()) {
            return success("Discord 登录成功");
        } else {
            return error(msg);
        }
    }

    /**
     * Apple 登录
     */
    @Operation(summary = "Apple 登录", description = "通过 Apple OAuth 进行登录")
    @Log(title = "Apple 登录", businessType = BusinessType.OTHER)
    @PostMapping("/apple")
    public AjaxResult appleLogin(@RequestBody OAuthLoginBody loginBody) {
        String msg = oauthService.appleLogin(loginBody);
        if (msg.isEmpty()) {
            return success("Apple 登录成功");
        } else {
            return error(msg);
        }
    }

    /**
     * Facebook 登录
     */
    @Operation(summary = "Facebook 登录", description = "通过 Facebook OAuth 进行登录")
    @Log(title = "Facebook 登录", businessType = BusinessType.OTHER)
    @PostMapping("/facebook")
    public AjaxResult facebookLogin(@RequestBody OAuthLoginBody loginBody) {
        String msg = oauthService.facebookLogin(loginBody);
        if (msg.isEmpty()) {
            return success("Facebook 登录成功");
        } else {
            return error(msg);
        }
    }

    /**
     * 获取 OAuth 授权 URL
     */
    @Operation(summary = "获取 OAuth 授权 URL", description = "获取第三方登录的授权 URL")
    @Log(title = "获取 OAuth 授权 URL", businessType = BusinessType.OTHER)
    @GetMapping("/url")
    public AjaxResult getOAuthUrl(@Parameter(description = "提供商") @RequestParam String provider) {
        String url = oauthService.getOAuthUrl(provider);
        if (url.isEmpty()) {
            return error("不支持的提供商: " + provider);
        } else {
            return success().put("url", url);
        }
    }

    /**
     * OAuth 回调处理
     */
    @Operation(summary = "OAuth 回调处理", description = "处理第三方登录的回调")
    @Log(title = "OAuth 回调处理", businessType = BusinessType.OTHER)
    @GetMapping("/callback/{provider}")
    public AjaxResult oauthCallback(
            @Parameter(description = "提供商") @PathVariable String provider,
            @Parameter(description = "授权码") @RequestParam String code,
            @Parameter(description = "状态码") @RequestParam(required = false) String state) {
        
        String msg = oauthService.handleOAuthCallback(code, state, provider);
        if (msg.isEmpty()) {
            return success("OAuth 登录成功");
        } else {
            return error(msg);
        }
    }
} 