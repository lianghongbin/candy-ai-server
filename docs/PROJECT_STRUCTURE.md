# Candy AI Server 项目结构

## 📁 项目目录结构

```
candy-ai-server/
├── 📦 后端模块
│   ├── ruoyi-admin/          # 🚀 主启动模块
│   ├── ruoyi-common/         # 📦 RuoYi 公共模块
│   ├── ruoyi-system/         # 🔧 RuoYi 系统模块
│   ├── ruoyi-framework/      # 🏗️ RuoYi 框架模块
│   ├── ruoyi-quartz/         # ⏰ RuoYi 定时任务
│   ├── ruoyi-generator/      # 🔨 RuoYi 代码生成器
│   └── candy-ai/             # 🍬 Candy AI 业务模块（独立）
│       ├── candy-ai-domain/  # 🏛️ AI 实体和 DTO
│       ├── candy-ai-service/ # ⚙️ AI 业务服务
│       └── candy-ai-api/     # 🌐 AI API 接口
├── 🎨 前端模块
│   └── ruoyi-ui/             # 🖥️ RuoYi 管理后台前端（包含 Candy AI 功能）
├── 📚 文档目录
│   ├── docs/                 # 📖 项目文档
│   │   ├── api/             # 📋 API 文档
│   │   └── PROJECT_STRUCTURE.md
│   └── 若依环境使用手册.docx
├── 🔧 脚本目录
│   ├── scripts/              # 🛠️ 项目脚本
│   │   ├── sql/             # 🗄️ SQL 脚本
│   │   ├── start_admin.sh   # 🚀 启动脚本
│   │   ├── stop_admin.sh    # 🛑 停止脚本
│   │   ├── ry.sh           # 🐧 Linux 启动脚本
│   │   └── ry.bat          # 🪟 Windows 启动脚本
│   └── .github/             # 🤖 GitHub Actions
├── 📄 配置文件
│   ├── pom.xml              # 📦 Maven 配置
│   ├── .gitignore           # 🚫 Git 忽略文件
│   ├── LICENSE              # 📜 许可证
│   └── README.md            # 📖 项目说明
└── 🗄️ 数据库文件
    └── ai_girlfriend.db     # 🍬 SQLite 数据库（开发用）
```

## 🏗️ 模块说明

### 后端模块 (Backend Modules)

#### RuoYi 基础模块
- **ruoyi-admin**: 主启动模块，包含 Spring Boot 启动类和配置文件
- **ruoyi-common**: 公共工具类、常量、异常等
- **ruoyi-system**: 系统管理模块，用户、角色、权限等
- **ruoyi-framework**: 框架核心，安全配置、拦截器等
- **ruoyi-quartz**: 定时任务模块
- **ruoyi-generator**: 代码生成器模块

#### Candy AI 业务模块
- **candy-ai-domain**: 实体类和 DTO，定义 AI 相关数据结构
- **candy-ai-service**: 业务服务层，实现 AI 相关业务逻辑
- **candy-ai-api**: API 接口层，提供 RESTful 接口

### 前端模块 (Frontend Modules)
- **ruoyi-ui**: 基于 Vue2 + Element UI 的管理后台界面，包含完整的 RuoYi 管理功能和 Candy AI 业务功能

### 文档目录 (Documentation)
- **docs/api/**: API 接口文档
- **docs/**: 项目相关文档

### 脚本目录 (Scripts)
- **scripts/sql/**: 数据库脚本
  - `ry_20250522.sql`: RuoYi 系统表结构
  - `quartz.sql`: 定时任务表结构
  - `ai_girlfriend_tables.sql`: Candy AI 业务表结构
- **scripts/**: 项目启动和管理脚本

## 🗄️ 数据库设计

### 表前缀规范
- **RuoYi 系统表**: 无前缀（如：sys_user, sys_role）
- **Candy AI 业务表**: `candy_` 前缀（如：candy_ai_character）

### 主要数据表
1. **系统管理表** (RuoYi)
   - `sys_user`: 用户表
   - `sys_role`: 角色表
   - `sys_menu`: 菜单表
   - `sys_dept`: 部门表

2. **Candy AI 业务表**
   - `candy_ai_character`: AI 角色表
   - `candy_ai_conversation`: 对话会话表
   - `candy_ai_message`: 对话消息表
   - `candy_ai_memory`: 角色记忆表

## 🚀 启动方式

### 后端启动
```bash
# 方式1: 使用 Maven
cd ruoyi-admin
mvn spring-boot:run

# 方式2: 使用脚本
./scripts/start_admin.sh
```

### 前端启动
```bash
# RuoYi 管理后台
cd ruoyi-ui
npm install
npm run dev

# RuoYi 管理后台（包含 Candy AI 功能）
cd ruoyi-ui
npm install
npm run dev
```

## 📋 开发规范

### 包命名规范
- **RuoYi 模块**: `com.ruoyi.*`
- **Candy AI 模块**: `com.vibetempt.candy.*`

### 接口命名规范
- 接口类名不以 `I` 开头
- 示例：`AiChatService` 而不是 `IAiChatService`

### 数据库表命名
- RuoYi 表：无前缀
- Candy AI 表：`candy_` 前缀

## 🔧 配置说明

### 数据库配置
- **数据库名**: `candy_ai`
- **用户名**: `root`
- **密码**: `123456`
- **端口**: `3306`

### 应用配置
- **后端端口**: `8080`
- **前端端口**: `80` (RuoYi 管理后台)

## 📝 注意事项

1. **模块独立性**: Candy AI 业务代码完全独立，不污染 RuoYi 原有代码
2. **数据库分离**: 使用表前缀区分系统表和业务表
3. **配置管理**: 敏感配置使用环境变量或外部配置文件
4. **日志管理**: 日志文件统一存放在 `logs/` 目录 