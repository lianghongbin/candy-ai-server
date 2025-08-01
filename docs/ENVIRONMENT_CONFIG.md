# 环境配置说明

## 配置文件结构

```
ruoyi-admin/src/main/resources/
├── application.yml          # 主配置文件（通用配置）
├── application-dev.yml      # 开发环境配置
└── application-prod.yml     # 生产环境配置
```

## 环境配置说明

### 1. 开发环境 (dev)

**特点：**
- 本地开发使用
- 启用热部署
- 详细的调试日志
- 较小的连接池配置
- 本地数据库和Redis

**配置内容：**
- 数据库：localhost:3306, root/123456
- Redis：localhost:6379, 密码: 123456
- 日志级别：debug
- 热部署：启用
- 文件上传路径：D:/ruoyi/uploadPath

**启动方式：**
```bash
# 方式1：使用默认配置（dev）
mvn spring-boot:run -pl ruoyi-admin

# 方式2：明确指定dev环境
mvn spring-boot:run -pl ruoyi-admin -Dspring.profiles.active=dev

# 方式3：使用jar包启动
java -jar ruoyi-admin.jar --spring.profiles.active=dev
```

### 2. 生产环境 (prod)

**特点：**
- 生产服务器使用
- 关闭热部署
- 优化的性能配置
- 支持环境变量配置
- 安全配置

**配置内容：**
- 数据库：支持环境变量配置
- Redis：支持环境变量配置
- 日志级别：info
- 热部署：关闭
- 文件上传路径：/opt/candy-ai/uploadPath
- 连接池：更大的配置

**环境变量配置：**
```bash
# 数据库配置
export DB_HOST=your-db-host
export DB_PORT=3306
export DB_NAME=candy_ai
export DB_USERNAME=your-db-user
export DB_PASSWORD=your-db-password

# Redis配置
export REDIS_HOST=your-redis-host
export REDIS_PORT=6379
export REDIS_PASSWORD=your-redis-password

# 服务器配置
export SERVER_PORT=8080

# Druid监控配置
export DRUID_USERNAME=admin
export DRUID_PASSWORD=admin123
```

**启动方式：**
```bash
# 方式1：使用环境变量
export DB_HOST=prod-db-server
export DB_PASSWORD=prod-password
export REDIS_HOST=prod-redis-server
export REDIS_PASSWORD=prod-redis-password
java -jar ruoyi-admin.jar --spring.profiles.active=prod

# 方式2：使用配置文件
java -jar ruoyi-admin.jar --spring.profiles.active=prod --spring.config.location=file:/path/to/config/

# 方式3：Docker环境
docker run -e DB_HOST=prod-db-server -e DB_PASSWORD=prod-password candy-ai-server:latest
```

## 配置优先级

Spring Boot配置优先级（从高到低）：
1. 命令行参数
2. 环境变量
3. 配置文件（application-{profile}.yml）
4. 主配置文件（application.yml）

## 环境切换

### 开发环境切换到生产环境

1. **修改主配置文件：**
```yaml
spring:
  profiles:
    active: prod  # 改为prod
```

2. **或者启动时指定：**
```bash
java -jar ruoyi-admin.jar --spring.profiles.active=prod
```

3. **或者设置环境变量：**
```bash
export SPRING_PROFILES_ACTIVE=prod
java -jar ruoyi-admin.jar
```

### 生产环境配置示例

**application-prod.yml 示例：**
```yaml
spring:
  datasource:
    druid:
      master:
        url: jdbc:mysql://prod-db-server:3306/candy_ai?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8
        username: prod_user
        password: prod_password
  data:
    redis:
      host: prod-redis-server
      port: 6379
      password: prod_redis_password
```

## 安全建议

### 1. 生产环境安全配置

- **数据库密码**：使用强密码，定期更换
- **Redis密码**：使用强密码，限制访问IP
- **Druid监控**：限制访问IP，使用强密码
- **文件上传**：使用绝对路径，设置适当的权限
- **日志配置**：避免记录敏感信息

### 2. 网络安全

- **防火墙**：只开放必要端口
- **SSL/TLS**：生产环境建议启用HTTPS
- **IP白名单**：限制管理后台访问IP

### 3. 监控和日志

- **应用监控**：启用Druid监控
- **日志轮转**：配置日志文件大小和保留时间
- **慢查询监控**：配置数据库慢查询日志

## 部署检查清单

### 开发环境
- [ ] MySQL服务启动，密码正确
- [ ] Redis服务启动，密码正确
- [ ] 数据库已初始化
- [ ] 应用端口8080未被占用
- [ ] 文件上传路径存在且有写权限

### 生产环境
- [ ] 环境变量配置正确
- [ ] 数据库连接正常
- [ ] Redis连接正常
- [ ] 防火墙配置正确
- [ ] 日志目录存在且有写权限
- [ ] 文件上传目录存在且有写权限
- [ ] SSL证书配置（如需要）
- [ ] 监控配置正确

## 常见问题

### Q: 如何查看当前使用的环境？
A: 启动日志中会显示 `The following 1 profile is active: "dev"` 或 `"prod"`

### Q: 如何临时修改配置？
A: 使用命令行参数：`java -jar app.jar --server.port=9090`

### Q: 如何禁用某个配置？
A: 使用 `@ConditionalOnProperty` 注解或在配置文件中设置 `enabled: false`

### Q: 生产环境如何保护敏感信息？
A: 使用环境变量或外部配置文件，不要将密码提交到代码仓库 