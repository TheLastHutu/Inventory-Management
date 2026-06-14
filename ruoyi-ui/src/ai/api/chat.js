import request from '@/utils/request'

export function listConversations() {
  return request({
    url: '/ai/conversations',
    method: 'get'
  })
}

export function createConversation(data) {
  return request({
    url: '/ai/conversations',
    method: 'post',
    data: data || {}
  })
}

export function listMessages(conversationId) {
  return request({
    url: '/ai/conversations/' + conversationId + '/messages',
    method: 'get'
  })
}

export function deleteConversation(conversationId) {
  return request({
    url: '/ai/conversations/' + conversationId,
    method: 'delete'
  })
}
