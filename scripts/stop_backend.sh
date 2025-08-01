#!/bin/bash
# Candy AI 后端停止脚本

set -e

# 颜色定义
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# 项目根目录
PROJECT_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"

echo -e "${BLUE}================================${NC}"
echo -e "${BLUE}    Candy AI 后端停止脚本${NC}"
echo -e "${BLUE}================================${NC}"

# 停止后端进程
echo -e "${YELLOW}停止后端进程...${NC}"
if [ -f "$PROJECT_ROOT/logs/backend.pid" ]; then
    BACKEND_PID=$(cat "$PROJECT_ROOT/logs/backend.pid")
    if kill -0 "$BACKEND_PID" 2>/dev/null; then
        kill "$BACKEND_PID"
        echo -e "${GREEN}✓ 后端进程已停止 (PID: $BACKEND_PID)${NC}"
    else
        echo -e "${YELLOW}后端进程已不存在${NC}"
    fi
    rm -f "$PROJECT_ROOT/logs/backend.pid"
else
    echo -e "${YELLOW}未找到后端PID文件${NC}"
fi

# 强制停止相关进程
echo -e "${YELLOW}强制停止相关进程...${NC}"
pkill -f "RuoYiApplication" || true
pkill -f "spring-boot:run" || true

echo -e "${BLUE}================================${NC}"
echo -e "${GREEN}✓ 后端服务已停止${NC}"
echo -e "${BLUE}================================${NC}" 