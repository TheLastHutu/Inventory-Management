export function rows(response) {
  return response && response.rows ? response.rows : []
}

export function num(value) {
  const result = Number(value)
  return Number.isFinite(result) ? result : 0
}

export function money(value) {
  return num(value).toLocaleString('zh-CN', {
    minimumFractionDigits: 2,
    maximumFractionDigits: 2
  })
}

export function qty(value) {
  const result = num(value)
  return Number.isInteger(result) ? String(result) : result.toFixed(2)
}

export function approved(row) {
  return String(row && row.billStatus) === '1'
}

export function dateKey(value) {
  if (!value) {
    return '未填日期'
  }
  return String(value).slice(0, 10)
}

export function groupSum(list, keyGetter, valueGetter) {
  const map = {}
  list.forEach(item => {
    const key = keyGetter(item) || '未分组'
    map[key] = (map[key] || 0) + num(valueGetter(item))
  })
  return Object.keys(map).map(name => ({ name, value: map[name] }))
}

export function top(list, limit = 6) {
  return list.sort((a, b) => num(b.value) - num(a.value)).slice(0, limit)
}

export function sum(list, key) {
  return list.reduce((total, item) => total + num(item[key]), 0)
}

export function barOption(names, values, color) {
  return {
    grid: { top: 28, right: 18, bottom: 28, left: 56 },
    tooltip: { trigger: 'axis' },
    xAxis: {
      type: 'category',
      data: names,
      axisTick: { show: false },
      axisLabel: { color: '#667085' }
    },
    yAxis: {
      type: 'value',
      splitLine: { lineStyle: { color: '#eef1f5' } },
      axisLabel: { color: '#667085' }
    },
    series: [{
      type: 'bar',
      data: values,
      barWidth: 28,
      itemStyle: {
        color,
        borderRadius: [4, 4, 0, 0]
      }
    }]
  }
}

export function horizontalBarOption(names, values, color) {
  return {
    grid: { top: 16, right: 28, bottom: 18, left: 92 },
    tooltip: { trigger: 'axis' },
    xAxis: {
      type: 'value',
      splitLine: { lineStyle: { color: '#eef1f5' } },
      axisLabel: { color: '#667085' }
    },
    yAxis: {
      type: 'category',
      data: names,
      axisTick: { show: false },
      axisLabel: { color: '#667085', width: 82, overflow: 'truncate' }
    },
    series: [{
      type: 'bar',
      data: values,
      barWidth: 18,
      itemStyle: {
        color,
        borderRadius: [0, 4, 4, 0]
      }
    }]
  }
}

export function lineOption(names, series) {
  return {
    grid: { top: 34, right: 28, bottom: 28, left: 58 },
    tooltip: { trigger: 'axis' },
    legend: { top: 0, right: 0, textStyle: { color: '#667085' } },
    xAxis: {
      type: 'category',
      data: names,
      boundaryGap: false,
      axisTick: { show: false },
      axisLabel: { color: '#667085' }
    },
    yAxis: {
      type: 'value',
      splitLine: { lineStyle: { color: '#eef1f5' } },
      axisLabel: { color: '#667085' }
    },
    series: series.map(item => ({
      name: item.name,
      type: 'line',
      data: item.data,
      smooth: true,
      symbolSize: 6,
      lineStyle: { width: 3, color: item.color },
      itemStyle: { color: item.color },
      areaStyle: { color: item.areaColor || 'rgba(64,158,255,.08)' }
    }))
  }
}

export function pieOption(data, colors) {
  return {
    color: colors,
    tooltip: { trigger: 'item' },
    legend: {
      bottom: 0,
      itemWidth: 10,
      itemHeight: 10,
      textStyle: { color: '#667085' }
    },
    series: [{
      type: 'pie',
      radius: ['48%', '70%'],
      center: ['50%', '42%'],
      avoidLabelOverlap: true,
      label: { color: '#344054' },
      data
    }]
  }
}
