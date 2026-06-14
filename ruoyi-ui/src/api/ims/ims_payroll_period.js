import request from '@/utils/request'

// 查询工资管理列表
export function listIms_payroll_period(query) {
  return request({
    url: '/ims/ims_payroll_period/list',
    method: 'get',
    params: query
  })
}

// 查询工资管理详细
export function getIms_payroll_period(periodId) {
  return request({
    url: '/ims/ims_payroll_period/' + periodId,
    method: 'get'
  })
}

// 新增工资管理
export function addIms_payroll_period(data) {
  return request({
    url: '/ims/ims_payroll_period',
    method: 'post',
    data: data
  })
}

// 修改工资管理
export function updateIms_payroll_period(data) {
  return request({
    url: '/ims/ims_payroll_period',
    method: 'put',
    data: data
  })
}

// 删除工资管理
export function delIms_payroll_period(periodId) {
  return request({
    url: '/ims/ims_payroll_period/' + periodId,
    method: 'delete'
  })
}
