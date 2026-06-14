<template>
  <el-drawer title="工资管理详情" :visible.sync="visible" direction="rtl" size="60%" append-to-body :before-close="handleClose" custom-class="detail-drawer">
    <div v-loading="loading" class="drawer-content">
      <h4 class="section-header">基本信息</h4>
      <el-row :gutter="20" class="mb8">
        <el-col :span="12">
          <div class="info-item">
            <label class="info-label">部门名称：</label>
            <span class="info-value plaintext">
              {{ info.deptName }}
            </span>
          </div>
        </el-col>
      </el-row>
      <el-row :gutter="20" class="mb8">
        <el-col :span="12">
          <div class="info-item">
            <label class="info-label">月份：</label>
            <span class="info-value plaintext">
              {{ info.periodCode }}
            </span>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="info-item">
            <label class="info-label">状态：</label>
            <span class="info-value plaintext">
              <dict-tag :options="dict.type.ims_salary_status" :value="info.status" />
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
        <el-col :span="12">
          <div class="info-item">
            <label class="info-label">员工：</label>
            <span class="info-value plaintext">
              {{ info.employeeName || employeeName((info.imsPayrollDetailList || [])[0] && (info.imsPayrollDetailList || [])[0].employeeId) }}
            </span>
          </div>
        </el-col>
      </el-row>
      <h4 class="section-header detail-section">明细信息</h4>
      <el-table :data="info.imsPayrollDetailList || []" border size="small" class="detail-table">
        <el-table-column label="员工名称" align="center" prop="employeeName">
          <template slot-scope="scope">
            <span>{{ scope.row.employeeName || employeeName(scope.row.employeeId) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="出勤天数" align="right" prop="attendanceDays" />
        <el-table-column label="基础工资" align="right" prop="baseSalary" />
        <el-table-column label="提成" align="right" prop="commission" />
        <el-table-column label="补贴" align="right" prop="allowance" />
        <el-table-column label="奖金" align="right" prop="bonus" />
        <el-table-column label="扣款" align="right" prop="deduction" />
        <el-table-column label="实发工资" align="right" prop="netPay" />
      </el-table>
    </div>
  </el-drawer>
</template>

<script>
import { getIms_payroll_period } from '@/api/ims/ims_payroll_period'
import { listIms_employee } from "@/api/ims/ims_employee"

export default {
  name: 'Ims_payroll_periodViewDrawer',
  dicts: ['ims_salary_status', ],
  data() {
    return {
      visible: false,
      loading: false,
      info: {},
      employeeOptions: []
    }
  },
  created() {
    listIms_employee({ pageNum: 1, pageSize: 10000 }).then(response => {
      this.employeeOptions = response.rows || []
    })
  },
  methods: {
    open(periodId) {
      this.visible = true
      this.loading = true
      getIms_payroll_period(periodId).then(res => {
        this.info = res.data || {}
      }).finally(() => {
        this.loading = false
      })
    },
    handleClose() {
      this.visible = false
    },
    employeeName(value) {
      const item = this.employeeOptions.find(employee => employee.id === value)
      return item ? item.employeeName : value
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
