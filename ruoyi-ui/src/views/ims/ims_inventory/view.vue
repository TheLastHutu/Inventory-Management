<template>
  <el-drawer title="库存(现存量)详情" :visible.sync="visible" direction="rtl" size="60%" append-to-body :before-close="handleClose" custom-class="detail-drawer">
    <div v-loading="loading" class="drawer-content">
      <h4 class="section-header">基本信息</h4>
      <el-row :gutter="20" class="mb8">
        <el-col :span="12">
          <div class="info-item">
            <label class="info-label">企业名称：</label>
            <span class="info-value plaintext">
              {{ enterpriseName(info.enterpriseId) }}
            </span>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="info-item">
            <label class="info-label">仓库名称：</label>
            <span class="info-value plaintext">
              {{ warehouseName(info.warehouseId) }}
            </span>
          </div>
        </el-col>
      </el-row>
      <el-row :gutter="20" class="mb8">
        <el-col :span="12">
          <div class="info-item">
            <label class="info-label">货位名称：</label>
            <span class="info-value plaintext">
              {{ locationName(info.locationId) }}
            </span>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="info-item">
            <label class="info-label">产品名称：</label>
            <span class="info-value plaintext">
              {{ productName(info.productId) }}
            </span>
          </div>
        </el-col>
      </el-row>
      <el-row :gutter="20" class="mb8">
        <el-col :span="12">
          <div class="info-item">
            <label class="info-label">现存量(片)：</label>
            <span class="info-value plaintext">
              {{ info.qtyPiece }}
            </span>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="info-item">
            <label class="info-label">锁定量(片)：</label>
            <span class="info-value plaintext">
              {{ info.lockedQtyPiece }}
            </span>
          </div>
        </el-col>
      </el-row>
      <el-row :gutter="20" class="mb8">
        <el-col :span="12">
          <div class="info-item">
            <label class="info-label">可用量(片)：</label>
            <span class="info-value plaintext">
              {{ info.availQtyPiece }}
            </span>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="info-item">
            <label class="info-label">状态：</label>
            <span class="info-value plaintext">
              <dict-tag :options="dict.type.ims_status" :value="info.status" />
            </span>
          </div>
        </el-col>
      </el-row>
      <el-row :gutter="20" class="mb8">
        <el-col :span="12">
          <div class="info-item">
            <label class="info-label">备注：</label>
            <span class="info-value plaintext">
              {{ info.remark }}
            </span>
          </div>
        </el-col>
      </el-row>
    </div>
  </el-drawer>
</template>

<script>
import { getIms_inventory } from '@/api/ims/ims_inventory'
import referenceData from "@/views/ims/mixins/referenceData"

export default {
  mixins: [referenceData],
  name: 'Ims_inventoryViewDrawer',
  dicts: ['ims_status', ],
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
      getIms_inventory(id).then(res => {
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
