import request from '@/utils/request'

// 查询仓库管理列表
export function listIms_warehouse(query) {
  return request({
    url: '/ims/ims_warehouse/list',
    method: 'get',
    params: query
  })
}

// 查询仓库管理详细
export function getIms_warehouse(id) {
  return request({
    url: '/ims/ims_warehouse/' + id,
    method: 'get'
  })
}

// 新增仓库管理
export function addIms_warehouse(data) {
  return request({
    url: '/ims/ims_warehouse',
    method: 'post',
    data: data
  })
}

// 修改仓库管理
export function updateIms_warehouse(data) {
  return request({
    url: '/ims/ims_warehouse',
    method: 'put',
    data: data
  })
}

// 删除仓库管理
export function delIms_warehouse(id) {
  return request({
    url: '/ims/ims_warehouse/' + id,
    method: 'delete'
  })
}
