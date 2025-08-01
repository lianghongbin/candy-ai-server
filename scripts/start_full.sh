#!/bin/bash
# Candy AI 完整启动脚本
# 同时启动后端和前端服务

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
echo -e "${BLUE}    Candy AI 完整启动脚本${NC}"
echo -e "${BLUE}================================${NC}"

# 检查Java环境
echo -e "${YELLOW}检查Java环境...${NC}"
if ! command -v java &> /dev/null; then
    echo -e "${RED}错误: 未找到Java环境${NC}"
    exit 1
fi
echo -e "${GREEN}✓ Java环境正常${NC}"

# 检查Node.js环境
echo -e "${YELLOW}检查Node.js环境...${NC}"
if ! command -v node &> /dev/null; then
    echo -e "${RED}错误: 未找到Node.js环境${NC}"
    exit 1
fi
echo -e "${GREEN}✓ Node.js环境正常${NC}"

# 检查MySQL
echo -e "${YELLOW}检查MySQL连接...${NC}"
if ! mysql -u root -p123456 -e "SELECT 1;" &> /dev/null; then
    echo -e "${RED}错误: 无法连接到MySQL数据库${NC}"
    echo -e "${YELLOW}请确保MySQL服务正在运行，并且密码为123456${NC}"
    exit 1
fi
echo -e "${GREEN}✓ MySQL连接正常${NC}"

# 检查Redis
echo -e "${YELLOW}检查Redis连接...${NC}"
if ! redis-cli -a 123456 ping &> /dev/null; then
    echo -e "${RED}错误: 无法连接到Redis${NC}"
    echo -e "${YELLOW}请确保Redis服务正在运行，并且密码为123456${NC}"
    exit 1
fi
echo -e "${GREEN}✓ Redis连接正常${NC}"

# 停止已存在的进程
echo -e "${YELLOW}停止已存在的进程...${NC}"
pkill -f "RuoYiApplication" || true
pkill -f "vue-cli-service" || true
sleep 2

# 启动后端
echo -e "${YELLOW}启动后端服务...${NC}"
cd "$PROJECT_ROOT"
nohup mvn spring-boot:run -pl ruoyi-admin -Dspring.profiles.active=dev > logs/backend.log 2>&1 &
BACKEND_PID=$!
echo -e "${GREEN}✓ 后端启动中 (PID: $BACKEND_PID)${NC}"

# 等待后端启动
echo -e "${YELLOW}等待后端启动...${NC}"
for i in {1..30}; do
    if curl -s http://localhost:8080 > /dev/null 2>&1; then
        echo -e "${GREEN}✓ 后端启动成功 (http://localhost:8080)${NC}"
        break
    fi
    if [ $i -eq 30 ]; then
        echo -e "${RED}错误: 后端启动超时${NC}"
        exit 1
    fi
    sleep 2
done

# 启动前端
echo -e "${YELLOW}启动前端服务...${NC}"
cd "$PROJECT_ROOT/ruoyi-ui"
nohup npm run dev > ../logs/frontend.log 2>&1 &
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

# 测试前后端通信
echo -e "${YELLOW}测试前后端通信...${NC}"
if curl -s http://localhost:1024/dev-api/captchaImage > /dev/null 2>&1; then
    echo -e "${GREEN}✓ 前后端通信正常${NC}"
else
    echo -e "${RED}警告: 前后端通信测试失败${NC}"
fi

echo -e "${BLUE}================================${NC}"
echo -e "${GREEN}🎉 Candy AI 启动完成！${NC}"
echo -e "${BLUE}================================${NC}"
echo -e "${GREEN}前端地址: http://localhost:1024${NC}"
echo -e "${GREEN}后端地址: http://localhost:8080${NC}"
echo -e "${GREEN}API文档: http://localhost:8080/swagger-ui.html${NC}"
echo -e "${BLUE}================================${NC}"
echo -e "${YELLOW}日志文件:${NC}"
echo -e "  后端日志: logs/backend.log"
echo -e "  前端日志: logs/frontend.log"
echo -e "${BLUE}================================${NC}"
echo -e "${YELLOW}停止服务: ./scripts/stop_full.sh${NC}"
echo -e "${BLUE}================================${NC}"

# 保存PID到文件
mkdir -p logs
echo "$BACKEND_PID" > logs/backend.pid
echo "$FRONTEND_PID" > logs/frontend.pid 