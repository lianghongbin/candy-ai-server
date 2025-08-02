-- =============================================
-- 复制系统用户管理配置到Candy AI用户管理
-- 基于系统用户管理的完整配置结构
-- =============================================

-- 1. 清理现有的Candy AI菜单配置
DELETE FROM sys_role_menu WHERE menu_id IN (4, 400, 401, 402, 403, 4000, 4001, 4002, 4003, 4004, 4005, 4010, 4011, 4012, 4013, 4014, 4020, 4021, 4022, 4030, 4031);
DELETE FROM sys_menu WHERE menu_id IN (4, 400, 401, 402, 403, 4000, 4001, 4002, 4003, 4004, 4005, 4010, 4011, 4012, 4013, 4014, 4020, 4021, 4022, 4030, 4031);

-- 2. 创建Candy AI一级菜单（基于系统管理）
INSERT INTO sys_menu VALUES('4', 'Candy AI', '0', '4', 'candy', null, '', '', 1, 0, 'M', '0', '0', '', 'candy', 'admin', sysdate(), '', null, 'Candy AI管理目录');

-- 3. 创建Candy AI用户管理（基于系统用户管理）
INSERT INTO sys_menu VALUES('400', '用户管理', '4', '1', 'user', 'candy/user/index', '', '', 1, 0, 'C', '0', '0', 'candy:user:list', 'user', 'admin', sysdate(), '', null, 'Candy AI用户管理菜单');

-- 4. 创建Candy AI用户管理按钮权限（基于系统用户管理按钮权限）
INSERT INTO sys_menu VALUES('4000', '用户查询', '400', '1', '', '', '', '', 1, 0, 'F', '0', '0', 'candy:user:query', '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('4001', '用户新增', '400', '2', '', '', '', '', 1, 0, 'F', '0', '0', 'candy:user:add', '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('4002', '用户修改', '400', '3', '', '', '', '', 1, 0, 'F', '0', '0', 'candy:user:edit', '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('4003', '用户删除', '400', '4', '', '', '', '', 1, 0, 'F', '0', '0', 'candy:user:remove', '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('4004', '用户导出', '400', '5', '', '', '', '', 1, 0, 'F', '0', '0', 'candy:user:export', '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('4005', '重置密码', '400', '6', '', '', '', '', 1, 0, 'F', '0', '0', 'candy:user:resetPwd', '#', 'admin', sysdate(), '', null, '');

-- 5. 创建Candy AI角色管理（基于系统角色管理）
INSERT INTO sys_menu VALUES('401', '角色管理', '4', '2', 'role', 'candy/role/index', '', '', 1, 0, 'C', '0', '0', 'candy:role:list', 'peoples', 'admin', sysdate(), '', null, 'Candy AI角色管理菜单');

-- 6. 创建Candy AI角色管理按钮权限（基于系统角色管理按钮权限）
INSERT INTO sys_menu VALUES('4010', '角色查询', '401', '1', '', '', '', '', 1, 0, 'F', '0', '0', 'candy:role:query', '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('4011', '角色新增', '401', '2', '', '', '', '', 1, 0, 'F', '0', '0', 'candy:role:add', '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('4012', '角色修改', '401', '3', '', '', '', '', 1, 0, 'F', '0', '0', 'candy:role:edit', '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('4013', '角色删除', '401', '4', '', '', '', '', 1, 0, 'F', '0', '0', 'candy:role:remove', '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('4014', '角色导出', '401', '5', '', '', '', '', 1, 0, 'F', '0', '0', 'candy:role:export', '#', 'admin', sysdate(), '', null, '');

-- 7. 创建Candy AI对话管理
INSERT INTO sys_menu VALUES('402', '对话管理', '4', '3', 'conversation', 'candy/conversation/index', '', '', 1, 0, 'C', '0', '0', 'candy:conversation:list', 'message', 'admin', sysdate(), '', null, 'Candy AI对话管理菜单');

-- 8. 创建Candy AI对话管理按钮权限
INSERT INTO sys_menu VALUES('4020', '对话查询', '402', '1', '', '', '', '', 1, 0, 'F', '0', '0', 'candy:conversation:query', '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('4021', '对话删除', '402', '2', '', '', '', '', 1, 0, 'F', '0', '0', 'candy:conversation:remove', '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('4022', '对话导出', '402', '3', '', '', '', '', 1, 0, 'F', '0', '0', 'candy:conversation:export', '#', 'admin', sysdate(), '', null, '');

-- 9. 创建Candy AI系统设置
INSERT INTO sys_menu VALUES('403', '系统设置', '4', '4', 'setting', 'candy/setting/index', '', '', 1, 0, 'C', '0', '0', 'candy:setting:list', 'system', 'admin', sysdate(), '', null, 'Candy AI系统设置菜单');

-- 10. 创建Candy AI系统设置按钮权限
INSERT INTO sys_menu VALUES('4030', '设置查询', '403', '1', '', '', '', '', 1, 0, 'F', '0', '0', 'candy:setting:query', '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('4031', '设置修改', '403', '2', '', '', '', '', 1, 0, 'F', '0', '0', 'candy:setting:edit', '#', 'admin', sysdate(), '', null, '');

-- 11. 为超级管理员分配所有Candy AI菜单权限
INSERT INTO sys_role_menu (role_id, menu_id) VALUES
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

-- 12. 验证配置
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