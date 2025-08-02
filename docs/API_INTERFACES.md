# Candy AI 角色管理 API 接口文档

## 1. 角色创建相关接口

### 1.1 获取创建流程配置
- **接口地址**: `GET /api/character/creation/config`
- **功能描述**: 获取角色创建的完整流程配置
- **请求参数**: 无
- **响应示例**:
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "steps": [
      {
        "step": "style",
        "title": "Choose Style*",
        "required": true,
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
    ]
  }
}
```

### 1.2 开始创建角色
- **接口地址**: `POST /api/character/creation/start`
- **功能描述**: 开始角色创建流程，创建新的会话
- **请求参数**: 无
- **响应示例**:
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "id": 1,
    "userId": 1,
    "sessionId": "session_1704067200000_abc12345",
    "currentStep": "style",
    "status": "active",
    "createTime": "2024-01-01 12:00:00"
  }
}
```

### 1.3 获取步骤选项
- **接口地址**: `GET /api/character/creation/step/{stepCode}`
- **功能描述**: 获取指定步骤的选项配置
- **路径参数**: 
  - `stepCode`: 步骤代码 (style, basic_attributes, hair, body, personality, occupation_hobbies, relationship, summary)
- **响应示例**:
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "stepCode": "style",
    "title": "Choose Style*",
    "required": true,
    "options": [
      {
        "value": "realistic",
        "label": "Realistic",
        "imageUrl": "/images/styles/realistic.jpg"
      }
    ],
    "currentSelections": {}
  }
}
```

### 1.4 保存步骤选择
- **接口地址**: `POST /api/character/creation/step/{stepCode}`
- **功能描述**: 保存当前步骤的选择
- **路径参数**: 
  - `stepCode`: 步骤代码
- **请求体**:
```json
{
  "style": "realistic",
  "ethnicity": "caucasian",
  "age": "20s"
}
```
- **响应示例**:
```json
{
  "code": 200,
  "msg": "操作成功"
}
```

### 1.5 获取创建进度
- **接口地址**: `GET /api/character/creation/progress`
- **功能描述**: 获取当前创建会话的进度信息
- **响应示例**:
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "sessionId": "session_1704067200000_abc12345",
    "currentStep": "style",
    "totalSteps": 8,
    "completedSteps": 1,
    "progressPercentage": 12,
    "steps": [
      {
        "stepCode": "style",
        "stepName": "选择风格",
        "completed": true,
        "current": false,
        "required": true,
        "skipped": false
      }
    ],
    "selections": {
      "style": "realistic"
    }
  }
}
```

### 1.6 获取下一步骤
- **接口地址**: `GET /api/character/creation/next`
- **功能描述**: 获取下一步骤的代码
- **响应示例**:
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": "basic_attributes"
}
```

### 1.7 获取上一步骤
- **接口地址**: `GET /api/character/creation/previous`
- **功能描述**: 获取上一步骤的代码
- **响应示例**:
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": "style"
}
```

### 1.8 完成创建
- **接口地址**: `POST /api/character/creation/complete`
- **功能描述**: 完成角色创建流程
- **响应示例**:
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "id": 1,
    "name": "realistic_temptress_1704067200000",
    "description": "A realistic character with temptress personality",
    "style": "realistic",
    "personality": "temptress",
    "createTime": "2024-01-01 12:00:00"
  }
}
```

### 1.9 取消创建
- **接口地址**: `POST /api/character/creation/cancel`
- **功能描述**: 取消当前创建会话
- **响应示例**:
```json
{
  "code": 200,
  "msg": "操作成功"
}
```

## 2. 角色管理相关接口

### 2.1 获取角色列表
- **接口地址**: `GET /api/character/list`
- **功能描述**: 分页查询角色列表
- **请求参数**:
  - `pageNum`: 页码 (默认1)
  - `pageSize`: 每页大小 (默认10)
  - `name`: 角色名称 (可选)
  - `style`: 风格 (可选)
  - `personality`: 个性 (可选)
- **响应示例**:
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "total": 100,
    "rows": [
      {
        "id": 1,
        "name": "My Character",
        "description": "A beautiful character",
        "style": "realistic",
        "personality": "temptress",
        "createTime": "2024-01-01 12:00:00"
      }
    ]
  }
}
```

### 2.2 获取角色详情
- **接口地址**: `GET /api/character/{id}`
- **功能描述**: 根据ID获取角色详情
- **路径参数**: 
  - `id`: 角色ID
- **响应示例**:
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "id": 1,
    "name": "My Character",
    "description": "A beautiful character",
    "avatarUrl": "/images/avatar/1.jpg",
    "style": "realistic",
    "ethnicity": "caucasian",
    "age": "20s",
    "eyeColor": "blue",
    "hairStyle": "long",
    "hairColor": "blonde",
    "bodyType": "slim",
    "breastSize": "medium",
    "buttSize": "medium",
    "personality": "temptress",
    "occupation": "model",
    "hobbies": "[\"photography\", \"fitness\"]",
    "relationship": "girlfriend",
    "totalUsers": 10,
    "totalConversations": 50,
    "totalMessages": 200,
    "createTime": "2024-01-01 12:00:00"
  }
}
```

### 2.3 创建角色
- **接口地址**: `POST /api/character`
- **功能描述**: 创建新角色
- **请求体**:
```json
{
  "name": "My Character",
  "description": "A beautiful character",
  "style": "realistic",
  "personality": "temptress",
  "ethnicity": "caucasian",
  "age": "20s"
}
```
- **响应示例**:
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "id": 1,
    "name": "My Character",
    "createTime": "2024-01-01 12:00:00"
  }
}
```

### 2.4 更新角色
- **接口地址**: `PUT /api/character`
- **功能描述**: 更新角色信息
- **请求体**:
```json
{
  "id": 1,
  "name": "Updated Character",
  "description": "Updated description"
}
```
- **响应示例**:
```json
{
  "code": 200,
  "msg": "操作成功"
}
```

### 2.5 删除角色
- **接口地址**: `DELETE /api/character/{ids}`
- **功能描述**: 删除角色
- **路径参数**: 
  - `ids`: 角色ID数组
- **响应示例**:
```json
{
  "code": 200,
  "msg": "操作成功"
}
```

### 2.6 复制角色
- **接口地址**: `POST /api/character/copy/{id}`
- **功能描述**: 复制角色
- **路径参数**: 
  - `id`: 要复制的角色ID
- **响应示例**:
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "id": 2,
    "name": "My Character_副本",
    "createTime": "2024-01-01 12:00:00"
  }
}
```

### 2.7 获取用户角色
- **接口地址**: `GET /api/character/user`
- **功能描述**: 获取当前用户创建的角色列表
- **响应示例**:
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": [
    {
      "id": 1,
      "name": "My Character",
      "style": "realistic",
      "personality": "temptress",
      "createTime": "2024-01-01 12:00:00"
    }
  ]
}
```

### 2.8 获取系统角色
- **接口地址**: `GET /api/character/system`
- **功能描述**: 获取系统预设的角色列表
- **响应示例**:
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": [
    {
      "id": 1,
      "name": "System Character",
      "style": "realistic",
      "personality": "caregiver",
      "createTime": "2024-01-01 12:00:00"
    }
  ]
}
```

## 3. 角色统计相关接口

### 3.1 获取统计概览
- **接口地址**: `GET /api/character/stats/overview`
- **功能描述**: 获取角色统计概览
- **响应示例**:
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "totalUserCharacters": 10,
    "totalSystemCharacters": 5,
    "totalCharacters": 15,
    "styleStats": {
      "realistic": 8,
      "anime": 2
    },
    "personalityStats": {
      "temptress": 5,
      "caregiver": 3,
      "sage": 2
    },
    "totalUsers": 100,
    "totalConversations": 500,
    "totalMessages": 2000
  }
}
```

### 3.2 获取使用排行
- **接口地址**: `GET /api/character/stats/ranking`
- **功能描述**: 获取角色使用排行
- **请求参数**:
  - `limit`: 返回数量 (默认10)
- **响应示例**:
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": [
    {
      "id": 1,
      "name": "Popular Character",
      "totalUsers": 50,
      "totalConversations": 200,
      "totalMessages": 1000
    }
  ]
}
```

### 3.3 获取风格分布
- **接口地址**: `GET /api/character/stats/style-distribution`
- **功能描述**: 获取角色风格分布统计
- **响应示例**:
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "realistic": 8,
    "anime": 2
  }
}
```

### 3.4 获取个性分布
- **接口地址**: `GET /api/character/stats/personality-distribution`
- **功能描述**: 获取角色个性分布统计
- **响应示例**:
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "temptress": 5,
    "caregiver": 3,
    "sage": 2
  }
}
```

## 4. 角色配置相关接口

### 4.1 获取创建配置
- **接口地址**: `GET /api/character/config/creation`
- **功能描述**: 获取角色创建配置
- **响应示例**:
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "steps": [
      {
        "step": "style",
        "title": "Choose Style*",
        "required": true,
        "options": [...]
      }
    ]
  }
}
```

### 4.2 获取角色选项
- **接口地址**: `GET /api/character/config/options`
- **功能描述**: 获取所有角色属性选项
- **响应示例**:
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "styles": {
      "realistic": {
        "label": "Realistic",
        "imageUrl": "/images/styles/realistic.jpg"
      }
    },
    "ethnicities": {...},
    "ages": {...},
    "eyeColors": {...},
    "hairStyles": {...},
    "hairColors": {...},
    "bodyTypes": {...},
    "breastSizes": {...},
    "buttSizes": {...},
    "personalities": {...},
    "occupations": {...},
    "hobbies": {...},
    "relationships": {...}
  }
}
```

### 4.3 获取步骤配置
- **接口地址**: `GET /api/character/config/steps`
- **功能描述**: 获取所有创建步骤配置
- **响应示例**:
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": [
    {
      "step": "style",
      "title": "Choose Style*",
      "required": true,
      "options": [...]
    }
  ]
}
```

### 4.4 获取特定步骤配置
- **接口地址**: `GET /api/character/config/steps/{stepCode}`
- **功能描述**: 获取特定步骤的配置
- **路径参数**: 
  - `stepCode`: 步骤代码
- **响应示例**:
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "step": "style",
    "title": "Choose Style*",
    "required": true,
    "options": [...]
  }
}
```

## 5. 错误码说明

| 错误码 | 说明 |
|--------|------|
| 200 | 操作成功 |
| 400 | 请求参数错误 |
| 401 | 未授权 |
| 403 | 权限不足 |
| 404 | 资源不存在 |
| 500 | 服务器内部错误 |

## 6. 注意事项

1. 所有接口都需要用户登录认证
2. 角色创建流程需要按照步骤顺序进行
3. 选择Realistic风格时会跳过职业爱好步骤
4. 会话有过期时间，长时间未操作会自动清理
5. 用户只能管理自己创建的角色
6. 系统角色由管理员创建，普通用户只能查看 