-- 更新AI角色表结构
-- 执行时间: 2025-01-02

-- 1. 删除原表（如果存在）
DROP TABLE IF EXISTS ai_character;

-- 2. 创建新的角色表
CREATE TABLE `ai_character` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `name` varchar(100) NOT NULL COMMENT '角色名称',
  `description` text COMMENT '角色描述',
  `avatar_url` varchar(500) DEFAULT NULL COMMENT '主图URL',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `creator_id` bigint NOT NULL COMMENT '创建者ID',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  
  -- 角色属性
  `style` varchar(20) DEFAULT NULL COMMENT '风格',
  `ethnicity` varchar(20) DEFAULT NULL COMMENT '种族',
  `age` varchar(20) DEFAULT NULL COMMENT '年龄',
  `eye_color` varchar(20) DEFAULT NULL COMMENT '眼睛颜色',
  `hair_style` varchar(20) DEFAULT NULL COMMENT '发型',
  `hair_color` varchar(20) DEFAULT NULL COMMENT '发色',
  `body_type` varchar(20) DEFAULT NULL COMMENT '体型',
  `breast_size` varchar(20) DEFAULT NULL COMMENT '胸围',
  `butt_size` varchar(20) DEFAULT NULL COMMENT '臀围',
  `personality` varchar(20) DEFAULT NULL COMMENT '个性',
  `occupation` varchar(50) DEFAULT NULL COMMENT '职业',
  `hobbies` json DEFAULT NULL COMMENT '爱好(JSON数组)',
  `relationship` varchar(20) DEFAULT NULL COMMENT '关系',
  
  -- 统计信息
  `total_users` int DEFAULT '0' COMMENT '使用用户数',
  `total_conversations` int DEFAULT '0' COMMENT '总对话数',
  `total_messages` int DEFAULT '0' COMMENT '总消息数',
  
  PRIMARY KEY (`id`),
  KEY `idx_creator_id` (`creator_id`),
  KEY `idx_status` (`status`),
  KEY `idx_style` (`style`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='AI角色表';

-- 3. 创建角色创建会话表
CREATE TABLE `ai_character_creation_session` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '会话ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `session_id` varchar(64) NOT NULL COMMENT '会话标识',
  `current_step` varchar(50) DEFAULT NULL COMMENT '当前步骤',
  `selections` json DEFAULT NULL COMMENT '已选择的选项(JSON)',
  `status` varchar(20) DEFAULT 'active' COMMENT '状态(active, completed, cancelled)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_session_id` (`session_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色创建会话表';

COMMIT; 