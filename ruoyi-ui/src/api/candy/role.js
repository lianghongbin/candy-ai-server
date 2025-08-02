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