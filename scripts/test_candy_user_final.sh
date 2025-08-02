#!/bin/bash

echo "=== Candy AI 用户管理最终测试 ==="
echo ""

echo "1. 检查菜单配置..."
mysql -u root -p123456 candy_ai -e "
SELECT 
    m.menu_id,
    m.menu_name,
    m.parent_id,
    m.path,
    m.component,
    m.menu_type,
    m.visible,
    m.perms,
    CASE 
        WHEN m.parent_id = 0 THEN CONCAT('/', m.path)
        ELSE CONCAT('/', (SELECT p.path FROM sys_menu p WHERE p.menu_id = m.parent_id), '/', m.path)
    END as expected_route
FROM sys_menu m 
WHERE m.menu_id IN (4, 400, 401, 402, 403) 
ORDER BY m.menu_id;
"

echo ""
echo "2. 检查权限配置..."
mysql -u root -p123456 candy_ai -e "
SELECT COUNT(*) as candy_menu_permissions 
FROM sys_role_menu rm 
JOIN sys_menu m ON rm.menu_id = m.menu_id 
WHERE rm.role_id = 1 AND m.perms LIKE 'candy:%';
"

echo ""
echo "3. 检查前端页面文件..."
echo "检查 candy/user/index.vue:"
if [ -f "ruoyi-ui/src/views/candy/user/index.vue" ]; then
    echo "✅ 文件存在"
    ls -la ruoyi-ui/src/views/candy/user/index.vue
else
    echo "❌ 文件不存在"
fi

echo ""
echo "4. 检查前端API文件..."
echo "检查 candy/user.js:"
if [ -f "ruoyi-ui/src/api/candy/user.js" ]; then
    echo "✅ 文件存在"
    ls -la ruoyi-ui/src/api/candy/user.js
else
    echo "❌ 文件不存在"
fi

echo ""
echo "5. 检查后端控制器..."
echo "检查 CandyUserController.java:"
if [ -f "candy-ai/candy-ai-api/src/main/java/com/vibetempt/candy/controller/CandyUserController.java" ]; then
    echo "✅ 文件存在"
    ls -la candy-ai/candy-ai-api/src/main/java/com/vibetempt/candy/controller/CandyUserController.java
else
    echo "❌ 文件不存在"
fi

echo ""
echo "6. 测试前端路由..."
echo "测试 /candy/user:"
curl -s "http://localhost:1024/candy/user" | grep -o "<title>.*</title>" || echo "无法获取页面标题"

echo ""
echo "7. 测试API代理..."
echo "测试 /dev-api/candy/user/list:"
API_RESPONSE=$(curl -s "http://localhost:1024/dev-api/candy/user/list")
if echo "$API_RESPONSE" | grep -q "认证失败"; then
    echo "✅ API代理正常工作，返回认证错误（正常）"
    echo "   响应: $API_RESPONSE"
else
    echo "❌ API代理可能有问题"
    echo "   响应: $API_RESPONSE"
fi

echo ""
echo "=== 测试结果总结 ==="
echo ""
echo "如果所有检查都通过，现在应该可以："
echo "1. 访问 http://localhost:1024"
echo "2. 使用管理员账号登录"
echo "3. 在左侧菜单看到 'Candy AI'"
echo "4. 点击 '用户管理' 子菜单"
echo "5. 正常使用Candy AI用户管理功能"
echo ""
echo "如果仍然有问题，请："
echo "1. 清除浏览器缓存"
echo "2. 重新登录管理后台"
echo "3. 检查浏览器控制台是否有错误" 