# Candy AI 女友项目接口文档

> 所有 `/api/ai/*` 接口均需登录认证，需在请求头携带 `Authorization: Bearer <token>`

## 统一返回格式

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": { ... }
}
```

---

## 1. 获取可用AI角色列表

- **接口地址**：`GET /api/ai/characters`
- **请求参数**：无
- **返回示例**：

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": [
    {
      "id": 1,
      "name": "小美",
      "avatar": "https://...",
      "description": "温柔体贴"
    }
  ]
}
```

---

## 2. 获取角色详情

- **接口地址**：`GET /api/ai/characters/{characterId}`
- **路径参数**：
  - `characterId`：角色ID
- **返回示例**：

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "id": 1,
    "name": "小美",
    "avatar": "https://...",
    "description": "温柔体贴"
  }
}
```

---

## 3. 获取用户会话列表

- **接口地址**：`GET /api/ai/conversations`
- **请求参数**：无
- **返回示例**：

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": [
    {
      "id": 100,
      "characterId": 1,
      "status": "active",
      "messageCount": 12,
      "createTime": "2025-07-31 20:00:00"
    }
  ]
}
```

---

## 4. 开始新会话

- **接口地址**：`POST /api/ai/conversations/start`
- **请求体**：

```json
{
  "characterId": 1
}
```

- **返回示例**：

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "id": 101,
    "characterId": 1,
    "status": "active",
    "messageCount": 0,
    "createTime": "2025-07-31 20:10:00"
  }
}
```

---

## 5. 获取会话消息历史

- **接口地址**：`GET /api/ai/conversations/{conversationId}/messages`
- **路径参数**：
  - `conversationId`：会话ID
- **返回示例**：

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": [
    {
      "id": 1,
      "conversationId": 100,
      "content": "你好呀",
      "messageType": "user",
      "createTime": "2025-07-31 20:01:00"
    },
    {
      "id": 2,
      "conversationId": 100,
      "content": "你好，我是小美~",
      "messageType": "ai",
      "createTime": "2025-07-31 20:01:01"
    }
  ]
}
```

---

## 6. 发送消息并获取AI回复

- **接口地址**：`POST /api/ai/chat/send`
- **请求体**：

```json
{
  "characterId": 1,
  "message": "你喜欢什么？"
}
```

- **返回示例**：

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "id": 3,
    "conversationId": 100,
    "content": "我喜欢和你聊天呀~",
    "messageType": "ai",
    "createTime": "2025-07-31 20:02:00"
  }
}
```

---

## 7. 结束会话

- **接口地址**：`POST /api/ai/conversations/{conversationId}/end`
- **路径参数**：
  - `conversationId`：会话ID
- **返回示例**：

```json
{
  "code": 200,
  "msg": "对话已结束"
}
```

---

## 8. 获取角色记忆

- **接口地址**：`GET /api/ai/characters/{characterId}/memories`
- **路径参数**：
  - `characterId`：角色ID
- **返回示例**：

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": [
    {
      "id": 1,
      "characterId": 1,
      "userId": 123,
      "content": "你喜欢猫",
      "createTime": "2025-07-31 20:00:00"
    }
  ]
}
```

---

> 如需补充参数说明、错误码说明、字段详细解释等，可随时补充！