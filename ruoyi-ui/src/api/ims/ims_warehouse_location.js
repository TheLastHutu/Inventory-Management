import request from '@/utils/request'

// 查询货位管理列表
export function listIms_warehouse_location(query) {
  return request({
    url: '/ims/ims_warehouse_location/list',
    method: 'get',
    params: query
  })
}

// 查询货位管理详细
export function getIms_warehouse_location(id) {
  return request({
    url: '/ims/ims_warehouse_location/' + id,
    method: 'get'
  })
}

// 新增货位管理
export function addIms_warehouse_location(data) {
  return request({
    url: '/ims/ims_warehouse_location',
    method: 'post',
    data: data
  })
}

// 修改货位管理
export function updateIms_warehouse_location(data) {
  return request({
    url: '/ims/ims_warehouse_location',
    method: 'put',
    data: data
  })
}

// 删除货位管理
export function delIms_warehouse_location(id) {
  return request({
    url: '/ims/ims_warehouse_location/' + id,
    method: 'delete'
  })
}
