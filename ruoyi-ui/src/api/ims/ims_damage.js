import request from '@/utils/request'

// 查询报损单列表
export function listIms_damage(query) {
  return request({
    url: '/ims/ims_damage/list',
    method: 'get',
    params: query
  })
}

// 查询报损单详细
export function getIms_damage(id) {
  return request({
    url: '/ims/ims_damage/' + id,
    method: 'get'
  })
}

// 新增报损单
export function addIms_damage(data) {
  return request({
    url: '/ims/ims_damage',
    method: 'post',
    data: data
  })
}

// 修改报损单
export function updateIms_damage(data) {
  return request({
    url: '/ims/ims_damage',
    method: 'put',
    data: data
  })
}

// 删除报损单
export function delIms_damage(id) {
  return request({
    url: '/ims/ims_damage/' + id,
    method: 'delete'
  })
}
