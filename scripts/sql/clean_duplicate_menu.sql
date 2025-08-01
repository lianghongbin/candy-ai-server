-- =============================================
-- 清理重复的Candy AI菜单
-- 删除menu_id为2000的重复菜单及其相关数据
-- =============================================

-- 删除重复的Candy AI菜单（menu_id = 2000）
DELETE FROM sys_menu WHERE menu_id = 2000;

-- 检查是否还有其他重复的菜单
SELECT menu_id, menu_name, parent_id, order_num, path, component 
FROM sys_menu 
WHERE menu_name LIKE '%Candy%' OR menu_name LIKE '%AI%' 
ORDER BY menu_id;

-- 验证菜单结构
SELECT 
    m1.menu_id,
    m1.menu_name,
    m1.parent_id,
    m1.order_num,
    m1.path,
    m1.component,
    m1.perms
FROM sys_menu m1
WHERE m1.menu_name LIKE '%Candy%' OR m1.menu_name LIKE '%AI%'
ORDER BY m1.menu_id; 