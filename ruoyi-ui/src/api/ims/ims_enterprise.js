import request from '@/utils/request'

// 查询企业管理列表
export function listIms_enterprise(query) {
  return request({
    url: '/ims/ims_enterprise/list',
    method: 'get',
    params: query
  })
}

// 查询企业管理详细
export function getIms_enterprise(id) {
  return request({
    url: '/ims/ims_enterprise/' + id,
    method: 'get'
  })
}

// 新增企业管理
export function addIms_enterprise(data) {
  return request({
    url: '/ims/ims_enterprise',
    method: 'post',
    data: data
  })
}

// 修改企业管理
export function updateIms_enterprise(data) {
  return request({
    url: '/ims/ims_enterprise',
    method: 'put',
    data: data
  })
}

// 删除企业管理
export function delIms_enterprise(id) {
  return request({
    url: '/ims/ims_enterprise/' + id,
    method: 'delete'
  })
}
