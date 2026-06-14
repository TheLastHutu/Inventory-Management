import request from '@/utils/request'

// 查询入库单列表
export function listIms_inbound(query) {
  return request({
    url: '/ims/ims_inbound/list',
    method: 'get',
    params: query
  })
}

// 查询入库单详细
export function getIms_inbound(id) {
  return request({
    url: '/ims/ims_inbound/' + id,
    method: 'get'
  })
}

// 新增入库单
export function addIms_inbound(data) {
  return request({
    url: '/ims/ims_inbound',
    method: 'post',
    data: data
  })
}

// 修改入库单
export function updateIms_inbound(data) {
  return request({
    url: '/ims/ims_inbound',
    method: 'put',
    data: data
  })
}

// 删除入库单
export function delIms_inbound(id) {
  return request({
    url: '/ims/ims_inbound/' + id,
    method: 'delete'
  })
}
