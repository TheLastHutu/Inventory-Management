import request from '@/utils/request'

// 查询客户管理列表
export function listIms_customer(query) {
  return request({
    url: '/ims/ims_customer/list',
    method: 'get',
    params: query
  })
}

// 查询客户管理详细
export function getIms_customer(id) {
  return request({
    url: '/ims/ims_customer/' + id,
    method: 'get'
  })
}

// 新增客户管理
export function addIms_customer(data) {
  return request({
    url: '/ims/ims_customer',
    method: 'post',
    data: data
  })
}

// 修改客户管理
export function updateIms_customer(data) {
  return request({
    url: '/ims/ims_customer',
    method: 'put',
    data: data
  })
}

// 删除客户管理
export function delIms_customer(id) {
  return request({
    url: '/ims/ims_customer/' + id,
    method: 'delete'
  })
}
