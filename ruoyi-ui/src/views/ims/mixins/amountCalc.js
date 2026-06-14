export default {
  methods: {
    normalizeMoney(value) {
      if (value === undefined || value === null || value === '') {
        return ''
      }
      let text = String(value).replace(/[^\d.]/g, '')
      const firstDot = text.indexOf('.')
      if (firstDot !== -1) {
        text = text.slice(0, firstDot + 1) + text.slice(firstDot + 1).replace(/\./g, '')
      }
      const parts = text.split('.')
      if (parts.length === 2) {
        text = `${parts[0]}.${parts[1].slice(0, 2)}`
      }
      text = text.replace(/^0+(?=\d)/, '')
      return text === '' ? '0' : text
    },
    normalizePositiveInteger(value) {
      if (value === undefined || value === null || value === '') {
        return ''
      }
      const text = String(value).replace(/\D/g, '').replace(/^0+(?=\d)/, '')
      return text === '' ? '' : text
    },
    toAmountNumber(value) {
      const number = Number(value)
      return Number.isFinite(number) ? number : 0
    },
    recalcLineAmount(row, priceKey = 'unitPrice') {
      row.qtyPiece = this.normalizePositiveInteger(row.qtyPiece)
      row[priceKey] = this.normalizeMoney(row[priceKey])
      const amount = this.toAmountNumber(row.qtyPiece) * this.toAmountNumber(row[priceKey])
      row.amount = amount ? amount.toFixed(2) : '0.00'
    },
    recalcDetailTotals(list, priceKey = 'unitPrice', hasAmount = true) {
      let totalQty = 0
      let totalAmount = 0
      ;(list || []).forEach((row, index) => {
        row.index = index + 1
        if ('lineNo' in row) {
          row.lineNo = index + 1
        }
        row.qtyPiece = this.normalizePositiveInteger(row.qtyPiece)
        totalQty += this.toAmountNumber(row.qtyPiece)
        if (hasAmount) {
          this.recalcLineAmount(row, priceKey)
          totalAmount += this.toAmountNumber(row.amount)
        }
      })
      this.form.totalQtyPiece = String(totalQty)
      if (hasAmount) {
        this.form.totalAmount = totalAmount.toFixed(2)
      }
    },
    recalcStockFlowAmount() {
      this.form.qtyPiece = this.normalizePositiveInteger(this.form.qtyPiece)
      this.form.unitPrice = this.normalizeMoney(this.form.unitPrice)
      const amount = this.toAmountNumber(this.form.qtyPiece) * this.toAmountNumber(this.form.unitPrice)
      this.form.amount = amount ? amount.toFixed(2) : '0.00'
    },
    normalizePayrollRow(row) {
      row.attendanceDays = this.normalizePositiveInteger(row.attendanceDays)
      ;['baseSalary', 'commission', 'allowance', 'bonus', 'deduction'].forEach(field => {
        row[field] = this.normalizeMoney(row[field])
      })
      const netPay = this.toAmountNumber(row.baseSalary) +
        this.toAmountNumber(row.commission) +
        this.toAmountNumber(row.allowance) +
        this.toAmountNumber(row.bonus) -
        this.toAmountNumber(row.deduction)
      row.netPay = netPay.toFixed(2)
    }
  }
}
