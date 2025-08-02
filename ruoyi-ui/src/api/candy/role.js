import request from '@/utils/request'

// 查询Candy AI角色列表
export function listCandyRole(query) {
  return request({
    url: '/candy/role/list',
    method: 'get',
    params: query
  })
}

// 查询Candy AI角色详细
export function getCandyRole(id) {
  return request({
    url: '/candy/role/' + id,
    method: 'get'
  })
}

// 新增Candy AI角色
export function addCandyRole(data) {
  return request({
    url: '/candy/role',
    method: 'post',
    data: data
  })
}

// 修改Candy AI角色
export function updateCandyRole(data) {
  return request({
    url: '/candy/role',
    method: 'put',
    data: data
  })
}

// 删除Candy AI角色
export function delCandyRole(id) {
  return request({
    url: '/candy/role/' + id,
    method: 'delete'
  })
}

// 复制Candy AI角色
export function copyCandyRole(id) {
  return request({
    url: '/candy/role/copy/' + id,
    method: 'post'
  })
}

// 导出Candy AI角色
export function exportCandyRole(query) {
  return request({
    url: '/candy/role/export',
    method: 'get',
    params: query
  })
}

// ========== 新的角色管理接口 ==========

// 获取角色列表 (新接口)
export function getCharacterList(query) {
  return request({
    url: '/api/character/list',
    method: 'get',
    params: query
  })
}

// 获取角色详情 (新接口)
export function getCharacterById(id) {
  return request({
    url: '/api/character/' + id,
    method: 'get'
  })
}

// 创建角色 (新接口)
export function createCharacter(data) {
  return request({
    url: '/api/character',
    method: 'post',
    data: data
  })
}

// 更新角色 (新接口)
export function updateCharacter(data) {
  return request({
    url: '/api/character',
    method: 'put',
    data: data
  })
}

// 删除角色 (新接口)
export function deleteCharacter(id) {
  return request({
    url: '/api/character/' + id,
    method: 'delete'
  })
}

// 复制角色 (新接口)
export function copyCharacter(id) {
  return request({
    url: '/api/character/copy/' + id,
    method: 'post'
  })
}

// 获取用户角色 (新接口)
export function getUserCharacters() {
  return request({
    url: '/api/character/user',
    method: 'get'
  })
}

// 获取系统角色 (新接口)
export function getSystemCharacters() {
  return request({
    url: '/api/character/system',
    method: 'get'
  })
}

// ========== 角色创建相关接口 ==========

// 获取创建流程配置
export function getCreationConfig() {
  return request({
    url: '/api/character/creation/config',
    method: 'get'
  })
}

// 开始创建角色
export function startCreation() {
  return request({
    url: '/api/character/creation/start',
    method: 'post'
  })
}

// 获取步骤选项
export function getStepOptions(stepCode) {
  return request({
    url: '/api/character/creation/step/' + stepCode,
    method: 'get'
  })
}

// 保存步骤选择
export function saveStepSelection(stepCode, selections) {
  return request({
    url: '/api/character/creation/step/' + stepCode,
    method: 'post',
    data: selections
  })
}

// 获取创建进度
export function getCreationProgress() {
  return request({
    url: '/api/character/creation/progress',
    method: 'get'
  })
}

// 获取下一步骤
export function getNextStep() {
  return request({
    url: '/api/character/creation/next',
    method: 'get'
  })
}

// 获取上一步骤
export function getPreviousStep() {
  return request({
    url: '/api/character/creation/previous',
    method: 'get'
  })
}

// 完成创建
export function completeCreation() {
  return request({
    url: '/api/character/creation/complete',
    method: 'post'
  })
}

// 取消创建
export function cancelCreation() {
  return request({
    url: '/api/character/creation/cancel',
    method: 'post'
  })
}

// 获取当前会话
export function getCurrentSession() {
  return request({
    url: '/api/character/creation/session',
    method: 'get'
  })
}

// ========== 角色统计相关接口 ==========

// 获取统计概览
export function getCharacterStats() {
  return request({
    url: '/api/character/stats/overview',
    method: 'get'
  })
}

// 获取使用排行
export function getCharacterRanking(limit = 10) {
  return request({
    url: '/api/character/stats/ranking',
    method: 'get',
    params: { limit }
  })
}

// 获取风格分布
export function getStyleDistribution() {
  return request({
    url: '/api/character/stats/style-distribution',
    method: 'get'
  })
}

// 获取个性分布
export function getPersonalityDistribution() {
  return request({
    url: '/api/character/stats/personality-distribution',
    method: 'get'
  })
}

// ========== 角色配置相关接口 ==========

// 获取角色选项
export function getCharacterOptions() {
  return request({
    url: '/api/character/config/options',
    method: 'get'
  })
}

// 获取步骤配置
export function getCreationSteps() {
  return request({
    url: '/api/character/config/steps',
    method: 'get'
  })
}

// 获取特定步骤配置
export function getStepConfig(stepCode) {
  return request({
    url: '/api/character/config/steps/' + stepCode,
    method: 'get'
  })
} 