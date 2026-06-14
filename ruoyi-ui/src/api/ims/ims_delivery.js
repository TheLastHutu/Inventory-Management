  import request from '@/utils/request'

// 查询送货单列表
export function listIms_delivery(query) {
  return request({
    url: '/ims/ims_delivery/list',
    method: 'get',
    params: query
  })
}

// 查询送货单详细
export function getIms_delivery(id) {
  return request({
    url: '/ims/ims_delivery/' + id,
    method: 'get'
  })
}

// 新增送货单
export function addIms_delivery(data) {
  return request({
    url: '/ims/ims_delivery',
    method: 'post',
    data: data
  })
}

// 修改送货单
export function updateIms_delivery(data) {
  return request({
    url: '/ims/ims_delivery',
    method: 'put',
    data: data
  })
}

// 删除送货单
export function delIms_delivery(id) {
  return request({
    url: '/ims/ims_delivery/' + id,
    method: 'delete'
  })
}
