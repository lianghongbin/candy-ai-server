package com.vibetempt.candy.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * AI角色创建会话实体
 * 
 * @author vibetempt
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ai_character_creation_session")
public class AiCharacterCreationSession extends BaseEntity {
    
    private static final long serialVersionUID = 1L;

    /** 会话ID */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 用户ID */
    private Long userId;

    /** 会话标识 */
    private String sessionId;

    /** 当前步骤 */
    private String currentStep;

    /** 已选择的选项(JSON) */
    private String selections;

    /** 状态(active, completed, expired) */
    private String status;

    /** 过期时间 */
    private Date expireTime;
} 