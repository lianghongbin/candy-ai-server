package com.vibetempt.candy.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Date;

/**
 * AI 角色实例实体
 * 
 * @author vibetempt
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("candy_ai_character")
public class AiCharacter extends BaseEntity {
    
    private static final long serialVersionUID = 1L;

    /** 角色实例ID */
    @TableId(value = "character_id", type = IdType.AUTO)
    private Long characterId;

    /** 角色模板ID */
    private Long templateId;

    /** 模板创建者ID */
    private String templateCreator;

    /** 角色实例名称 */
    @NotBlank(message = "角色名称不能为空")
    @Size(max = 50, message = "角色名称长度不能超过50个字符")
    private String characterName;

    /** 角色状态（0正常 1停用） */
    private String status;

    /** 角色实例拥有者用户ID（可以聊天、管理此角色） */
    private Long ownerUserId;

    /** 创建者ID（创建此角色实例的用户） */
    private String createBy;

    /** 创建时间 */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 更新者ID */
    private String updateBy;

    /** 更新时间 */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /** 备注 */
    private String remark;
} 