export default {
  data() {
    return {
      selectionRows: []
    }
  },
  methods: {
    pageIndex(index) {
      const pageNum = Number(this.queryParams && this.queryParams.pageNum) || 1
      const pageSize = Number(this.queryParams && this.queryParams.pageSize) || 10
      return (pageNum - 1) * pageSize + index + 1
    },
    setSelectionRows(selection) {
      this.selectionRows = selection || []
    },
    getDeleteTargetRows(row, idKey) {
      if (row && row[idKey] !== undefined && row[idKey] !== null) {
        return [row]
      }
      return this.selectionRows || []
    },
    getRowMainName(row, nameKey, idKey) {
      if (!row) {
        return ""
      }
      const keys = Array.isArray(nameKey) ? nameKey : [nameKey]
      for (const key of keys) {
        if (typeof key === "function") {
          const value = key.call(this, row)
          if (value !== undefined && value !== null && value !== "") {
            return value
          }
          continue
        }
        if (row[key] !== undefined && row[key] !== null && row[key] !== "") {
          return row[key]
        }
      }
      return row[idKey]
    },
    confirmDeleteData(row, options, deleteApi) {
      const idKey = options.idKey || "id"
      const ids = row && row[idKey] !== undefined && row[idKey] !== null ? row[idKey] : this.ids
      const rows = this.getDeleteTargetRows(row, idKey)
      const names = rows
        .map(item => this.getRowMainName(item, options.nameKey, idKey))
        .filter(item => item !== undefined && item !== null && item !== "")
      const mainName = names.length ? names.join("\u3001") : ids
      const label = options.label || "\u6570\u636e"
      return this.$modal.confirm(`\u60a8\u786e\u8ba4\u8981\u5220\u9664${label}\u4e3a\u201c${mainName}\u201d\u7684\u6570\u636e\u5417\uff1f`).then(function() {
        return deleteApi(ids)
      })
    },
    isPayrollPaid(row) {
      return row && String(row.status) === "1"
    },
    hasPayrollPaid(rows) {
      return (rows || []).some(row => this.isPayrollPaid(row))
    }
  }
}
