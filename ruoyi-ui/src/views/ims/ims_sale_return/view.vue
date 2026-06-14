<template>
  <el-drawer title="销售退货单详情" :visible.sync="visible" direction="rtl" size="60%" append-to-body :before-close="handleClose" custom-class="detail-drawer">
    <div v-loading="loading" class="drawer-content">
      <h4 class="section-header">基本信息</h4>
      <el-row :gutter="20" class="mb8">
        <el-col :span="12">
          <div class="info-item">
            <label class="info-label">退货单号：</label>
            <span class="info-value plaintext">
              {{ info.billNo }}
            </span>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="info-item">
            <label class="info-label">退货日期：</label>
            <span class="info-value plaintext">
              {{ parseTime(info.billDate, '{y}-{m}-{d}') }}
            </span>
          </div>
        </el-col>
      </el-row>
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
            <label class="info-label">退货入库仓库名称：</label>
            <span class="info-value plaintext">
              {{ warehouseName(info.warehouseId) }}
            </span>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="info-item">
            <label class="info-label">原出库单号：</label>
            <span class="info-value plaintext">
              {{ info.sourceOutboundNo }}
            </span>
          </div>
        </el-col>
      </el-row>
      <el-row :gutter="20" class="mb8">
        <el-col :span="12">
          <div class="info-item">
            <label class="info-label">退货原因：</label>
            <span class="info-value plaintext">
              {{ info.reason }}
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
            <label class="info-label">总金额(按退货价)：</label>
            <span class="info-value plaintext">
              {{ info.totalAmount }}
            </span>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="info-item">
            <label class="info-label">单据状态：</label>
            <span class="info-value plaintext">
              <dict-tag :options="dict.type.ims_bill_status" :value="info.billStatus" />
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
      <h4 class="section-header detail-section">明细信息</h4>
      <el-table :data="info.imsSaleReturnItemList || []" border size="small" class="detail-table">
        <el-table-column label="产品名称" align="center" prop="productId">
          <template slot-scope="scope">
            <span>{{ productName(scope.row.productId) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="数量(片)" align="right" prop="qtyPiece" />
        <el-table-column label="退货单价" align="right" prop="unitPrice" />
        <el-table-column label="金额" align="right" prop="amount" />
      </el-table>
    </div>
  </el-drawer>
</template>

<script>
import { getIms_sale_return } from '@/api/ims/ims_sale_return'
import referenceData from "@/views/ims/mixins/referenceData"

export default {
  mixins: [referenceData],
  name: 'Ims_sale_returnViewDrawer',
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
      getIms_sale_return(id).then(res => {
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
