const DEFAULT_INPUT_MAX_LENGTH = 100
const DEFAULT_TEXTAREA_MAX_LENGTH = 500

const LENGTH_RULES = [
  { pattern: /电话|手机|联系电话|手机号|phone|mobile/i, max: 11 },
  { pattern: /邮箱|email/i, max: 100 },
  { pattern: /身份证|idcard/i, max: 18 },
  { pattern: /银行账号|账号|bankAccount/i, max: 32 },
  { pattern: /税号|纳税人|tax/i, max: 32 },
  { pattern: /地址|address/i, max: 200 },
  { pattern: /备注|说明|描述|用途|原因|内容|remark|description/i, max: 500 },
  { pattern: /编码|编号|单号|货号|code|no/i, max: 64 },
  { pattern: /名称|姓名|客户|供应商|企业|仓库|产品|品牌|单位|货位|部门|资产|name/i, max: 100 },
  { pattern: /联系人|经手人|负责人|保管人|领用人|接收人|员工|handler|manager|keeper/i, max: 50 },
  { pattern: /数量|天数|qty|days/i, max: 10 },
  { pattern: /金额|单价|工资|奖金|补贴|提成|扣款|社保|公积金|税|amount|price|salary|pay/i, max: 14 }
]

function contextText(el) {
  const formItem = el.closest && el.closest('.el-form-item')
  const label = formItem ? formItem.querySelector('.el-form-item__label') : null
  return [
    label && label.textContent,
    el.getAttribute('placeholder'),
    el.getAttribute('name'),
    el.getAttribute('aria-label')
  ].filter(Boolean).join(' ')
}

function inferMaxLength(el) {
  const text = contextText(el)
  const matched = LENGTH_RULES.find(rule => rule.pattern.test(text))
  if (matched) {
    return matched.max
  }
  return el.tagName === 'TEXTAREA' ? DEFAULT_TEXTAREA_MAX_LENGTH : DEFAULT_INPUT_MAX_LENGTH
}

function applyInputMaxLength(root) {
  const scope = root && root.querySelectorAll ? root : document
  scope.querySelectorAll('.el-input__inner, .el-textarea__inner').forEach(el => {
    if (el.dataset.maxlengthApplied || el.hasAttribute('maxlength')) {
      return
    }
    el.setAttribute('maxlength', String(inferMaxLength(el)))
    el.dataset.maxlengthApplied = 'true'
  })
}

export function installInputMaxlength(Vue) {
  Vue.mixin({
    mounted() {
      this.$nextTick(() => applyInputMaxLength(this.$el))
    },
    updated() {
      this.$nextTick(() => applyInputMaxLength(this.$el))
    }
  })

  if (typeof MutationObserver !== 'undefined') {
    Vue.nextTick(() => {
      applyInputMaxLength(document)
      const observer = new MutationObserver(mutations => {
        mutations.forEach(mutation => {
          mutation.addedNodes.forEach(node => {
            if (node.nodeType === 1) {
              applyInputMaxLength(node)
            }
          })
        })
      })
      observer.observe(document.body, { childList: true, subtree: true })
    })
  }
}
