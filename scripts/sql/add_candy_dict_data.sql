-- =============================================
-- Candy AI 相关字典数据
-- 用于角色管理和用户管理功能
-- =============================================

-- 添加角色类型字典
INSERT INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time, remark) VALUES 
('角色类型', 'character_type', '0', 'admin', NOW(), 'AI角色类型列表');

-- 添加角色性别字典
INSERT INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time, remark) VALUES 
('角色性别', 'character_gender', '0', 'admin', NOW(), 'AI角色性别列表');

-- 角色类型数据
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES 
(1, '女友', 'girlfriend', 'character_type', '', 'primary', 'Y', '0', 'admin', NOW(), 'AI女友角色'),
(2, '朋友', 'friend', 'character_type', '', 'success', 'N', '0', 'admin', NOW(), 'AI朋友角色'),
(3, '导师', 'mentor', 'character_type', '', 'warning', 'N', '0', 'admin', NOW(), 'AI导师角色'),
(4, '助手', 'assistant', 'character_type', '', 'info', 'N', '0', 'admin', NOW(), 'AI助手角色');

-- 角色性别数据
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES 
(1, '男', 'male', 'character_gender', '', 'primary', 'Y', '0', 'admin', NOW(), '男性角色'),
(2, '女', 'female', 'character_gender', '', 'success', 'N', '0', 'admin', NOW(), '女性角色');

-- 检查是否已存在，避免重复插入
-- 如果已存在则跳过，如果不存在则插入 