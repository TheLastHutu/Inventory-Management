import request from '@/utils/request'

// 查询供应商管理列表
export function listIms_supplier(query) {
  return request({
    url: '/ims/ims_supplier/list',
    method: 'get',
    params: query
  })
}

// 查询供应商管理详细
export function getIms_supplier(id) {
  return request({
    url: '/ims/ims_supplier/' + id,
    method: 'get'
  })
}

// 新增供应商管理
export function addIms_supplier(data) {
  return request({
    url: '/ims/ims_supplier',
    method: 'post',
    data: data
  })
}

// 修改供应商管理
export function updateIms_supplier(data) {
  return request({
    url: '/ims/ims_supplier',
    method: 'put',
    data: data
  })
}

// 删除供应商管理
export function delIms_supplier(id) {
  return request({
    url: '/ims/ims_supplier/' + id,
    method: 'delete'
  })
}
