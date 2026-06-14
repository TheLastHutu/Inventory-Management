import request from '@/utils/request'

// 查询库存(现存量)列表
export function listIms_inventory(query) {
  return request({
    url: '/ims/ims_inventory/list',
    method: 'get',
    params: query
  })
}

// 查询库存(现存量)详细
export function getIms_inventory(id) {
  return request({
    url: '/ims/ims_inventory/' + id,
    method: 'get'
  })
}

// 新增库存(现存量)
export function addIms_inventory(data) {
  return request({
    url: '/ims/ims_inventory',
    method: 'post',
    data: data
  })
}

// 修改库存(现存量)
export function updateIms_inventory(data) {
  return request({
    url: '/ims/ims_inventory',
    method: 'put',
    data: data
  })
}

// 删除库存(现存量)
export function delIms_inventory(id) {
  return request({
    url: '/ims/ims_inventory/' + id,
    method: 'delete'
  })
}
