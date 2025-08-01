# Candy AI 脚本说明

本目录包含了 Candy AI 项目的各种启动、停止和管理脚本。

## 📁 脚本列表

### 🚀 启动脚本

#### 1. `start_full.sh` - 完整启动脚本（推荐）
**功能**: 一键启动前后端服务
**用法**: `./scripts/start_full.sh`
**特点**: 
- 自动检查环境（Java、Node.js、MySQL、Redis）
- 先启动后端，再启动前端
- 自动测试前后端通信
- 保存进程PID到文件

#### 2. `start.sh` - 后端启动脚本
**功能**: 单独启动后端服务
**用法**: 
```bash
./scripts/start.sh              # 使用默认环境(dev)
./scripts/start.sh -p dev       # 启动开发环境
./scripts/start.sh -p prod      # 启动生产环境
```
**特点**:
- 支持开发环境和生产环境
- 后台运行并保存PID
- 自动等待服务启动完成

#### 3. `start_frontend.sh` - 前端启动脚本
**功能**: 单独启动前端服务
**用法**: `./scripts/start_frontend.sh`
**特点**:
- 自动检查Node.js和npm环境
- 自动安装依赖（如果未安装）
- 后台运行并保存PID
- 自动等待服务启动完成

### 🛑 停止脚本

#### 1. `stop_full.sh` - 完整停止脚本
**功能**: 停止所有服务
**用法**: `./scripts/stop_full.sh`
**特点**: 停止前端和后端所有相关进程

#### 2. `stop_backend.sh` - 后端停止脚本
**功能**: 单独停止后端服务
**用法**: `./scripts/stop_backend.sh`
**特点**: 通过PID文件优雅停止，并强制清理相关进程

#### 3. `stop_frontend.sh` - 前端停止脚本
**功能**: 单独停止前端服务
**用法**: `./scripts/stop_frontend.sh`
**特点**: 通过PID文件优雅停止，并强制清理相关进程

### 🗄️ 数据库脚本

#### `init_database.sh` - 数据库初始化脚本
**功能**: 初始化数据库和表结构
**用法**: `./scripts/init_database.sh`
**特点**: 
- 自动创建数据库
- 导入SQL脚本
- 支持密码配置

## 🎯 使用场景

### 开发环境
```bash
# 方式一：一键启动（推荐）
./scripts/start_full.sh

# 方式二：分别启动
./scripts/start.sh -p dev        # 启动后端
./scripts/start_frontend.sh      # 启动前端（新终端）
```

### 生产环境
```bash
# 设置环境变量
export DB_HOST=your-db-host
export DB_PASSWORD=your-db-password
export REDIS_HOST=your-redis-host
export REDIS_PASSWORD=your-redis-password

# 启动服务
./scripts/start.sh -p prod
```

### 停止服务
```bash
# 停止所有服务
./scripts/stop_full.sh

# 或分别停止
./scripts/stop_backend.sh
./scripts/stop_frontend.sh
```

## 📊 日志文件

所有脚本的日志文件都保存在 `logs/` 目录下：
- `logs/backend.log` - 后端日志
- `logs/frontend.log` - 前端日志
- `logs/backend.pid` - 后端进程PID
- `logs/frontend.pid` - 前端进程PID

## 🔧 环境要求

### 后端启动要求
- Java 17+
- Maven 3.6+
- MySQL 8.0+
- Redis 6.0+

### 前端启动要求
- Node.js 16+
- npm 8+

## 🚨 常见问题

### 端口冲突
如果遇到端口冲突，脚本会自动选择下一个可用端口：
- 后端默认端口：8080
- 前端默认端口：80（如果被占用会自动选择1024等）

### 进程管理
所有脚本都使用PID文件管理进程，确保可以优雅停止：
```bash
# 查看进程状态
ps aux | grep java | grep ruoyi
ps aux | grep vue-cli-service

# 强制停止（如果脚本无法停止）
pkill -f "RuoYiApplication"
pkill -f "vue-cli-service"
```

### 日志查看
```bash
# 实时查看后端日志
tail -f logs/backend.log

# 实时查看前端日志
tail -f logs/frontend.log
```

## 📝 注意事项

1. **权限**: 确保脚本有执行权限 `chmod +x scripts/*.sh`
2. **目录**: 请在项目根目录下运行脚本
3. **环境**: 确保相关服务（MySQL、Redis）已启动
4. **网络**: 确保端口未被其他程序占用
5. **依赖**: 前端首次运行会自动安装依赖，请保持网络连接 