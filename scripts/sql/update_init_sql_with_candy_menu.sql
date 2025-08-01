-- =============================================
-- 更新初始化SQL文件，添加Candy AI菜单
-- 在系统工具菜单之后添加Candy AI菜单
-- =============================================

-- 在系统工具菜单之后添加Candy AI一级菜单
-- 找到这一行：insert into sys_menu values('3', '系统工具', '0', '3', 'tool', null, '', '', 1, 0, 'M', '0', '0', '', 'tool', 'admin', sysdate(), '', null, '系统工具目录');
-- 在其后添加：
insert into sys_menu values('4', 'Candy AI', '0', '4', 'candy', null, '', '', 1, 0, 'M', '0', '0', '', 'candy', 'admin', sysdate(), '', null, 'Candy AI管理目录');

-- 在系统工具二级菜单之后添加Candy AI二级菜单
-- 找到这一行：insert into sys_menu values('117', '系统接口', '3', '3', 'swagger', 'tool/swagger/index', '', '', 1, 0, 'C', '0', '0', 'tool:swagger:list', 'swagger', 'admin', sysdate(), '', null, '系统接口菜单');
-- 在其后添加：
-- Candy AI 二级菜单
insert into sys_menu values('400', '用户管理', '4', '1', 'user', 'candy/user/index', '', '', 1, 0, 'C', '0', '0', 'candy:user:list', 'user', 'admin', sysdate(), '', null, 'Candy AI用户管理菜单');
insert into sys_menu values('401', '角色管理', '4', '2', 'role', 'candy/role/index', '', '', 1, 0, 'C', '0', '0', 'candy:role:list', 'peoples', 'admin', sysdate(), '', null, 'Candy AI角色管理菜单');
insert into sys_menu values('402', '对话管理', '4', '3', 'conversation', 'candy/conversation/index', '', '', 1, 0, 'C', '0', '0', 'candy:conversation:list', 'message', 'admin', sysdate(), '', null, 'Candy AI对话管理菜单');
insert into sys_menu values('403', '系统设置', '4', '4', 'setting', 'candy/setting/index', '', '', 1, 0, 'C', '0', '0', 'candy:setting:list', 'system', 'admin', sysdate(), '', null, 'Candy AI系统设置菜单');

-- 在代码生成按钮之后添加Candy AI按钮权限
-- 找到这一行：insert into sys_menu values('1060', '生成代码', '116', '6', '#', '', '', '', 1, 0, 'F', '0', '0', 'tool:gen:code', '#', 'admin', sysdate(), '', null, '');
-- 在其后添加：
-- Candy AI 用户管理按钮权限
insert into sys_menu values('4000', '用户查询', '400', '1', '', '', '', '', 1, 0, 'F', '0', '0', 'candy:user:query', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('4001', '用户新增', '400', '2', '', '', '', '', 1, 0, 'F', '0', '0', 'candy:user:add', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('4002', '用户修改', '400', '3', '', '', '', '', 1, 0, 'F', '0', '0', 'candy:user:edit', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('4003', '用户删除', '400', '4', '', '', '', '', 1, 0, 'F', '0', '0', 'candy:user:remove', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('4004', '用户导出', '400', '5', '', '', '', '', 1, 0, 'F', '0', '0', 'candy:user:export', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('4005', '重置密码', '400', '6', '', '', '', '', 1, 0, 'F', '0', '0', 'candy:user:resetPwd', '#', 'admin', sysdate(), '', null, '');

-- Candy AI 角色管理按钮权限
insert into sys_menu values('4010', '角色查询', '401', '1', '', '', '', '', 1, 0, 'F', '0', '0', 'candy:role:query', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('4011', '角色新增', '401', '2', '', '', '', '', 1, 0, 'F', '0', '0', 'candy:role:add', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('4012', '角色修改', '401', '3', '', '', '', '', 1, 0, 'F', '0', '0', 'candy:role:edit', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('4013', '角色删除', '401', '4', '', '', '', '', 1, 0, 'F', '0', '0', 'candy:role:remove', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('4014', '角色导出', '401', '5', '', '', '', '', 1, 0, 'F', '0', '0', 'candy:role:export', '#', 'admin', sysdate(), '', null, '');

-- Candy AI 对话管理按钮权限
insert into sys_menu values('4020', '对话查询', '402', '1', '', '', '', '', 1, 0, 'F', '0', '0', 'candy:conversation:query', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('4021', '对话删除', '402', '2', '', '', '', '', 1, 0, 'F', '0', '0', 'candy:conversation:remove', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('4022', '对话导出', '402', '3', '', '', '', '', 1, 0, 'F', '0', '0', 'candy:conversation:export', '#', 'admin', sysdate(), '', null, '');

-- Candy AI 系统设置按钮权限
insert into sys_menu values('4030', '设置查询', '403', '1', '', '', '', '', 1, 0, 'F', '0', '0', 'candy:setting:query', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('4031', '设置修改', '403', '2', '', '', '', '', 1, 0, 'F', '0', '0', 'candy:setting:edit', '#', 'admin', sysdate(), '', null, '');

-- 在角色菜单关联表数据中添加Candy AI菜单权限
-- 找到这一行：insert into sys_role_menu values ('2', '1060');
-- 在其后添加：
-- 为管理员角色分配Candy AI管理权限
insert into sys_role_menu values ('1', '4');   -- Candy AI 管理目录
insert into sys_role_menu values ('1', '400'); -- 用户管理
insert into sys_role_menu values ('1', '401'); -- 角色管理
insert into sys_role_menu values ('1', '402'); -- 对话管理
insert into sys_role_menu values ('1', '403'); -- 系统设置
insert into sys_role_menu values ('1', '4000'); -- 用户查询
insert into sys_role_menu values ('1', '4001'); -- 用户新增
insert into sys_role_menu values ('1', '4002'); -- 用户修改
insert into sys_role_menu values ('1', '4003'); -- 用户删除
insert into sys_role_menu values ('1', '4004'); -- 用户导出
insert into sys_role_menu values ('1', '4005'); -- 重置密码
insert into sys_role_menu values ('1', '4010'); -- 角色查询
insert into sys_role_menu values ('1', '4011'); -- 角色新增
insert into sys_role_menu values ('1', '4012'); -- 角色修改
insert into sys_role_menu values ('1', '4013'); -- 角色删除
insert into sys_role_menu values ('1', '4014'); -- 角色导出
insert into sys_role_menu values ('1', '4020'); -- 对话查询
insert into sys_role_menu values ('1', '4021'); -- 对话删除
insert into sys_role_menu values ('1', '4022'); -- 对话导出
insert into sys_role_menu values ('1', '4030'); -- 设置查询
insert into sys_role_menu values ('1', '4031'); -- 设置修改 