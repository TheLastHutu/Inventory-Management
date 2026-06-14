import request from '@/utils/request'

// 查询产品分类列表
export function listIms_product_category(query) {
  return request({
    url: '/ims/ims_product_category/list',
    method: 'get',
    params: query
  })
}

// 查询产品分类详细
export function getIms_product_category(id) {
  return request({
    url: '/ims/ims_product_category/' + id,
    method: 'get'
  })
}

// 新增产品分类
export function addIms_product_category(data) {
  return request({
    url: '/ims/ims_product_category',
    method: 'post',
    data: data
  })
}

// 修改产品分类
export function updateIms_product_category(data) {
  return request({
    url: '/ims/ims_product_category',
    method: 'put',
    data: data
  })
}

// 删除产品分类
export function delIms_product_category(id) {
  return request({
    url: '/ims/ims_product_category/' + id,
    method: 'delete'
  })
}
