import request from '@/utils/request'

// 查询Candy用户列表
export function listCandyUser(query) {
  return request({
    url: '/candy/user/list',
    method: 'get',
    params: query
  })
}

// 查询Candy用户详细
export function getCandyUser(userId) {
  return request({
    url: '/candy/user/' + userId,
    method: 'get'
  })
}

// 新增Candy用户
export function addCandyUser(data) {
  return request({
    url: '/candy/user',
    method: 'post',
    data: data
  })
}

// 修改Candy用户
export function updateCandyUser(data) {
  return request({
    url: '/candy/user',
    method: 'put',
    data: data
  })
}

// 删除Candy用户
export function delCandyUser(userId) {
  return request({
    url: '/candy/user/' + userId,
    method: 'delete'
  })
}

// 重置Candy用户密码
export function resetCandyUserPwd(userId, password) {
  const data = {
    userId,
    password
  }
  return request({
    url: '/candy/user/resetPwd',
    method: 'put',
    data: data
  })
}

// 获取Candy用户统计信息
export function getCandyUserStats() {
  return request({
    url: '/candy/user/stats',
    method: 'get'
  })
} 