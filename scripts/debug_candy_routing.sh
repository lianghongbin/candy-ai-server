#!/bin/bash

echo "=== Candy AI 路由调试脚本 ==="
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
    CASE 
        WHEN m.parent_id = 0 THEN CONCAT('/', m.path)
        ELSE CONCAT('/', (SELECT p.path FROM sys_menu p WHERE p.menu_id = m.parent_id), '/', m.path)
    END as expected_route
FROM sys_menu m 
WHERE m.menu_id IN (4, 400, 401, 402, 403) 
ORDER BY m.menu_id;
"

echo ""
echo "2. 检查前端页面文件是否存在..."
echo "检查 candy/user/index.vue:"
if [ -f "ruoyi-ui/src/views/candy/user/index.vue" ]; then
    echo "✅ 文件存在"
    ls -la ruoyi-ui/src/views/candy/user/index.vue
else
    echo "❌ 文件不存在"
fi

echo ""
echo "检查 candy/role/index.vue:"
if [ -f "ruoyi-ui/src/views/candy/role/index.vue" ]; then
    echo "✅ 文件存在"
    ls -la ruoyi-ui/src/views/candy/role/index.vue
else
    echo "❌ 文件不存在"
fi

echo ""
echo "3. 测试前端路由..."
echo "测试 /candy/candyuser:"
curl -s "http://localhost:1024/candy/candyuser" | grep -o "<title>.*</title>" || echo "无法获取页面标题"

echo ""
echo "测试 /candy/candyrole:"
curl -s "http://localhost:1024/candy/candyrole" | grep -o "<title>.*</title>" || echo "无法获取页面标题"

echo ""
echo "4. 检查前端编译状态..."
if ps aux | grep -q "vue-cli-service"; then
    echo "✅ 前端服务正在运行"
else
    echo "❌ 前端服务未运行"
fi

echo ""
echo "=== 问题分析 ==="
echo ""
echo "根据测试结果，可能的问题："
echo "1. 前端路由没有正确生成"
echo "2. 菜单配置的路径有问题"
echo "3. 前端页面文件路径不正确"
echo ""
echo "=== 解决建议 ==="
echo "1. 重新登录管理后台，让前端重新加载菜单"
echo "2. 检查浏览器控制台是否有JavaScript错误"
echo "3. 确认前端页面文件路径正确"
echo "4. 检查菜单配置中的路径是否正确" 