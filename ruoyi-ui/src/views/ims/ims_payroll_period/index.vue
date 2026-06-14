<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="auto">
      <el-form-item label="部门名称" prop="deptId" >
        <treeselect
          v-model="queryParams.deptId"
          :options="deptOptions"
          :show-count="true"
          placeholder="请选择部门"
          clearable
          style="width: 210px "
        />
      </el-form-item>
      <el-form-item label="员工" prop="employeeId">
        <el-select v-model="queryParams.employeeId" placeholder="请选择员工" clearable filterable style="width: 210px">
          <el-option
            v-for="employee in employeeOptions"
            :key="employee.id"
            :label="employee.employeeName"
            :value="employee.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="月份范围">
        <el-date-picker
          v-model="dateRange"
          type="daterange"
          range-separator="-"
          start-placeholder="开始月份"
          end-placeholder="结束月份"
          value-format="yyyy-MM"
          format="yyyy-MM"
          :picker-options="{ pickerMode: 'month' }"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option
            v-for="dict in dict.type.ims_salary_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['ims:ims_payroll_period:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single || selectedHasPaid"
          @click="handleUpdate"
          v-hasPermi="['ims:ims_payroll_period:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple || selectedHasPaid"
          @click="handleDelete"
          v-hasPermi="['ims:ims_payroll_period:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['ims:ims_payroll_period:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-s-data"
          size="mini"
          @click="handleStatistics"
        >统计</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="ims_payroll_periodList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" width="80">
        <template slot-scope="scope">
          <span>{{ (queryParams.pageNum - 1) * queryParams.pageSize + scope.$index + 1 }}</span>
        </template>
      </el-table-column>
      <el-table-column label="部门名称" align="center" prop="deptName" />
      <el-table-column label="员工" align="center" prop="employeeName" />
      <el-table-column label="月份" align="center" prop="periodCode" />
      <el-table-column label="实发工资" align="center" prop="netPay" />
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.ims_salary_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleViewData(scope.row)"
            v-hasPermi="['ims:ims_payroll_period:query']"
          >详情</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            :disabled="isPayrollPaid(scope.row)"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['ims:ims_payroll_period:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            :disabled="isPayrollPaid(scope.row)"
            @click="handleDelete(scope.row)"
            v-hasPermi="['ims:ims_payroll_period:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 工资管理详情抽屉 -->
    <ims_payroll_period-view-drawer ref="ims_payroll_periodViewRef" />
    <!-- 添加或修改工资管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
          <el-row>
          <el-col :span="12">
            <el-form-item label="部门名称" prop="deptId">
              <treeselect
                v-model="form.deptId"
                :options="deptOptions"
                :show-count="true"
                placeholder="请选择部门"
              />
            </el-form-item>
          </el-col>


          <el-col :span="12">
            <el-form-item label="月份" prop="periodCode">
              <el-date-picker
                v-model="form.periodCode"
                type="month"
                placeholder="请选择月份"
                value-format="yyyy-MM"
                format="yyyy-MM"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>



          <el-col>
            <el-col :span="12">
              <el-form-item label="员工" prop="employeeId">
                <el-select v-model="form.employeeId" placeholder="请选择员工" clearable filterable style="width: 100%" @change="handlePayrollEmployeeChange">
                  <el-option
                    v-for="employee in filteredEmployeeOptions"
                    :key="employee.id"
                    :label="employee.employeeName"
                    :value="employee.id"
                  />
                </el-select>
              </el-form-item>
            </el-col>
          <el-col :span="12">
            <el-form-item label="状态" prop="status">
              <el-select v-model="form.status" placeholder="请选择状态" style="width: 100%">
                <el-option
                  v-for="dict in dict.type.ims_salary_status"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
            </el-col>
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"  maxlength="200" show-word-limit/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider content-position="center">工资明细信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddImsPayrollDetail">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteImsPayrollDetail">删除</el-button>
          </el-col>
        </el-row>
        <el-table :data="imsPayrollDetailList" @selection-change="handleImsPayrollDetailSelectionChange" ref="imsPayrollDetail" style="width: 100% ">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="出勤天数" align="center" prop="attendanceDays" >
            <template slot-scope="scope">
              <el-input v-model="scope.row.attendanceDays" placeholder="请输入正整数" @input="handleInputChange(scope.row)" />
            </template>
          </el-table-column>
          <el-table-column label="基础工资" align="center" prop="baseSalary" >
            <template slot-scope="scope">
              <el-input v-model="scope.row.baseSalary" placeholder="最多两位小数" @input="handleInputChange(scope.row)" />
            </template>
          </el-table-column>
          <el-table-column label="提成"  align="center" prop="commission" >
            <template slot-scope="scope">
              <el-input v-model="scope.row.commission" placeholder="最多两位小数" @input="handleInputChange(scope.row)" />
            </template>
          </el-table-column>
          <el-table-column label="补贴"  align="center" prop="allowance" >
            <template slot-scope="scope">
              <el-input v-model="scope.row.allowance" placeholder="最多两位小数" @input="handleInputChange(scope.row)" />
            </template>
          </el-table-column>
          <el-table-column label="奖金"  align="center" prop="bonus" >
            <template slot-scope="scope">
              <el-input v-model="scope.row.bonus" placeholder="最多两位小数" @input="handleInputChange(scope.row)" />
            </template>
          </el-table-column>
          <el-table-column label="扣款"  align="center" prop="deduction" >
            <template slot-scope="scope">
              <el-input v-model="scope.row.deduction" placeholder="最多两位小数" @input="handleInputChange(scope.row)" />
            </template>
          </el-table-column>
          <el-table-column label="实发工资"  align="center" prop="netPay" >
            <template slot-scope="scope">
              <span class="net-pay-value">{{ scope.row.netPay || 0 }}</span>
            </template>
          </el-table-column>
        </el-table>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog title="工资统计" :visible.sync="statisticsOpen" width="980px" append-to-body custom-class="payroll-stat-dialog" @closed="disposePayrollChart">
      <div v-loading="statisticsLoading" class="payroll-stat">
        <el-form :model="statisticsQuery" size="small" :inline="true" class="stat-filter">
          <el-form-item label="部门">
            <treeselect
              v-model="statisticsQuery.deptId"
              :options="deptOptions"
              :show-count="true"
              placeholder="请选择部门"
              clearable
              style="width: 220px"
            />
          </el-form-item>
          <el-form-item label="周期">
            <el-select v-model="statisticsQuery.periodType" style="width: 120px">
              <el-option label="按年" value="year" />
              <el-option label="按月" value="month" />
            </el-select>
          </el-form-item>
          <el-form-item label="年份">
            <el-date-picker
              v-model="statisticsQuery.year"
              type="year"
              value-format="yyyy"
              placeholder="请选择年份"
              style="width: 140px"
            />
          </el-form-item>
          <el-form-item label="TopN">
            <el-input-number v-model="statisticsQuery.topN" :min="1" :max="50" controls-position="right" style="width: 120px" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" @click="loadPayrollStatistics">查询</el-button>
          </el-form-item>
        </el-form>
        <el-row :gutter="14" class="stat-cards">
          <el-col :span="6">
            <div class="stat-card stat-card-blue">
              <div class="stat-label">总实发工资(元)</div>
              <div class="stat-value">{{ formatMoney(payrollSummary.totalPay) }}</div>
            </div>
          </el-col>
          <el-col :span="6">
            <div class="stat-card stat-card-green">
              <div class="stat-label">工资单数</div>
              <div class="stat-value">{{ payrollSummary.recordCount }}</div>
            </div>
          </el-col>
          <el-col :span="6">
            <div class="stat-card stat-card-orange">
              <div class="stat-label">员工数</div>
              <div class="stat-value">{{ payrollSummary.employeeCount }}</div>
            </div>
          </el-col>
          <el-col :span="6">
            <div class="stat-card stat-card-purple">
              <div class="stat-label">部门数</div>
              <div class="stat-value">{{ payrollSummary.deptCount }}</div>
            </div>
          </el-col>
        </el-row>
        <el-row :gutter="16" class="stat-body">
          <el-col :span="15">
            <div class="stat-panel">
              <div class="stat-panel-title">部门工资分布</div>
              <div ref="payrollChart" class="payroll-chart"></div>
            </div>
          </el-col>
          <el-col :span="9">
            <div class="stat-panel">
              <div class="stat-panel-title">员工工资排行</div>
              <el-table :data="employeeRankRows" size="mini" height="320">
                <el-table-column label="序号" type="index" width="58" align="center" />
                <el-table-column label="部门" prop="deptName" min-width="95" show-overflow-tooltip />
                <el-table-column label="员工" prop="employeeName" min-width="90" show-overflow-tooltip />
                <el-table-column label="总实发(元)" prop="pay" width="100" align="right">
                  <template slot-scope="scope">{{ formatMoney(scope.row.pay) }}</template>
                </el-table-column>
              </el-table>
            </div>
          </el-col>
        </el-row>
        <div class="stat-panel">
          <div class="stat-panel-title">部门汇总明细</div>
          <el-table :data="deptStatRows" size="mini">
            <el-table-column label="部门" prop="deptName" min-width="140" />
            <el-table-column label="员工数" prop="employeeCount" align="center" width="100" />
            <el-table-column label="记录数" prop="recordCount" align="center" width="100" />
            <el-table-column label="工资总额" prop="pay" align="right" width="140">
              <template slot-scope="scope">{{ formatMoney(scope.row.pay) }}</template>
            </el-table-column>
            <el-table-column label="平均工资" prop="avgPay" align="right" width="140">
              <template slot-scope="scope">{{ formatMoney(scope.row.avgPay) }}</template>
            </el-table-column>
          </el-table>
        </div>
        <el-row :gutter="16" class="stat-bottom">
          <el-col :span="12">
            <div class="stat-panel">
              <div class="stat-panel-title">月份工资排名</div>
              <el-table :data="monthStatRows" size="mini" height="210">
                <el-table-column label="序号" type="index" width="58" align="center" />
                <el-table-column label="月份" prop="period" />
                <el-table-column label="总实发(元)" prop="pay" align="right">
                  <template slot-scope="scope">{{ formatMoney(scope.row.pay) }}</template>
                </el-table-column>
              </el-table>
            </div>
          </el-col>
          <el-col :span="12">
            <div class="stat-panel">
              <div class="stat-panel-title">季度工资排名</div>
              <el-table :data="quarterStatRows" size="mini" height="210">
                <el-table-column label="序号" type="index" width="58" align="center" />
                <el-table-column label="季度" prop="period" />
                <el-table-column label="总实发(元)" prop="pay" align="right">
                  <template slot-scope="scope">{{ formatMoney(scope.row.pay) }}</template>
                </el-table-column>
              </el-table>
            </div>
          </el-col>
        </el-row>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listIms_payroll_period, getIms_payroll_period, delIms_payroll_period, addIms_payroll_period, updateIms_payroll_period } from "@/api/ims/ims_payroll_period"
import { deptTreeSelect } from "@/api/system/user"
import { listIms_employee } from "@/api/ims/ims_employee"
import Ims_payroll_periodViewDrawer from "./view"
import Treeselect from "@riophae/vue-treeselect"
import "@riophae/vue-treeselect/dist/vue-treeselect.css"
import * as echarts from "echarts"
import amountCalc from "@/views/ims/mixins/amountCalc"
import listActions from "@/views/ims/mixins/listActions"

export default {
  name: "Ims_payroll_period",
  mixins: [amountCalc, listActions],
  components: { Ims_payroll_periodViewDrawer, Treeselect },
  dicts: ['ims_salary_status'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      selectedRows: [],
      selectedHasPaid: false,
      // 子表选中数据
      checkedImsPayrollDetail: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 工资管理表格数据
      ims_payroll_periodList: [],
      // 工资明细表格数据
      imsPayrollDetailList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      statisticsOpen: false,
      statisticsLoading: false,
      payrollChart: null,
      payrollSummary: {
        totalPay: 0,
        employeeCount: 0,
        avgPay: 0,
        recordCount: 0,
        deptCount: 0
      },
      deptStatRows: [],
      employeeRankRows: [],
      monthStatRows: [],
      quarterStatRows: [],
      statisticsQuery: {
        deptId: null,
        periodType: 'year',
        year: String(new Date().getFullYear()),
        topN: 10
      },
      // 部门树选项
      deptOptions: undefined,
      // 员工选项
      employeeOptions: [],
      // 日期范围
      dateRange: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        deptId: null,
        employeeId: null,
        periodCode: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        deptId: [
          { required: true, message: "部门名称不能为空", trigger: "change" }
        ],
        periodCode: [
          { required: true, message: "月份不能为空", trigger: "change" }
        ],
        employeeId: [
          { required: true, message: "员工不能为空", trigger: "change" }
        ],
        status: [
          { required: true, message: "状态不能为空", trigger: "change" }
        ],
      }
    }
  },
  computed: {
    filteredEmployeeOptions() {
      if (!this.form.deptId) {
        return this.employeeOptions
      }
      return this.employeeOptions.filter(employee => String(employee.deptId) === String(this.form.deptId))
    }
  },
  created() {
    this.getList()
    this.getDeptTree()
    this.getEmployeeList()
  },
  methods: {
    /** 查询工资管理列表 */
    getList() {
      this.loading = true
      listIms_payroll_period(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
        this.ims_payroll_periodList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    /** 查询部门下拉树结构 */
    getDeptTree() {
      deptTreeSelect().then(response => {
        this.deptOptions = response.data
      })
    },
    /** 查询员工列表 */
    getEmployeeList() {
      listIms_employee({ pageNum: 1, pageSize: 100 }).then(response => {
        this.employeeOptions = response.rows
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        periodId: null,
        deptId: null,
        employeeId: null,
        periodCode: null,
        status: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      }
      this.imsPayrollDetailList = []
      this.resetForm("form")
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = []
      this.resetForm("queryForm")
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.setSelectionRows(selection)
      this.selectedRows = selection
      this.ids = selection.map(item => item.periodId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
      this.selectedHasPaid = this.hasPayrollPaid(selection)
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加工资管理"
    },
    /** 修改按钮操作 */
    handleUpdate(row = {}) {
      const target = row.periodId ? row : this.selectedRows[0]
      if (this.isPayrollPaid(target)) {
        this.$modal.msgError("工资表已发放，不能修改")
        return
      }
      this.reset()
      const periodId = row.periodId || this.ids
      getIms_payroll_period(periodId).then(response => {
        this.form = response.data
        this.imsPayrollDetailList = response.data.imsPayrollDetailList || []
        if (this.imsPayrollDetailList.length) {
          this.$set(this.form, "employeeId", this.imsPayrollDetailList[0].employeeId)
        }
        // 重新计算每一行的实发工资
        this.imsPayrollDetailList.forEach(item => {
          this.normalizePayrollRow(item)
        })
        this.open = true
        this.title = "修改工资管理"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (!this.imsPayrollDetailList.length) {
            this.handlePayrollEmployeeChange(this.form.employeeId)
          }
          this.imsPayrollDetailList.forEach(item => {
            this.normalizePayrollRow(item)
          })
          this.form.imsPayrollDetailList = this.imsPayrollDetailList
          if (this.form.periodId != null) {
            updateIms_payroll_period(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addIms_payroll_period(this.form).then(response => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row = {}) {
      const rows = row.periodId ? [row] : this.selectedRows
      if (this.hasPayrollPaid(rows)) {
        this.$modal.msgError("工资表已发放，不能删除")
        return
      }
      const periodIds = row.periodId || this.ids
      const names = rows.length ? rows.map(item => item.periodCode).join("、") : periodIds
      this.$modal.confirm('您确认要删除工资表为“' + names + '”的数据吗？').then(function() {
        return delIms_payroll_period(periodIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 工资明细添加按钮操作 */
    handleAddImsPayrollDetail() {
      let obj = {}
      obj.employeeId = this.form.employeeId || ""
      obj.attendanceDays = ""
      obj.baseSalary = ""
      obj.commission = ""
      obj.allowance = ""
      obj.bonus = ""
      obj.deduction = ""
      obj.netPay = "0"
      obj.remark = ""
      this.imsPayrollDetailList.push(obj)
    },
    handlePayrollEmployeeChange(value) {
      if (!this.imsPayrollDetailList.length) {
        this.imsPayrollDetailList.push({
          employeeId: value || "",
          attendanceDays: "",
          baseSalary: "",
          commission: "",
          allowance: "",
          bonus: "",
          deduction: "",
          netPay: "0",
          remark: ""
        })
      }
      this.imsPayrollDetailList.forEach(item => {
        item.employeeId = value || this.form.employeeId || ""
      })
    },
    /** 工资明细删除按钮操作 */
    handleDeleteImsPayrollDetail() {
      if (this.checkedImsPayrollDetail.length == 0) {
        this.$modal.msgError("请先选择要删除的工资明细数据")
      } else {
        const checkedImsPayrollDetail = [...this.checkedImsPayrollDetail]
        this.imsPayrollDetailList = this.imsPayrollDetailList.filter(function(item) {
          return checkedImsPayrollDetail.indexOf(item.index) == -1
        })
        // 清空选中状态
        this.checkedImsPayrollDetail = []
        // 重新设置表格选择状态
        if (this.$refs.imsPayrollDetail) {
          this.$refs.imsPayrollDetail.clearSelection()
        }
      }
    },
    /** 复选框选中数据 */
    handleImsPayrollDetailSelectionChange(selection) {
      this.checkedImsPayrollDetail = selection.map(item => item.index)
    },
    /** 工资明细输入变化处理 */
    handleInputChange(row) {
      this.normalizePayrollRow(row)
      const index = this.imsPayrollDetailList.indexOf(row)
      if (index !== -1) {
        this.imsPayrollDetailList.splice(index, 1, { ...row })
      }
    },
    handleStatistics() {
      this.statisticsOpen = true
      this.statisticsQuery.deptId = this.queryParams.deptId
      this.loadPayrollStatistics()
    },
    loadPayrollStatistics() {
      this.statisticsLoading = true
      const query = {
        pageNum: 1,
        pageSize: 10000,
        deptId: this.statisticsQuery.deptId,
        status: this.queryParams.status
      }
      const range = this.statisticsQuery.year
        ? [`${this.statisticsQuery.year}-01`, `${this.statisticsQuery.year}-12`]
        : []
      listIms_payroll_period(this.addDateRange(query, range)).then(response => {
        const rows = response.rows || []
        return Promise.all(rows.map(row => getIms_payroll_period(row.periodId).then(res => ({ ...row, ...(res.data || {}) }))))
      }).then(rows => {
        this.buildPayrollStatistics(rows)
        this.$nextTick(this.renderPayrollChart)
      }).finally(() => {
        this.statisticsLoading = false
      })
    },
    buildPayrollStatistics(rows) {
      const deptMap = new Map()
      const employeeMap = new Map()
      const monthMap = new Map()
      const quarterMap = new Map()
      const employeeSet = new Set()
      let totalPay = 0
      rows.forEach(row => {
        const pay = this.toAmountNumber(row.netPay)
        const deptName = row.deptName || '未分配部门'
        totalPay += pay
        if (!deptMap.has(deptName)) {
          deptMap.set(deptName, { deptName, pay: 0, recordCount: 0, employeeIds: new Set() })
        }
        const dept = deptMap.get(deptName)
        dept.pay += pay
        dept.recordCount += 1
        const month = row.periodCode || '未知月份'
        monthMap.set(month, (monthMap.get(month) || 0) + pay)
        const quarter = this.getQuarterLabel(month)
        quarterMap.set(quarter, (quarterMap.get(quarter) || 0) + pay)
        ;(row.imsPayrollDetailList || []).forEach(detail => {
          const employeeName = this.employeeName(detail.employeeId) || detail.employeeName || '未命名员工'
          const detailPay = this.toAmountNumber(detail.netPay)
          employeeSet.add(detail.employeeId || employeeName)
          dept.employeeIds.add(detail.employeeId || employeeName)
          if (!employeeMap.has(employeeName)) {
            employeeMap.set(employeeName, { employeeName, deptName, pay: 0, recordCount: 0 })
          }
          const employee = employeeMap.get(employeeName)
          employee.pay += detailPay
          employee.recordCount += 1
        })
        if (!row.imsPayrollDetailList || !row.imsPayrollDetailList.length) {
          const fallbackName = row.employeeName || '未命名员工'
          employeeSet.add(row.employeeId || fallbackName)
          dept.employeeIds.add(row.employeeId || fallbackName)
          if (!employeeMap.has(fallbackName)) {
            employeeMap.set(fallbackName, { employeeName: fallbackName, deptName, pay: 0, recordCount: 0 })
          }
          const employee = employeeMap.get(fallbackName)
          employee.pay += pay
          employee.recordCount += 1
        }
      })
      this.payrollSummary = {
        totalPay,
        employeeCount: employeeSet.size,
        avgPay: employeeSet.size ? totalPay / employeeSet.size : 0,
        recordCount: rows.length,
        deptCount: deptMap.size
      }
      this.deptStatRows = Array.from(deptMap.values()).map(item => ({
        deptName: item.deptName,
        pay: item.pay,
        recordCount: item.recordCount,
        employeeCount: item.employeeIds.size,
        avgPay: item.employeeIds.size ? item.pay / item.employeeIds.size : 0
      })).sort((a, b) => b.pay - a.pay)
      const topN = this.statisticsQuery.topN || 10
      this.employeeRankRows = Array.from(employeeMap.values()).sort((a, b) => b.pay - a.pay).slice(0, topN)
      this.monthStatRows = Array.from(monthMap.entries()).map(([period, pay]) => ({ period, pay })).sort((a, b) => a.period.localeCompare(b.period))
      this.quarterStatRows = Array.from(quarterMap.entries()).map(([period, pay]) => ({ period, pay })).sort((a, b) => a.period.localeCompare(b.period))
    },
    getQuarterLabel(periodCode) {
      const parts = String(periodCode || '').split('-')
      const year = parts[0] || '未知'
      const month = Number(parts[1] || 1)
      const quarter = Math.max(1, Math.ceil(month / 3))
      return `${year}-Q${quarter}`
    },
    renderPayrollChart() {
      if (!this.$refs.payrollChart) {
        return
      }
      if (!this.payrollChart) {
        this.payrollChart = echarts.init(this.$refs.payrollChart, 'macarons')
      }
      this.payrollChart.setOption({
        tooltip: { trigger: 'axis', axisPointer: { type: 'shadow' } },
        grid: { left: 16, right: 20, top: 36, bottom: 20, containLabel: true },
        xAxis: { type: 'category', data: this.deptStatRows.map(item => item.deptName), axisLabel: { interval: 0, rotate: 25 } },
        yAxis: { type: 'value' },
        series: [{
          name: '工资总额',
          type: 'bar',
          barMaxWidth: 42,
          data: this.deptStatRows.map(item => Number(item.pay.toFixed(2))),
          itemStyle: { color: '#409EFF', borderRadius: [4, 4, 0, 0] }
        }]
      })
      this.payrollChart.resize()
    },
    disposePayrollChart() {
      if (this.payrollChart) {
        this.payrollChart.dispose()
        this.payrollChart = null
      }
    },
    formatMoney(value) {
      return Number(value || 0).toFixed(2)
    },
    employeeName(value) {
      const item = this.employeeOptions.find(employee => employee.id === value)
      return item ? item.employeeName : value
    },
    /** 详情按钮操作 */
    handleViewData(row) {
      this.$refs["ims_payroll_periodViewRef"].open(row.periodId)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('ims/ims_payroll_period/export', {
        ...this.queryParams
      }, `ims_payroll_period_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>

<style scoped>
.payroll-stat {
  padding: 2px 0 4px;
}

.stat-cards {
  margin-bottom: 14px;
}

.stat-card {
  min-height: 92px;
  border-radius: 6px;
  padding: 16px 18px;
  color: #fff;
}

.stat-card-blue {
  background: linear-gradient(135deg, #409EFF, #5E7CE2);
}

.stat-card-green {
  background: linear-gradient(135deg, #13CE66, #19B6A3);
}

.stat-card-orange {
  background: linear-gradient(135deg, #FF9F43, #F56C6C);
}

.stat-card-purple {
  background: linear-gradient(135deg, #8E7CC3, #6C5CE7);
}

.stat-label {
  font-size: 13px;
  opacity: .86;
}

.stat-value {
  margin-top: 12px;
  font-size: 26px;
  font-weight: 600;
  line-height: 1;
}

.stat-body {
  margin-bottom: 14px;
}

.stat-panel {
  border: 1px solid #e6ebf5;
  border-radius: 6px;
  padding: 14px;
  background: #fff;
}

.stat-panel-title {
  margin-bottom: 12px;
  color: #303133;
  font-size: 15px;
  font-weight: 600;
}

.payroll-chart {
  width: 100%;
  height: 320px;
}

.net-pay-value {
  display: inline-block;
  min-width: 72px;
  color: #303133;
  font-weight: 600;
}
</style>
