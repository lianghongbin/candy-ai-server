-- =============================================
-- 强制刷新Candy AI菜单配置
-- 确保菜单配置正确并强制前端重新加载
-- =============================================

-- 1. 检查并修复菜单状态
UPDATE sys_menu SET visible = '0' WHERE menu_id IN (4, 400, 401, 402, 403);

-- 2. 确保菜单类型正确
UPDATE sys_menu SET menu_type = 'M' WHERE menu_id = 4;  -- Candy AI 为目录
UPDATE sys_menu SET menu_type = 'C' WHERE menu_id IN (400, 401, 402, 403);  -- 子菜单为菜单

-- 3. 确保路径配置正确
UPDATE sys_menu SET path = 'candy' WHERE menu_id = 4;
UPDATE sys_menu SET path = 'candyuser' WHERE menu_id = 400;
UPDATE sys_menu SET path = 'candyrole' WHERE menu_id = 401;
UPDATE sys_menu SET path = 'candyconversation' WHERE menu_id = 402;
UPDATE sys_menu SET path = 'candysetting' WHERE menu_id = 403;

-- 4. 确保组件路径正确
UPDATE sys_menu SET component = NULL WHERE menu_id = 4;  -- 目录不需要组件
UPDATE sys_menu SET component = 'candy/user/index' WHERE menu_id = 400;
UPDATE sys_menu SET component = 'candy/role/index' WHERE menu_id = 401;
UPDATE sys_menu SET component = 'candy/conversation/index' WHERE menu_id = 402;
UPDATE sys_menu SET component = 'candy/setting/index' WHERE menu_id = 403;

-- 5. 确保权限配置正确
-- 检查超级管理员是否有所有Candy AI菜单权限
INSERT IGNORE INTO sys_role_menu (role_id, menu_id) VALUES
(1, 4),   -- Candy AI 管理目录
(1, 400), -- 用户管理
(1, 401), -- 角色管理
(1, 402), -- 对话管理
(1, 403), -- 系统设置
(1, 4000), -- 用户查询
(1, 4001), -- 用户新增
(1, 4002), -- 用户修改
(1, 4003), -- 用户删除
(1, 4004), -- 用户导出
(1, 4005), -- 重置密码
(1, 4010), -- 角色查询
(1, 4011), -- 角色新增
(1, 4012), -- 角色修改
(1, 4013), -- 角色删除
(1, 4014), -- 角色导出
(1, 4020), -- 对话查询
(1, 4021), -- 对话删除
(1, 4022), -- 对话导出
(1, 4030), -- 设置查询
(1, 4031); -- 设置修改

-- 6. 验证配置
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

-- 7. 验证权限
SELECT COUNT(*) as candy_menu_permissions 
FROM sys_role_menu rm 
JOIN sys_menu m ON rm.menu_id = m.menu_id 
WHERE rm.role_id = 1 AND m.perms LIKE 'candy:%'; 