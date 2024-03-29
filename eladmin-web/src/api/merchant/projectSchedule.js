import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/merchant/projectSchedule',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: 'api/merchant/projectSchedule/',
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: 'api/merchant/projectSchedule',
    method: 'put',
    data
  })
}
export function details(id) {
  return request({
    url: 'api/merchant/projectSchedule/details/' + id,
    method: 'get'
  })
}
export function assign(data){
  return request({
    url: 'api/merchant/projectSchedule/assign',
    method: 'post',
    data
  })
}
export function manager(data){
  return request({
    url: 'api/merchant/projectSchedule/manager',
    method: 'post',
    data
  })
}
export function transfer(data){
  return request({
    url: 'api/merchant/projectSchedule/transfer',
    method: 'post',
    data
  })
}
export function editUser(data){
  return request({
    url: 'api/merchant/projectSchedule/changeUser',
    method: 'post',
    data
  })
}
export function finance(data){
  return request({
    url: 'api/merchant/projectSchedule/finance',
    method: 'post',
    data
  })
}
export function complete(data){
  return request({
    url: 'api/merchant/projectSchedule/complete',
    method: 'post',
    data
  })
}
export function managers(data){
  return request({
    url: 'api/merchant/projectSchedule/managers',
    method: 'post',
    data
  })
}
export function transfers(data){
  return request({
    url: 'api/merchant/projectSchedule/transfers',
    method: 'post',
    data
  })
}
export function finish(data){
  return request({
    url: 'api/merchant/projectSchedule/finish',
    method: 'post',
    data
  })
}
export default { add, edit, del,details,assign,manager,transfer,finance,complete,editUser,managers,transfers,finish}
