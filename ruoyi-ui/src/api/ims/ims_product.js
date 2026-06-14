import request from '@/utils/request'

// 查询产品管理列表
export function listIms_product(query) {
  return request({
    url: '/ims/ims_product/list',
    method: 'get',
    params: query
  })
}

// 查询产品管理详细
export function getIms_product(id) {
  return request({
    url: '/ims/ims_product/' + id,
    method: 'get'
  })
}

// 新增产品管理
export function addIms_product(data) {
  return request({
    url: '/ims/ims_product',
    method: 'post',
    data: data
  })
}

// 修改产品管理
export function updateIms_product(data) {
  return request({
    url: '/ims/ims_product',
    method: 'put',
    data: data
  })
}

// 删除产品管理
export function delIms_product(id) {
  return request({
    url: '/ims/ims_product/' + id,
    method: 'delete'
  })
}
