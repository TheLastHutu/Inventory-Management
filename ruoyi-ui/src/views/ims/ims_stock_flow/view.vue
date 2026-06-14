<template>
  <el-drawer title="库存流水详情" :visible.sync="visible" direction="rtl" size="60%" append-to-body :before-close="handleClose" custom-class="detail-drawer">
    <div v-loading="loading" class="drawer-content">
      <h4 class="section-header">基本信息</h4>
      <el-row :gutter="20" class="mb8">
        <el-col :span="12">
          <div class="info-item">
            <label class="info-label">业务单据编号：</label>
            <span class="info-value plaintext">
              {{ info.bizNo }}
            </span>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="info-item">
            <label class="info-label">业务类型：</label>
            <span class="info-value plaintext">
              <dict-tag :options="dict.type.ims_business_type" :value="info.bizType" />
            </span>
          </div>
        </el-col>
      </el-row>
      <el-row :gutter="20" class="mb8">
<!--        <el-col :span="12">-->
<!--          <div class="info-item">-->
<!--            <label class="info-label">业务单据ID：</label>-->
<!--            <span class="info-value plaintext">-->
<!--              {{ info.bizId }}-->
<!--            </span>-->
<!--          </div>-->
<!--        </el-col>-->
        <el-col :span="12">
          <div class="info-item">
            <label class="info-label">企业名称</label>
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
            <label class="info-label">货位名称：</label>
            <span class="info-value plaintext">
              {{ locationName(info.locationId) }}
            </span>
          </div>
        </el-col>
      </el-row>
      <el-row :gutter="20" class="mb8">
        <el-col :span="12">
          <div class="info-item">
            <label class="info-label">产品名称 ：</label>
            <span class="info-value plaintext">
              {{ productName(info.productId) }}
            </span>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="info-item">
            <label class="info-label">发生时间：</label>
            <span class="info-value plaintext">
              {{ parseTime(info.occurTime, '{y}-{m}-{d}') }}
            </span>
          </div>
        </el-col>
      </el-row>
      <el-row :gutter="20" class="mb8">
        <el-col :span="12">
          <div class="info-item">
            <label class="info-label">方向：</label>
            <span class="info-value plaintext">
              <dict-tag :options="dict.type.ims_inout" :value="info.inout" />
            </span>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="info-item">
            <label class="info-label">数量(片)：</label>
            <span class="info-value plaintext">
              {{ info.qtyPiece }}
            </span>
          </div>
        </el-col>
      </el-row>
      <el-row :gutter="20" class="mb8">
        <el-col :span="12">
          <div class="info-item">
            <label class="info-label">单价：</label>
            <span class="info-value plaintext">
              {{ info.unitPrice }}
            </span>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="info-item">
            <label class="info-label">金额：</label>
            <span class="info-value plaintext">
              {{ info.amount }}
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
import { getIms_stock_flow } from '@/api/ims/ims_stock_flow'
import referenceData from "@/views/ims/mixins/referenceData"

export default {
  mixins: [referenceData],
  name: 'Ims_stock_flowViewDrawer',
  dicts: ['ims_business_type', 'ims_inout', ],
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
      getIms_stock_flow(id).then(res => {
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
