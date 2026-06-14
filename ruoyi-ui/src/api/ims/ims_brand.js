import request from '@/utils/request'

// 查询品牌管理列表
export function listIms_brand(query) {
  return request({
    url: '/ims/ims_brand/list',
    method: 'get',
    params: query
  })
}

// 查询品牌管理详细
export function getIms_brand(id) {
  return request({
    url: '/ims/ims_brand/' + id,
    method: 'get'
  })
}

// 新增品牌管理
export function addIms_brand(data) {
  return request({
    url: '/ims/ims_brand',
    method: 'post',
    data: data
  })
}

// 修改品牌管理
export function updateIms_brand(data) {
  return request({
    url: '/ims/ims_brand',
    method: 'put',
    data: data
  })
}

// 删除品牌管理
export function delIms_brand(id) {
  return request({
    url: '/ims/ims_brand/' + id,
    method: 'delete'
  })
}
