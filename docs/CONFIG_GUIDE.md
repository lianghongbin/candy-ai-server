# 配置文件指南

## 当前配置状态

### ✅ 已配置的密码

1. **Redis密码** - 已在 `application.yml` 中配置
   ```yaml
   spring:
     data:
       redis:
         password: "123456"
   ```

2. **MySQL数据库密码** - 已在 `application-druid.yml` 中配置
   ```yaml
   spring:
     datasource:
       druid:
         master:
           password: "123456"
   ```

## 在不同机器上部署时的配置

### 1. 开发环境配置（当前状态）

当前配置文件已经包含了密码，可以直接使用：

- **MySQL**: localhost:3306, root/123456
- **Redis**: localhost:6379, 密码: 123456

### 2. 生产环境配置建议

为了安全起见，建议在生产环境中使用环境变量或外部配置文件：

#### 方案一：使用环境变量

修改 `application-druid.yml`：
```yaml
spring:
    datasource:
        druid:
            master:
                url: jdbc:mysql://${DB_HOST:localhost}:${DB_PORT:3306}/${DB_NAME:candy_ai}?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8
                username: ${DB_USERNAME:root}
                password: ${DB_PASSWORD:123456}
```

修改 `application.yml`：
```yaml
spring:
  data:
    redis:
      host: ${REDIS_HOST:localhost}
      port: ${REDIS_PORT:6379}
      password: ${REDIS_PASSWORD:123456}
```

然后在启动时设置环境变量：
```bash
export DB_HOST=your-db-host
export DB_PASSWORD=your-db-password
export REDIS_HOST=your-redis-host
export REDIS_PASSWORD=your-redis-password
```

#### 方案二：使用外部配置文件

创建 `application-prod.yml`：
```yaml
spring:
  datasource:
    druid:
      master:
        url: jdbc:mysql://prod-db-host:3306/candy_ai?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8
        username: prod_user
        password: prod_password
  data:
    redis:
      host: prod-redis-host
      port: 6379
      password: prod_redis_password
```

启动时指定profile：
```bash
java -jar ruoyi-admin.jar --spring.profiles.active=prod
```

### 3. 配置文件模板

为了便于部署，建议创建以下配置文件模板：

#### `application-dev.yml` (开发环境)
```yaml
spring:
  datasource:
    druid:
      master:
        url: jdbc:mysql://localhost:3306/candy_ai?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8
        username: root
        password: "123456"
  data:
    redis:
      host: localhost
      port: 6379
      password: "123456"
```

#### `application-test.yml` (测试环境)
```yaml
spring:
  datasource:
    druid:
      master:
        url: jdbc:mysql://test-db-host:3306/candy_ai?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8
        username: test_user
        password: "test_password"
  data:
    redis:
      host: test-redis-host
      port: 6379
      password: "test_redis_password"
```

#### `application-prod.yml` (生产环境)
```yaml
spring:
  datasource:
    druid:
      master:
        url: jdbc:mysql://prod-db-host:3306/candy_ai?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8
        username: prod_user
        password: "prod_password"
  data:
    redis:
      host: prod-redis-host
      port: 6379
      password: "prod_redis_password"
```

## 部署检查清单

### 1. 数据库配置检查
- [ ] 数据库服务器地址正确
- [ ] 数据库端口正确（默认3306）
- [ ] 数据库名称正确（candy_ai）
- [ ] 用户名和密码正确
- [ ] 数据库已创建并导入了表结构

### 2. Redis配置检查
- [ ] Redis服务器地址正确
- [ ] Redis端口正确（默认6379）
- [ ] Redis密码正确
- [ ] Redis服务正在运行

### 3. 应用配置检查
- [ ] 应用端口配置正确（默认8080）
- [ ] 文件上传路径配置正确
- [ ] 日志级别配置合适

## 安全建议

1. **生产环境密码安全**
   - 使用强密码
   - 定期更换密码
   - 使用环境变量或外部配置文件存储密码
   - 不要将密码提交到代码仓库

2. **网络安全**
   - 配置防火墙规则
   - 使用SSL/TLS连接
   - 限制数据库和Redis的访问IP

3. **监控和日志**
   - 启用数据库连接池监控
   - 配置慢查询日志
   - 监控Redis连接状态

## 快速部署命令

```bash
# 1. 克隆项目
git clone <repository-url>
cd candy-ai-server

# 2. 初始化数据库
./scripts/init_database.sh

# 3. 编译项目
mvn clean package -DskipTests

# 4. 启动应用（开发环境）
java -jar ruoyi-admin/target/ruoyi-admin.jar

# 5. 启动应用（生产环境）
java -jar ruoyi-admin/target/ruoyi-admin.jar --spring.profiles.active=prod
```

## 常见问题

### Q: 数据库连接失败
A: 检查数据库服务是否启动，用户名密码是否正确，网络连接是否正常

### Q: Redis连接失败
A: 检查Redis服务是否启动，密码是否正确，防火墙是否阻止连接

### Q: 应用启动失败
A: 检查配置文件语法是否正确，端口是否被占用，依赖服务是否正常 