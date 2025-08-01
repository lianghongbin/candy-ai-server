#!/bin/bash

# 邮箱注册功能测试脚本
# 用于验证邮箱注册相关API是否正常工作

set -e

# 颜色定义
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# 配置
BASE_URL="http://localhost:8080"
API_BASE="/auth/email"

# 测试邮箱
TEST_EMAIL="test_$(date +%s)@example.com"

echo -e "${YELLOW}开始测试邮箱注册功能...${NC}"
echo "测试邮箱: $TEST_EMAIL"
echo "API地址: $BASE_URL$API_BASE"
echo ""

# 检查后端是否运行
check_backend() {
    echo -e "${YELLOW}检查后端服务状态...${NC}"
    if curl -s "$BASE_URL/actuator/health" > /dev/null 2>&1; then
        echo -e "${GREEN}✓ 后端服务运行正常${NC}"
    else
        echo -e "${RED}✗ 后端服务未运行，请先启动后端服务${NC}"
        echo "运行命令: ./scripts/start.sh -p dev"
        exit 1
    fi
    echo ""
}

# 测试发送验证码
test_send_verify_code() {
    echo -e "${YELLOW}测试发送验证码...${NC}"
    
    # 测试正常发送
    response=$(curl -s -X POST "$BASE_URL$API_BASE/sendVerifyCode" \
        -d "email=$TEST_EMAIL" \
        -d "verifyType=register" \
        -H "Content-Type: application/x-www-form-urlencoded")
    
    if echo "$response" | grep -q '"code":200'; then
        echo -e "${GREEN}✓ 发送验证码成功${NC}"
    else
        echo -e "${RED}✗ 发送验证码失败${NC}"
        echo "响应: $response"
    fi
    
    # 测试空邮箱
    response=$(curl -s -X POST "$BASE_URL$API_BASE/sendVerifyCode" \
        -d "email=" \
        -d "verifyType=register" \
        -H "Content-Type: application/x-www-form-urlencoded")
    
    if echo "$response" | grep -q '"code":500'; then
        echo -e "${GREEN}✓ 空邮箱验证正常${NC}"
    else
        echo -e "${RED}✗ 空邮箱验证异常${NC}"
        echo "响应: $response"
    fi
    
    # 测试无效邮箱格式
    response=$(curl -s -X POST "$BASE_URL$API_BASE/sendVerifyCode" \
        -d "email=invalid-email" \
        -d "verifyType=register" \
        -H "Content-Type: application/x-www-form-urlencoded")
    
    if echo "$response" | grep -q '"code":500'; then
        echo -e "${GREEN}✓ 无效邮箱格式验证正常${NC}"
    else
        echo -e "${RED}✗ 无效邮箱格式验证异常${NC}"
        echo "响应: $response"
    fi
    
    echo ""
}

# 测试检查邮箱
test_check_email() {
    echo -e "${YELLOW}测试检查邮箱...${NC}"
    
    response=$(curl -s -X POST "$BASE_URL$API_BASE/checkEmail" \
        -d "email=$TEST_EMAIL" \
        -H "Content-Type: application/x-www-form-urlencoded")
    
    if echo "$response" | grep -q '"code":200'; then
        echo -e "${GREEN}✓ 检查邮箱成功${NC}"
        if echo "$response" | grep -q '"isRegistered":false'; then
            echo -e "${GREEN}✓ 邮箱未注册状态正确${NC}"
        else
            echo -e "${RED}✗ 邮箱注册状态异常${NC}"
        fi
    else
        echo -e "${RED}✗ 检查邮箱失败${NC}"
        echo "响应: $response"
    fi
    
    echo ""
}

# 测试邮箱注册
test_register() {
    echo -e "${YELLOW}测试邮箱注册...${NC}"
    
    # 准备注册数据
    register_data="{
        \"email\": \"$TEST_EMAIL\",
        \"password\": \"123456\",
        \"nickName\": \"测试用户\"
    }"
    
    response=$(curl -s -X POST "$BASE_URL$API_BASE/register" \
        -d "$register_data" \
        -H "Content-Type: application/json")
    
    if echo "$response" | grep -q '"code":200'; then
        echo -e "${GREEN}✓ 邮箱注册成功${NC}"
    else
        echo -e "${RED}✗ 邮箱注册失败${NC}"
        echo "响应: $response"
    fi
    
    # 测试重复注册
    response=$(curl -s -X POST "$BASE_URL$API_BASE/register" \
        -d "$register_data" \
        -H "Content-Type: application/json")
    
    if echo "$response" | grep -q '"code":500'; then
        echo -e "${GREEN}✓ 重复注册验证正常${NC}"
    else
        echo -e "${RED}✗ 重复注册验证异常${NC}"
        echo "响应: $response"
    fi
    
    echo ""
}

# 测试验证码验证
test_verify_code() {
    echo -e "${YELLOW}测试验证码验证...${NC}"
    
    # 先发送验证码
    curl -s -X POST "$BASE_URL$API_BASE/sendVerifyCode" \
        -d "email=$TEST_EMAIL" \
        -d "verifyType=register" \
        -H "Content-Type: application/x-www-form-urlencoded" > /dev/null
    
    # 等待一秒让验证码保存到数据库
    sleep 1
    
    # 测试验证码验证（这里使用一个假设的验证码）
    response=$(curl -s -X POST "$BASE_URL$API_BASE/verifyCode" \
        -d "email=$TEST_EMAIL" \
        -d "verifyCode=123456" \
        -d "verifyType=register" \
        -H "Content-Type: application/x-www-form-urlencoded")
    
    if echo "$response" | grep -q '"code":200'; then
        echo -e "${GREEN}✓ 验证码验证成功${NC}"
    else
        echo -e "${YELLOW}⚠ 验证码验证失败（可能是验证码不匹配）${NC}"
        echo "响应: $response"
    fi
    
    echo ""
}

# 测试错误情况
test_error_cases() {
    echo -e "${YELLOW}测试错误情况...${NC}"
    
    # 测试空邮箱注册
    register_data='{
        "email": "",
        "password": "123456",
        "nickName": "测试用户"
    }'
    
    response=$(curl -s -X POST "$BASE_URL$API_BASE/register" \
        -d "$register_data" \
        -H "Content-Type: application/json")
    
    if echo "$response" | grep -q '"code":500'; then
        echo -e "${GREEN}✓ 空邮箱注册验证正常${NC}"
    else
        echo -e "${RED}✗ 空邮箱注册验证异常${NC}"
    fi
    
    # 测试空密码注册
    register_data="{
        \"email\": \"$TEST_EMAIL\",
        \"password\": \"\",
        \"nickName\": \"测试用户\"
    }"
    
    response=$(curl -s -X POST "$BASE_URL$API_BASE/register" \
        -d "$register_data" \
        -H "Content-Type: application/json")
    
    if echo "$response" | grep -q '"code":500'; then
        echo -e "${GREEN}✓ 空密码注册验证正常${NC}"
    else
        echo -e "${RED}✗ 空密码注册验证异常${NC}"
    fi
    
    # 测试密码太短
    register_data="{
        \"email\": \"$TEST_EMAIL\",
        \"password\": \"123\",
        \"nickName\": \"测试用户\"
    }"
    
    response=$(curl -s -X POST "$BASE_URL$API_BASE/register" \
        -d "$register_data" \
        -H "Content-Type: application/json")
    
    if echo "$response" | grep -q '"code":500'; then
        echo -e "${GREEN}✓ 密码长度验证正常${NC}"
    else
        echo -e "${RED}✗ 密码长度验证异常${NC}"
    fi
    
    echo ""
}

# 主测试流程
main() {
    echo -e "${GREEN}================================${NC}"
    echo -e "${GREEN}  邮箱注册功能测试开始${NC}"
    echo -e "${GREEN}================================${NC}"
    echo ""
    
    check_backend
    test_send_verify_code
    test_check_email
    test_register
    test_verify_code
    test_error_cases
    
    echo -e "${GREEN}================================${NC}"
    echo -e "${GREEN}  邮箱注册功能测试完成${NC}"
    echo -e "${GREEN}================================${NC}"
    echo ""
    echo -e "${YELLOW}注意：${NC}"
    echo "1. 验证码功能需要实际的邮件服务配置"
    echo "2. 测试中使用的验证码可能不匹配，这是正常现象"
    echo "3. 建议查看后端日志以获取更详细的错误信息"
}

# 运行测试
main "$@" 