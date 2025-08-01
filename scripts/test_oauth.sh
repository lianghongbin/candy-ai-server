#!/bin/bash

# OAuth 第三方登录功能测试脚本
# 作者: Candy AI Team
# 日期: 2025-08-01

echo "=================================="
echo "    OAuth 第三方登录功能测试"
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
    local data=$3
    local description=$4
    
    echo -e "\n${YELLOW}测试: $description${NC}"
    echo "请求: $method $endpoint"
    
    if [ -n "$data" ]; then
        echo "数据: $data"
        response=$(curl -s -X $method "$BASE_URL$endpoint" \
            -H "Content-Type: application/json" \
            -d "$data")
    else
        response=$(curl -s -X $method "$BASE_URL$endpoint" \
            -H "Content-Type: application/json")
    fi
    
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

# 测试 OAuth 授权 URL 获取
echo -e "\n${YELLOW}=== 测试 OAuth 授权 URL 获取 ===${NC}"

test_endpoint "GET" "/auth/oauth/url?provider=google" "" "获取 Google OAuth 授权 URL"
test_endpoint "GET" "/auth/oauth/url?provider=github" "" "获取 GitHub OAuth 授权 URL"
test_endpoint "GET" "/auth/oauth/url?provider=discord" "" "获取 Discord OAuth 授权 URL"
test_endpoint "GET" "/auth/oauth/url?provider=apple" "" "获取 Apple OAuth 授权 URL"
test_endpoint "GET" "/auth/oauth/url?provider=facebook" "" "获取 Facebook OAuth 授权 URL"
test_endpoint "GET" "/auth/oauth/url?provider=invalid" "" "测试无效提供商"

# 测试 OAuth 登录接口（使用模拟数据）
echo -e "\n${YELLOW}=== 测试 OAuth 登录接口 ===${NC}"

test_endpoint "POST" "/auth/oauth/google" '{"code":"test-code","state":"test-state"}' "Google 登录接口测试"
test_endpoint "POST" "/auth/oauth/github" '{"code":"test-code","state":"test-state"}' "GitHub 登录接口测试"
test_endpoint "POST" "/auth/oauth/discord" '{"code":"test-code","state":"test-state"}' "Discord 登录接口测试"
test_endpoint "POST" "/auth/oauth/apple" '{"code":"test-code","state":"test-state"}' "Apple 登录接口测试"
test_endpoint "POST" "/auth/oauth/facebook" '{"code":"test-code","state":"test-state"}' "Facebook 登录接口测试"

# 测试 OAuth 回调接口
echo -e "\n${YELLOW}=== 测试 OAuth 回调接口 ===${NC}"

test_endpoint "GET" "/auth/oauth/callback/google?code=test-code&state=test-state" "" "Google OAuth 回调测试"
test_endpoint "GET" "/auth/oauth/callback/github?code=test-code&state=test-state" "" "GitHub OAuth 回调测试"
test_endpoint "GET" "/auth/oauth/callback/discord?code=test-code&state=test-state" "" "Discord OAuth 回调测试"
test_endpoint "GET" "/auth/oauth/callback/apple?code=test-code&state=test-state" "" "Apple OAuth 回调测试"
test_endpoint "GET" "/auth/oauth/callback/facebook?code=test-code&state=test-state" "" "Facebook OAuth 回调测试"

# 测试邮箱注册功能（验证 OAuth 用户注册）
echo -e "\n${YELLOW}=== 测试邮箱注册功能 ===${NC}"

test_endpoint "GET" "/auth/email/check?email=test@example.com" "" "检查邮箱注册状态"
test_endpoint "POST" "/auth/email/sendVerifyCode" "email=test@example.com&verifyType=register" "发送邮箱验证码"

# 测试结果汇总
echo -e "\n${YELLOW}=== 测试结果汇总 ===${NC}"
echo "OAuth 第三方登录功能测试完成！"
echo ""
echo "下一步操作："
echo "1. 配置 OAuth 应用获取真实的 Client ID 和 Secret"
echo "2. 参考 docs/OAUTH_SETUP.md 进行详细配置"
echo "3. 在浏览器中测试完整的 OAuth 登录流程"
echo ""
echo "配置完成后，您可以："
echo "- 在浏览器中访问 OAuth 授权 URL 进行真实登录测试"
echo "- 验证用户是否成功注册到系统中"
echo "- 检查数据库中的用户和 OAuth 关联信息"

echo -e "\n${GREEN}=================================="
echo "    测试完成！"
echo "==================================${NC}" 