#!/bin/bash

echo "=========================================="
echo "启动 Candy AI 管理后台"
echo "=========================================="

# 检查Java版本
java_version=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2 | cut -d'.' -f1)
if [ "$java_version" != "17" ]; then
    echo "错误: 需要Java 17，当前版本: $java_version"
    exit 1
fi

# 检查Maven
if ! command -v mvn &> /dev/null; then
    echo "错误: 未找到Maven，请先安装Maven"
    exit 1
fi

# 检查Node.js (用于前端)
if ! command -v node &> /dev/null; then
    echo "警告: 未找到Node.js，前端将无法启动"
    echo "请安装Node.js后重新运行此脚本"
fi

# 检查npm
if ! command -v npm &> /dev/null; then
    echo "警告: 未找到npm，前端将无法启动"
fi

echo "1. 编译后端项目..."
mvn clean compile -DskipTests

if [ $? -ne 0 ]; then
    echo "后端编译失败！"
    exit 1
fi

echo "2. 启动后端服务..."
# 后台启动后端服务
mvn spring-boot:run -pl candy-admin > backend.log 2>&1 &
BACKEND_PID=$!

echo "后端服务已启动，PID: $BACKEND_PID"
echo "后端日志文件: backend.log"

# 等待后端启动
echo "等待后端服务启动..."
sleep 10

# 检查后端是否启动成功
if curl -s http://localhost:8080/actuator/health > /dev/null; then
    echo "后端服务启动成功！"
else
    echo "后端服务启动失败，请检查日志文件: backend.log"
    kill $BACKEND_PID 2>/dev/null
    exit 1
fi

echo "3. 启动前端服务..."
cd candy-ui

# 安装依赖
if [ -d "node_modules" ]; then
    echo "前端依赖已存在，跳过安装"
else
    echo "安装前端依赖..."
    npm install
fi

# 启动前端开发服务器
echo "启动前端开发服务器..."
npm run dev > ../frontend.log 2>&1 &
FRONTEND_PID=$!

echo "前端服务已启动，PID: $FRONTEND_PID"
echo "前端日志文件: frontend.log"

# 等待前端启动
echo "等待前端服务启动..."
sleep 5

echo "=========================================="
echo "管理后台启动完成！"
echo "=========================================="
echo "后端服务: http://localhost:8080"
echo "前端服务: http://localhost:80 (或 http://localhost:3000)"
echo "管理后台: http://localhost:80 (或 http://localhost:3000)"
echo ""
echo "默认管理员账号: admin"
echo "默认密码: admin123"
echo ""
echo "停止服务: 按 Ctrl+C 或运行 ./scripts/stop_admin.sh"
echo "=========================================="

# 保存PID到文件
echo $BACKEND_PID > ../backend.pid
echo $FRONTEND_PID > ../frontend.pid

# 等待用户中断
wait 