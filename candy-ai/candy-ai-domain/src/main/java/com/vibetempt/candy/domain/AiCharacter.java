package com.vibetempt.candy.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Date;

/**
 * AI 角色实体
 * 
 * @author vibetempt
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ai_character")
public class AiCharacter extends BaseEntity {
    
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

    /** 角色风格 */
    private String style;

    /** 种族 */
    private String ethnicity;

    /** 年龄 */
    private String age;

    /** 眼睛颜色 */
    private String eyeColor;

    /** 发型 */
    private String hairStyle;

    /** 发色 */
    private String hairColor;

    /** 体型 */
    private String bodyType;

    /** 胸部大小 */
    private String breastSize;

    /** 臀部大小 */
    private String buttSize;

    /** 性格 */
    private String personality;

    /** 职业 */
    private String occupation;

    /** 爱好 */
    private String hobbies;

    /** 关系 */
    private String relationship;

    /** 角色类型 */
    @NotBlank(message = "角色类型不能为空")
    private String characterType;

    /** 会员类型 */
    @NotBlank(message = "会员类型不能为空")
    private String membershipType;

    /** 是否系统创建 */
    private String isSystem;

    /** 是否公开 */
    private Boolean isPublic;

    /** 是否激活 */
    private Integer isActive;

    /** 创建者ID */
    private Long creatorId;

    /** 头像URL */
    private String avatarUrl;

    /** 状态 */
    private String status;
} 