-- =============================================
-- 用户Profile管理功能数据库更新脚本
-- 用于在现有数据库上添加新字段
-- =============================================

-- 检查并添加邮箱验证相关字段
ALTER TABLE sys_user ADD COLUMN IF NOT EXISTS email_verified INT DEFAULT 0 COMMENT '邮箱验证状态（0未验证 1已验证）';
ALTER TABLE sys_user ADD COLUMN IF NOT EXISTS email_verify_code VARCHAR(10) DEFAULT NULL COMMENT '邮箱验证码';
ALTER TABLE sys_user ADD COLUMN IF NOT EXISTS email_verify_expire DATETIME DEFAULT NULL COMMENT '邮箱验证码过期时间';
ALTER TABLE sys_user ADD COLUMN IF NOT EXISTS old_email_verify_code VARCHAR(10) DEFAULT NULL COMMENT '原邮箱验证码（用于换绑验证）';
ALTER TABLE sys_user ADD COLUMN IF NOT EXISTS old_email_verify_expire DATETIME DEFAULT NULL COMMENT '原邮箱验证码过期时间';
ALTER TABLE sys_user ADD COLUMN IF NOT EXISTS new_email_verify_code VARCHAR(10) DEFAULT NULL COMMENT '新邮箱验证码（用于换绑验证）';
ALTER TABLE sys_user ADD COLUMN IF NOT EXISTS new_email_verify_expire DATETIME DEFAULT NULL COMMENT '新邮箱验证码过期时间';

-- 检查并添加手机验证相关字段
ALTER TABLE sys_user ADD COLUMN IF NOT EXISTS phone_verified INT DEFAULT 0 COMMENT '手机验证状态（0未验证 1已验证）';
ALTER TABLE sys_user ADD COLUMN IF NOT EXISTS phone_verify_code VARCHAR(10) DEFAULT NULL COMMENT '手机验证码';
ALTER TABLE sys_user ADD COLUMN IF NOT EXISTS phone_verify_expire DATETIME DEFAULT NULL COMMENT '手机验证码过期时间';

-- 检查并添加用户扩展信息字段
ALTER TABLE sys_user ADD COLUMN IF NOT EXISTS register_source VARCHAR(20) DEFAULT NULL COMMENT '注册来源（email/google/github/discord/apple/facebook）';
ALTER TABLE sys_user ADD COLUMN IF NOT EXISTS avatar_url VARCHAR(255) DEFAULT NULL COMMENT '头像URL';
ALTER TABLE sys_user ADD COLUMN IF NOT EXISTS last_login_source VARCHAR(20) DEFAULT NULL COMMENT '最后登录来源';

-- 更新现有用户的邮箱验证状态（有邮箱的用户设为已验证）
UPDATE sys_user SET email_verified = 1 WHERE email IS NOT NULL AND email != '' AND email_verified IS NULL;

-- 更新现有用户的注册来源
UPDATE sys_user SET register_source = 'email' WHERE register_source IS NULL;

-- 为admin和ry用户设置邮箱验证状态
UPDATE sys_user SET email_verified = 1, register_source = 'email' WHERE user_name IN ('admin', 'ry'); 