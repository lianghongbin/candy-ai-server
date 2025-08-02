# Candy AI 角色创建流程设计文档

## 1. 角色模型设计

### 1.1 角色实体 (AiCharacter)

```java
public class AiCharacter {
    // 基础信息
    private Long id;                    // 角色ID
    private String name;                // 角色名称
    private String description;         // 角色描述
    private String avatarUrl;           // 主图URL
    private String status;              // 状态 (0-正常, 1-停用)
    private Long creatorId;             // 创建者ID
    private String createBy;            // 创建者
    private Date createTime;            // 创建时间
    private String updateBy;            // 更新者
    private Date updateTime;            // 更新时间
    private String delFlag;             // 删除标志
    
    // 角色属性
    private String style;               // 风格 (realistic, anime)
    private String ethnicity;           // 种族 (caucasian, latina, asian, ...)
    private String age;                 // 年龄 (teen, 20s, ...)
    private String eyeColor;            // 眼睛颜色 (brown, blue, green, ...)
    private String hairStyle;           // 发型 (straight, short, long, ...)
    private String hairColor;           // 发色 (blonde, brunette, black, ...)
    private String bodyType;            // 体型 (slim, athletic, voluptuous, ...)
    private String breastSize;          // 胸围 (medium, large, huge, ...)
    private String buttSize;            // 臀围 (medium, large, athletic, ...)
    private String personality;         // 个性 (caregiver, sage, innocent, ...)
    private String occupation;          // 职业 (model, doctor, teacher, ...)
    private String hobbies;             // 爱好 (JSON数组，最多3个)
    private String relationship;        // 关系 (stranger, girlfriend, wife, ...)
    
    // 统计信息
    private Integer totalUsers;         // 使用用户数
    private Integer totalConversations; // 总对话数
    private Integer totalMessages;      // 总消息数
}
```

### 1.2 角色创建步骤枚举

```java
public enum CharacterCreationStep {
    STEP_1_STYLE("style", "选择风格", true),
    STEP_2_BASIC_ATTRIBUTES("basic_attributes", "基础属性", true),
    STEP_3_HAIR("hair", "发型发色", true),
    STEP_4_BODY("body", "体型特征", true),
    STEP_5_PERSONALITY("personality", "个性选择", true),
    STEP_6_OCCUPATION_HOBBIES("occupation_hobbies", "职业爱好", true),
    STEP_7_RELATIONSHIP("relationship", "关系选择", true),
    STEP_8_SUMMARY("summary", "确认信息", true);
    
    private String code;
    private String name;
    private boolean required;
}
```

## 2. 角色创建流程设计

### 2.1 创建流程规则

1. **步骤1 - 风格选择**：
   - 选择 "Realistic"：跳过步骤6（职业爱好）
   - 选择 "Anime"：包含所有步骤

2. **步骤依赖关系**：
   - 步骤1 → 步骤2 → 步骤3 → 步骤4 → 步骤5 → 步骤6(可选) → 步骤7 → 步骤8

### 2.2 每个步骤的选项配置

#### 步骤1: 风格选择
```json
{
  "step": "style",
  "title": "Choose Style*",
  "options": [
    {
      "value": "realistic",
      "label": "Realistic",
      "imageUrl": "/images/styles/realistic.jpg",
      "skipSteps": ["occupation_hobbies"]
    },
    {
      "value": "anime", 
      "label": "Anime",
      "imageUrl": "/images/styles/anime.jpg",
      "skipSteps": []
    }
  ]
}
```

#### 步骤2: 基础属性
```json
{
  "step": "basic_attributes",
  "title": "基础属性",
  "sections": [
    {
      "name": "ethnicity",
      "title": "Choose Ethnicity*",
      "options": [
        {"value": "caucasian", "label": "Caucasian", "imageUrl": "/images/ethnicity/caucasian.jpg"},
        {"value": "latina", "label": "Latina", "imageUrl": "/images/ethnicity/latina.jpg"},
        {"value": "asian", "label": "Asian", "imageUrl": "/images/ethnicity/asian.jpg"}
      ]
    },
    {
      "name": "age",
      "title": "Choose Age",
      "options": [
        {"value": "teen", "label": "Teen (18+)"},
        {"value": "20s", "label": "20s"}
      ]
    },
    {
      "name": "eyeColor", 
      "title": "Choose Eye Color*",
      "options": [
        {"value": "brown", "label": "Brown", "imageUrl": "/images/eyes/brown.jpg"},
        {"value": "blue", "label": "Blue", "imageUrl": "/images/eyes/blue.jpg"},
        {"value": "green", "label": "Green", "imageUrl": "/images/eyes/green.jpg"}
      ]
    }
  ]
}
```

#### 步骤3: 发型发色
```json
{
  "step": "hair",
  "title": "发型发色",
  "sections": [
    {
      "name": "hairStyle",
      "title": "Choose Hair Style*", 
      "options": [
        {"value": "straight", "label": "Straight", "imageUrl": "/images/hair/straight.jpg"},
        {"value": "short", "label": "Short", "imageUrl": "/images/hair/short.jpg"},
        {"value": "long", "label": "Long", "imageUrl": "/images/hair/long.jpg"}
      ]
    },
    {
      "name": "hairColor",
      "title": "Choose Hair Color*",
      "options": [
        {"value": "blonde", "label": "Blonde"},
        {"value": "brunette", "label": "Brunette"}, 
        {"value": "black", "label": "Black"}
      ]
    }
  ]
}
```

#### 步骤4: 体型特征
```json
{
  "step": "body",
  "title": "体型特征",
  "sections": [
    {
      "name": "bodyType",
      "title": "Choose Body Type*",
      "options": [
        {"value": "slim", "label": "Slim", "imageUrl": "/images/body/slim.jpg"},
        {"value": "athletic", "label": "Athletic", "imageUrl": "/images/body/athletic.jpg"},
        {"value": "voluptuous", "label": "Voluptuous", "imageUrl": "/images/body/voluptuous.jpg"}
      ]
    },
    {
      "name": "breastSize",
      "title": "Choose Breast Size*",
      "options": [
        {"value": "medium", "label": "Medium", "imageUrl": "/images/breast/medium.jpg"},
        {"value": "large", "label": "Large", "imageUrl": "/images/breast/large.jpg"},
        {"value": "huge", "label": "Huge", "imageUrl": "/images/breast/huge.jpg"}
      ]
    },
    {
      "name": "buttSize",
      "title": "Choose Butt Size*", 
      "options": [
        {"value": "medium", "label": "Medium", "imageUrl": "/images/butt/medium.jpg"},
        {"value": "large", "label": "Large", "imageUrl": "/images/butt/large.jpg"},
        {"value": "athletic", "label": "Athletic", "imageUrl": "/images/butt/athletic.jpg"}
      ]
    }
  ]
}
```

#### 步骤5: 个性选择
```json
{
  "step": "personality",
  "title": "Choose Personality*",
  "options": [
    {
      "value": "caregiver",
      "label": "Caregiver",
      "icon": "heart",
      "description": "Nurturing, protective, and always there to offer comfort."
    },
    {
      "value": "sage", 
      "label": "Sage",
      "icon": "brain",
      "description": "Wise, reflective, and a source of guidance."
    },
    {
      "value": "innocent",
      "label": "Innocent", 
      "icon": "star",
      "description": "Optimistic, naive, and sees world with wonder."
    },
    {
      "value": "jester",
      "label": "Jester",
      "icon": "hat", 
      "description": "Playful, humorous, and always there to make you laugh."
    },
    {
      "value": "temptress",
      "label": "Temptress",
      "icon": "flask",
      "description": "Flirtatious, playful, and always leaving you wanting more."
    },
    {
      "value": "dominant",
      "label": "Dominant",
      "icon": "crown",
      "description": "Assertive, controlling, and commanding."
    },
    {
      "value": "submissive",
      "label": "Submissive",
      "icon": "handcuffs",
      "description": "Obedient, yielding, and happy to follow."
    },
    {
      "value": "lover",
      "label": "Lover",
      "icon": "heart-pink",
      "description": "Romantic, affectionate, and cherishes deep emotional."
    },
    {
      "value": "nympho",
      "label": "Nympho",
      "icon": "heart-fire",
      "description": "Insatiable, passionate, and constantly craving intimacy."
    },
    {
      "value": "mean",
      "label": "Mean",
      "icon": "snowflake",
      "description": "Cold, dismissive, and often sarcastic."
    },
    {
      "value": "confidant",
      "label": "Confidant",
      "icon": "hands",
      "description": "Trustworthy, a good listener, and always can offer advice."
    },
    {
      "value": "experimenter",
      "label": "Experimenter",
      "icon": "atom",
      "description": "Curious, willing, and always eager to try new things."
    }
  ]
}
```

#### 步骤6: 职业爱好 (仅Anime风格)
```json
{
  "step": "occupation_hobbies",
  "title": "职业爱好",
  "sections": [
    {
      "name": "occupation",
      "title": "Choose Occupation*",
      "options": [
        {"value": "model", "label": "Model"},
        {"value": "doctor", "label": "Doctor"},
        {"value": "teacher", "label": "Teacher"},
        {"value": "nurse", "label": "Nurse"},
        {"value": "student", "label": "Student"},
        {"value": "artist", "label": "Artist"},
        {"value": "singer", "label": "Singer/Musician"},
        {"value": "actress", "label": "Movie Star/Actress"},
        {"value": "scientist", "label": "Scientist"},
        {"value": "lawyer", "label": "Lawyer"},
        {"value": "writer", "label": "Writer"},
        {"value": "photographer", "label": "Photographer"},
        {"value": "athlete", "label": "Professional Athlete"},
        {"value": "pilot", "label": "Plane Pilot"},
        {"value": "spy", "label": "Spy"},
        {"value": "dancer", "label": "Dancer"},
        {"value": "soldier", "label": "Soldier"},
        {"value": "police", "label": "Police Officer"},
        {"value": "firefighter", "label": "Firefighter"},
        {"value": "coach", "label": "Life Coach"},
        {"value": "kindergarten", "label": "Kindergarten Teacher"},
        {"value": "florist", "label": "Florist"},
        {"value": "baker", "label": "Baker"},
        {"value": "jewelry", "label": "Jewelry Designer"},
        {"value": "military", "label": "Soldier/Military Personnel"},
        {"value": "astronaut", "label": "Astronaut"},
        {"value": "massage", "label": "Massage Therapist"},
        {"value": "dentist", "label": "Dentist"},
        {"value": "nutritionist", "label": "Nutritionist"},
        {"value": "fitness", "label": "Fitness Coach"},
        {"value": "pharmacist", "label": "Pharmacist"},
        {"value": "hairdresser", "label": "Hairdresser"},
        {"value": "makeup", "label": "Makeup Artist"},
        {"value": "gynecologist", "label": "Gynecologist"},
        {"value": "librarian", "label": "Librarian"},
        {"value": "secretary", "label": "Secretary"},
        {"value": "social_worker", "label": "Social Worker"},
        {"value": "fashion_designer", "label": "Fashion Designer"},
        {"value": "interior_designer", "label": "Interior Designer"},
        {"value": "cook", "label": "Cook"},
        {"value": "designer", "label": "Designer"},
        {"value": "stylist", "label": "Stylist"},
        {"value": "esthetician", "label": "Esthetician"},
        {"value": "yoga", "label": "Yoga Instructor"},
        {"value": "flight_attendant", "label": "Flight Attendant"}
      ]
    },
    {
      "name": "hobbies",
      "title": "Choose Hobbies*",
      "maxSelect": 3,
      "description": "You can choose up to 3 variants",
      "options": [
        {"value": "photography", "label": "Photography"},
        {"value": "fitness", "label": "Fitness"},
        {"value": "vlogging", "label": "Vlogging"},
        {"value": "traveling", "label": "Traveling"},
        {"value": "hiking", "label": "Hiking"},
        {"value": "gaming", "label": "Gaming"},
        {"value": "parties", "label": "Parties"},
        {"value": "series", "label": "Series"},
        {"value": "anime", "label": "Anime"},
        {"value": "cosplay", "label": "Cosplay"},
        {"value": "self_development", "label": "Self-Development"},
        {"value": "writing", "label": "Writing"},
        {"value": "diy_crafting", "label": "DIY Crafting"},
        {"value": "veganism", "label": "Veganism"},
        {"value": "volunteering", "label": "Volunteering"},
        {"value": "cars", "label": "Cars"},
        {"value": "art", "label": "Art"},
        {"value": "netflix", "label": "Watching Netflix"},
        {"value": "manga_anime", "label": "Manga And Anime"},
        {"value": "martial_arts", "label": "Martial Arts"}
      ]
    }
  ]
}
```

#### 步骤7: 关系选择
```json
{
  "step": "relationship",
  "title": "Choose Relationship*",
  "options": [
    {
      "value": "stranger",
      "label": "Stranger",
      "icon": "fedora"
    },
    {
      "value": "school_mate",
      "label": "School Mate", 
      "icon": "graduation_cap"
    },
    {
      "value": "colleague",
      "label": "Colleague",
      "icon": "briefcase"
    },
    {
      "value": "mentor",
      "label": "Mentor",
      "icon": "diamond_books"
    },
    {
      "value": "girlfriend",
      "label": "Girlfriend",
      "icon": "heart"
    },
    {
      "value": "sex_friend",
      "label": "Sex Friend",
      "icon": "gender_symbols"
    },
    {
      "value": "wife",
      "label": "Wife",
      "icon": "wedding_rings"
    },
    {
      "value": "mistress",
      "label": "Mistress",
      "icon": "crown_heart"
    },
    {
      "value": "friend",
      "label": "Friend",
      "icon": "clapping_hands"
    },
    {
      "value": "best_friend",
      "label": "Best Friend",
      "icon": "friendship_hands"
    },
    {
      "value": "step_sister",
      "label": "Step Sister",
      "icon": "heart_locket"
    },
    {
      "value": "step_mom",
      "label": "Step Mom",
      "icon": "heart_flame"
    }
  ]
}
```

#### 步骤8: 确认信息
```json
{
  "step": "summary",
  "title": "Summary",
  "description": "确认角色信息并创建角色"
}
```

## 3. 服务接口设计

### 3.1 角色创建服务 (CharacterCreationService)

```java
public interface CharacterCreationService {
    
    /**
     * 获取角色创建流程配置
     */
    CharacterCreationConfig getCreationConfig();
    
    /**
     * 开始创建角色
     */
    CharacterCreationSession startCreation(Long userId);
    
    /**
     * 获取当前步骤的选项
     */
    StepOptions getStepOptions(String stepCode, CharacterCreationSession session);
    
    /**
     * 保存步骤选择
     */
    void saveStepSelection(String stepCode, Map<String, Object> selections, CharacterCreationSession session);
    
    /**
     * 获取下一步骤
     */
    String getNextStep(String currentStep, CharacterCreationSession session);
    
    /**
     * 获取上一步骤
     */
    String getPreviousStep(String currentStep, CharacterCreationSession session);
    
    /**
     * 完成角色创建
     */
    AiCharacter completeCreation(CharacterCreationSession session);
    
    /**
     * 获取创建进度
     */
    CreationProgress getProgress(CharacterCreationSession session);
    
    /**
     * 取消创建
     */
    void cancelCreation(CharacterCreationSession session);
}
```

### 3.2 角色管理服务 (AiCharacterService)

```java
public interface AiCharacterService {
    
    /**
     * 创建角色
     */
    AiCharacter createCharacter(AiCharacter character);
    
    /**
     * 更新角色
     */
    int updateCharacter(AiCharacter character);
    
    /**
     * 删除角色
     */
    int deleteCharacter(Long id);
    
    /**
     * 获取角色列表
     */
    List<AiCharacter> getCharacterList(AiCharacter query);
    
    /**
     * 获取角色详情
     */
    AiCharacter getCharacterById(Long id);
    
    /**
     * 获取用户创建的角色
     */
    List<AiCharacter> getUserCharacters(Long userId);
    
    /**
     * 获取系统预设角色
     */
    List<AiCharacter> getSystemCharacters();
    
    /**
     * 复制角色
     */
    AiCharacter copyCharacter(Long sourceId, Long userId);
}
```

## 4. 控制器接口设计

### 4.1 角色创建控制器 (CharacterCreationController)

```java
@RestController
@RequestMapping("/api/character/creation")
public class CharacterCreationController {
    
    /**
     * 获取创建流程配置
     */
    @GetMapping("/config")
    public AjaxResult getCreationConfig();
    
    /**
     * 开始创建角色
     */
    @PostMapping("/start")
    public AjaxResult startCreation();
    
    /**
     * 获取步骤选项
     */
    @GetMapping("/step/{stepCode}")
    public AjaxResult getStepOptions(@PathVariable String stepCode);
    
    /**
     * 保存步骤选择
     */
    @PostMapping("/step/{stepCode}")
    public AjaxResult saveStepSelection(@PathVariable String stepCode, @RequestBody Map<String, Object> selections);
    
    /**
     * 获取创建进度
     */
    @GetMapping("/progress")
    public AjaxResult getProgress();
    
    /**
     * 完成创建
     */
    @PostMapping("/complete")
    public AjaxResult completeCreation();
    
    /**
     * 取消创建
     */
    @PostMapping("/cancel")
    public AjaxResult cancelCreation();
}
```

### 4.2 角色管理控制器 (AiCharacterController)

```java
@RestController
@RequestMapping("/api/character")
public class AiCharacterController {
    
    /**
     * 获取角色列表
     */
    @GetMapping("/list")
    public TableDataInfo list(AiCharacter character);
    
    /**
     * 获取角色详情
     */
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable Long id);
    
    /**
     * 创建角色
     */
    @PostMapping
    public AjaxResult add(@RequestBody AiCharacter character);
    
    /**
     * 更新角色
     */
    @PutMapping
    public AjaxResult edit(@RequestBody AiCharacter character);
    
    /**
     * 删除角色
     */
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids);
    
    /**
     * 复制角色
     */
    @PostMapping("/copy/{id}")
    public AjaxResult copyCharacter(@PathVariable Long id);
    
    /**
     * 获取用户角色
     */
    @GetMapping("/user")
    public AjaxResult getUserCharacters();
    
    /**
     * 获取系统角色
     */
    @GetMapping("/system")
    public AjaxResult getSystemCharacters();
}
```

## 5. 数据库设计

### 5.1 角色表 (ai_character)

```sql
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
```

### 5.2 角色创建会话表 (ai_character_creation_session)

```sql
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
```

## 6. 实施计划

### 阶段1: 基础架构 ✅
- [x] 更新角色实体类
- [x] 创建角色创建会话实体
- [x] 更新数据库表结构
- [x] 创建配置管理类

### 阶段2: 服务层实现 ✅
- [x] 实现CharacterCreationService
- [x] 实现AiCharacterService
- [x] 创建步骤配置管理
- [x] 实现创建流程逻辑

### 阶段3: 控制器层 ✅
- [x] 实现CharacterCreationController
- [x] 更新AiCharacterController
- [x] 添加接口文档

### 阶段4: 前端集成 ✅
- [x] 更新前端API调用
- [x] 实现创建流程界面
- [x] 添加图片资源管理

### 阶段5: 测试和优化
- [ ] 单元测试
- [ ] 集成测试
- [ ] 性能优化
- [ ] 文档完善

## 7. 注意事项

1. **图片资源管理**: 所有选项的图片需要统一管理，建议使用CDN
2. **权限控制**: 用户只能管理自己创建的角色
3. **数据验证**: 每个步骤的选择都需要验证
4. **会话管理**: 创建会话需要设置过期时间
5. **错误处理**: 完善的异常处理机制
6. **国际化**: 支持多语言配置
7. **缓存策略**: 配置数据可以缓存提高性能 