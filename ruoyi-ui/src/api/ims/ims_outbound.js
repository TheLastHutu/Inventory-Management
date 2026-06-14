import request from '@/utils/request'

// 查询出库单列表
export function listIms_outbound(query) {
  return request({
    url: '/ims/ims_outbound/list',
    method: 'get',
    params: query
  })
}

// 查询出库单详细
export function getIms_outbound(id) {
  return request({
    url: '/ims/ims_outbound/' + id,
    method: 'get'
  })
}

// 新增出库单
export function addIms_outbound(data) {
  return request({
    url: '/ims/ims_outbound',
    method: 'post',
    data: data
  })
}

// 修改出库单
export function updateIms_outbound(data) {
  return request({
    url: '/ims/ims_outbound',
    method: 'put',
    data: data
  })
}

// 删除出库单
export function delIms_outbound(id) {
  return request({
    url: '/ims/ims_outbound/' + id,
    method: 'delete'
  })
}
