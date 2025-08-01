# Candy AI 项目初始化指南

## 环境要求

- Java 17+
- MySQL 8.0+
- Maven 3.6+
- Redis (可选，用于缓存)

## 数据库初始化

### 1. 确保MySQL服务正在运行

```bash
# macOS (使用Homebrew)
brew services start mysql

# 检查服务状态
brew services list | grep mysql
```

### 2. 运行数据库初始化脚本

```bash
# 进入项目根目录
cd candy-ai-server

# 运行初始化脚本
./scripts/init_database.sh
```

脚本会提示你输入MySQL root用户密码。如果MySQL root用户没有密码，直接按回车即可。

### 3. 验证数据库初始化

```bash
# 连接到数据库
mysql -u root -p candy_ai

# 查看表列表
SHOW TABLES;
```

你应该能看到以下表：
- 系统表：sys_* (用户、角色、菜单等)
- 定时任务表：QRTZ_* 
- AI相关表：ai_* (角色、对话、消息等)

## 配置数据库连接

确保 `ruoyi-admin/src/main/resources/application-druid.yml` 中的数据库配置正确：

```yaml
master:
    url: jdbc:mysql://localhost:3306/candy_ai?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8
    username: root
    password: "你的MySQL密码"  # 请修改为实际密码
```

## 启动项目

### 1. 编译项目

```bash
mvn clean compile
```

### 2. 启动应用

```bash
# 方式1：使用Maven
mvn spring-boot:run -pl ruoyi-admin

# 方式2：直接运行主类
cd ruoyi-admin
mvn spring-boot:run
```

### 3. 访问应用

- 后台管理：http://localhost:8080
- 默认管理员账号：admin
- 默认管理员密码：admin123
- API文档：http://localhost:8080/swagger-ui.html
- 数据库监控：http://localhost:8080/druid

## 常见问题

### 1. 数据库连接失败

- 检查MySQL服务是否正在运行
- 检查数据库用户名和密码是否正确
- 检查数据库是否已创建

### 2. 端口被占用

如果8080端口被占用，可以修改 `application.yml` 中的端口配置：

```yaml
server:
  port: 8081  # 修改为其他可用端口
```

### 3. Java版本问题

确保使用Java 17或更高版本：

```bash
java -version
```

### 4. Maven依赖下载失败

可以尝试使用阿里云镜像：

```bash
# 在项目根目录创建 .mvn/settings.xml
mkdir -p .mvn
```

然后在 `.mvn/settings.xml` 中添加阿里云镜像配置。

## 项目结构

```
candy-ai-server/
├── ruoyi-admin/          # 主启动模块
├── ruoyi-common/         # 通用工具模块
├── ruoyi-framework/      # 核心框架模块
├── ruoyi-system/         # 系统管理模块
├── ruoyi-quartz/         # 定时任务模块
├── ruoyi-generator/      # 代码生成模块
├── candy-ai/             # AI业务模块
│   ├── candy-ai-domain/  # 领域模型
│   ├── candy-ai-service/ # 业务服务
│   └── candy-ai-api/     # API接口
└── scripts/              # 脚本文件
    └── sql/              # 数据库脚本
``` 