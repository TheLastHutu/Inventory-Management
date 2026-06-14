import request from '@/utils/request'

// 查询库存流水列表
export function listIms_stock_flow(query) {
  return request({
    url: '/ims/ims_stock_flow/list',
    method: 'get',
    params: query
  })
}

// 查询库存流水详细
export function getIms_stock_flow(id) {
  return request({
    url: '/ims/ims_stock_flow/' + id,
    method: 'get'
  })
}

// 新增库存流水
export function addIms_stock_flow(data) {
  return request({
    url: '/ims/ims_stock_flow',
    method: 'post',
    data: data
  })
}

// 修改库存流水
export function updateIms_stock_flow(data) {
  return request({
    url: '/ims/ims_stock_flow',
    method: 'put',
    data: data
  })
}

// 删除库存流水
export function delIms_stock_flow(id) {
  return request({
    url: '/ims/ims_stock_flow/' + id,
    method: 'delete'
  })
}
