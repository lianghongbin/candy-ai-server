package com.vibetempt.candy.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Date;

/**
 * AI 角色模板实体
 * 
 * @author candy-ai
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ai_character")
public class AiCharacterTemplate extends BaseEntity {
    
    private static final long serialVersionUID = 1L;

    /** 角色ID */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 角色名称 */
    @NotBlank(message = "角色名称不能为空")
    @Size(max = 100, message = "角色名称长度不能超过100个字符")
    private String name;

    /** 角色描述 */
    private String description;

    /** 性格设定 */
    private String personality;

    /** 头像URL */
    private String avatarUrl;

    /** 角色类型 */
    private String characterType;

    /** 创建者ID */
    private Long creatorId;

    /** 是否激活 */
    private Integer isActive;

    /** 是否公开 */
    private Integer isPublic;

    /** 使用用户数 */
    private Integer totalUsers;

    /** 总对话数 */
    private Integer totalConversations;

    /** 总消息数 */
    private Integer totalMessages;

    /** 性别 */
    private String gender;

    /** 年龄 */
    private Integer age;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;
} 