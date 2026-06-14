<template>
  <div class="ai-assistant">
    <button
      class="ai-ball"
      :class="{ active: visible, streaming: streaming }"
      :style="ballStyle"
      @mousedown="startBallDrag"
      @click="toggleWindow"
    >
      <span>智发</span>
      <small>AI</small>
    </button>

    <section
      v-show="visible"
      ref="panel"
      class="ai-panel"
      :class="{ maximized: maximized }"
      :style="panelStyle"
    >
      <header class="ai-header" @mousedown="startPanelDrag">
        <div class="ai-title">
          <strong>智发 AI</strong>
          <span>{{ currentTitle }}</span>
        </div>
        <div class="ai-window-actions">
          <button title="新建聊天" @click.stop="newChat"><i class="el-icon-plus"></i></button>
          <button title="历史会话" @click.stop="historyVisible = !historyVisible"><i class="el-icon-chat-dot-round"></i></button>
          <button :title="maximized ? '还原' : '最大化'" @click.stop="toggleMaximize">
            <i :class="maximized ? 'el-icon-copy-document' : 'el-icon-full-screen'"></i>
          </button>
          <button title="关闭" @click.stop="visible = false"><i class="el-icon-close"></i></button>
        </div>
      </header>

      <div class="ai-body">
        <transition name="ai-history-slide">
          <aside v-show="historyVisible" class="ai-history">
          <button class="ai-new-chat" @click="newChat"><i class="el-icon-plus"></i> 新建聊天</button>
          <div class="ai-history-list">
            <button
              v-for="item in conversations"
              :key="item.conversationId"
              :class="{ selected: item.conversationId === conversationId }"
              @click="openConversation(item)"
            >
              <span>{{ item.title || '新的聊天' }}</span>
              <small>{{ item.updateTime || item.createTime }}</small>
            </button>
          </div>
          </aside>
        </transition>

        <main class="ai-chat">
          <div ref="messages" class="ai-messages">
            <div v-if="messages.length === 0" class="ai-empty">
              <strong>你好，我是智发 AI</strong>
              <span>输入问题后按 Enter 发送，Shift + Enter 换行。</span>
            </div>

            <div
              v-for="message in messages"
              :key="message.localId || message.messageId"
              class="ai-message"
              :class="message.role"
            >
              <div class="ai-avatar">{{ message.role === 'user' ? '我' : 'AI' }}</div>
              <div class="ai-bubble">
                <div v-if="message.role === 'assistant'" class="ai-md" v-html="render(message.content)"></div>
                <div v-else>{{ message.content }}</div>
              </div>
            </div>
          </div>

          <transition name="ai-suggestions-swap" mode="out-in">
            <div
              v-if="suggestionQuestions.length && !streaming"
              :key="suggestionRouteKey"
              class="ai-suggestions"
            >
              <button
                v-for="question in suggestionQuestions"
                :key="question"
                type="button"
                @click="useSuggestion(question)"
              >
                {{ question }}
              </button>
            </div>
          </transition>

          <footer class="ai-input">
            <textarea
              ref="inputBox"
              v-model="input"
              :disabled="streaming"
              placeholder="给智发 AI 发送消息"
              @keydown.enter="handleEnter"
            ></textarea>
            <button :disabled="!canSend" :title="streaming ? '正在回复' : '发送'" @click="send">
              <i :class="streaming ? 'el-icon-loading' : 'el-icon-top'"></i>
            </button>
          </footer>
        </main>
      </div>

      <template v-if="!maximized">
        <span class="ai-resize ai-resize-n" @mousedown="startResize('n', $event)"></span>
        <span class="ai-resize ai-resize-e" @mousedown="startResize('e', $event)"></span>
        <span class="ai-resize ai-resize-s" @mousedown="startResize('s', $event)"></span>
        <span class="ai-resize ai-resize-w" @mousedown="startResize('w', $event)"></span>
        <span class="ai-resize ai-resize-ne" @mousedown="startResize('ne', $event)"></span>
        <span class="ai-resize ai-resize-se" @mousedown="startResize('se', $event)"></span>
        <span class="ai-resize ai-resize-sw" @mousedown="startResize('sw', $event)"></span>
        <span class="ai-resize ai-resize-nw" @mousedown="startResize('nw', $event)"></span>
      </template>
    </section>
  </div>
</template>

<script>
import { listConversations, listMessages } from '../api/chat'
import { streamChat } from '../utils/sse'
import { renderMarkdown } from '../utils/markdown'

const BALL_KEY = 'zf-ai-ball-position'
const PANEL_KEY = 'zf-ai-panel-rect'
const MIN_WIDTH = 420
const MIN_HEIGHT = 460
const EDGE_GAP = 12

function defaultPanel() {
  const width = 560
  const height = 680
  const viewportWidth = typeof window === 'undefined' ? 1440 : window.innerWidth
  const viewportHeight = typeof window === 'undefined' ? 900 : window.innerHeight
  return {
    left: Math.max(EDGE_GAP, viewportWidth - width - 32),
    top: Math.max(EDGE_GAP, viewportHeight - height - 88),
    width,
    height
  }
}

export default {
  name: 'AiAssistant',
  data() {
    return {
      visible: false,
      historyVisible: false,
      maximized: false,
      streaming: false,
      input: '',
      conversationId: null,
      conversations: [],
      messages: [],
      ball: { right: 28, bottom: 34 },
      panel: defaultPanel(),
      dragState: null
    }
  },
  computed: {
    canSend() {
      return this.input.trim() && !this.streaming
    },
    currentTitle() {
      const current = this.conversations.find(item => item.conversationId === this.conversationId)
      return current ? current.title : '新的聊天'
    },
    ballStyle() {
      return { right: this.ball.right + 'px', bottom: this.ball.bottom + 'px' }
    },
    panelStyle() {
      if (this.maximized) return {}
      return {
        left: this.panel.left + 'px',
        top: this.panel.top + 'px',
        width: this.panel.width + 'px',
        height: this.panel.height + 'px'
      }
    },
    suggestionRouteKey() {
      return this.$route ? this.$route.path : 'default'
    },
    suggestionQuestions() {
      const route = this.$route ? this.$route.path : ''
      if (route.indexOf('ims_damage') !== -1) {
        return [
          '帮我分析这个月报损总金额是多少',
          '这个月哪些产品报损金额最高？',
          '按报损原因汇总一下本月报损情况'
        ]
      }
      if (route.indexOf('ims_stock_flow') !== -1) {
        return [
          '帮我分析最近一周库存流水趋势',
          '哪些产品出入库最频繁？',
          '本月入库和出库金额分别是多少？'
        ]
      }
      if (route.indexOf('ims_inventory') !== -1) {
        return [
          '当前库存总量和可用量是多少？',
          '按仓库汇总一下现存量',
          '哪些产品可用库存不足？'
        ]
      }
      if (route.indexOf('ims_inbound') !== -1) {
        return [
          '帮我汇总本月采购入库金额和数量',
          '本月哪些供应商入库金额最高？',
          '哪些产品本月入库数量最多？'
        ]
      }
      if (route.indexOf('ims_delivery') !== -1) {
        return [
          '帮我分析本月送货金额和数量',
          '本月送货金额最高的客户有哪些？',
          '哪些产品本月送货最多？'
        ]
      }
      if (route.indexOf('ims_outbound') !== -1) {
        return [
          '帮我汇总本月出库金额和数量',
          '本月销售出库和样品出库分别是多少？',
          '哪些产品本月出库最多？'
        ]
      }
      if (route.indexOf('ims_sale_return') !== -1) {
        return [
          '帮我分析本月销售退货金额和数量',
          '本月退货原因主要有哪些？',
          '哪些产品本月退货最多？'
        ]
      }
      if (route.indexOf('ims_sample') !== -1) {
        return [
          '帮我汇总本月样品单数量和样品数量',
          '本月样品用途分布是什么？',
          '哪些产品本月送样最多？'
        ]
      }
      if (route.indexOf('ims_fixed_asset') !== -1) {
        return [
          '当前固定资产总原值和净值是多少？',
          '按使用部门统计固定资产情况',
          '帮我分析固定资产折旧趋势和风险资产'
        ]
      }
      if (route.indexOf('ims_payroll_period') !== -1) {
        return [
          '帮我汇总本月工资总额',
          '哪个部门本月工资支出最高？',
          '检查本月工资是否有异常扣款'
        ]
      }
      if (route.indexOf('ims_unit') !== -1) {
        return [
          '帮我分析计量单位是否有停用或资料缺失',
          '哪些计量单位被产品使用得最多？',
          '当前单位主数据整体是否健康？'
        ]
      }
      if (route.indexOf('ims_warehouse_location') !== -1) {
        return [
          '帮我检查哪些仓库还没有配置货位',
          '货位资料里有没有停用或异常情况？',
          '按仓库汇总一下货位配置情况'
        ]
      }
      if (route.indexOf('ims_warehouse') !== -1) {
        return [
          '帮我分析仓库和货位配置是否完整',
          '哪些仓库没有配置货位？',
          '当前仓库基础资料整体有哪些风险？'
        ]
      }
      if (route.indexOf('ims_supplier') !== -1) {
        return [
          '帮我检查供应商联系方式是否完整',
          '有哪些供应商资料缺少电话或联系人？',
          '供应商主数据整体是否健康？'
        ]
      }
      if (route.indexOf('ims_product_category') !== -1) {
        return [
          '帮我分析产品分类使用情况',
          '哪些产品分类没有被产品使用？',
          '分类和品牌基础资料整体是否健康？'
        ]
      }
      if (route.indexOf('ims_product') !== -1) {
        return [
          '帮我检查产品资料是否完整',
          '哪些产品缺少分类、单位、品牌或价格？',
          '按分类分析一下产品主数据情况'
        ]
      }
      if (route.indexOf('ims_enterprise') !== -1) {
        return [
          '帮我检查企业资料是否完整',
          '哪些企业缺少税号、银行或联系方式？',
          '企业基础资料有哪些需要补充？'
        ]
      }
      if (route.indexOf('ims_employee') !== -1) {
        return [
          '帮我分析员工资料完整性',
          '哪些员工缺少手机号或部门信息？',
          '按部门汇总一下员工数量和状态'
        ]
      }
      if (route.indexOf('ims_customer') !== -1) {
        return [
          '帮我检查客户联系方式是否完整',
          '有哪些客户缺少联系人、电话或地址？',
          '客户主数据整体是否健康？'
        ]
      }
      if (route.indexOf('ims_brand') !== -1) {
        return [
          '帮我分析品牌使用情况',
          '哪些品牌没有被产品使用？',
          '品牌和分类资料有哪些异常？'
        ]
      }
      return []
    }
  },
  mounted() {
    this.restorePosition()
    this.loadConversations()
    window.addEventListener('mousemove', this.onMove)
    window.addEventListener('mouseup', this.stopDrag)
    window.addEventListener('resize', this.keepPanelInViewport)
  },
  beforeDestroy() {
    window.removeEventListener('mousemove', this.onMove)
    window.removeEventListener('mouseup', this.stopDrag)
    window.removeEventListener('resize', this.keepPanelInViewport)
  },
  methods: {
    render(content) {
      return renderMarkdown(content)
    },
    async loadConversations() {
      try {
        const res = await listConversations()
        this.conversations = res.data || []
      } catch (e) {
        this.conversations = []
      }
    },
    async openConversation(item) {
      this.conversationId = item.conversationId
      const res = await listMessages(item.conversationId)
      this.messages = (res.data || []).map(message => ({
        ...message,
        localId: message.messageId
      }))
      this.historyVisible = false
      this.scrollToBottom()
    },
    newChat() {
      this.conversationId = null
      this.messages = []
      this.input = ''
      this.historyVisible = false
    },
    handleEnter(event) {
      if (event.shiftKey) return
      event.preventDefault()
      this.send()
    },
    useSuggestion(question) {
      this.input = question
      this.$nextTick(() => {
        const inputBox = this.$refs.inputBox
        if (inputBox) inputBox.focus()
      })
    },
    async send() {
      if (!this.canSend) return
      const text = this.input.trim()
      this.input = ''
      this.visible = true
      this.streaming = true
      this.messages.push({ localId: Date.now() + '-u', role: 'user', content: text })
      const assistant = { localId: Date.now() + '-a', role: 'assistant', content: '' }
      this.messages.push(assistant)
      this.scrollToBottom()

      const payload = {
        conversationId: this.conversationId,
        message: text,
        route: this.$route ? this.$route.path : '',
        pageTitle: this.$route && this.$route.meta ? this.$route.meta.title : document.title,
        queryParams: JSON.stringify(this.$route ? this.$route.query || {} : {}),
        selectedRows: '[]'
      }

      try {
        await streamChat(payload, {
          onMessage: data => {
            assistant.content += data.content || ''
            if (data.conversationId) this.conversationId = data.conversationId
            this.scrollToBottom()
          },
          onToolStart: data => {
            assistant.content += '\n\n> 正在调用工具：' + ((data && data.toolName) || '业务查询') + '。'
            if (data && data.content) assistant.content += data.content
            this.scrollToBottom()
          },
          onToolResult: data => {
            assistant.content += '\n\n> 工具查询完成：' + ((data && data.content) || (data && data.toolName) || '已完成')
            this.scrollToBottom()
          },
          onDone: data => {
            if (data.conversationId) this.conversationId = data.conversationId
          },
          onError: data => {
            assistant.content += '\n\n' + ((data && data.msg) || 'AI 回复失败')
          }
        })
        await this.loadConversations()
      } catch (e) {
        assistant.content += '\n\nAI 聊天接口连接失败，请稍后重试。'
      } finally {
        this.streaming = false
        this.scrollToBottom()
      }
    },
    toggleWindow() {
      if (this.dragState && this.dragState.moved) return
      this.visible = !this.visible
    },
    toggleMaximize() {
      this.maximized = !this.maximized
    },
    scrollToBottom() {
      this.$nextTick(() => {
        const box = this.$refs.messages
        if (box) box.scrollTop = box.scrollHeight
      })
    },
    restorePosition() {
      const ball = localStorage.getItem(BALL_KEY)
      const panel = localStorage.getItem(PANEL_KEY)
      if (ball) this.ball = JSON.parse(ball)
      if (panel) {
        const saved = JSON.parse(panel)
        if (typeof saved.left === 'number' && typeof saved.top === 'number') {
          this.panel = saved
        } else {
          const fallback = defaultPanel()
          this.panel = {
            left: fallback.left,
            top: fallback.top,
            width: saved.width || fallback.width,
            height: saved.height || fallback.height
          }
        }
      }
      this.keepPanelInViewport()
    },
    startBallDrag(event) {
      this.dragState = {
        type: 'ball',
        startX: event.clientX,
        startY: event.clientY,
        right: this.ball.right,
        bottom: this.ball.bottom,
        moved: false
      }
    },
    startPanelDrag(event) {
      if (this.maximized || event.target.closest('.ai-window-actions')) return
      this.dragState = {
        type: 'panel',
        startX: event.clientX,
        startY: event.clientY,
        left: this.panel.left,
        top: this.panel.top,
        moved: false
      }
    },
    startResize(direction, event) {
      this.dragState = {
        type: 'resize',
        direction,
        startX: event.clientX,
        startY: event.clientY,
        left: this.panel.left,
        top: this.panel.top,
        width: this.panel.width,
        height: this.panel.height,
        moved: false
      }
      event.preventDefault()
      event.stopPropagation()
    },
    onMove(event) {
      if (!this.dragState) return
      const dx = event.clientX - this.dragState.startX
      const dy = event.clientY - this.dragState.startY
      if (Math.abs(dx) + Math.abs(dy) > 4) this.dragState.moved = true
      if (this.dragState.type === 'ball') {
        this.ball.right = Math.max(10, this.dragState.right - dx)
        this.ball.bottom = Math.max(10, this.dragState.bottom - dy)
      } else if (this.dragState.type === 'panel') {
        this.panel.left = this.dragState.left + dx
        this.panel.top = this.dragState.top + dy
        this.keepPanelInViewport()
      } else if (this.dragState.type === 'resize') {
        this.resizePanel(dx, dy)
      }
    },
    resizePanel(dx, dy) {
      const direction = this.dragState.direction
      let left = this.dragState.left
      let top = this.dragState.top
      let width = this.dragState.width
      let height = this.dragState.height
      const maxWidth = window.innerWidth - EDGE_GAP * 2
      const maxHeight = window.innerHeight - EDGE_GAP * 2

      if (direction.includes('e')) width = this.dragState.width + dx
      if (direction.includes('s')) height = this.dragState.height + dy
      if (direction.includes('w')) {
        width = this.dragState.width - dx
        left = this.dragState.left + dx
      }
      if (direction.includes('n')) {
        height = this.dragState.height - dy
        top = this.dragState.top + dy
      }

      if (width < MIN_WIDTH) {
        if (direction.includes('w')) left -= MIN_WIDTH - width
        width = MIN_WIDTH
      }
      if (height < MIN_HEIGHT) {
        if (direction.includes('n')) top -= MIN_HEIGHT - height
        height = MIN_HEIGHT
      }

      width = Math.min(width, maxWidth)
      height = Math.min(height, maxHeight)
      left = Math.min(Math.max(EDGE_GAP, left), window.innerWidth - width - EDGE_GAP)
      top = Math.min(Math.max(EDGE_GAP, top), window.innerHeight - height - EDGE_GAP)

      this.panel = { left, top, width, height }
    },
    keepPanelInViewport() {
      const width = Math.min(Math.max(this.panel.width, MIN_WIDTH), window.innerWidth - EDGE_GAP * 2)
      const height = Math.min(Math.max(this.panel.height, MIN_HEIGHT), window.innerHeight - EDGE_GAP * 2)
      this.panel = {
        width,
        height,
        left: Math.min(Math.max(EDGE_GAP, this.panel.left), window.innerWidth - width - EDGE_GAP),
        top: Math.min(Math.max(EDGE_GAP, this.panel.top), window.innerHeight - height - EDGE_GAP)
      }
    },
    stopDrag() {
      if (!this.dragState) return
      localStorage.setItem(BALL_KEY, JSON.stringify(this.ball))
      localStorage.setItem(PANEL_KEY, JSON.stringify(this.panel))
      const moved = this.dragState.moved
      this.$nextTick(() => {
        if (this.dragState) this.dragState.moved = moved
        setTimeout(() => { this.dragState = null }, 0)
      })
    }
  }
}
</script>

<style scoped>
.ai-ball {
  position: fixed;
  z-index: 3000;
  width: 64px;
  height: 64px;
  border: 0;
  border-radius: 50%;
  color: #fff;
  cursor: pointer;
  background: linear-gradient(145deg, #1677ff, #17a983);
  box-shadow: 0 12px 30px rgba(24, 118, 255, .28);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  user-select: none;
}
.ai-ball span {
  font-weight: 700;
  line-height: 18px;
}
.ai-ball small {
  font-size: 12px;
  opacity: .92;
}
.ai-ball.streaming {
  box-shadow: 0 0 0 8px rgba(23, 169, 131, .16), 0 12px 30px rgba(24, 118, 255, .28);
}
.ai-panel {
  position: fixed;
  z-index: 2999;
  min-width: 420px;
  min-height: 460px;
  background: #f5f7fa;
  border: 1px solid #d8e1ec;
  border-radius: 18px;
  box-shadow: 0 24px 64px rgba(30, 42, 60, .18);
  overflow: hidden;
  display: flex;
  flex-direction: column;
}
.ai-panel.maximized {
  left: 18px;
  right: 18px;
  top: 18px;
  bottom: 18px;
  width: auto;
  height: auto;
}
.ai-header {
  height: 58px;
  padding: 0 16px 0 20px;
  border-bottom: 1px solid #e1e8f2;
  display: flex;
  align-items: center;
  justify-content: space-between;
  cursor: move;
  background: #f8fbff;
}
.ai-title {
  min-width: 0;
}
.ai-title strong {
  display: block;
  color: #101827;
  line-height: 22px;
  font-size: 20px;
  letter-spacing: 0;
}
.ai-title span {
  display: block;
  margin-top: 1px;
  max-width: 300px;
  color: #6f7f94;
  font-size: 12px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.ai-window-actions {
  display: flex;
  gap: 8px;
}
.ai-window-actions button,
.ai-new-chat {
  border: 1px solid #d8e1ec;
  background: #fff;
  color: #3f526b;
  border-radius: 10px;
  cursor: pointer;
  transition: all .16s ease;
}
.ai-window-actions button:hover,
.ai-new-chat:hover {
  border-color: #1677ff;
  color: #1677ff;
  background: #f2f7ff;
}
.ai-window-actions button {
  width: 38px;
  height: 38px;
  font-size: 16px;
}
.ai-body {
  flex: 1;
  display: flex;
  min-height: 0;
  background: #f5f7fa;
  overflow: hidden;
}
.ai-history {
  width: 196px;
  flex: 0 0 196px;
  border-right: 1px solid #e7edf5;
  background: #f8fbff;
  padding: 12px;
  box-sizing: border-box;
  overflow: hidden;
  transform-origin: left center;
  will-change: flex-basis, width, opacity, transform;
  transition:
    flex-basis .24s cubic-bezier(.22, .61, .36, 1),
    width .24s cubic-bezier(.22, .61, .36, 1),
    padding .24s cubic-bezier(.22, .61, .36, 1),
    opacity .2s ease,
    transform .24s cubic-bezier(.22, .61, .36, 1);
}
.ai-history-slide-enter,
.ai-history-slide-leave-to {
  width: 0;
  flex-basis: 0;
  padding-left: 0;
  padding-right: 0;
  opacity: 0;
  transform: translateX(-14px);
  border-right-color: transparent;
}
.ai-history-slide-enter-active,
.ai-history-slide-leave-active {
  pointer-events: none;
}
.ai-new-chat {
  width: 100%;
  height: 38px;
  margin-bottom: 10px;
}
.ai-history-list {
  display: flex;
  flex-direction: column;
  gap: 6px;
  overflow: auto;
  max-height: calc(100% - 46px);
}
.ai-history-list button {
  border: 0;
  border-radius: 10px;
  padding: 10px;
  text-align: left;
  background: transparent;
  cursor: pointer;
}
.ai-history-list button.selected,
.ai-history-list button:hover {
  background: #edf5ff;
}
.ai-history-list span,
.ai-history-list small {
  display: block;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.ai-history-list span {
  color: #233246;
}
.ai-history-list small {
  margin-top: 4px;
  color: #94a3b8;
}
.ai-chat {
  flex: 1;
  min-width: 0;
  display: flex;
  flex-direction: column;
  background: #f5f7fa;
  transition: flex-basis .24s cubic-bezier(.22, .61, .36, 1);
}
.ai-messages {
  flex: 1;
  overflow: auto;
  padding: 24px 32px;
  background: #f5f7fa;
}
.ai-empty {
  height: 100%;
  color: #708096;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 8px;
}
.ai-empty strong {
  color: #26364a;
}
.ai-message {
  display: flex;
  gap: 12px;
  margin-bottom: 18px;
}
.ai-message.user {
  flex-direction: row-reverse;
}
.ai-avatar {
  width: 32px;
  height: 32px;
  border-radius: 12px;
  background: #eaf3ff;
  color: #1677ff;
  display: none;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
  font-size: 12px;
  font-weight: 700;
}
.ai-message.user .ai-avatar {
  background: #e7f7f1;
  color: #17a983;
}
.ai-bubble {
  max-width: min(82%, 840px);
  padding: 16px 18px;
  border-radius: 14px;
  background: #fff;
  color: #263445;
  font-size: 15px;
  line-height: 1.78;
  box-shadow: 0 8px 24px rgba(30, 42, 60, .08);
  word-break: break-word;
}
.ai-message.user .ai-bubble {
  background: #eef7ff;
  color: #1f344c;
  border: 1px solid #dbeafe;
}
.ai-suggestions {
  border-top: 1px solid #e7edf5;
  padding: 10px 14px 0;
  display: flex;
  gap: 8px;
  overflow-x: auto;
  background: #f5f7fa;
  scrollbar-width: thin;
}
.ai-suggestions button {
  flex: 0 0 auto;
  max-width: 260px;
  border: 1px solid #dbe7f4;
  border-radius: 999px;
  padding: 8px 12px;
  background: linear-gradient(180deg, #ffffff 0%, #f4f9ff 100%);
  color: #2c4058;
  cursor: pointer;
  font-size: 13px;
  line-height: 18px;
  box-shadow: 0 6px 14px rgba(30, 88, 150, .08);
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  transition: transform .16s ease, border-color .16s ease, box-shadow .16s ease, color .16s ease;
}
.ai-suggestions button:hover {
  transform: translateY(-1px);
  border-color: #8bbcf8;
  color: #1677ff;
  box-shadow: 0 8px 18px rgba(22, 119, 255, .14);
}
.ai-suggestions-swap-enter-active,
.ai-suggestions-swap-leave-active {
  transition: opacity .22s ease, transform .24s cubic-bezier(.22, .61, .36, 1), max-height .24s ease, padding .24s ease;
  overflow: hidden;
}
.ai-suggestions-swap-enter {
  opacity: 0;
  transform: translateY(14px) scale(.98);
  max-height: 0;
  padding-top: 0;
}
.ai-suggestions-swap-leave-to {
  opacity: 0;
  transform: translateY(14px) scale(.98);
  max-height: 0;
  padding-top: 0;
}
.ai-suggestions-swap-enter-to,
.ai-suggestions-swap-leave {
  opacity: 1;
  transform: translateY(0) scale(1);
  max-height: 58px;
}
.ai-input {
  margin: 12px 14px 14px;
  padding: 16px 14px 14px 20px;
  display: flex;
  align-items: flex-end;
  gap: 12px;
  background: #fff;
  border: 1px solid #d8e1ec;
  border-radius: 28px;
  box-shadow: 0 6px 18px rgba(30, 42, 60, .08);
  transition: border-color .18s ease, box-shadow .18s ease, background .18s ease;
}
.ai-input:focus-within {
  border-color: #1677ff;
  background: #fff;
  box-shadow: 0 0 0 3px rgba(22, 119, 255, .12), 0 8px 22px rgba(30, 42, 60, .1);
}
.ai-input textarea {
  flex: 1;
  height: 78px;
  min-height: 78px;
  max-height: 138px;
  resize: none;
  border: 0;
  border-radius: 0;
  padding: 4px 0 0;
  outline: none;
  line-height: 24px;
  color: #1f2d3d;
  background: transparent;
  box-shadow: none;
  font-size: 16px;
  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", "PingFang SC", "Microsoft YaHei", Arial, sans-serif;
}
.ai-input textarea::placeholder {
  color: #8a96a8;
  font-size: 16px;
  font-weight: 500;
}
.ai-input textarea:focus {
  box-shadow: none;
}
.ai-input button {
  width: 44px;
  height: 44px;
  flex: 0 0 44px;
  border: 0;
  border-radius: 50%;
  margin-bottom: 8px;
  background: #1677ff;
  color: #fff;
  cursor: pointer;
  font-weight: 700;
  font-size: 21px;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 8px 18px rgba(22, 119, 255, .24);
  transition: transform .16s ease, background .16s ease, box-shadow .16s ease, opacity .16s ease;
}
.ai-input button:hover:not(:disabled) {
  background: #0f67dd;
  transform: translateY(-1px);
  box-shadow: 0 10px 22px rgba(22, 119, 255, .3);
}
.ai-input button:disabled {
  background: #adc9ec;
  opacity: 1;
  cursor: not-allowed;
  box-shadow: none;
}
.ai-resize {
  position: absolute;
  z-index: 2;
}
.ai-resize-n,
.ai-resize-s {
  left: 10px;
  right: 10px;
  height: 8px;
  cursor: ns-resize;
}
.ai-resize-n {
  top: 0;
}
.ai-resize-s {
  bottom: 0;
}
.ai-resize-e,
.ai-resize-w {
  top: 10px;
  bottom: 10px;
  width: 8px;
  cursor: ew-resize;
}
.ai-resize-e {
  right: 0;
}
.ai-resize-w {
  left: 0;
}
.ai-resize-ne,
.ai-resize-se,
.ai-resize-sw,
.ai-resize-nw {
  width: 16px;
  height: 16px;
}
.ai-resize-ne {
  right: 0;
  top: 0;
  cursor: nesw-resize;
}
.ai-resize-se {
  right: 0;
  bottom: 0;
  cursor: nwse-resize;
}
.ai-resize-sw {
  left: 0;
  bottom: 0;
  cursor: nesw-resize;
}
.ai-resize-nw {
  left: 0;
  top: 0;
  cursor: nwse-resize;
}
.ai-md >>> h1,
.ai-md >>> h2,
.ai-md >>> h3,
.ai-md >>> h4,
.ai-md >>> h5,
.ai-md >>> h6,
.ai-md >>> p,
.ai-md >>> ul,
.ai-md >>> pre,
.ai-md >>> .ai-md-callout,
.ai-md >>> .ai-md-table-wrap {
  margin: 0 0 8px;
}
.ai-md >>> h1,
.ai-md >>> h2,
.ai-md >>> h3,
.ai-md >>> h4,
.ai-md >>> h5,
.ai-md >>> h6 {
  color: #1f2d3d;
  font-weight: 700;
  line-height: 1.45;
  word-break: break-word;
}
.ai-md >>> h1 {
  font-size: 19px;
}
.ai-md >>> h2 {
  font-size: 17px;
}
.ai-md >>> h3,
.ai-md >>> h4,
.ai-md >>> h5,
.ai-md >>> h6 {
  position: relative;
  margin-top: 14px;
  padding-left: 12px;
  font-size: 15px;
}
.ai-md >>> h3::before,
.ai-md >>> h4::before,
.ai-md >>> h5::before,
.ai-md >>> h6::before {
  content: "";
  position: absolute;
  left: 0;
  top: 7px;
  width: 5px;
  height: 5px;
  border-radius: 50%;
  background: #17a983;
  box-shadow: 0 0 0 4px rgba(23, 169, 131, .12);
}
.ai-md >>> p {
  color: #2a3a4e;
}
.ai-md >>> strong {
  color: #142238;
  font-weight: 700;
}
.ai-md >>> code {
  padding: 2px 4px;
  border-radius: 4px;
  background: #eef3f8;
}
.ai-md >>> pre {
  overflow: auto;
  padding: 10px;
  border-radius: 6px;
  background: #1f2937;
  color: #edf2f7;
}
.ai-md >>> ul {
  padding-left: 0;
  list-style: none;
}
.ai-md >>> li {
  position: relative;
  margin-bottom: 6px;
  padding-left: 16px;
  color: #34465c;
  line-height: 1.72;
}
.ai-md >>> li::before {
  content: "";
  position: absolute;
  left: 2px;
  top: 12px;
  width: 6px;
  height: 6px;
  border-radius: 50%;
  background: #8fb2d6;
}
.ai-md >>> .ai-md-callout {
  padding: 10px 12px;
  border: 1px solid #dbe7f4;
  border-left: 4px solid #17a983;
  border-radius: 10px;
  background: linear-gradient(180deg, #f8fbff 0%, #f1f8f5 100%);
  color: #2b4359;
}
.ai-md >>> .ai-md-callout p {
  margin: 0;
  font-size: 13px;
  line-height: 1.7;
}
.ai-md >>> .ai-md-table-wrap {
  overflow-x: auto;
  border: 1px solid #e1e8f2;
  border-radius: 12px;
  background: #fbfdff;
  box-shadow: inset 0 1px 0 rgba(255, 255, 255, .9);
}
.ai-md >>> table {
  border-collapse: collapse;
  min-width: 100%;
  overflow: hidden;
}
.ai-md >>> th,
.ai-md >>> td {
  border: 0;
  border-bottom: 1px solid #e8eef6;
  padding: 9px 10px;
  text-align: left;
  vertical-align: middle;
}
.ai-md >>> th {
  color: #718197;
  background: #f4f8fc;
  font-size: 12px;
  font-weight: 700;
}
.ai-md >>> td {
  color: #27394f;
  font-size: 14px;
}
.ai-md >>> tr:last-child td {
  border-bottom: 0;
}
.ai-md >>> .ai-md-metric-table table,
.ai-md >>> .ai-md-metric-table thead,
.ai-md >>> .ai-md-metric-table tbody,
.ai-md >>> .ai-md-metric-table tr,
.ai-md >>> .ai-md-metric-table th,
.ai-md >>> .ai-md-metric-table td {
  display: block;
}
.ai-md >>> .ai-md-metric-table thead {
  display: none;
}
.ai-md >>> .ai-md-metric-table tbody {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 8px;
  padding: 8px;
}
.ai-md >>> .ai-md-metric-table tr {
  min-height: 72px;
  padding: 10px 12px;
  border: 1px solid #e1e8f2;
  border-radius: 10px;
  background: #fff;
  box-shadow: 0 6px 14px rgba(40, 64, 90, .06);
}
.ai-md >>> .ai-md-metric-table td {
  border: 0;
  padding: 0;
}
.ai-md >>> .ai-md-metric-table td:first-child {
  color: #8a97a8;
  font-size: 12px;
  line-height: 18px;
}
.ai-md >>> .ai-md-metric-table td:nth-child(2) {
  margin-top: 4px;
  color: #172235;
  font-size: 19px;
  line-height: 24px;
  font-weight: 800;
}
.ai-md >>> .ai-md-metric-table td:nth-child(n+3) {
  margin-top: 4px;
  color: #6d7d90;
  font-size: 12px;
}
@media (max-width: 768px) {
  .ai-panel {
    left: 10px !important;
    right: 10px !important;
    top: auto !important;
    bottom: 86px !important;
    width: auto !important;
    height: 70vh !important;
    min-width: 0;
  }
  .ai-history {
    width: 150px;
    flex-basis: 150px;
  }
  .ai-bubble {
    max-width: 86%;
  }
}
</style>
