#!/bin/bash

echo "=========================================="
echo "停止 Candy AI 管理后台"
echo "=========================================="

# 停止后端服务
if [ -f "backend.pid" ]; then
    BACKEND_PID=$(cat backend.pid)
    if ps -p $BACKEND_PID > /dev/null; then
        echo "停止后端服务 (PID: $BACKEND_PID)..."
        kill $BACKEND_PID
        sleep 2
        if ps -p $BACKEND_PID > /dev/null; then
            echo "强制停止后端服务..."
            kill -9 $BACKEND_PID
        fi
        echo "后端服务已停止"
    else
        echo "后端服务未运行"
    fi
    rm -f backend.pid
else
    echo "未找到后端PID文件"
fi

# 停止前端服务
if [ -f "frontend.pid" ]; then
    FRONTEND_PID=$(cat frontend.pid)
    if ps -p $FRONTEND_PID > /dev/null; then
        echo "停止前端服务 (PID: $FRONTEND_PID)..."
        kill $FRONTEND_PID
        sleep 2
        if ps -p $FRONTEND_PID > /dev/null; then
            echo "强制停止前端服务..."
            kill -9 $FRONTEND_PID
        fi
        echo "前端服务已停止"
    else
        echo "前端服务未运行"
    fi
    rm -f frontend.pid
else
    echo "未找到前端PID文件"
fi

echo "=========================================="
echo "所有服务已停止"
echo "==========================================" 