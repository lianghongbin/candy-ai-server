# OAuth 第三方登录配置指南

## 📋 **概述**

本文档介绍如何配置 Google、GitHub、Discord、Apple 和 Facebook OAuth 应用，以支持 Candy AI 的第三方登录功能。

## 🔧 **Google OAuth 应用配置**

### 1. 创建 Google Cloud 项目
1. 访问 [Google Cloud Console](https://console.cloud.google.com/)
2. 点击"选择项目" → "新建项目"
3. 输入项目名称：`Candy AI OAuth`
4. 点击"创建"

### 2. 启用 Google+ API
1. 在左侧菜单中选择"API 和服务" → "库"
2. 搜索 "Google+ API" 并启用
3. 搜索 "Google OAuth2 API" 并启用

### 3. 创建 OAuth 2.0 客户端 ID
1. 在左侧菜单中选择"API 和服务" → "凭据"
2. 点击"创建凭据" → "OAuth 客户端 ID"
3. 选择应用类型：`Web 应用`
4. 输入名称：`Candy AI Web Client`
5. 添加授权重定向 URI：
   - `http://localhost:8080/auth/oauth/callback/google` (开发环境)
   - `https://your-domain.com/auth/oauth/callback/google` (生产环境)
6. 点击"创建"

### 4. 获取 Client ID 和 Client Secret
- **Client ID**: 复制显示的客户端 ID
- **Client Secret**: 点击"下载 JSON"获取客户端密钥

## 🔧 **GitHub OAuth 应用配置**

### 1. 创建 GitHub OAuth App
1. 访问 [GitHub Developer Settings](https://github.com/settings/developers)
2. 点击"OAuth Apps" → "New OAuth App"
3. 填写应用信息：
   - **Application name**: `Candy AI`
   - **Homepage URL**: `http://localhost:8080` (开发环境)
   - **Application description**: `Candy AI 虚拟女友管理系统`
   - **Authorization callback URL**: `http://localhost:8080/auth/oauth/callback/github`

### 2. 获取 Client ID 和 Client Secret
- **Client ID**: 复制显示的客户端 ID
- **Client Secret**: 点击"Generate a new client secret"生成新的客户端密钥

## 🔧 **Discord OAuth 应用配置**

### 1. 创建 Discord OAuth App
1. 访问 [Discord Developer Portal](https://discord.com/developers/applications)
2. 点击"New Application"
3. 输入应用名称：`Candy AI`
4. 点击"Create"

### 2. 配置 OAuth2 设置
1. 在左侧菜单中选择"OAuth2"
2. 点击"General"
3. 添加重定向 URI：`http://localhost:8080/auth/oauth/callback/discord`
4. 保存设置

### 3. 获取 Client ID 和 Client Secret
- **Client ID**: 复制显示的客户端 ID
- **Client Secret**: 点击"Reset Secret"生成新的客户端密钥

## 🔧 **Apple OAuth 应用配置**

### 1. 创建 Apple Developer 账号
1. 访问 [Apple Developer](https://developer.apple.com/)
2. 注册 Apple Developer 账号（需要付费）

### 2. 创建 App ID
1. 在 Apple Developer Console 中创建新的 App ID
2. 启用 "Sign In with Apple" 功能
3. 配置域名和重定向 URL

### 3. 创建 Service ID
1. 创建新的 Service ID
2. 配置域名：`localhost`
3. 添加重定向 URL：`http://localhost:8080/auth/oauth/callback/apple`

### 4. 获取 Client ID 和 Client Secret
- **Client ID**: 使用 Service ID 作为客户端 ID
- **Client Secret**: 生成 JWT 格式的客户端密钥

## 🔧 **Facebook OAuth 应用配置**

### 1. 创建 Facebook 应用
1. 访问 [Facebook Developers](https://developers.facebook.com/)
2. 点击"创建应用"
3. 选择"消费者"应用类型
4. 输入应用名称：`Candy AI`

### 2. 配置 Facebook 登录
1. 在左侧菜单中选择"产品" → "Facebook 登录"
2. 点击"设置"
3. 添加有效 OAuth 重定向 URI：`http://localhost:8080/auth/oauth/callback/facebook`
4. 保存设置

### 3. 获取 Client ID 和 Client Secret
- **Client ID**: 复制应用 ID
- **Client Secret**: 复制应用密钥

## ⚙️ **环境变量配置**

### 开发环境配置
在 `application-dev.yml` 中配置：

```yaml
candy:
  oauth:
    google:
      client-id: your-google-client-id
      client-secret: your-google-client-secret
      redirect-uri: http://localhost:8080/auth/oauth/callback/google
    github:
      client-id: your-github-client-id
      client-secret: your-github-client-secret
      redirect-uri: http://localhost:8080/auth/oauth/callback/github
    discord:
      client-id: your-discord-client-id
      client-secret: your-discord-client-secret
      redirect-uri: http://localhost:8080/auth/oauth/callback/discord
    apple:
      client-id: your-apple-client-id
      client-secret: your-apple-client-secret
      redirect-uri: http://localhost:8080/auth/oauth/callback/apple
    facebook:
      client-id: your-facebook-client-id
      client-secret: your-facebook-client-secret
      redirect-uri: http://localhost:8080/auth/oauth/callback/facebook
```

### 生产环境配置
在 `application-prod.yml` 中配置：

```yaml
candy:
  oauth:
    google:
      client-id: ${GOOGLE_CLIENT_ID}
      client-secret: ${GOOGLE_CLIENT_SECRET}
      redirect-uri: https://your-domain.com/auth/oauth/callback/google
    github:
      client-id: ${GITHUB_CLIENT_ID}
      client-secret: ${GITHUB_CLIENT_SECRET}
      redirect-uri: https://your-domain.com/auth/oauth/callback/github
```

### 环境变量设置
```bash
# 开发环境
export GOOGLE_CLIENT_ID="your-google-client-id"
export GOOGLE_CLIENT_SECRET="your-google-client-secret"
export GITHUB_CLIENT_ID="your-github-client-id"
export GITHUB_CLIENT_SECRET="your-github-client-secret"
export DISCORD_CLIENT_ID="your-discord-client-id"
export DISCORD_CLIENT_SECRET="your-discord-client-secret"
export APPLE_CLIENT_ID="your-apple-client-id"
export APPLE_CLIENT_SECRET="your-apple-client-secret"
export FACEBOOK_CLIENT_ID="your-facebook-client-id"
export FACEBOOK_CLIENT_SECRET="your-facebook-client-secret"

# 生产环境
export GOOGLE_CLIENT_ID="your-production-google-client-id"
export GOOGLE_CLIENT_SECRET="your-production-google-client-secret"
export GITHUB_CLIENT_ID="your-production-github-client-id"
export GITHUB_CLIENT_SECRET="your-production-github-client-secret"
export DISCORD_CLIENT_ID="your-production-discord-client-id"
export DISCORD_CLIENT_SECRET="your-production-discord-client-secret"
export APPLE_CLIENT_ID="your-production-apple-client-id"
export APPLE_CLIENT_SECRET="your-production-apple-client-secret"
export FACEBOOK_CLIENT_ID="your-production-facebook-client-id"
export FACEBOOK_CLIENT_SECRET="your-production-facebook-client-secret"
```

## 🧪 **测试 OAuth 登录**

### 1. 获取 OAuth 授权 URL
```bash
# Google OAuth URL
curl -X GET "http://localhost:8080/auth/oauth/url?provider=google"

# GitHub OAuth URL
curl -X GET "http://localhost:8080/auth/oauth/url?provider=github"
```

### 2. 测试 OAuth 回调
1. 在浏览器中访问获取到的 OAuth URL
2. 完成第三方登录授权
3. 系统会自动跳转到回调地址并处理登录

### 3. 验证用户注册
```bash
# 检查用户是否已注册
curl -X GET "http://localhost:8080/auth/email/check?email=user@example.com"
```

## 🔒 **安全注意事项**

1. **Client Secret 保护**
   - 永远不要在客户端代码中暴露 Client Secret
   - 使用环境变量或配置文件存储敏感信息
   - 定期轮换 Client Secret

2. **重定向 URI 验证**
   - 确保重定向 URI 与 OAuth 应用配置完全匹配
   - 使用 HTTPS 在生产环境中

3. **状态参数验证**
   - 验证 OAuth 回调中的 state 参数
   - 防止 CSRF 攻击

## 🐛 **常见问题**

### 1. "redirect_uri_mismatch" 错误
- 检查重定向 URI 是否与 OAuth 应用配置完全匹配
- 确保协议、域名、端口都正确

### 2. "invalid_client" 错误
- 检查 Client ID 和 Client Secret 是否正确
- 确保 OAuth 应用已正确配置

### 3. "access_denied" 错误
- 用户取消了授权
- 检查 OAuth 应用权限配置

## 📞 **技术支持**

如果遇到配置问题，请检查：
1. OAuth 应用配置是否正确
2. 环境变量是否已设置
3. 网络连接是否正常
4. 应用日志中的错误信息 