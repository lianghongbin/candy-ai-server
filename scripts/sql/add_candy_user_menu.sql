-- =============================================
-- Candy AI 用户管理菜单添加脚本
-- 用于在管理后台添加Candy AI用户管理功能
-- =============================================

-- 添加 Candy AI 管理一级菜单
INSERT INTO sys_menu VALUES('4', 'Candy AI', '0', '4', 'candy', null, '', '', 1, 0, 'M', '0', '0', '', 'candy', 'admin', sysdate(), '', null, 'Candy AI管理目录');

-- 添加 Candy AI 用户管理二级菜单
INSERT INTO sys_menu VALUES('400', 'Candy用户管理', '4', '1', 'candyuser', 'candy/user/index', '', '', 1, 0, 'C', '0', '0', 'candy:user:list', 'user', 'admin', sysdate(), '', null, 'Candy AI用户管理菜单');

-- 添加 Candy AI 角色管理二级菜单
INSERT INTO sys_menu VALUES('401', 'AI角色管理', '4', '2', 'aicharacter', 'candy/character/index', '', '', 1, 0, 'C', '0', '0', 'candy:character:list', 'peoples', 'admin', sysdate(), '', null, 'AI角色管理菜单');

-- 添加 Candy AI 对话管理二级菜单
INSERT INTO sys_menu VALUES('402', '对话管理', '4', '3', 'conversation', 'candy/conversation/index', '', '', 1, 0, 'C', '0', '0', 'candy:conversation:list', 'message', 'admin', sysdate(), '', null, '对话管理菜单');

-- Candy AI 用户管理按钮权限
INSERT INTO sys_menu VALUES('4000', 'Candy用户查询', '400', '1', '', '', '', '', 1, 0, 'F', '0', '0', 'candy:user:query', '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('4001', 'Candy用户新增', '400', '2', '', '', '', '', 1, 0, 'F', '0', '0', 'candy:user:add', '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('4002', 'Candy用户修改', '400', '3', '', '', '', '', 1, 0, 'F', '0', '0', 'candy:user:edit', '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('4003', 'Candy用户删除', '400', '4', '', '', '', '', 1, 0, 'F', '0', '0', 'candy:user:remove', '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('4004', 'Candy用户导出', '400', '5', '', '', '', '', 1, 0, 'F', '0', '0', 'candy:user:export', '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('4005', '重置密码', '400', '6', '', '', '', '', 1, 0, 'F', '0', '0', 'candy:user:resetPwd', '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('4006', '邮箱验证', '400', '7', '', '', '', '', 1, 0, 'F', '0', '0', 'candy:user:verifyEmail', '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('4007', '手机验证', '400', '8', '', '', '', '', 1, 0, 'F', '0', '0', 'candy:user:verifyPhone', '#', 'admin', sysdate(), '', null, '');

-- AI角色管理按钮权限
INSERT INTO sys_menu VALUES('4010', 'AI角色查询', '401', '1', '', '', '', '', 1, 0, 'F', '0', '0', 'candy:character:query', '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('4011', 'AI角色新增', '401', '2', '', '', '', '', 1, 0, 'F', '0', '0', 'candy:character:add', '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('4012', 'AI角色修改', '401', '3', '', '', '', '', 1, 0, 'F', '0', '0', 'candy:character:edit', '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('4013', 'AI角色删除', '401', '4', '', '', '', '', 1, 0, 'F', '0', '0', 'candy:character:remove', '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('4014', 'AI角色导出', '401', '5', '', '', '', '', 1, 0, 'F', '0', '0', 'candy:character:export', '#', 'admin', sysdate(), '', null, '');

-- 对话管理按钮权限
INSERT INTO sys_menu VALUES('4020', '对话查询', '402', '1', '', '', '', '', 1, 0, 'F', '0', '0', 'candy:conversation:query', '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('4021', '对话删除', '402', '2', '', '', '', '', 1, 0, 'F', '0', '0', 'candy:conversation:remove', '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('4022', '对话导出', '402', '3', '', '', '', '', 1, 0, 'F', '0', '0', 'candy:conversation:export', '#', 'admin', sysdate(), '', null, '');

-- 为管理员角色分配Candy AI管理权限
INSERT INTO sys_role_menu (role_id, menu_id) VALUES 
(1, 4),   -- Candy AI 管理目录
(1, 400), -- Candy用户管理
(1, 401), -- AI角色管理
(1, 402), -- 对话管理
(1, 4000), (1, 4001), (1, 4002), (1, 4003), (1, 4004), (1, 4005), (1, 4006), (1, 4007), -- Candy用户管理按钮
(1, 4010), (1, 4011), (1, 4012), (1, 4013), (1, 4014), -- AI角色管理按钮
(1, 4020), (1, 4021), (1, 4022); -- 对话管理按钮 