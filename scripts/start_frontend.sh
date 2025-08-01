#!/bin/bash
# Candy AI 前端启动脚本

set -e

# 颜色定义
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# 项目根目录
PROJECT_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
FRONTEND_DIR="$PROJECT_ROOT/ruoyi-ui"

echo -e "${BLUE}================================${NC}"
echo -e "${BLUE}    Candy AI 前端启动脚本${NC}"
echo -e "${BLUE}================================${NC}"

# 检查Node.js环境
echo -e "${YELLOW}检查Node.js环境...${NC}"
if ! command -v node &> /dev/null; then
    echo -e "${RED}错误: 未找到Node.js环境${NC}"
    exit 1
fi
echo -e "${GREEN}✓ Node.js环境正常${NC}"

# 检查npm环境
echo -e "${YELLOW}检查npm环境...${NC}"
if ! command -v npm &> /dev/null; then
    echo -e "${RED}错误: 未找到npm环境${NC}"
    exit 1
fi
echo -e "${GREEN}✓ npm环境正常${NC}"

# 检查前端目录
echo -e "${YELLOW}检查前端项目目录...${NC}"
if [ ! -d "$FRONTEND_DIR" ]; then
    echo -e "${RED}错误: 前端项目目录不存在: $FRONTEND_DIR${NC}"
    exit 1
fi
echo -e "${GREEN}✓ 前端项目目录存在${NC}"

# 检查package.json
if [ ! -f "$FRONTEND_DIR/package.json" ]; then
    echo -e "${RED}错误: package.json文件不存在${NC}"
    exit 1
fi

# 检查node_modules
if [ ! -d "$FRONTEND_DIR/node_modules" ]; then
    echo -e "${YELLOW}检测到未安装依赖，正在安装...${NC}"
    cd "$FRONTEND_DIR"
    npm install
    cd "$PROJECT_ROOT"
fi

# 停止已存在的前端进程
echo -e "${YELLOW}停止已存在的前端进程...${NC}"
pkill -f "vue-cli-service" || true
sleep 2

# 启动前端
echo -e "${YELLOW}启动前端服务...${NC}"
cd "$FRONTEND_DIR"

# 创建logs目录
mkdir -p "$PROJECT_ROOT/logs"

# 启动前端并保存PID
nohup npm run dev > "$PROJECT_ROOT/logs/frontend.log" 2>&1 &
FRONTEND_PID=$!

echo -e "${GREEN}✓ 前端启动中 (PID: $FRONTEND_PID)${NC}"

# 等待前端启动
echo -e "${YELLOW}等待前端启动...${NC}"
for i in {1..30}; do
    if curl -s http://localhost:1024 > /dev/null 2>&1; then
        echo -e "${GREEN}✓ 前端启动成功 (http://localhost:1024)${NC}"
        break
    fi
    if [ $i -eq 30 ]; then
        echo -e "${RED}错误: 前端启动超时${NC}"
        exit 1
    fi
    sleep 2
done

# 保存PID到文件
echo "$FRONTEND_PID" > "$PROJECT_ROOT/logs/frontend.pid"

echo -e "${BLUE}================================${NC}"
echo -e "${GREEN}🎉 前端启动完成！${NC}"
echo -e "${BLUE}================================${NC}"
echo -e "${GREEN}前端地址: http://localhost:1024${NC}"
echo -e "${BLUE}================================${NC}"
echo -e "${YELLOW}日志文件: logs/frontend.log${NC}"
echo -e "${YELLOW}停止前端: ./scripts/stop_frontend.sh${NC}"
echo -e "${BLUE}================================${NC}" 