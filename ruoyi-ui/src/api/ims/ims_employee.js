import request from '@/utils/request'

// 查询员工管理列表
export function listIms_employee(query) {
  return request({
    url: '/ims/ims_employee/list',
    method: 'get',
    params: query
  })
}

// 查询员工管理详细
export function getIms_employee(id) {
  return request({
    url: '/ims/ims_employee/' + id,
    method: 'get'
  })
}

// 新增员工管理
export function addIms_employee(data) {
  return request({
    url: '/ims/ims_employee',
    method: 'post',
    data: data
  })
}

// 修改员工管理
export function updateIms_employee(data) {
  return request({
    url: '/ims/ims_employee',
    method: 'put',
    data: data
  })
}

// 删除员工管理
export function delIms_employee(id) {
  return request({
    url: '/ims/ims_employee/' + id,
    method: 'delete'
  })
}
