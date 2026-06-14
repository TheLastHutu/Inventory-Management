<template>
  <el-drawer title="样品单详情" :visible.sync="visible" direction="rtl" size="60%" append-to-body :before-close="handleClose" custom-class="detail-drawer">
    <div v-loading="loading" class="drawer-content">
      <h4 class="section-header">基本信息</h4>
      <el-row :gutter="20" class="mb8">
        <el-col :span="12">
          <div class="info-item">
            <label class="info-label">样品单号：</label>
            <span class="info-value plaintext">
              {{ info.billNo }}
            </span>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="info-item">
            <label class="info-label">企业名称：</label>
            <span class="info-value plaintext">
              {{ enterpriseName(info.enterpriseId) }}
            </span>
          </div>
        </el-col>
      </el-row>
      <el-row :gutter="20" class="mb8">
        <el-col :span="12">
          <div class="info-item">
            <label class="info-label">仓库名称：</label>
            <span class="info-value plaintext">
              {{ warehouseName(info.warehouseId) }}
            </span>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="info-item">
            <label class="info-label">客户名称：</label>
            <span class="info-value plaintext">
              {{ customerName(info.customerId) }}
            </span>
          </div>
        </el-col>
      </el-row>
      <el-row :gutter="20" class="mb8">
        <el-col :span="12">
          <div class="info-item">
            <label class="info-label">日期：</label>
            <span class="info-value plaintext">
              {{ parseTime(info.billDate, '{y}-{m}-{d}') }}
            </span>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="info-item">
            <label class="info-label">收货地点：</label>
            <span class="info-value plaintext">
              {{ info.receiver }}
            </span>
          </div>
        </el-col>
      </el-row>
      <el-row :gutter="20" class="mb8">
        <el-col :span="12">
          <div class="info-item">
            <label class="info-label">用途：</label>
            <span class="info-value plaintext">
              {{ info.purpose }}
            </span>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="info-item">
            <label class="info-label">总数量(片)：</label>
            <span class="info-value plaintext">
              {{ info.totalQtyPiece }}
            </span>
          </div>
        </el-col>
      </el-row>
      <el-row :gutter="20" class="mb8">
        <el-col :span="12">
          <div class="info-item">
            <label class="info-label">单据状态：</label>
            <span class="info-value plaintext">
              <dict-tag :options="dict.type.ims_bill_status" :value="info.billStatus" />
            </span>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="info-item">
            <label class="info-label">备注：</label>
            <span class="info-value plaintext">
              {{ info.remark }}
            </span>
          </div>
        </el-col>
      </el-row>
      <h4 class="section-header detail-section">明细信息</h4>
      <el-table :data="info.imsSampleItemList || []" border size="small" class="detail-table">
        <el-table-column label="产品名称" align="center" prop="productId">
          <template slot-scope="scope">
            <span>{{ productName(scope.row.productId) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="数量(片)" align="right" prop="qtyPiece" />
      </el-table>
    </div>
  </el-drawer>
</template>

<script>
import { getIms_sample } from '@/api/ims/ims_sample'
import referenceData from "@/views/ims/mixins/referenceData"

export default {
  mixins: [referenceData],
  name: 'Ims_sampleViewDrawer',
  dicts: ['ims_bill_status', ],
  data() {
    return {
      visible: false,
      loading: false,
      info: {}
    }
  },
  methods: {
    open(id) {
      this.visible = true
      this.loading = true
      getIms_sample(id).then(res => {
        this.info = res.data || {}
      }).finally(() => {
        this.loading = false
      })
    },
    handleClose() {
      this.visible = false
    }
  }
}
</script>

<style scoped>
.detail-section {
  margin-top: 22px;
}

.detail-table {
  margin-top: 8px;
}
</style>
