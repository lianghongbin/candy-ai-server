<p align="center">
	<img alt="logo" src="https://oscimg.oschina.net/oscnet/up-d3d0a9303e11d522a06cd263f3079027715.png">
</p>
<h1 align="center" style="margin: 30px 0 30px; font-weight: bold;">RuoYi v3.9.0</h1>
<h4 align="center">基于SpringBoot+Vue前后端分离的Java快速开发框架</h4>
<p align="center">
	<a href="https://gitee.com/y_project/RuoYi-Vue/stargazers"><img src="https://gitee.com/y_project/RuoYi-Vue/badge/star.svg?theme=dark"></a>
	<a href="https://gitee.com/y_project/RuoYi-Vue"><img src="https://img.shields.io/badge/RuoYi-v3.9.0-brightgreen.svg"></a>
	<a href="https://gitee.com/y_project/RuoYi-Vue/blob/master/LICENSE"><img src="https://img.shields.io/github/license/mashape/apistatus.svg"></a>
</p>

# Candy AI 虚拟女友管理系统

基于若依框架开发的AI虚拟女友管理系统，提供角色管理、对话功能、用户管理等核心功能。

## 🚀 快速开始

### 环境要求

- **Java**: 17+
- **Maven**: 3.6+
- **MySQL**: 8.0+
- **Redis**: 6.0+

### 1. 克隆项目

```bash
git clone <repository-url>
cd candy-ai-server
```

### 2. 初始化数据库

```bash
# 运行数据库初始化脚本
./scripts/init_database.sh
```

### 3. 启动应用

#### 开发环境
```bash
# 使用启动脚本（推荐）
./scripts/start.sh -p dev

# 或使用Maven
mvn spring-boot:run -pl ruoyi-admin -Dspring.profiles.active=dev
```

#### 生产环境
```bash
# 设置环境变量
export DB_HOST=your-db-host
export DB_PASSWORD=your-db-password
export REDIS_HOST=your-redis-host
export REDIS_PASSWORD=your-redis-password

# 启动应用
./scripts/start.sh -p prod
```

## 📁 项目结构

```
candy-ai-server/
├── ruoyi-admin/          # 主应用模块
├── ruoyi-framework/      # 框架核心
├── ruoyi-system/         # 系统模块
├── ruoyi-common/         # 通用工具
├── ruoyi-quartz/         # 定时任务
├── ruoyi-generator/      # 代码生成
├── candy-ai/             # Candy AI 业务模块
│   ├── candy-ai-domain/  # 领域模型
│   ├── candy-ai-service/ # 业务服务
│   └── candy-ai-api/     # API接口
├── ruoyi-ui/             # 前端项目
├── scripts/              # 脚本文件
└── docs/                 # 文档目录
```

## 🔧 环境配置

### 开发环境 (dev)
- **配置文件**: `application-dev.yml`
- **数据库**: localhost:3306, root/123456
- **Redis**: localhost:6379, 密码: 123456
- **日志级别**: debug
- **热部署**: 启用

### 生产环境 (prod)
- **配置文件**: `application-prod.yml`
- **数据库**: 支持环境变量配置
- **Redis**: 支持环境变量配置
- **日志级别**: info
- **热部署**: 关闭

## 🌐 访问信息

### 后端服务
- **应用地址**: http://localhost:8080
- **API文档**: http://localhost:8080/swagger-ui.html
- **数据库监控**: http://localhost:8080/druid
- **默认管理员账号**: admin
- **默认管理员密码**: admin123

### 前端项目
前端项目在 `ruoyi-ui` 目录，需要单独启动：
```bash
cd ruoyi-ui
npm install
npm run dev
```

## 📚 文档

- [环境配置说明](docs/ENVIRONMENT_CONFIG.md) - 详细的环境配置指南
- [配置文件指南](docs/CONFIG_GUIDE.md) - 配置文件使用说明

## 🔧 开发

### 编译项目
```bash
mvn clean compile
```

### 打包项目
```bash
mvn clean package -DskipTests
```

### 运行测试
```bash
mvn test
```

## 🚨 常见问题

### 数据库连接失败
- 检查MySQL服务是否启动
- 确认用户名密码是否正确
- 检查数据库是否已创建

### Redis连接失败
- 检查Redis服务是否启动
- 确认密码是否正确
- 检查防火墙设置

### 应用启动失败
- 检查配置文件语法
- 确认端口是否被占用
- 查看启动日志

## 📄 许可证

本项目基于 [MIT License](LICENSE) 开源。

## 🤝 贡献

欢迎提交 Issue 和 Pull Request！

## 📞 联系方式

如有问题，请通过以下方式联系：
- 提交 Issue
- 发送邮件