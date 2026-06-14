<template>
  <div class="app-container statistics-panel">
    <div class="statistics-panel__header">
      <div>
        <h2>统计面板</h2>
        <p>集中查看采购统计、销售统计和库存看板。</p>
      </div>
    </div>

    <el-row :gutter="18">
      <el-col v-for="item in panels" :key="item.title" :xs="24" :md="8">
        <button type="button" class="panel-card" :class="item.type" @click="openPanel(item.path)">
          <span><i :class="item.icon"></i></span>
          <strong>{{ item.title }}</strong>
          <em>{{ item.desc }}</em>
        </button>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: 'StatisticsPanel',
  data() {
    return {
      panels: [
        { title: '采购统计', desc: '采购金额、入库趋势、供应商排行', path: 'ims_purchase_statistics', icon: 'el-icon-s-data', type: 'purchase' },
        { title: '销售统计', desc: '销售出库、退货抵减、客户排行', path: 'ims_sale_statistics', icon: 'el-icon-data-line', type: 'sale' },
        { title: '库存看板', desc: '现存量、可用量、库存流水趋势', path: 'ims_inventory_statistics', icon: 'el-icon-pie-chart', type: 'inventory' }
      ]
    }
  },
  methods: {
    openPanel(path) {
      const route = this.findRouteByPath(path)
      if (route) {
        this.$router.push(route.path).catch(() => {})
      }
    },
    findRouteByPath(routePath) {
      const list = []
      const walk = (routes, parentPath = '') => {
        routes.forEach(route => {
          const path = this.resolvePath(parentPath, route.path)
          list.push({ path })
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
.statistics-panel {
  min-height: calc(100vh - 84px);
  background: #f5f7fb;
}

.statistics-panel__header {
  background: #fff;
  border: 1px solid #eef1f5;
  border-radius: 6px;
  padding: 22px 24px;
  margin-bottom: 18px;
  box-shadow: 0 8px 24px rgba(18, 38, 63, 0.04);

  h2 {
    color: #1f2d3d;
    font-size: 24px;
    line-height: 32px;
    margin: 0;
  }

  p {
    color: #8b97a8;
    font-size: 13px;
    line-height: 22px;
    margin: 4px 0 0;
  }
}

.panel-card {
  width: 100%;
  min-height: 176px;
  border: 0;
  border-radius: 6px;
  color: #fff;
  padding: 24px;
  margin-bottom: 18px;
  text-align: left;
  cursor: pointer;
  box-shadow: 0 12px 28px rgba(18, 38, 63, 0.12);

  span {
    width: 48px;
    height: 48px;
    border-radius: 6px;
    background: rgba(255, 255, 255, 0.18);
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 25px;
    margin-bottom: 18px;
  }

  strong {
    display: block;
    font-size: 20px;
    line-height: 28px;
  }

  em {
    display: block;
    color: rgba(255, 255, 255, 0.78);
    font-style: normal;
    font-size: 13px;
    line-height: 22px;
    margin-top: 6px;
  }

  &.purchase {
    background: linear-gradient(120deg, #2563eb, #14b8d4);
  }

  &.sale {
    background: linear-gradient(120deg, #16a34a, #0f9f8f);
  }

  &.inventory {
    background: linear-gradient(120deg, #7c3aed, #0891b2);
  }
}
</style>
