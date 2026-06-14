import request from '@/utils/request'

// 查询固定资产列表
export function listIms_fixed_asset(query) {
  return request({
    url: '/ims/ims_fixed_asset/list',
    method: 'get',
    params: query
  })
}

// 查询固定资产详细
export function getIms_fixed_asset(id) {
  return request({
    url: '/ims/ims_fixed_asset/' + id,
    method: 'get'
  })
}

// 新增固定资产
export function addIms_fixed_asset(data) {
  return request({
    url: '/ims/ims_fixed_asset',
    method: 'post',
    data: data
  })
}

// 修改固定资产
export function updateIms_fixed_asset(data) {
  return request({
    url: '/ims/ims_fixed_asset',
    method: 'put',
    data: data
  })
}

// 删除固定资产
export function delIms_fixed_asset(id) {
  return request({
    url: '/ims/ims_fixed_asset/' + id,
    method: 'delete'
  })
}
