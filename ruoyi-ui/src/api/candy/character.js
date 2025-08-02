import request from '@/utils/request'

// 查询角色列表
export function listCharacter(query) {
  return request({
    url: '/api/character/list',
    method: 'get',
    params: query
  })
}

// 查询角色详细
export function getCharacter(id) {
  return request({
    url: '/api/character/' + id,
    method: 'get'
  })
}

// 新增角色
export function addCharacter(data) {
  return request({
    url: '/api/character',
    method: 'post',
    data: data
  })
}

// 修改角色
export function updateCharacter(data) {
  return request({
    url: '/api/character',
    method: 'put',
    data: data
  })
}

// 删除角色
export function delCharacter(id) {
  return request({
    url: '/api/character/' + id,
    method: 'delete'
  })
}

// 复制角色
export function copyCharacter(id) {
  return request({
    url: '/api/character/copy/' + id,
    method: 'post'
  })
}

// 获取角色创建配置
export function getCreationConfig() {
  return request({
    url: '/api/character/creation/config',
    method: 'get'
  })
}

// 开始角色创建
export function startCreation() {
  return request({
    url: '/api/character/creation/start',
    method: 'post'
  })
}

// 获取步骤选项
export function getStepOptions(stepCode, sessionId) {
  return request({
    url: '/api/character/creation/step/' + stepCode,
    method: 'get',
    params: { sessionId }
  })
}

// 保存步骤选择
export function saveStepSelection(stepCode, data) {
  return request({
    url: '/api/character/creation/step/' + stepCode,
    method: 'post',
    data: data
  })
}

// 完成角色创建
export function completeCreation(sessionId) {
  return request({
    url: '/api/character/creation/complete',
    method: 'post',
    data: { sessionId }
  })
}

// 获取创建进度
export function getCreationProgress(sessionId) {
  return request({
    url: '/api/character/creation/progress',
    method: 'get',
    params: { sessionId }
  })
}

// 取消创建
export function cancelCreation(sessionId) {
  return request({
    url: '/api/character/creation/cancel',
    method: 'post',
    data: { sessionId }
  })
} 