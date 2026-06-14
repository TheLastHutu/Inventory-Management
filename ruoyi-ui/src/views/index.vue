<template>
  <div class="home-dashboard">
    <section class="hero-band">
      <div class="hero-band__content">
        <div>
          <div class="hero-band__eyebrow">智发·进销存管理系统</div>
          <h1>进销存业务系统首页</h1>
          <p>聚焦采购、销售、库存三条主线，沉淀客户最关心的经营数据和高频业务入口。</p>
        </div>
        <div class="hero-band__actions">
          <el-button type="primary" icon="el-icon-s-data" @click="openRoute('统计面板')">统计面板</el-button>
          <el-button icon="el-icon-refresh" @click="loadDashboard">刷新数据</el-button>
        </div>
      </div>
    </section>

    <section class="metric-grid">
      <div v-for="item in metrics" :key="item.label" class="metric-tile">
        <div class="metric-tile__icon" :class="item.type"><i :class="item.icon"></i></div>
        <div>
          <div class="metric-tile__value">{{ item.value }}</div>
          <div class="metric-tile__label">{{ item.label }}</div>
        </div>
      </div>
    </section>

    <section class="flow-section">
      <div class="section-title">
        <div>
          <h2>业务流程管理</h2>
          <p>从采购入库、库存管理、销售出库到退货入库，形成闭环跟踪。</p>
        </div>
      </div>

      <div class="flow-shortcuts">
        <button
          v-for="item in flowLinks"
          :key="item.title"
          type="button"
          class="flow-card"
          :class="item.type"
          @click="openRoute(item.routeTitle, item.routePath)"
        >
          <span><i :class="item.icon"></i></span>
          <strong>{{ item.title }}</strong>
        </button>
      </div>

      <div class="flow-map">
        <div v-for="(item, index) in flowNodes" :key="item.title" class="flow-map__item">
          <div class="flow-node" :class="item.type">
            <i :class="item.icon"></i>
          </div>
          <div class="flow-map__text">{{ item.title }}</div>
          <i v-if="index < flowNodes.length - 1" class="el-icon-arrow-right flow-arrow"></i>
        </div>
      </div>
    </section>

    <el-row :gutter="18" class="main-row">
      <el-col :xs="24" :lg="15">
        <chart-panel title="近期开票业务走势" subtitle="采购入库、销售出库、销售退货金额" :option="trendOption" :loading="loading" />
      </el-col>
      <el-col :xs="24" :lg="9">
        <div class="work-panel">
          <div class="work-panel__title">高频页面</div>
          <div class="quick-grid">
            <button
              v-for="item in quickLinks"
              :key="item.title"
              type="button"
              class="quick-link"
              @click="openRoute(item.routeTitle, item.routePath)"
            >
              <i :class="item.icon"></i>
              <span>{{ item.title }}</span>
            </button>
          </div>
        </div>
      </el-col>
    </el-row>

    <el-row :gutter="18" class="module-row">
      <el-col v-for="module in modules" :key="module.title" :xs="24" :lg="8">
        <div class="module-panel">
          <div class="module-panel__head">
            <div>
              <h3>{{ module.title }}</h3>
              <p>{{ module.summary }}</p>
            </div>
            <div class="module-panel__icon" :class="module.type"><i :class="module.icon"></i></div>
          </div>
          <div class="module-panel__metric">
            <span>{{ module.metricLabel }}</span>
            <strong>{{ module.metricValue }}</strong>
          </div>
          <button type="button" class="module-panel__button" @click="openRoute(module.routeTitle, module.routePath)">
            {{ module.action }}
            <i class="el-icon-arrow-right"></i>
          </button>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import ChartPanel from './statistics/components/ChartPanel'
import { listIms_inbound } from '@/api/ims/ims_inbound'
import { listIms_outbound } from '@/api/ims/ims_outbound'
import { listIms_sale_return } from '@/api/ims/ims_sale_return'
import { listIms_inventory } from '@/api/ims/ims_inventory'
import { rows, num, money, qty, approved, dateKey, groupSum, sum, lineOption } from './statistics/utils'

export default {
  name: 'Index',
  components: { ChartPanel },
  data() {
    return {
      loading: false,
      inboundRows: [],
      outboundRows: [],
      saleReturnRows: [],
      inventoryRows: [],
      flowLinks: [
        { title: '采购入库', routeTitle: '入库单', routePath: 'ims_inbound', icon: 'el-icon-bottom', type: 'blue' },
        { title: '库存管理', routeTitle: '库存(现存量)', routePath: 'ims_inventory', icon: 'el-icon-box', type: 'cyan' },
        { title: '销售出库', routeTitle: '出库单', icon: 'el-icon-truck', type: 'orange' },
        { title: '销售退货', routeTitle: '销售退货单', icon: 'el-icon-refresh-left', type: 'purple' }
      ],
      flowNodes: [
        { title: '采购入库', icon: 'el-icon-bottom', type: 'blue' },
        { title: '库存现存量', icon: 'el-icon-box', type: 'cyan' },
        { title: '销售出库', icon: 'el-icon-truck', type: 'orange' },
        { title: '销售退货', icon: 'el-icon-refresh-left', type: 'purple' }
      ],
      quickLinks: [
        { title: '采购统计', routeTitle: '采购统计', icon: 'el-icon-s-data' },
        { title: '销售统计', routeTitle: '销售统计', icon: 'el-icon-data-line' },
        { title: '库存看板', routeTitle: '库存看板', icon: 'el-icon-pie-chart' },
        { title: '产品管理', routeTitle: '产品管理', icon: 'el-icon-goods' },
        { title: '客户管理', routeTitle: '客户管理', icon: 'el-icon-user' },
        { title: '供应商管理', routeTitle: '供应商管理', icon: 'el-icon-office-building' },
        { title: '库存流水', routeTitle: '库存流水', icon: 'el-icon-tickets' },
        { title: '员工管理', routeTitle: '员工管理', icon: 'el-icon-s-custom' }
      ]
    }
  },
  computed: {
    purchaseRows() {
      return this.inboundRows.filter(row => approved(row) && String(row.inType) === '0')
    },
    saleRows() {
      return this.outboundRows.filter(row => approved(row) && String(row.outType) === '0')
    },
    returnRows() {
      return this.saleReturnRows.filter(row => approved(row))
    },
    purchaseAmount() {
      return sum(this.purchaseRows, 'totalAmount')
    },
    saleAmount() {
      return sum(this.saleRows, 'totalAmount')
    },
    returnAmount() {
      return sum(this.returnRows, 'totalAmount')
    },
    availableQty() {
      return sum(this.inventoryRows, 'availQtyPiece')
    },
    metrics() {
      return [
        { label: '采购金额', value: `¥ ${money(this.purchaseAmount)}`, icon: 'el-icon-bottom', type: 'blue' },
        { label: '销售净额', value: `¥ ${money(this.saleAmount - this.returnAmount)}`, icon: 'el-icon-data-line', type: 'green' },
        { label: '可用库存', value: qty(this.availableQty), icon: 'el-icon-box', type: 'cyan' },
        { label: '退货金额', value: `¥ ${money(this.returnAmount)}`, icon: 'el-icon-refresh-left', type: 'orange' }
      ]
    },
    modules() {
      return [
        {
          title: '采购管理',
          summary: '关注供应商采购金额和入库节奏',
          metricLabel: '已审核采购金额',
          metricValue: `¥ ${money(this.purchaseAmount)}`,
          icon: 'el-icon-shopping-cart-full',
          type: 'blue',
          action: '查看采购统计',
          routeTitle: '采购统计'
        },
        {
          title: '销售管理',
          summary: '关注销售出库、客户贡献和退货抵减',
          metricLabel: '净销售金额',
          metricValue: `¥ ${money(this.saleAmount - this.returnAmount)}`,
          icon: 'el-icon-s-marketing',
          type: 'green',
          action: '查看销售统计',
          routeTitle: '销售统计'
        },
        {
          title: '库存管理',
          summary: '关注现存量、锁定量和可用库存',
          metricLabel: '可用库存数量',
          metricValue: qty(this.availableQty),
          icon: 'el-icon-box',
          type: 'cyan',
          action: '打开库存看板',
          routeTitle: '库存看板'
        }
      ]
    },
    trendOption() {
      const purchase = groupSum(this.purchaseRows, row => dateKey(row.billDate), row => row.totalAmount)
      const sale = groupSum(this.saleRows, row => dateKey(row.billDate), row => row.totalAmount)
      const returned = groupSum(this.returnRows, row => dateKey(row.billDate), row => row.totalAmount)
      const dates = Array.from(new Set(purchase.concat(sale).concat(returned).map(item => item.name))).sort()
      return lineOption(dates, [
        { name: '采购入库', color: '#2563eb', areaColor: 'rgba(37,99,235,.08)', data: dates.map(date => num((purchase.find(item => item.name === date) || {}).value)) },
        { name: '销售出库', color: '#16a34a', areaColor: 'rgba(22,163,74,.08)', data: dates.map(date => num((sale.find(item => item.name === date) || {}).value)) },
        { name: '销售退货', color: '#f97316', areaColor: 'rgba(249,115,22,.08)', data: dates.map(date => num((returned.find(item => item.name === date) || {}).value)) }
      ])
    }
  },
  created() {
    this.loadDashboard()
  },
  methods: {
    loadDashboard() {
      this.loading = true
      const query = { pageNum: 1, pageSize: 10000 }
      Promise.all([
        listIms_inbound(query),
        listIms_outbound(query),
        listIms_sale_return(query),
        listIms_inventory(query)
      ]).then(([inbound, outbound, saleReturn, inventory]) => {
        this.inboundRows = rows(inbound)
        this.outboundRows = rows(outbound)
        this.saleReturnRows = rows(saleReturn)
        this.inventoryRows = rows(inventory)
      }).finally(() => {
        this.loading = false
      })
    },
    openRoute(title, routePath) {
      const route = routePath ? this.findRouteByPath(routePath) : this.findRouteByTitle(title)
      if (route && route.path) {
        this.$router.push(route.path).catch(() => {})
        return
      }
      this.$message.warning(`未找到菜单：${title}`)
    },
    findRouteByTitle(title) {
      const list = []
      const walk = (routes, parentPath = '') => {
        routes.forEach(route => {
          const path = this.resolvePath(parentPath, route.path)
          const metaTitle = route.meta && route.meta.title ? route.meta.title : ''
          const firstChildPath = route.children && route.children.length ? this.resolvePath(path, route.children[0].path) : ''
          list.push({ path, title: metaTitle, firstChildPath })
          if (route.children && route.children.length) {
            walk(route.children, path)
          }
        })
      }
      walk(this.$store.state.permission.sidebarRouters || [])
      const route = list.find(route => route.title === title) || list.find(route => route.title.indexOf(title) > -1)
      if (route && route.firstChildPath) {
        return { path: route.firstChildPath, title: route.title }
      }
      return route
    },
    findRouteByPath(routePath) {
      const list = []
      const walk = (routes, parentPath = '') => {
        routes.forEach(route => {
          const path = this.resolvePath(parentPath, route.path)
          list.push({ path, title: route.meta && route.meta.title ? route.meta.title : '' })
          if (route.children && route.children.length) {
            walk(route.children, path)
          }
        })
      }
      walk(this.$store.state.permission.sidebarRouters || [])
      return list.find(route => route.path === routePath || route.path.endsWith(`/${routePath}`))
    },
    resolvePath(parentPath, routePath) {
      if (!routePath) {
        return parentPath || '/'
      }
      if (routePath.indexOf('http') === 0 || routePath.charAt(0) === '/') {
        return routePath
      }
      const base = parentPath && parentPath !== '/' ? parentPath : ''
      return `${base}/${routePath}`.replace(/\/+/g, '/')
    }
  }
}
</script>

<style lang="scss" scoped>
.home-dashboard {
  min-height: calc(100vh - 84px);
  padding: 22px;
  background: #f5f7fb;
}

.hero-band {
  background: linear-gradient(120deg, #14315f 0%, #166f6b 54%, #1f8f5f 100%);
  border-radius: 8px;
  color: #fff;
  padding: 28px 30px;
  margin-bottom: 18px;
  box-shadow: 0 14px 34px rgba(20, 49, 95, 0.18);
}

.hero-band__content {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 20px;
}

.hero-band__eyebrow {
  color: rgba(255, 255, 255, 0.78);
  font-size: 13px;
  margin-bottom: 6px;
}

.hero-band h1 {
  font-size: 30px;
  line-height: 38px;
  margin: 0;
  font-weight: 700;
}

.hero-band p {
  color: rgba(255, 255, 255, 0.78);
  font-size: 14px;
  line-height: 24px;
  margin: 8px 0 0;
}

.hero-band__actions {
  display: flex;
  gap: 10px;
  flex-shrink: 0;
}

.metric-grid {
  display: grid;
  grid-template-columns: repeat(4, minmax(0, 1fr));
  gap: 16px;
  margin-bottom: 18px;
}

.metric-tile {
  display: flex;
  align-items: center;
  min-height: 104px;
  background: #fff;
  border: 1px solid #edf0f5;
  border-radius: 6px;
  padding: 18px;
  box-shadow: 0 8px 24px rgba(18, 38, 63, 0.04);
}

.metric-tile__icon {
  width: 50px;
  height: 50px;
  border-radius: 6px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 14px;
  font-size: 24px;

  &.blue {
    color: #2563eb;
    background: #eaf1ff;
  }

  &.green {
    color: #16a34a;
    background: #eaf8ef;
  }

  &.cyan {
    color: #0891b2;
    background: #e7f9fd;
  }

  &.orange {
    color: #f97316;
    background: #fff3e8;
  }
}

.metric-tile__value {
  color: #1f2d3d;
  font-size: 23px;
  line-height: 30px;
  font-weight: 700;
  word-break: break-all;
}

.metric-tile__label {
  color: #8b97a8;
  font-size: 13px;
  line-height: 20px;
  margin-top: 4px;
}

.flow-section,
.work-panel,
.module-panel {
  background: #fff;
  border: 1px solid #edf0f5;
  border-radius: 6px;
  box-shadow: 0 8px 24px rgba(18, 38, 63, 0.04);
}

.flow-section {
  padding: 22px;
  margin-bottom: 18px;
}

.section-title {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 16px;

  h2 {
    color: #1f2d3d;
    font-size: 20px;
    line-height: 28px;
    margin: 0;
  }

  p {
    color: #8b97a8;
    font-size: 13px;
    line-height: 22px;
    margin: 4px 0 0;
  }
}

.flow-shortcuts {
  display: grid;
  grid-template-columns: repeat(4, minmax(0, 1fr));
  gap: 16px;
  margin-bottom: 30px;
}

.flow-card {
  border: 0;
  border-radius: 6px;
  color: #fff;
  min-height: 86px;
  padding: 18px 20px;
  display: flex;
  align-items: center;
  cursor: pointer;
  text-align: left;

  span {
    font-size: 32px;
    margin-right: 14px;
  }

  strong {
    font-size: 17px;
    line-height: 24px;
  }

  &.blue {
    background: linear-gradient(120deg, #246bfe, #17b6d4);
  }

  &.cyan {
    background: linear-gradient(120deg, #0f9f8f, #18c38f);
  }

  &.orange {
    background: linear-gradient(120deg, #ff5f2e, #f7b731);
  }

  &.purple {
    background: linear-gradient(120deg, #7c6ee6, #b978a2);
  }
}

.flow-map {
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 170px;
}

.flow-map__item {
  position: relative;
  display: flex;
  align-items: center;
  padding-bottom: 46px;
}

.flow-node {
  width: 74px;
  height: 74px;
  border-radius: 50%;
  color: #fff;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 34px;
  box-shadow: 0 12px 30px rgba(0, 0, 0, 0.14);

  &.blue {
    background: #4169e1;
  }

  &.cyan {
    background: #14b8d4;
  }

  &.orange {
    background: #f97316;
  }

  &.purple {
    background: #9b6bd3;
  }
}

.flow-map__text {
  position: absolute;
  top: 88px;
  left: 0;
  width: 74px;
  color: #5d6675;
  font-size: 14px;
  text-align: center;
}

.flow-arrow {
  color: #c4cad4;
  font-size: 24px;
  margin: 0 14px;
}

.main-row,
.module-row {
  margin-bottom: 18px;
}

.work-panel {
  min-height: 330px;
  padding: 18px;
}

.work-panel__title {
  color: #1f2d3d;
  font-size: 16px;
  line-height: 22px;
  font-weight: 600;
  margin-bottom: 16px;
}

.quick-grid {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 12px;
}

.quick-link {
  height: 56px;
  border: 1px solid #edf0f5;
  border-radius: 6px;
  background: #f8fafc;
  color: #344054;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  font-size: 14px;
  cursor: pointer;

  i {
    color: #2563eb;
    font-size: 18px;
  }
}

.module-panel {
  padding: 20px;
  min-height: 220px;
  margin-bottom: 18px;
}

.module-panel__head {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;

  h3 {
    color: #1f2d3d;
    font-size: 19px;
    line-height: 26px;
    margin: 0;
  }

  p {
    color: #8b97a8;
    font-size: 13px;
    line-height: 22px;
    margin: 5px 0 0;
  }
}

.module-panel__icon {
  width: 46px;
  height: 46px;
  border-radius: 6px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 24px;

  &.blue {
    color: #2563eb;
    background: #eaf1ff;
  }

  &.green {
    color: #16a34a;
    background: #eaf8ef;
  }

  &.cyan {
    color: #0891b2;
    background: #e7f9fd;
  }
}

.module-panel__metric {
  display: flex;
  align-items: baseline;
  justify-content: space-between;
  margin: 28px 0 20px;

  span {
    color: #8b97a8;
    font-size: 13px;
  }

  strong {
    color: #1f2d3d;
    font-size: 22px;
    line-height: 30px;
  }
}

.module-panel__button {
  width: 100%;
  height: 42px;
  border: 0;
  border-radius: 6px;
  color: #fff;
  background: #2563eb;
  cursor: pointer;
  font-size: 14px;
}

@media (max-width: 1200px) {
  .metric-grid,
  .flow-shortcuts {
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }
}

@media (max-width: 768px) {
  .home-dashboard {
    padding: 14px;
  }

  .hero-band__content {
    align-items: flex-start;
    flex-direction: column;
  }

  .hero-band__actions {
    width: 100%;
    flex-wrap: wrap;
  }

  .metric-grid,
  .flow-shortcuts,
  .quick-grid {
    grid-template-columns: 1fr;
  }

  .flow-map {
    align-items: flex-start;
    flex-direction: column;
    padding-left: 16px;
  }

  .flow-map__item {
    margin-bottom: 14px;
  }

  .flow-map__text {
    position: static;
    width: auto;
    margin: 0 0 0 12px;
  }

  .flow-arrow {
    display: none;
  }
}
</style>
