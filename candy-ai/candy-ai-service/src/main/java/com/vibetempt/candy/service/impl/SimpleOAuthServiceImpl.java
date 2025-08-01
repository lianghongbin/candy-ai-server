package com.vibetempt.candy.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.vibetempt.candy.config.AppConfig;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.service.ISysUserService;
import com.vibetempt.candy.domain.entity.SysUserOauth;
import com.vibetempt.candy.domain.model.OAuthLoginBody;
import com.vibetempt.candy.service.OAuthService;
import com.vibetempt.candy.service.mapper.SysUserOauthMapper;

/**
 * 简单第三方登录服务实现类
 * 直接调用第三方 API，不依赖复杂的 OAuth2 框架
 * 
 * @author candy
 */
@Service
public class SimpleOAuthServiceImpl implements OAuthService {

    @Autowired
    private ISysUserService userService;

    @Autowired
    private SysUserOauthMapper userOauthMapper;

    @Value("${candy.oauth.google.client-id:}")
    private String googleClientId;

    @Value("${candy.oauth.google.client-secret:}")
    private String googleClientSecret;

    @Value("${candy.oauth.github.client-id:}")
    private String githubClientId;

    @Value("${candy.oauth.github.client-secret:}")
    private String githubClientSecret;

    @Value("${candy.oauth.discord.client-id:}")
    private String discordClientId;

    @Value("${candy.oauth.discord.client-secret:}")
    private String discordClientSecret;

    @Value("${candy.oauth.apple.client-id:}")
    private String appleClientId;

    @Value("${candy.oauth.apple.client-secret:}")
    private String appleClientSecret;

    @Value("${candy.oauth.facebook.client-id:}")
    private String facebookClientId;

    @Value("${candy.oauth.facebook.client-secret:}")
    private String facebookClientSecret;

    @Autowired
    private AppConfig appConfig;

    @Override
    public String googleLogin(OAuthLoginBody loginBody) {
        try {
            // 使用授权码获取访问令牌
            String accessToken = getGoogleAccessToken(loginBody.getCode());
            if (accessToken == null) {
                return "获取 Google 访问令牌失败";
            }

            // 使用访问令牌获取用户信息
            JSONObject userInfo = getGoogleUserInfo(accessToken);
            if (userInfo == null) {
                return "获取 Google 用户信息失败";
            }

            // 处理用户登录/注册
            return processOAuthUser(userInfo, "google");
        } catch (Exception e) {
            return "Google 登录失败: " + e.getMessage();
        }
    }

    @Override
    public String githubLogin(OAuthLoginBody loginBody) {
        try {
            // 使用授权码获取访问令牌
            String accessToken = getGithubAccessToken(loginBody.getCode());
            if (accessToken == null) {
                return "获取 GitHub 访问令牌失败";
            }

            // 使用访问令牌获取用户信息
            JSONObject userInfo = getGithubUserInfo(accessToken);
            if (userInfo == null) {
                return "获取 GitHub 用户信息失败";
            }

            // 处理用户登录/注册
            return processOAuthUser(userInfo, "github");
        } catch (Exception e) {
            return "GitHub 登录失败: " + e.getMessage();
        }
    }

    @Override
    public String discordLogin(OAuthLoginBody loginBody) {
        try {
            // 使用授权码获取访问令牌
            String accessToken = getDiscordAccessToken(loginBody.getCode());
            if (accessToken == null) {
                return "获取 Discord 访问令牌失败";
            }

            // 使用访问令牌获取用户信息
            JSONObject userInfo = getDiscordUserInfo(accessToken);
            if (userInfo == null) {
                return "获取 Discord 用户信息失败";
            }

            // 处理用户登录/注册
            return processOAuthUser(userInfo, "discord");
        } catch (Exception e) {
            return "Discord 登录失败: " + e.getMessage();
        }
    }

    @Override
    public String appleLogin(OAuthLoginBody loginBody) {
        try {
            // 使用授权码获取访问令牌
            String accessToken = getAppleAccessToken(loginBody.getCode());
            if (accessToken == null) {
                return "获取 Apple 访问令牌失败";
            }

            // 使用访问令牌获取用户信息
            JSONObject userInfo = getAppleUserInfo(accessToken);
            if (userInfo == null) {
                return "获取 Apple 用户信息失败";
            }

            // 处理用户登录/注册
            return processOAuthUser(userInfo, "apple");
        } catch (Exception e) {
            return "Apple 登录失败: " + e.getMessage();
        }
    }

    @Override
    public String facebookLogin(OAuthLoginBody loginBody) {
        try {
            // 使用授权码获取访问令牌
            String accessToken = getFacebookAccessToken(loginBody.getCode());
            if (accessToken == null) {
                return "获取 Facebook 访问令牌失败";
            }

            // 使用访问令牌获取用户信息
            JSONObject userInfo = getFacebookUserInfo(accessToken);
            if (userInfo == null) {
                return "获取 Facebook 用户信息失败";
            }

            // 处理用户登录/注册
            return processOAuthUser(userInfo, "facebook");
        } catch (Exception e) {
            return "Facebook 登录失败: " + e.getMessage();
        }
    }

    @Override
    public String handleOAuthCallback(String code, String state, String provider) {
        try {
            OAuthLoginBody loginBody = new OAuthLoginBody();
            loginBody.setCode(code);
            loginBody.setState(state);

            switch (provider.toLowerCase()) {
                case "google":
                    return googleLogin(loginBody);
                case "github":
                    return githubLogin(loginBody);
                case "discord":
                    return discordLogin(loginBody);
                case "apple":
                    return appleLogin(loginBody);
                case "facebook":
                    return facebookLogin(loginBody);
                default:
                    return "不支持的提供商: " + provider;
            }
        } catch (Exception e) {
            return "OAuth 回调处理失败: " + e.getMessage();
        }
    }

    @Override
    public String getOAuthUrl(String provider) {
        String state = UUID.randomUUID().toString();
        
        switch (provider.toLowerCase()) {
            case "google":
                return "https://accounts.google.com/o/oauth2/v2/auth?" +
                       "client_id=" + googleClientId + "&" +
                       "redirect_uri=" + appConfig.buildCallbackUrl("/auth/oauth/callback/google") + "&" +
                       "response_type=code&" +
                       "scope=openid%20email%20profile&" +
                       "state=" + state;
            case "github":
                return "https://github.com/login/oauth/authorize?" +
                       "client_id=" + githubClientId + "&" +
                       "redirect_uri=" + appConfig.buildCallbackUrl("/auth/oauth/callback/github") + "&" +
                       "response_type=code&" +
                       "scope=read:user%20user:email&" +
                       "state=" + state;
            case "discord":
                return "https://discord.com/api/oauth2/authorize?" +
                       "client_id=" + discordClientId + "&" +
                       "redirect_uri=" + appConfig.buildCallbackUrl("/auth/oauth/callback/discord") + "&" +
                       "response_type=code&" +
                       "scope=identify%20email&" +
                       "state=" + state;
            case "apple":
                return "https://appleid.apple.com/auth/authorize?" +
                       "client_id=" + appleClientId + "&" +
                       "redirect_uri=" + appConfig.buildCallbackUrl("/auth/oauth/callback/apple") + "&" +
                       "response_type=code&" +
                       "scope=name%20email&" +
                       "state=" + state;
            case "facebook":
                return "https://www.facebook.com/v18.0/dialog/oauth?" +
                       "client_id=" + facebookClientId + "&" +
                       "redirect_uri=" + appConfig.buildCallbackUrl("/auth/oauth/callback/facebook") + "&" +
                       "response_type=code&" +
                       "scope=email%20public_profile&" +
                       "state=" + state;
            default:
                return "";
        }
    }

    /**
     * 获取 Google 访问令牌
     */
    private String getGoogleAccessToken(String code) {
        try {
            String url = "https://oauth2.googleapis.com/token";
                               String postData = "client_id=" + googleClientId +
                                   "&client_secret=" + googleClientSecret +
                                   "&code=" + code +
                                   "&grant_type=authorization_code" +
                                   "&redirect_uri=" + appConfig.buildCallbackUrl("/auth/oauth/callback/google");

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setDoOutput(true);

            byte[] postDataBytes = postData.getBytes(StandardCharsets.UTF_8);
            con.getOutputStream().write(postDataBytes);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject jsonResponse = JSON.parseObject(response.toString());
            return jsonResponse.getString("access_token");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取 Google 用户信息
     */
    private JSONObject getGoogleUserInfo(String accessToken) {
        try {
            String url = "https://www.googleapis.com/oauth2/v2/userinfo?access_token=" + accessToken;
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return JSON.parseObject(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取 GitHub 访问令牌
     */
    private String getGithubAccessToken(String code) {
        try {
            String url = "https://github.com/login/oauth/access_token";
                               String postData = "client_id=" + githubClientId +
                                   "&client_secret=" + githubClientSecret +
                                   "&code=" + code +
                                   "&redirect_uri=" + appConfig.buildCallbackUrl("/auth/oauth/callback/github");

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            byte[] postDataBytes = postData.getBytes(StandardCharsets.UTF_8);
            con.getOutputStream().write(postDataBytes);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject jsonResponse = JSON.parseObject(response.toString());
            return jsonResponse.getString("access_token");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取 GitHub 用户信息
     */
    private JSONObject getGithubUserInfo(String accessToken) {
        try {
            String url = "https://api.github.com/user";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", "token " + accessToken);
            con.setRequestProperty("Accept", "application/vnd.github.v3+json");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return JSON.parseObject(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取 Discord 访问令牌
     */
    private String getDiscordAccessToken(String code) {
        try {
            String url = "https://discord.com/api/oauth2/token";
                               String postData = "client_id=" + discordClientId +
                                   "&client_secret=" + discordClientSecret +
                                   "&code=" + code +
                                   "&grant_type=authorization_code" +
                                   "&redirect_uri=" + appConfig.buildCallbackUrl("/auth/oauth/callback/discord");

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setDoOutput(true);

            byte[] postDataBytes = postData.getBytes(StandardCharsets.UTF_8);
            con.getOutputStream().write(postDataBytes);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject jsonResponse = JSON.parseObject(response.toString());
            return jsonResponse.getString("access_token");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取 Discord 用户信息
     */
    private JSONObject getDiscordUserInfo(String accessToken) {
        try {
            String url = "https://discord.com/api/users/@me";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", "Bearer " + accessToken);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return JSON.parseObject(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取 Apple 访问令牌
     */
    private String getAppleAccessToken(String code) {
        try {
            String url = "https://appleid.apple.com/auth/token";
                               String postData = "client_id=" + appleClientId +
                                   "&client_secret=" + appleClientSecret +
                                   "&code=" + code +
                                   "&grant_type=authorization_code" +
                                   "&redirect_uri=" + appConfig.buildCallbackUrl("/auth/oauth/callback/apple");

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setDoOutput(true);

            byte[] postDataBytes = postData.getBytes(StandardCharsets.UTF_8);
            con.getOutputStream().write(postDataBytes);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject jsonResponse = JSON.parseObject(response.toString());
            return jsonResponse.getString("access_token");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取 Apple 用户信息
     */
    private JSONObject getAppleUserInfo(String accessToken) {
        try {
            String url = "https://appleid.apple.com/auth/userinfo";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", "Bearer " + accessToken);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return JSON.parseObject(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取 Facebook 访问令牌
     */
    private String getFacebookAccessToken(String code) {
        try {
            String url = "https://graph.facebook.com/v18.0/oauth/access_token";
                               String postData = "client_id=" + facebookClientId +
                                   "&client_secret=" + facebookClientSecret +
                                   "&code=" + code +
                                   "&grant_type=authorization_code" +
                                   "&redirect_uri=" + appConfig.buildCallbackUrl("/auth/oauth/callback/facebook");

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setDoOutput(true);

            byte[] postDataBytes = postData.getBytes(StandardCharsets.UTF_8);
            con.getOutputStream().write(postDataBytes);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject jsonResponse = JSON.parseObject(response.toString());
            return jsonResponse.getString("access_token");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取 Facebook 用户信息
     */
    private JSONObject getFacebookUserInfo(String accessToken) {
        try {
            String url = "https://graph.facebook.com/v18.0/me?fields=id,name,email,picture&access_token=" + accessToken;
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return JSON.parseObject(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 处理 OAuth 用户信息
     */
    private String processOAuthUser(JSONObject userInfo, String provider) {
        try {
            String email = userInfo.getString("email");
            String name = userInfo.getString("name");
            String oauthId = userInfo.getString("id");
            String avatarUrl = userInfo.getString("avatar_url");

            if (email == null || email.isEmpty()) {
                return "无法获取邮箱信息";
            }

            // 检查用户是否已存在
            SysUser existingUser = new SysUser();
            existingUser.setEmail(email);
            
            if (userService.checkEmailUnique(existingUser)) {
                // 用户不存在，创建新用户
                SysUser newUser = new SysUser();
                newUser.setUserName(email);
                newUser.setNickName(name != null ? name : "OAuth用户");
                newUser.setEmail(email);
                newUser.setStatus("0");
                // 设置随机密码
                newUser.setPassword("oauth_" + UUID.randomUUID().toString());
                
                boolean regFlag = userService.registerUser(newUser);
                if (!regFlag) {
                    return "用户注册失败";
                }
            }

            // 保存 OAuth 关联信息
            SysUserOauth userOauth = new SysUserOauth();
            userOauth.setOauthType(provider);
            userOauth.setOauthUserId(oauthId);
            userOauth.setOauthUsername(name);
            userOauth.setOauthEmail(email);
            userOauth.setOauthAvatarUrl(avatarUrl);
            userOauth.setCreateTime(new Date());
            
            userOauthMapper.insertSysUserOauth(userOauth);

            return "";
        } catch (Exception e) {
            return "OAuth 用户处理失败: " + e.getMessage();
        }
    }
} 