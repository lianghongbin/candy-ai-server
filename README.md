<p align="center">
	<img alt="logo" src="https://oscimg.oschina.net/oscnet/up-d3d0a9303e11d522a06cd263f3079027715.png">
</p>
<h1 align="center" style="margin: 30px 0 30px; font-weight: bold;">Candy AI v1.0.1</h1>
<h4 align="center">基于Spring Boot 3 + Vue 2前后端分离的AI虚拟女友管理系统</h4>
<p align="center">
	<a href="https://github.com/lianghongbin/candy-ai-server/stargazers"><img src="https://img.shields.io/github/stars/lianghongbin/candy-ai-server?style=social"></a>
	<a href="https://github.com/lianghongbin/candy-ai-server"><img src="https://img.shields.io/badge/Candy%20AI-v1.0.1-brightgreen.svg"></a>
	<a href="https://github.com/lianghongbin/candy-ai-server/blob/main/LICENSE"><img src="https://img.shields.io/github/license/lianghongbin/candy-ai-server.svg"></a>
</p>

# Candy AI 虚拟女友管理系统

基于若依框架(RuoYi v3.9.0)开发的AI虚拟女友管理系统，提供角色管理、对话功能、用户管理等核心功能。

## 🛠️ 技术栈

### 后端技术
- **框架**: Spring Boot 3.3.5
- **基础框架**: 若依(RuoYi) v3.9.0
- **数据库**: MySQL 8.0+
- **缓存**: Redis 6.0+
- **ORM**: MyBatis Plus
- **安全**: Spring Security + JWT
- **任务调度**: Quartz
- **API文档**: SpringDoc OpenAPI 3

### 前端技术
- **框架**: Vue 2.6.12
- **UI组件**: Element UI 2.15.14
- **构建工具**: Vue CLI 4.4.6
- **路由**: Vue Router 3.4.9
- **状态管理**: Vuex 3.6.0
- **HTTP客户端**: Axios 0.28.1

### 开发工具
- **构建工具**: Maven 3.6+
- **包管理**: npm 8+
- **版本控制**: Git
- **IDE推荐**: IntelliJ IDEA / VS Code

## 🚀 快速开始

### 环境要求

- **Java**: 17+
- **Maven**: 3.6+
- **Node.js**: 16+
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

#### 方式一：完整启动（推荐）
```bash
# 一键启动前后端
./scripts/start_full.sh

# 停止所有服务
./scripts/stop_full.sh
```

#### 方式二：分别启动
```bash
# 后端启动
./scripts/start.sh -p dev

# 前端启动（新终端）
cd ruoyi-ui && npm run dev
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
- **应用地址**: http://localhost:1024
- **项目目录**: `ruoyi-ui`
- **技术栈**: Vue 2 + Element UI
- **代理配置**: 自动代理到后端 8080 端口

前端项目需要单独启动：
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

### 致谢
- 感谢 [若依(RuoYi)](https://gitee.com/y_project/RuoYi-Vue) 框架提供的优秀基础架构
- 感谢所有开源项目的贡献者

## 🤝 贡献

欢迎提交 Issue 和 Pull Request！

### 贡献指南
1. Fork 本仓库
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 打开 Pull Request

## 📞 联系方式

如有问题，请通过以下方式联系：
- [GitHub Issues](https://github.com/lianghongbin/candy-ai-server/issues)
- [项目主页](https://github.com/lianghongbin/candy-ai-server)

## 📈 项目状态

- **当前版本**: v1.0.1
- **状态**: 开发中
- **最后更新**: 2025-08-01
- **支持**: 持续维护