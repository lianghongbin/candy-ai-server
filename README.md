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

基于 RuoYi-Vue 的 AI 虚拟女友管理系统，提供完整的角色管理、对话交互、记忆管理等功能。

## 🏗️ 项目架构

### 技术栈
- **后端**: Spring Boot 3.3.5 + MyBatis Plus + Redis + MySQL
- **前端**: Vue3 + Element Plus
- **基础框架**: RuoYi-Vue (Spring Boot 3 版本)

### 模块结构
```
candy-ai-server/
├── ruoyi-admin/          # 🚀 主启动模块（RuoYi 管理后台）
├── ruoyi-common/         # 📦 RuoYi 公共模块
├── ruoyi-system/         # 🔧 RuoYi 系统模块
├── ruoyi-framework/      # 🏗️ RuoYi 框架模块
├── ruoyi-quartz/         # ⏰ RuoYi 定时任务
├── ruoyi-generator/      # 🔨 RuoYi 代码生成器
├── candy-ai/             # 🎭 Candy AI 业务模块（独立）
│   ├── candy-ai-domain/  # 📋 AI 实体和 DTO
│   ├── candy-ai-service/ # ⚙️ AI 业务服务
│   └── candy-ai-api/     # 🌐 AI API 接口
├── ruoyi-ui/             # 🎨 前端界面（RuoYi 管理后台）
└── docs/                 # 📚 项目文档
```

### 模块职责

#### RuoYi 基础模块
- **ruoyi-admin**: 主启动模块，包含完整的 RuoYi 管理后台功能
- **ruoyi-common**: 公共工具类、常量、异常等
- **ruoyi-system**: 系统管理功能（用户、角色、菜单等）
- **ruoyi-framework**: 框架核心功能（安全、配置等）
- **ruoyi-quartz**: 定时任务管理
- **ruoyi-generator**: 代码生成器

#### Candy AI 业务模块
- **candy-ai-domain**: AI 相关实体类、DTO、枚举等
- **candy-ai-service**: AI 业务逻辑服务层
- **candy-ai-api**: AI 相关 RESTful API 接口

## 🚀 快速开始

### 环境要求
- JDK 17+
- Maven 3.6+
- MySQL 8.0+
- Redis 6.0+
- Node.js 16+

### 后端启动
```bash
# 1. 创建数据库
mysql -u root -p123456 -e "CREATE DATABASE IF NOT EXISTS candy_ai CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"

# 2. 导入 SQL
mysql -u root -p123456 candy_ai < scripts/sql/ry_20250522.sql

# 3. 启动后端
cd ruoyi-admin
mvn spring-boot:run

# 或者使用脚本启动
./scripts/start_admin.sh
```

### 前端启动
```bash
cd ruoyi-ui
npm install
npm run dev
```

### 访问地址
- **管理后台**: http://localhost:8080
- **前端界面**: http://localhost:80
- **API 文档**: http://localhost:8080/swagger-ui.html

### 默认账号
- **用户名**: admin
- **密码**: admin123

## 📋 功能特性

### 系统管理
- ✅ 用户管理
- ✅ 角色管理
- ✅ 菜单管理
- ✅ 部门管理
- ✅ 字典管理
- ✅ 参数设置
- ✅ 通知公告
- ✅ 日志管理
- ✅ 系统监控

### AI 功能
- 🎭 AI 角色管理
- 💬 AI 对话交互
- 🧠 AI 记忆管理
- 📊 对话历史记录
- 🔄 角色状态管理

## 🗄️ 数据库设计

### 表前缀策略
- **RuoYi 系统表**: 无前缀（如：`sys_user`, `sys_role`）
- **Candy AI 业务表**: `candy_` 前缀（如：`candy_ai_character`, `candy_ai_conversation`）

### 主要业务表
- `candy_ai_character` - AI 角色表
- `candy_ai_conversation` - 对话会话表
- `candy_ai_message` - 对话消息表
- `candy_ai_memory` - AI 记忆表

## 🔧 开发指南

### 添加新的 AI 功能
1. 在 `candy-ai-domain` 中定义实体类
2. 在 `candy-ai-service` 中实现业务逻辑
3. 在 `candy-ai-api` 中提供 RESTful 接口
4. 在 `ruoyi-admin` 中注册新模块

### 代码规范
- 遵循 RuoYi 的代码规范
- 使用 MyBatis Plus 进行数据库操作
- 统一使用 `com.vibetempt.candy` 包名
- 接口命名不包含 'I' 前缀

## 📝 更新日志

### v1.0.0 (2025-01-01)
- ✅ 基于 RuoYi Spring Boot 3 版本
- ✅ 独立的 Candy AI 业务模块
- ✅ 完整的模块化架构
- ✅ AI 角色管理功能
- ✅ 对话交互功能
- ✅ 记忆管理功能

## 📄 许可证

本项目基于 MIT 许可证开源。

## 🤝 贡献

欢迎提交 Issue 和 Pull Request！

---

**Candy AI** - 让 AI 虚拟女友更智能、更贴心！ 💕