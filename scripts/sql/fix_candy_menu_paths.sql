-- =============================================
-- 修复Candy AI菜单路径冲突问题
-- 将Candy AI菜单的路径修改为唯一值，避免与系统菜单冲突
-- =============================================

-- 修复Candy AI用户管理路径（避免与系统用户管理冲突）
UPDATE sys_menu SET path = 'candyuser' WHERE menu_id = 400;

-- 修复Candy AI角色管理路径
UPDATE sys_menu SET path = 'candyrole' WHERE menu_id = 401;

-- 修复Candy AI对话管理路径
UPDATE sys_menu SET path = 'candyconversation' WHERE menu_id = 402;

-- 修复Candy AI系统设置路径
UPDATE sys_menu SET path = 'candysetting' WHERE menu_id = 403;

-- 验证修改结果
SELECT menu_id, menu_name, path, component FROM sys_menu WHERE menu_id IN (4, 400, 401, 402, 403) ORDER BY menu_id; 