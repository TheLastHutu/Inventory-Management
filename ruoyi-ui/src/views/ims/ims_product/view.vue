<template>
  <el-drawer title="产品管理详情" :visible.sync="visible" direction="rtl" size="60%" append-to-body :before-close="handleClose" custom-class="detail-drawer">
    <div v-loading="loading" class="drawer-content">
      <h4 class="section-header">基本信息</h4>
      <el-row :gutter="20" class="mb8">
        <el-col :span="12">
          <div class="info-item">
            <label class="info-label">产品编号：</label>
            <span class="info-value plaintext">
              {{ info.productCode }}
            </span>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="info-item">
            <label class="info-label">产品名称：</label>
            <span class="info-value plaintext">
              {{ info.productName }}
            </span>
          </div>
        </el-col>
      </el-row>
      <el-row :gutter="20" class="mb8">
<!--        <el-col :span="12">-->
<!--          <div class="info-item">-->
<!--            <label class="info-label">分类ID：</label>-->
<!--            <span class="info-value plaintext">-->
<!--              {{ categoryName(info.categoryId) }}-->
<!--            </span>-->
<!--          </div>-->
<!--        </el-col>-->

        <el-col :span="12">
          <div class="info-item">
            <label class="info-label">品牌名称：</label>
            <span class="info-value plaintext">
              {{ brandName(info.brandId) }}
            </span>
          </div>
        </el-col>


        <el-col :span="12">
          <div class="info-item">
            <label class="info-label">厂家名称：</label>
            <span class="info-value plaintext">
              {{ supplierName(info.supplierId) }}
            </span>
          </div>
        </el-col>
      </el-row>
      <el-row :gutter="20" class="mb8">
        <el-col :span="12">
          <div class="info-item">
            <label class="info-label">单位名称：</label>
            <span class="info-value plaintext">
              {{ unitName(info.unitId) }}
            </span>
          </div>
        </el-col>

        <el-col :span="12">
          <div class="info-item">
            <label class="info-label">规格：</label>
            <span class="info-value plaintext">
              {{ info.spec }}
            </span>
          </div>
        </el-col>
      </el-row>
      <el-row :gutter="20" class="mb8">

        <el-col :span="12">
          <div class="info-item">
            <label class="info-label">色号：</label>
            <span class="info-value plaintext">
              {{ info.colorNo }}
            </span>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="info-item">
            <label class="info-label">等级：</label>
            <span class="info-value plaintext">
              <dict-tag :options="dict.type.ims_level" :value="info.grade" />
            </span>
          </div>
        </el-col>
      </el-row>
      <el-row :gutter="20" class="mb8">

        <el-col :span="12">
          <div class="info-item">
            <label class="info-label">装箱系数：</label>
            <span class="info-value plaintext">
              {{ info.boxRate }}
            </span>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="info-item">
            <label class="info-label">参考进价单价：</label>
            <span class="info-value plaintext">
              {{ info.purchasePrice }}
            </span>
          </div>
        </el-col>

      </el-row>
      <el-row :gutter="20" class="mb8">

        <el-col :span="12">
          <div class="info-item">
            <label class="info-label">参考售价单价：</label>
            <span class="info-value plaintext">
              {{ info.salePrice }}
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
import { getIms_product } from '@/api/ims/ims_product'
import referenceData from "@/views/ims/mixins/referenceData"

export default {
  mixins: [referenceData],
  name: 'Ims_productViewDrawer',
  dicts: ['ims_brand', 'ims_level', 'ims_status', ],
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
      getIms_product(id).then(res => {
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
