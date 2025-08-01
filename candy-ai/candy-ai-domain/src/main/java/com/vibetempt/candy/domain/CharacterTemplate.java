package com.vibetempt.candy.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Date;

/**
 * 角色模板实体
 * 
 * @author vibetempt
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("candy_character_template")
public class CharacterTemplate extends BaseEntity {
    
    private static final long serialVersionUID = 1L;

    /** 模板ID */
    @TableId(value = "template_id", type = IdType.AUTO)
    private Long templateId;

    /** 模板名称 */
    @NotBlank(message = "模板名称不能为空")
    @Size(max = 50, message = "模板名称长度不能超过50个字符")
    private String templateName;

    /** 模板类型 */
    @NotBlank(message = "模板类型不能为空")
    @Size(max = 30, message = "模板类型长度不能超过30个字符")
    private String templateType;

    /** 模板描述 */
    @Size(max = 500, message = "模板描述长度不能超过500个字符")
    private String description;

    /** 默认头像 */
    private String defaultAvatar;

    /** 默认性格 */
    @Size(max = 200, message = "默认性格长度不能超过200个字符")
    private String defaultPersonality;

    /** 默认背景故事 */
    private String defaultBackground;

    /** 模板状态（0正常 1停用） */
    private String status;

    /** 是否系统模板（0否 1是） */
    private String isSystem;

    /** 创建者ID */
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

    // 手动添加 setter 方法（Lombok 可能没有正常工作）
    public void setStatus(String status) {
        this.status = status;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }
} 