import { getToken } from '@/utils/auth'

function parseEvent(block) {
  const event = { event: 'message', data: '' }
  block.split(/\r?\n/).forEach(line => {
    if (line.indexOf('event:') === 0) {
      event.event = line.slice(6).trim()
    } else if (line.indexOf('data:') === 0) {
      event.data += line.slice(5).trim()
    }
  })
  return event
}

export async function streamChat(payload, handlers) {
  const controller = new AbortController()
  const token = getToken()
  if (!token) {
    throw new Error('登录状态已过期，请重新登录')
  }
  const response = await fetch(process.env.VUE_APP_BASE_API + '/ai/chat/stream', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json;charset=utf-8',
      'Authorization': 'Bearer ' + token
    },
    body: JSON.stringify(payload),
    signal: controller.signal
  })

  if (!response.ok || !response.body) {
    throw new Error('AI聊天接口连接失败')
  }

  const reader = response.body.getReader()
  const decoder = new TextDecoder('utf-8')
  let buffer = ''

  while (true) {
    const { value, done } = await reader.read()
    if (done) break
    buffer += decoder.decode(value, { stream: true })
    const blocks = buffer.split(/\r?\n\r?\n/)
    buffer = blocks.pop()
    blocks.forEach(block => {
      if (!block.trim()) return
      const event = parseEvent(block)
      let data = event.data
      try {
        data = JSON.parse(event.data)
      } catch (e) {
        // Keep raw data when the backend sends plain text.
      }
      if (event.event === 'message' && handlers.onMessage) {
        handlers.onMessage(data)
      } else if (event.event === 'tool_start' && handlers.onToolStart) {
        handlers.onToolStart(data)
      } else if (event.event === 'tool_result' && handlers.onToolResult) {
        handlers.onToolResult(data)
      } else if (event.event === 'done' && handlers.onDone) {
        handlers.onDone(data)
      } else if (event.event === 'error' && handlers.onError) {
        handlers.onError(data)
      }
    })
  }

  return controller
}
