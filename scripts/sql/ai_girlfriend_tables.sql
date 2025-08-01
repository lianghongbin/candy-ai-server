-- AI虚拟女友相关表结构

-- 角色表
CREATE TABLE `ai_character` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `name` varchar(100) NOT NULL COMMENT '角色名称',
  `description` text COMMENT '角色描述',
  `personality` text COMMENT '性格设定',
  `avatar_url` varchar(500) DEFAULT NULL COMMENT '头像URL',
  `character_type` varchar(20) DEFAULT 'girlfriend' COMMENT '角色类型：girlfriend-女友,friend-朋友,mentor-导师,assistant-助手',
  `creator_id` bigint(20) NOT NULL COMMENT '创建者ID',
  `is_active` tinyint(1) DEFAULT 1 COMMENT '是否激活',
  `is_public` tinyint(1) DEFAULT 0 COMMENT '是否公开',
  `total_users` int(11) DEFAULT 0 COMMENT '使用用户数',
  `total_conversations` int(11) DEFAULT 0 COMMENT '总对话数',
  `total_messages` int(11) DEFAULT 0 COMMENT '总消息数',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`),
  KEY `idx_creator_id` (`creator_id`),
  KEY `idx_character_type` (`character_type`),
  KEY `idx_is_active` (`is_active`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='AI角色表';

-- 用户角色关系表
CREATE TABLE `ai_user_character_relation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '关系ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `character_id` bigint(20) NOT NULL COMMENT '角色ID',
  `relationship_level` int(11) DEFAULT 1 COMMENT '关系等级(1-10)',
  `emotional_state` varchar(50) DEFAULT 'neutral' COMMENT '情感状态',
  `trust_level` int(11) DEFAULT 1 COMMENT '信任度(1-10)',
  `total_interactions` int(11) DEFAULT 0 COMMENT '总互动次数',
  `total_messages` int(11) DEFAULT 0 COMMENT '总消息数',
  `first_interaction_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '首次互动时间',
  `last_interaction_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后互动时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_character` (`user_id`,`character_id`),
  KEY `idx_character_id` (`character_id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关系表';

-- 对话会话表
CREATE TABLE `ai_conversation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '会话ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `character_id` bigint(20) NOT NULL COMMENT '角色ID',
  `title` varchar(200) DEFAULT NULL COMMENT '会话标题',
  `description` text COMMENT '会话描述',
  `status` varchar(20) DEFAULT 'active' COMMENT '状态：active-活跃,paused-暂停,ended-结束',
  `message_count` int(11) DEFAULT 0 COMMENT '消息数量',
  `total_tokens` int(11) DEFAULT 0 COMMENT '总Token数',
  `start_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `last_message_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后消息时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_character_id` (`character_id`),
  KEY `idx_status` (`status`),
  KEY `idx_start_time` (`start_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='对话会话表';

-- 消息表
CREATE TABLE `ai_message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '消息ID',
  `conversation_id` bigint(20) NOT NULL COMMENT '会话ID',
  `content` text NOT NULL COMMENT '消息内容',
  `message_type` varchar(20) NOT NULL COMMENT '消息类型：user-用户,ai-AI,system-系统',
  `tokens_used` int(11) DEFAULT 0 COMMENT '使用的Token数',
  `model_used` varchar(50) DEFAULT NULL COMMENT '使用的模型',
  `emotional_context` json DEFAULT NULL COMMENT '情感上下文',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`),
  KEY `idx_conversation_id` (`conversation_id`),
  KEY `idx_message_type` (`message_type`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消息表';

-- 角色记忆表
CREATE TABLE `ai_character_memory` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '记忆ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `character_id` bigint(20) NOT NULL COMMENT '角色ID',
  `memory_type` varchar(20) NOT NULL COMMENT '记忆类型：basic_info-基本信息,preferences-偏好设置,important_events-重要事件,shared_moments-共同回忆,emotional_state-情感状态',
  `memory_key` varchar(100) NOT NULL COMMENT '记忆键',
  `memory_value` json NOT NULL COMMENT '记忆值',
  `importance_level` int(11) DEFAULT 1 COMMENT '重要程度(1-5)',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_character_memory` (`user_id`,`character_id`,`memory_type`,`memory_key`),
  KEY `idx_character_id` (`character_id`),
  KEY `idx_memory_type` (`memory_type`),
  KEY `idx_importance_level` (`importance_level`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色记忆表';

-- 聊天会话状态表
CREATE TABLE `ai_chat_session` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '会话状态ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `character_id` bigint(20) NOT NULL COMMENT '角色ID',
  `is_active` tinyint(1) DEFAULT 1 COMMENT '是否活跃',
  `current_conversation_id` bigint(20) DEFAULT NULL COMMENT '当前会话ID',
  `context_data` json DEFAULT NULL COMMENT '上下文数据',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_character_session` (`user_id`,`character_id`),
  KEY `idx_character_id` (`character_id`),
  KEY `idx_is_active` (`is_active`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='聊天会话状态表';

-- 聊天统计表
CREATE TABLE `ai_chat_analytics` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '统计ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `character_id` bigint(20) NOT NULL COMMENT '角色ID',
  `date` date NOT NULL COMMENT '日期',
  `message_count` int(11) DEFAULT 0 COMMENT '消息数量',
  `conversation_count` int(11) DEFAULT 0 COMMENT '对话数量',
  `total_duration` int(11) DEFAULT 0 COMMENT '总时长(分钟)',
  `tokens_used` int(11) DEFAULT 0 COMMENT 'Token使用量',
  `average_sentiment` decimal(5,2) DEFAULT 0.00 COMMENT '平均情感值',
  `dominant_emotion` varchar(50) DEFAULT NULL COMMENT '主要情感',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_character_date` (`user_id`,`character_id`,`date`),
  KEY `idx_character_id` (`character_id`),
  KEY `idx_date` (`date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='聊天统计表';

-- 插入默认角色数据
INSERT INTO `ai_character` (`name`, `description`, `personality`, `character_type`, `creator_id`, `is_public`, `create_by`) VALUES
('艾莉', '一个温柔可爱的AI女友，喜欢聊天和关心人', '温柔、体贴、善解人意，喜欢听你分享生活中的点点滴滴，会给你温暖的安慰和建议', 'girlfriend', 1, 1, 'admin'),
('小雅', '一个活泼开朗的AI朋友，总是能带来快乐', '活泼、开朗、幽默，喜欢开玩笑，能让你心情变好，是个很好的朋友', 'friend', 1, 1, 'admin'),
('导师小明', '一个知识渊博的AI导师，可以帮你学习各种知识', '博学、耐心、专业，能够深入浅出地解释复杂概念，是个很好的学习伙伴', 'mentor', 1, 1, 'admin'),
('助手小智', '一个智能的AI助手，可以帮你处理各种任务', '智能、高效、专业，能够快速准确地回答问题和完成任务', 'assistant', 1, 1, 'admin'); 