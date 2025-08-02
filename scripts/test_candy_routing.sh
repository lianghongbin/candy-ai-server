#!/bin/bash

echo "=== Candy AI 路由测试脚本 ==="
echo ""

echo "1. 检查前端服务状态..."
if curl -s "http://localhost:1024" > /dev/null; then
    echo "✅ 前端服务正常运行在 http://localhost:1024"
else
    echo "❌ 前端服务未运行"
    exit 1
fi

echo ""
echo "2. 检查后端服务状态..."
if curl -s "http://localhost:8080" > /dev/null; then
    echo "✅ 后端服务正常运行在 http://localhost:8080"
else
    echo "❌ 后端服务未运行"
    exit 1
fi

echo ""
echo "3. 测试API代理..."
API_RESPONSE=$(curl -s "http://localhost:1024/dev-api/candy/user/list")
if echo "$API_RESPONSE" | grep -q "认证失败"; then
    echo "✅ API代理正常工作，返回认证错误（正常）"
    echo "   响应: $API_RESPONSE"
else
    echo "❌ API代理可能有问题"
    echo "   响应: $API_RESPONSE"
fi

echo ""
echo "4. 检查菜单配置..."
MENU_CONFIG=$(mysql -u root -p123456 candy_ai -e "SELECT menu_id, menu_name, path, component FROM sys_menu WHERE menu_id IN (4, 400, 401, 402, 403) ORDER BY menu_id;" 2>/dev/null)
if [ $? -eq 0 ]; then
    echo "✅ 菜单配置正常"
    echo "$MENU_CONFIG"
else
    echo "❌ 无法查询菜单配置"
fi

echo ""
echo "5. 检查权限配置..."
PERM_CONFIG=$(mysql -u root -p123456 candy_ai -e "SELECT COUNT(*) as count FROM sys_role_menu rm JOIN sys_menu m ON rm.menu_id = m.menu_id WHERE rm.role_id = 1 AND m.perms LIKE 'candy:%';" 2>/dev/null)
if [ $? -eq 0 ]; then
    echo "✅ 权限配置正常，超级管理员有 $PERM_CONFIG 个Candy AI权限"
else
    echo "❌ 无法查询权限配置"
fi

echo ""
echo "=== 问题诊断 ==="
echo ""
echo "如果API代理正常但页面无法访问，可能的原因："
echo "1. 用户未登录 - 需要先登录管理后台"
echo "2. 菜单缓存问题 - 需要重新登录或清除缓存"
echo "3. 前端路由未生成 - 需要重新登录加载菜单"
echo ""
echo "=== 解决步骤 ==="
echo "1. 打开浏览器访问 http://localhost:1024"
echo "2. 使用管理员账号登录"
echo "3. 检查左侧菜单是否显示 'Candy AI'"
echo "4. 点击 '用户管理' 或 '角色管理' 子菜单"
echo ""
echo "如果问题仍然存在，请："
echo "1. 清除浏览器缓存 (Ctrl+Shift+Delete)"
echo "2. 重新登录管理后台"
echo "3. 检查浏览器控制台是否有JavaScript错误" 