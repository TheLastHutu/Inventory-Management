import request from '@/utils/request'

// 查询样品单列表
export function listIms_sample(query) {
  return request({
    url: '/ims/ims_sample/list',
    method: 'get',
    params: query
  })
}

// 查询样品单详细
export function getIms_sample(id) {
  return request({
    url: '/ims/ims_sample/' + id,
    method: 'get'
  })
}

// 新增样品单
export function addIms_sample(data) {
  return request({
    url: '/ims/ims_sample',
    method: 'post',
    data: data
  })
}

// 修改样品单
export function updateIms_sample(data) {
  return request({
    url: '/ims/ims_sample',
    method: 'put',
    data: data
  })
}

// 删除样品单
export function delIms_sample(id) {
  return request({
    url: '/ims/ims_sample/' + id,
    method: 'delete'
  })
}
