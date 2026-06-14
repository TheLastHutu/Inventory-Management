<template>
  <div class="app-container stat-page sale-stat">
    <div class="stat-header">
      <div>
        <h2>销售统计</h2>
        <p>销售出库、退货抵减、客户贡献和净销售表现</p>
      </div>
      <el-button type="primary" icon="el-icon-refresh" size="small" @click="loadData">刷新</el-button>
    </div>

    <el-form :model="queryParams" size="small" :inline="true" class="filter-bar">
      <el-form-item label="业务日期">
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
      <el-form-item label="客户">
        <el-select v-model="queryParams.customerId" placeholder="全部客户" clearable filterable>
          <el-option v-for="item in customerOptions" :key="item.id" :label="customerName(item.id)" :value="item.id" />
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
        <chart-panel title="销售与退货趋势" subtitle="按日期展示销售出库和退货金额" :option="trendOption" :loading="loading" />
      </el-col>
      <el-col :xs="24" :lg="10">
        <chart-panel title="客户销售排行" subtitle="按净销售金额排序" :option="customerOption" :loading="loading" />
      </el-col>
    </el-row>

    <el-row :gutter="16" class="section-row">
      <el-col :xs="24" :lg="12">
        <chart-panel title="业务类型占比" subtitle="销售出库、样品出库、销售退货" :option="typeOption" :loading="loading" />
      </el-col>
      <el-col :xs="24" :lg="12">
        <div class="data-panel">
          <div class="data-panel__title">销售出库明细</div>
          <el-table :data="saleRows" height="286" v-loading="loading">
            <el-table-column label="日期" prop="billDate" width="110" />
            <el-table-column label="单号" prop="billNo" min-width="150" show-overflow-tooltip />
            <el-table-column label="客户" min-width="140" show-overflow-tooltip>
              <template slot-scope="scope">{{ customerName(scope.row.customerId) }}</template>
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
import { listIms_outbound } from '@/api/ims/ims_outbound'
import { listIms_sale_return } from '@/api/ims/ims_sale_return'
import { rows, num, money, qty, approved, dateKey, groupSum, top, sum, lineOption, horizontalBarOption, pieOption } from '../utils'

export default {
  name: 'SaleStatistics',
  components: { ChartPanel },
  mixins: [referenceData],
  data() {
    return {
      loading: false,
      dateRange: [],
      queryParams: {
        customerId: null,
        warehouseId: null
      },
      outboundRows: [],
      returnRows: []
    }
  },
  computed: {
    saleRows() {
      return this.outboundRows.filter(row => approved(row) && String(row.outType) === '0')
    },
    sampleRows() {
      return this.outboundRows.filter(row => approved(row) && String(row.outType) === '1')
    },
    approvedReturnRows() {
      return this.returnRows.filter(row => approved(row))
    },
    saleAmount() {
      return sum(this.saleRows, 'totalAmount')
    },
    returnAmount() {
      return sum(this.approvedReturnRows, 'totalAmount')
    },
    netAmount() {
      return this.saleAmount - this.returnAmount
    },
    metrics() {
      const saleQty = sum(this.saleRows, 'totalQtyPiece')
      const returnQty = sum(this.approvedReturnRows, 'totalQtyPiece')
      return [
        { label: '销售出库金额', value: `¥ ${money(this.saleAmount)}`, icon: 'el-icon-truck', type: 'blue' },
        { label: '销售退货金额', value: `¥ ${money(this.returnAmount)}`, icon: 'el-icon-refresh-left', type: 'orange' },
        { label: '净销售金额', value: `¥ ${money(this.netAmount)}`, icon: 'el-icon-data-line', type: 'green' },
        { label: '净销售数量', value: qty(saleQty - returnQty), icon: 'el-icon-box', type: 'purple' }
      ]
    },
    trendOption() {
      const sale = groupSum(this.saleRows, row => dateKey(row.billDate), row => row.totalAmount)
      const returned = groupSum(this.approvedReturnRows, row => dateKey(row.billDate), row => row.totalAmount)
      const dates = Array.from(new Set(sale.concat(returned).map(item => item.name))).sort()
      return lineOption(dates, [
        { name: '销售金额', color: '#2563eb', areaColor: 'rgba(37,99,235,.08)', data: dates.map(date => num((sale.find(item => item.name === date) || {}).value)) },
        { name: '退货金额', color: '#f97316', areaColor: 'rgba(249,115,22,.08)', data: dates.map(date => num((returned.find(item => item.name === date) || {}).value)) }
      ])
    },
    customerOption() {
      const sale = groupSum(this.saleRows, row => this.customerName(row.customerId) || '未填客户', row => row.totalAmount)
      const returned = groupSum(this.approvedReturnRows, row => this.customerName(row.customerId) || '未填客户', row => row.totalAmount)
      const names = Array.from(new Set(sale.concat(returned).map(item => item.name)))
      const data = top(names.map(name => {
        const saleValue = num((sale.find(item => item.name === name) || {}).value)
        const returnValue = num((returned.find(item => item.name === name) || {}).value)
        return { name, value: saleValue - returnValue }
      }), 6)
      return horizontalBarOption(data.map(item => item.name).reverse(), data.map(item => item.value).reverse(), '#2563eb')
    },
    typeOption() {
      return pieOption([
        { name: '销售出库', value: this.saleAmount },
        { name: '销售退货', value: this.returnAmount },
        { name: '样品出库', value: sum(this.sampleRows, 'totalQtyPiece') }
      ], ['#2563eb', '#f97316', '#14b8a6'])
    }
  },
  created() {
    this.loadData()
  },
  methods: {
    loadData() {
      this.loading = true
      const baseQuery = {
        pageNum: 1,
        pageSize: 10000,
        customerId: this.queryParams.customerId,
        warehouseId: this.queryParams.warehouseId
      }
      const outboundQuery = this.addDateRange(Object.assign({}, baseQuery), this.dateRange, 'BillDate')
      const returnQuery = this.addDateRange(Object.assign({}, baseQuery), this.dateRange, 'BillDate')
      Promise.all([
        listIms_outbound(outboundQuery),
        listIms_sale_return(returnQuery)
      ]).then(([outbound, saleReturn]) => {
        this.outboundRows = rows(outbound)
        this.returnRows = rows(saleReturn)
      }).finally(() => {
        this.loading = false
      })
    },
    resetQuery() {
      this.dateRange = []
      this.queryParams = { customerId: null, warehouseId: null }
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
