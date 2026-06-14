import request from '@/utils/request'

// 查询销售单列表
export function listIms_sale_order(query) {
  return request({
    url: '/ims/ims_sale_order/list',
    method: 'get',
    params: query
  })
}

// 查询销售单详细
export function getIms_sale_order(id) {
  return request({
    url: '/ims/ims_sale_order/' + id,
    method: 'get'
  })
}

// 新增销售单
export function addIms_sale_order(data) {
  return request({
    url: '/ims/ims_sale_order',
    method: 'post',
    data: data
  })
}

// 修改销售单
export function updateIms_sale_order(data) {
  return request({
    url: '/ims/ims_sale_order',
    method: 'put',
    data: data
  })
}

// 删除销售单
export function delIms_sale_order(id) {
  return request({
    url: '/ims/ims_sale_order/' + id,
    method: 'delete'
  })
}
