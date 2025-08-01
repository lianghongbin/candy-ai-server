-- Candy AI 用户认证功能扩展
-- 扩展用户表，添加第三方登录和邮箱验证相关字段

USE candy_ai;

-- 1. 扩展 sys_user 表，添加第三方登录和邮箱验证相关字段
ALTER TABLE sys_user 
ADD COLUMN email_verified TINYINT(1) DEFAULT 0 COMMENT '邮箱验证状态（0未验证 1已验证）',
ADD COLUMN email_verify_code VARCHAR(64) DEFAULT NULL COMMENT '邮箱验证码',
ADD COLUMN email_verify_expire DATETIME DEFAULT NULL COMMENT '邮箱验证码过期时间',
ADD COLUMN register_source VARCHAR(20) DEFAULT 'email' COMMENT '注册来源（email/google/discord）',
ADD COLUMN avatar_url VARCHAR(255) DEFAULT NULL COMMENT '头像URL',
ADD COLUMN last_login_source VARCHAR(20) DEFAULT NULL COMMENT '最后登录来源';

-- 添加索引
ALTER TABLE sys_user 
ADD UNIQUE KEY uk_email (email),
ADD KEY idx_register_source (register_source),
ADD KEY idx_email_verified (email_verified);

-- 2. 创建第三方登录关联表
CREATE TABLE IF NOT EXISTS sys_user_oauth (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    oauth_type VARCHAR(20) NOT NULL COMMENT '第三方平台类型（google/discord）',
    oauth_user_id VARCHAR(100) NOT NULL COMMENT '第三方平台用户ID',
    oauth_username VARCHAR(100) DEFAULT NULL COMMENT '第三方平台用户名',
    oauth_email VARCHAR(100) DEFAULT NULL COMMENT '第三方平台邮箱',
    oauth_avatar VARCHAR(255) DEFAULT NULL COMMENT '第三方平台头像',
    access_token VARCHAR(500) DEFAULT NULL COMMENT '访问令牌',
    refresh_token VARCHAR(500) DEFAULT NULL COMMENT '刷新令牌',
    token_expire_time DATETIME DEFAULT NULL COMMENT '令牌过期时间',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_user_oauth (user_id, oauth_type),
    UNIQUE KEY uk_oauth_user (oauth_type, oauth_user_id),
    KEY idx_user_id (user_id),
    KEY idx_oauth_type (oauth_type),
    CONSTRAINT fk_user_oauth_user_id FOREIGN KEY (user_id) REFERENCES sys_user (user_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户第三方登录关联表';

-- 3. 创建邮箱验证码表（可选，也可以使用Redis）
CREATE TABLE IF NOT EXISTS sys_email_verify (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    email VARCHAR(100) NOT NULL COMMENT '邮箱地址',
    verify_code VARCHAR(64) NOT NULL COMMENT '验证码',
    verify_type VARCHAR(20) NOT NULL COMMENT '验证类型（register/reset）',
    expire_time DATETIME NOT NULL COMMENT '过期时间',
    used TINYINT(1) DEFAULT 0 COMMENT '是否已使用（0未使用 1已使用）',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (id),
    KEY idx_email_type (email, verify_type),
    KEY idx_expire_time (expire_time),
    KEY idx_used (used)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='邮箱验证码表';

-- 4. 插入系统配置项
INSERT INTO sys_config (config_name, config_key, config_value, config_type, create_by, create_time, remark) VALUES
('用户注册开关', 'sys.account.registerUser', 'true', 'Y', 'admin', NOW(), '是否允许用户注册'),
('邮箱验证开关', 'sys.account.emailVerify', 'true', 'Y', 'admin', NOW(), '是否启用邮箱验证'),
('Google登录开关', 'sys.account.googleLogin', 'false', 'Y', 'admin', NOW(), '是否启用Google登录'),
('Discord登录开关', 'sys.account.discordLogin', 'false', 'Y', 'admin', NOW(), '是否启用Discord登录'),
('密码最小长度', 'sys.account.passwordMinLength', '6', 'N', 'admin', NOW(), '密码最小长度要求'),
('密码最大长度', 'sys.account.passwordMaxLength', '20', 'N', 'admin', NOW(), '密码最大长度要求')
ON DUPLICATE KEY UPDATE 
config_value = VALUES(config_value),
update_time = NOW();

-- 5. 创建默认用户角色（普通用户角色）
INSERT INTO sys_role (role_name, role_key, role_sort, data_scope, menu_check_strictly, dept_check_strictly, status, del_flag, create_by, create_time, remark) VALUES
('普通用户', 'common', 3, '1', 1, 1, '0', '0', 'admin', NOW(), '普通用户角色')
ON DUPLICATE KEY UPDATE 
role_name = VALUES(role_name),
update_time = NOW();

-- 6. 为普通用户角色分配基础权限（可选，根据实际需求调整）
-- 这里需要根据实际的菜单ID来分配权限
-- INSERT INTO sys_role_menu (role_id, menu_id) VALUES 
-- ((SELECT role_id FROM sys_role WHERE role_key = 'common'), 菜单ID);

-- 7. 更新现有用户的邮箱验证状态（如果有邮箱的用户设为已验证）
UPDATE sys_user SET email_verified = 1 WHERE email IS NOT NULL AND email != '';

-- 8. 创建索引优化查询性能
CREATE INDEX idx_user_email ON sys_user(email);
CREATE INDEX idx_user_register_source ON sys_user(register_source);
CREATE INDEX idx_user_status ON sys_user(status);

-- 9. 添加表注释
ALTER TABLE sys_user COMMENT = '用户表（已扩展第三方登录和邮箱验证功能）';

-- 10. 创建视图：用户登录统计
CREATE OR REPLACE VIEW v_user_login_stats AS
SELECT 
    u.user_id,
    u.user_name,
    u.nick_name,
    u.email,
    u.register_source,
    u.email_verified,
    u.status,
    u.create_time,
    u.last_login_source,
    u.login_date,
    COUNT(o.id) as oauth_count
FROM sys_user u
LEFT JOIN sys_user_oauth o ON u.user_id = o.user_id
GROUP BY u.user_id;

-- 完成提示
SELECT '用户认证功能扩展完成！' as message; 