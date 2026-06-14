<template>
  <div class="app-container stat-page inventory-stat">
    <div class="stat-header">
      <div>
        <h2>库存看板</h2>
        <p>现存量、可用量、锁定量和出入库流水</p>
      </div>
      <el-button type="primary" icon="el-icon-refresh" size="small" @click="loadData">刷新</el-button>
    </div>

    <el-form :model="queryParams" size="small" :inline="true" class="filter-bar">
      <el-form-item label="仓库">
        <el-select v-model="queryParams.warehouseId" placeholder="全部仓库" clearable filterable>
          <el-option v-for="item in warehouseOptions" :key="item.id" :label="warehouseName(item.id)" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="货位">
        <el-select v-model="queryParams.locationId" placeholder="全部货位" clearable filterable>
          <el-option v-for="item in locationOptions" :key="item.id" :label="locationName(item.id)" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="产品">
        <el-select v-model="queryParams.productId" placeholder="全部产品" clearable filterable>
          <el-option v-for="item in productOptions" :key="item.id" :label="productName(item.id)" :value="item.id" />
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
        <chart-panel title="出入库流水趋势" subtitle="按发生日期统计库存流水数量" :option="flowOption" :loading="loading" />
      </el-col>
      <el-col :xs="24" :lg="10">
        <chart-panel title="仓库库存分布" subtitle="按现存量统计" :option="warehouseOption" :loading="loading" />
      </el-col>
    </el-row>

    <el-row :gutter="16" class="section-row">
      <el-col :xs="24" :lg="12">
        <chart-panel title="库存状态占比" subtitle="现存、锁定、可用数量结构" :option="stockStatusOption" :loading="loading" />
      </el-col>
      <el-col :xs="24" :lg="12">
        <div class="data-panel">
          <div class="data-panel__title">库存排行</div>
          <el-table :data="inventoryRows" height="286" v-loading="loading">
            <el-table-column label="产品" min-width="150" show-overflow-tooltip>
              <template slot-scope="scope">{{ productName(scope.row.productId) }}</template>
            </el-table-column>
            <el-table-column label="仓库" min-width="120" show-overflow-tooltip>
              <template slot-scope="scope">{{ warehouseName(scope.row.warehouseId) }}</template>
            </el-table-column>
            <el-table-column label="现存" prop="qtyPiece" align="right" width="90" />
            <el-table-column label="锁定" prop="lockedQtyPiece" align="right" width="90" />
            <el-table-column label="可用" prop="availQtyPiece" align="right" width="90" />
          </el-table>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import ChartPanel from '../components/ChartPanel'
import referenceData from '@/views/ims/mixins/referenceData'
import { listIms_inventory } from '@/api/ims/ims_inventory'
import { listIms_stock_flow } from '@/api/ims/ims_stock_flow'
import { rows, num, money, qty, dateKey, groupSum, top, sum, lineOption, horizontalBarOption, pieOption } from '../utils'

export default {
  name: 'InventoryDashboard',
  components: { ChartPanel },
  mixins: [referenceData],
  data() {
    return {
      loading: false,
      queryParams: {
        warehouseId: null,
        locationId: null,
        productId: null
      },
      inventoryRows: [],
      flowRows: []
    }
  },
  computed: {
    metrics() {
      const totalQty = sum(this.inventoryRows, 'qtyPiece')
      const lockedQty = sum(this.inventoryRows, 'lockedQtyPiece')
      const availQty = sum(this.inventoryRows, 'availQtyPiece')
      return [
        { label: '现存总量', value: qty(totalQty), icon: 'el-icon-box', type: 'blue' },
        { label: '可用库存', value: qty(availQty), icon: 'el-icon-finished', type: 'green' },
        { label: '锁定库存', value: qty(lockedQty), icon: 'el-icon-lock', type: 'orange' },
        { label: '库存金额估算', value: `¥ ${money(this.stockAmount)}`, icon: 'el-icon-coin', type: 'purple' }
      ]
    },
    stockAmount() {
      return this.inventoryRows.reduce((total, row) => {
        const product = this.productOptions.find(item => String(item.id) === String(row.productId))
        return total + num(row.qtyPiece) * num(product && product.purchasePrice)
      }, 0)
    },
    flowOption() {
      const inbound = groupSum(this.flowRows.filter(row => row.inout === 'I'), row => dateKey(row.occurTime), row => row.qtyPiece)
      const outbound = groupSum(this.flowRows.filter(row => row.inout === 'O'), row => dateKey(row.occurTime), row => row.qtyPiece)
      const dates = Array.from(new Set(inbound.concat(outbound).map(item => item.name))).sort()
      return lineOption(dates, [
        { name: '入库量', color: '#14b8a6', areaColor: 'rgba(20,184,166,.08)', data: dates.map(date => num((inbound.find(item => item.name === date) || {}).value)) },
        { name: '出库量', color: '#ef4444', areaColor: 'rgba(239,68,68,.08)', data: dates.map(date => num((outbound.find(item => item.name === date) || {}).value)) }
      ])
    },
    warehouseOption() {
      const data = top(groupSum(this.inventoryRows, row => this.warehouseName(row.warehouseId) || '未填仓库', row => row.qtyPiece), 6)
      return horizontalBarOption(data.map(item => item.name).reverse(), data.map(item => item.value).reverse(), '#14b8a6')
    },
    stockStatusOption() {
      return pieOption([
        { name: '现存总量', value: sum(this.inventoryRows, 'qtyPiece') },
        { name: '锁定库存', value: sum(this.inventoryRows, 'lockedQtyPiece') },
        { name: '可用库存', value: sum(this.inventoryRows, 'availQtyPiece') }
      ], ['#2563eb', '#f97316', '#14b8a6'])
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
        warehouseId: this.queryParams.warehouseId,
        locationId: this.queryParams.locationId,
        productId: this.queryParams.productId
      }
      Promise.all([
        listIms_inventory(query),
        listIms_stock_flow(query)
      ]).then(([inventory, flow]) => {
        this.inventoryRows = rows(inventory)
        this.flowRows = rows(flow)
      }).finally(() => {
        this.loading = false
      })
    },
    resetQuery() {
      this.queryParams = { warehouseId: null, locationId: null, productId: null }
      this.loadData()
    }
  }
}
</script>

<style lang="scss" scoped>
@import "../stat-page.scss";
</style>
