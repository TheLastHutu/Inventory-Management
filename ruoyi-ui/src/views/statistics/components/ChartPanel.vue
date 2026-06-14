<template>
  <div class="chart-panel" v-loading="loading">
    <div class="chart-panel__head">
      <div>
        <div class="chart-panel__title">{{ title }}</div>
        <div v-if="subtitle" class="chart-panel__subtitle">{{ subtitle }}</div>
      </div>
      <slot name="extra"></slot>
    </div>
    <div ref="chart" class="chart-panel__chart"></div>
  </div>
</template>

<script>
import * as echarts from 'echarts'
require('echarts/theme/macarons')

export default {
  name: 'ChartPanel',
  props: {
    title: {
      type: String,
      required: true
    },
    subtitle: {
      type: String,
      default: ''
    },
    option: {
      type: Object,
      required: true
    },
    loading: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      chart: null
    }
  },
  watch: {
    option: {
      deep: true,
      handler() {
        this.renderChart()
      }
    }
  },
  mounted() {
    this.chart = echarts.init(this.$refs.chart, 'macarons')
    this.renderChart()
    window.addEventListener('resize', this.resizeChart)
  },
  beforeDestroy() {
    window.removeEventListener('resize', this.resizeChart)
    if (this.chart) {
      this.chart.dispose()
      this.chart = null
    }
  },
  methods: {
    renderChart() {
      this.$nextTick(() => {
        if (!this.chart && this.$refs.chart) {
          this.chart = echarts.init(this.$refs.chart, 'macarons')
        }
        if (this.chart) {
          this.chart.setOption(this.option || {}, true)
        }
      })
    },
    resizeChart() {
      if (this.chart) {
        this.chart.resize()
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.chart-panel {
  background: #fff;
  border: 1px solid #eef1f5;
  border-radius: 6px;
  padding: 18px 18px 14px;
  min-height: 330px;
  box-shadow: 0 8px 24px rgba(18, 38, 63, 0.04);
}

.chart-panel__head {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  min-height: 42px;
}

.chart-panel__title {
  color: #1f2d3d;
  font-size: 16px;
  font-weight: 600;
  line-height: 22px;
}

.chart-panel__subtitle {
  color: #8b97a8;
  font-size: 12px;
  line-height: 20px;
  margin-top: 2px;
}

.chart-panel__chart {
  width: 100%;
  height: 260px;
}
</style>
