<template>
  <div class="app-container stat-page purchase-stat">
    <div class="stat-header">
      <div>
        <h2>采购统计</h2>
        <p>采购入库金额、供应商贡献和入库趋势</p>
      </div>
      <el-button type="primary" icon="el-icon-refresh" size="small" @click="loadData">刷新</el-button>
    </div>

    <el-form :model="queryParams" size="small" :inline="true" class="filter-bar">
      <el-form-item label="入库日期">
        <el-date-picker
          v-model="dateRange"
          type="daterange"
          value-format="yyyy-MM-dd"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          clearable
        />
      </el-form-item>
      <el-form-item label="供应商">
        <el-select v-model="queryParams.supplierId" placeholder="全部供应商" clearable filterable>
          <el-option v-for="item in supplierOptions" :key="item.id" :label="supplierName(item.id)" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="仓库">
        <el-select v-model="queryParams.warehouseId" placeholder="全部仓库" clearable filterable>
          <el-option v-for="item in warehouseOptions" :key="item.id" :label="warehouseName(item.id)" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="loadData">查询</el-button>
        <el-button icon="el-icon-refresh-left" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="16" class="metric-row">
      <el-col v-for="item in metrics" :key="item.label" :xs="24" :sm="12" :lg="6">
        <div class="metric-card">
          <div class="metric-card__icon" :class="item.type"><i :class="item.icon"></i></div>
          <div>
            <div class="metric-card__value">{{ item.value }}</div>
            <div class="metric-card__label">{{ item.label }}</div>
          </div>
        </div>
      </el-col>
    </el-row>

    <el-row :gutter="16">
      <el-col :xs="24" :lg="14">
        <chart-panel title="采购趋势" subtitle="按入库日期统计已审核采购入库" :option="trendOption" :loading="loading" />
      </el-col>
      <el-col :xs="24" :lg="10">
        <chart-panel title="供应商采购排行" subtitle="按采购金额排序" :option="supplierOption" :loading="loading" />
      </el-col>
    </el-row>

    <el-row :gutter="16" class="section-row">
      <el-col :xs="24" :lg="12">
        <chart-panel title="入库类型占比" subtitle="采购入库与退货入库对比" :option="typeOption" :loading="loading" />
      </el-col>
      <el-col :xs="24" :lg="12">
        <div class="data-panel">
          <div class="data-panel__title">采购单据明细</div>
          <el-table :data="purchaseRows" height="286" v-loading="loading">
            <el-table-column label="日期" prop="billDate" width="110" />
            <el-table-column label="单号" prop="billNo" min-width="150" show-overflow-tooltip />
            <el-table-column label="供应商" min-width="140" show-overflow-tooltip>
              <template slot-scope="scope">{{ supplierName(scope.row.supplierId) }}</template>
            </el-table-column>
            <el-table-column label="数量" prop="totalQtyPiece" align="right" width="100" />
            <el-table-column label="金额" align="right" width="120">
              <template slot-scope="scope">{{ formatMoney(scope.row.totalAmount) }}</template>
            </el-table-column>
          </el-table>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import ChartPanel from '../components/ChartPanel'
import referenceData from '@/views/ims/mixins/referenceData'
import { listIms_inbound } from '@/api/ims/ims_inbound'
import { rows, num, money, qty, approved, dateKey, groupSum, top, sum, lineOption, horizontalBarOption, pieOption } from '../utils'

export default {
  name: 'PurchaseStatistics',
  components: { ChartPanel },
  mixins: [referenceData],
  data() {
    return {
      loading: false,
      dateRange: [],
      queryParams: {
        supplierId: null,
        warehouseId: null
      },
      inboundRows: []
    }
  },
  computed: {
    purchaseRows() {
      return this.inboundRows.filter(row => approved(row) && String(row.inType) === '0')
    },
    returnRows() {
      return this.inboundRows.filter(row => approved(row) && String(row.inType) === '1')
    },
    metrics() {
      const amount = sum(this.purchaseRows, 'totalAmount')
      const totalQty = sum(this.purchaseRows, 'totalQtyPiece')
      return [
        { label: '采购金额', value: `¥ ${money(amount)}`, icon: 'el-icon-coin', type: 'blue' },
        { label: '采购数量', value: qty(totalQty), icon: 'el-icon-box', type: 'green' },
        { label: '采购单数', value: this.purchaseRows.length, icon: 'el-icon-document', type: 'orange' },
        { label: '平均采购单价', value: `¥ ${money(totalQty ? amount / totalQty : 0)}`, icon: 'el-icon-data-line', type: 'purple' }
      ]
    },
    trendOption() {
      const groupedAmount = groupSum(this.purchaseRows, row => dateKey(row.billDate), row => row.totalAmount)
      const groupedQty = groupSum(this.purchaseRows, row => dateKey(row.billDate), row => row.totalQtyPiece)
      const dates = Array.from(new Set(groupedAmount.concat(groupedQty).map(item => item.name))).sort()
      return lineOption(dates, [
        { name: '采购金额', color: '#2979ff', areaColor: 'rgba(41,121,255,.08)', data: dates.map(date => num((groupedAmount.find(item => item.name === date) || {}).value)) },
        { name: '采购数量', color: '#16c784', areaColor: 'rgba(22,199,132,.08)', data: dates.map(date => num((groupedQty.find(item => item.name === date) || {}).value)) }
      ])
    },
    supplierOption() {
      const data = top(groupSum(this.purchaseRows, row => this.supplierName(row.supplierId) || '未填供应商', row => row.totalAmount), 6)
      return horizontalBarOption(data.map(item => item.name).reverse(), data.map(item => item.value).reverse(), '#2979ff')
    },
    typeOption() {
      return pieOption([
        { name: '采购入库', value: sum(this.purchaseRows, 'totalAmount') },
        { name: '退货入库', value: sum(this.returnRows, 'totalAmount') }
      ], ['#2979ff', '#f59e0b'])
    }
  },
  created() {
    this.loadData()
  },
  methods: {
    loadData() {
      this.loading = true
      const query = {
        pageNum: 1,
        pageSize: 10000,
        supplierId: this.queryParams.supplierId,
        warehouseId: this.queryParams.warehouseId
      }
      const params = this.addDateRange(query, this.dateRange, 'BillDate')
      listIms_inbound(params).then(response => {
        this.inboundRows = rows(response)
      }).finally(() => {
        this.loading = false
      })
    },
    resetQuery() {
      this.dateRange = []
      this.queryParams = { supplierId: null, warehouseId: null }
      this.loadData()
    },
    formatMoney(value) {
      return money(value)
    }
  }
}
</script>

<style lang="scss" scoped>
@import "../stat-page.scss";
</style>
