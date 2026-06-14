import request from '@/utils/request'

// 查询计量单位管理列表
export function listIms_unit(query) {
  return request({
    url: '/ims/ims_unit/list',
    method: 'get',
    params: query
  })
}

// 查询计量单位管理详细
export function getIms_unit(id) {
  return request({
    url: '/ims/ims_unit/' + id,
    method: 'get'
  })
}

// 新增计量单位管理
export function addIms_unit(data) {
  return request({
    url: '/ims/ims_unit',
    method: 'post',
    data: data
  })
}

// 修改计量单位管理
export function updateIms_unit(data) {
  return request({
    url: '/ims/ims_unit',
    method: 'put',
    data: data
  })
}

// 删除计量单位管理
export function delIms_unit(id) {
  return request({
    url: '/ims/ims_unit/' + id,
    method: 'delete'
  })
}
