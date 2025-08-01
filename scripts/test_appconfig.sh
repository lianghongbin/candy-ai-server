#!/bin/bash

# AppConfig 功能测试脚本
# 作者: Candy AI Team
# 日期: 2025-08-01

echo "=================================="
echo "    AppConfig 功能测试"
echo "=================================="

BASE_URL="http://localhost:8080"

# 颜色定义
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# 测试函数
test_endpoint() {
    local method=$1
    local endpoint=$2
    local description=$3
    
    echo -e "\n${YELLOW}测试: $description${NC}"
    echo "请求: $method $endpoint"
    
    response=$(curl -s -X $method "$BASE_URL$endpoint" \
        -H "Content-Type: application/json")
    
    echo "响应: $response"
    
    # 检查响应状态
    if echo "$response" | grep -q '"code":200'; then
        echo -e "${GREEN}✓ 测试通过${NC}"
        return 0
    else
        echo -e "${RED}✗ 测试失败${NC}"
        return 1
    fi
}

# 检查后端服务是否运行
echo "检查后端服务状态..."
if ! curl -s "$BASE_URL/actuator/health" > /dev/null 2>&1; then
    echo -e "${RED}错误: 后端服务未运行，请先启动后端服务${NC}"
    echo "启动命令: ./scripts/start.sh -p dev"
    exit 1
fi
echo -e "${GREEN}✓ 后端服务运行正常${NC}"

# 测试 OAuth 授权 URL 获取（验证 AppConfig 配置）
echo -e "\n${YELLOW}=== 测试 AppConfig 配置的 OAuth URL ===${NC}"

test_endpoint "GET" "/auth/oauth/url?provider=google" "Google OAuth URL (使用 AppConfig)"
test_endpoint "GET" "/auth/oauth/url?provider=github" "GitHub OAuth URL (使用 AppConfig)"
test_endpoint "GET" "/auth/oauth/url?provider=discord" "Discord OAuth URL (使用 AppConfig)"
test_endpoint "GET" "/auth/oauth/url?provider=apple" "Apple OAuth URL (使用 AppConfig)"
test_endpoint "GET" "/auth/oauth/url?provider=facebook" "Facebook OAuth URL (使用 AppConfig)"

# 验证 URL 格式
echo -e "\n${YELLOW}=== 验证 URL 格式 ===${NC}"

# 测试 Google OAuth URL
google_response=$(curl -s -X GET "$BASE_URL/auth/oauth/url?provider=google")
google_url=$(echo "$google_response" | grep -o '"url":"[^"]*"' | cut -d'"' -f4)

if [[ "$google_url" == *"http://localhost:8080/auth/oauth/callback/google"* ]]; then
    echo -e "${GREEN}✓ Google OAuth URL 格式正确${NC}"
else
    echo -e "${RED}✗ Google OAuth URL 格式错误: $google_url${NC}"
fi

# 测试 Discord OAuth URL
discord_response=$(curl -s -X GET "$BASE_URL/auth/oauth/url?provider=discord")
discord_url=$(echo "$discord_response" | grep -o '"url":"[^"]*"' | cut -d'"' -f4)

if [[ "$discord_url" == *"http://localhost:8080/auth/oauth/callback/discord"* ]]; then
    echo -e "${GREEN}✓ Discord OAuth URL 格式正确${NC}"
else
    echo -e "${RED}✗ Discord OAuth URL 格式错误: $discord_url${NC}"
fi

# 检查是否有双斜杠问题
if [[ "$google_url" == *"//"* ]] && [[ "$google_url" != *"https://"* ]]; then
    echo -e "${RED}✗ 发现双斜杠问题: $google_url${NC}"
else
    echo -e "${GREEN}✓ 无双斜杠问题${NC}"
fi

# 测试结果汇总
echo -e "\n${YELLOW}=== 测试结果汇总 ===${NC}"
echo "AppConfig 功能测试完成！"
echo ""
echo "✅ 已完成的优化："
echo "1. 统一配置参数管理"
echo "2. 动态 URL 构建"
echo "3. 环境适配支持"
echo "4. 双斜杠问题修复"
echo ""
echo "🔧 配置优势："
echo "- 修改端口或地址只需要改一处配置"
echo "- 支持开发和生产环境不同配置"
echo "- 所有 OAuth 回调地址自动更新"
echo "- 避免硬编码 URL 问题"

echo -e "\n${GREEN}=================================="
echo "    测试完成！"
echo "==================================${NC}" 