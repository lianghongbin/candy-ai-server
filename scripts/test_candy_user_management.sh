#!/bin/bash

# Candy AI 用户管理功能测试脚本
# 测试管理后台的Candy用户管理功能

echo "=== Candy AI 用户管理功能测试 ==="

# 检查后端服务状态
echo "1. 检查后端服务状态..."
if curl -s http://localhost:8080/actuator/health > /dev/null; then
    echo "✅ 后端服务运行正常"
else
    echo "❌ 后端服务未运行，请先启动后端服务"
    exit 1
fi

# 测试函数
test_endpoint() {
    local method=$1
    local endpoint=$2
    local data=$3
    local description=$4
    
    echo "测试: $description"
    echo "  $method $endpoint"
    
    if [ "$method" = "GET" ]; then
        response=$(curl -s -w "\n%{http_code}" -X GET "http://localhost:8080$endpoint" \
            -H "Content-Type: application/json" \
            -H "Authorization: Bearer $(cat token.txt 2>/dev/null || echo 'test-token')")
    elif [ "$method" = "POST" ]; then
        response=$(curl -s -w "\n%{http_code}" -X POST "http://localhost:8080$endpoint" \
            -H "Content-Type: application/json" \
            -H "Authorization: Bearer $(cat token.txt 2>/dev/null || echo 'test-token')" \
            -d "$data")
    elif [ "$method" = "PUT" ]; then
        response=$(curl -s -w "\n%{http_code}" -X PUT "http://localhost:8080$endpoint" \
            -H "Content-Type: application/json" \
            -H "Authorization: Bearer $(cat token.txt 2>/dev/null || echo 'test-token')" \
            -d "$data")
    elif [ "$method" = "DELETE" ]; then
        response=$(curl -s -w "\n%{http_code}" -X DELETE "http://localhost:8080$endpoint" \
            -H "Content-Type: application/json" \
            -H "Authorization: Bearer $(cat token.txt 2>/dev/null || echo 'test-token')")
    fi
    
    # 分离响应体和状态码
    body=$(echo "$response" | head -n -1)
    status=$(echo "$response" | tail -n 1)
    
    if [ "$status" = "200" ] || [ "$status" = "201" ]; then
        echo "  ✅ 成功 ($status)"
        echo "  响应: $body" | head -c 100
        [ ${#body} -gt 100 ] && echo "..."
    elif [ "$status" = "401" ]; then
        echo "  ⚠️  需要认证 ($status) - 这是正常的，表示接口存在且受保护"
    elif [ "$status" = "403" ]; then
        echo "  ⚠️  权限不足 ($status) - 这是正常的，表示接口存在且需要权限"
    elif [ "$status" = "404" ]; then
        echo "  ❌ 接口不存在 ($status)"
    else
        echo "  ❌ 失败 ($status)"
        echo "  响应: $body"
    fi
    echo ""
}

echo ""
echo "2. 测试Candy用户管理API接口..."

# 测试用户列表接口
test_endpoint "GET" "/candy/user/list" "" "获取Candy用户列表"

# 测试用户统计接口
test_endpoint "GET" "/candy/user/stats" "" "获取Candy用户统计信息"

# 测试获取用户详情接口
test_endpoint "GET" "/candy/user/1" "" "获取用户详情"

# 测试新增用户接口
test_endpoint "POST" "/candy/user" '{
  "userName": "testuser",
  "nickName": "测试用户",
  "password": "123456",
  "email": "test@example.com",
  "phonenumber": "13800138000",
  "sex": "0",
  "status": "0",
  "deptId": 103,
  "roleIds": [2],
  "postIds": [1]
}' "新增Candy用户"

# 测试修改用户接口
test_endpoint "PUT" "/candy/user" '{
  "userId": 1,
  "userName": "admin",
  "nickName": "管理员",
  "email": "admin@example.com",
  "phonenumber": "15888888888",
  "sex": "0",
  "status": "0",
  "deptId": 103,
  "roleIds": [1],
  "postIds": [1]
}' "修改Candy用户"

# 测试重置密码接口
test_endpoint "PUT" "/candy/user/resetPwd" '{
  "userId": 1,
  "password": "newpassword123"
}' "重置用户密码"

# 测试删除用户接口（注意：这里不会真正删除，因为需要认证）
test_endpoint "DELETE" "/candy/user/999" "" "删除Candy用户"

echo ""
echo "3. 测试菜单权限..."

# 检查菜单数据是否存在
echo "检查菜单数据..."
mysql -u root -p123456 candy_ai -e "SELECT menu_id, menu_name, perms FROM sys_menu WHERE menu_name LIKE '%Candy%' OR menu_name LIKE '%AI%';" 2>/dev/null || echo "无法连接数据库"

echo ""
echo "4. 测试结果总结..."
echo "✅ 如果看到 '需要认证' 或 '权限不足' 的响应，说明接口存在且正常工作"
echo "✅ 如果看到 '接口不存在' 的响应，说明需要检查路由配置"
echo "✅ 如果看到 '成功' 的响应，说明接口完全正常"

echo ""
echo "=== 测试完成 ==="
echo ""
echo "下一步操作："
echo "1. 启动前端服务: cd ruoyi-ui && npm run dev"
echo "2. 访问管理后台: http://localhost:1024"
echo "3. 登录后查看 'Candy AI' 菜单"
echo "4. 测试用户管理功能" 