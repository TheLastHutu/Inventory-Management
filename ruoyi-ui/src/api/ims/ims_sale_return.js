import request from '@/utils/request'

// 查询销售退货单列表
export function listIms_sale_return(query) {
  return request({
    url: '/ims/ims_sale_return/list',
    method: 'get',
    params: query
  })
}

// 查询销售退货单详细
export function getIms_sale_return(id) {
  return request({
    url: '/ims/ims_sale_return/' + id,
    method: 'get'
  })
}

// 新增销售退货单
export function addIms_sale_return(data) {
  return request({
    url: '/ims/ims_sale_return',
    method: 'post',
    data: data
  })
}

// 修改销售退货单
export function updateIms_sale_return(data) {
  return request({
    url: '/ims/ims_sale_return',
    method: 'put',
    data: data
  })
}

// 删除销售退货单
export function delIms_sale_return(id) {
  return request({
    url: '/ims/ims_sale_return/' + id,
    method: 'delete'
  })
}
