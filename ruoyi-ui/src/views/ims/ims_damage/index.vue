<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="auto">
      <el-form-item label="报损单号" prop="billNo">
        <el-input
          v-model="queryParams.billNo"
          placeholder="请输入报损单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="企业名称" prop="enterpriseId">
        <el-select v-model="queryParams.enterpriseId" placeholder="请选择企业名称" clearable filterable>
                <el-option
                  v-for="item in enterpriseOptions"
                  :key="item.id"
                  :label="enterpriseName(item.id)"
                  :value="item.id"
                />
              </el-select>
      </el-form-item>
      <el-form-item label="仓库名称" prop="warehouseId">
        <el-select v-model="queryParams.warehouseId" placeholder="请选择仓库名称" clearable filterable>
                <el-option
                  v-for="item in warehouseOptions"
                  :key="item.id"
                  :label="warehouseName(item.id)"
                  :value="item.id"
                />
              </el-select>
      </el-form-item>
      <el-form-item label="报损日期" prop="billDate">
        <el-date-picker clearable
          v-model="dateRangeBillDate"
          type="daterange"
          value-format="yyyy-MM-dd"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="单据状态" prop="billStatus">
        <el-select v-model="queryParams.billStatus" placeholder="请选择单据状态" clearable>
          <el-option
            v-for="dict in dict.type.ims_bill_status"
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
          v-hasPermi="['ims:ims_damage:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['ims:ims_damage:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['ims:ims_damage:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['ims:ims_damage:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="ims_damageList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" width="80">
        <template slot-scope="scope">
          <span>{{ (queryParams.pageNum - 1) * queryParams.pageSize + scope.$index + 1 }}</span>
        </template>
      </el-table-column>
      <el-table-column label="报损单号" align="center" prop="billNo" />
      <el-table-column label="企业名称" align="center" prop="enterpriseId">
        <template slot-scope="scope">
          <span>{{ enterpriseName(scope.row.enterpriseId) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="仓库名称" align="center" prop="warehouseId">
        <template slot-scope="scope">
          <span>{{ warehouseName(scope.row.warehouseId) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="报损日期" align="center" prop="billDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.billDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="报损原因" align="center" prop="reason" />
      <el-table-column label="总数量(片)" align="center" prop="totalQtyPiece" />
      <el-table-column label="总金额(按成本)" align="center" prop="totalAmount" />
      <el-table-column label="单据状态" align="center" prop="billStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.ims_bill_status" :value="scope.row.billStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="180">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleViewData(scope.row)"
            v-hasPermi="['ims:ims_damage:query']"
          >详情</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['ims:ims_damage:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['ims:ims_damage:remove']"
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

    <!-- 报损单详情抽屉 -->
    <ims_damage-view-drawer ref="ims_damageViewRef" />
    <!-- 添加或修改报损单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="900px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="报损单号" prop="billNo">
              <el-input v-model="form.billNo" placeholder="请输入报损单号" maxlength="30" show-word-limit/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="企业名称" prop="enterpriseId">
              <el-select v-model="form.enterpriseId" placeholder="请选择企业名称" clearable filterable style="width: 100%">
                <el-option
                  v-for="item in enterpriseOptions"
                  :key="item.id"
                  :label="enterpriseName(item.id)"
                  :value="item.id"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="仓库名称" prop="warehouseId">
              <el-select v-model="form.warehouseId" placeholder="请选择仓库名称" clearable filterable  style="width: 100%">
                <el-option
                  v-for="item in warehouseOptions"
                  :key="item.id"
                  :label="warehouseName(item.id)"
                  :value="item.id"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="报损日期" prop="billDate">
              <el-date-picker clearable
                v-model="form.billDate"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择报损日期"  style="width: 100%">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="报损原因" prop="reason">
              <el-input v-model="form.reason" placeholder="请输入报损原因" maxlength="30" show-word-limit/>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="单据状态" prop="billStatus">
              <el-select v-model="form.billStatus" placeholder="请选择单据状态"  style="width: 100%">
                <el-option
                  v-for="dict in dict.type.ims_bill_status"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"   maxlength="200" show-word-limit/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="总数量(片)" prop="totalQtyPiece">
              <el-input v-model="form.totalQtyPiece" disabled placeholder="由明细自动计算" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="总金额" prop="totalAmount">
              <el-input v-model="form.totalAmount" disabled placeholder="由明细自动计算" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider content-position="center">报损明细信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddImsDamageItem">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteImsDamageItem">删除</el-button>
          </el-col>
        </el-row>
        <el-table :data="imsDamageItemList" @selection-change="handleImsDamageItemSelectionChange" ref="imsDamageItem" style="width: 100%">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="产品名称" prop="productId" >
            <template slot-scope="scope">
              <el-select v-model="scope.row.productId" placeholder="请选择产品名称" clearable filterable>
                <el-option
                  v-for="item in productOptions"
                  :key="item.id"
                  :label="productName(item.id)"
                  :value="item.id"
                />
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="数量(片)" prop="qtyPiece" >
            <template slot-scope="scope">
              <div class="qty-stock-cell">
                <el-input v-model="scope.row.qtyPiece" placeholder="请输入正整数" @input="handleDetailInput(scope.row)" />
                <div class="stock-hint">剩余库存：{{ stockAfterInput(scope.row, imsDamageItemList, 'out', form.warehouseId, form.enterpriseId) }}
                </div>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="成本单价" prop="costPrice" >
            <template slot-scope="scope">
              <el-input v-model="scope.row.costPrice" placeholder="最多两位小数" @input="handleDetailInput(scope.row)" />
            </template>
          </el-table-column>
          <el-table-column label="金额" prop="amount" >
            <template slot-scope="scope">
              <el-input v-model="scope.row.amount" disabled placeholder="自动计算" />
            </template>
          </el-table-column>
        </el-table>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listIms_damage, getIms_damage, delIms_damage, addIms_damage, updateIms_damage } from "@/api/ims/ims_damage"
import Ims_damageViewDrawer from "./view"
import referenceData from "@/views/ims/mixins/referenceData"
import amountCalc from "@/views/ims/mixins/amountCalc"
import listActions from "@/views/ims/mixins/listActions"

export default {
  mixins: [referenceData, amountCalc, listActions],
  name: "Ims_damage",
  components: { Ims_damageViewDrawer },
  dicts: ['ims_bill_status'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedImsDamageItem: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 报损单表格数据
      ims_damageList: [],
      // 报损明细表格数据
      imsDamageItemList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      dateRangeBillDate: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        billNo: null,
        enterpriseId: null,
        warehouseId: null,
        billDate: null,
        billStatus: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        billNo: [
          { required: true, message: "报损单号不能为空", trigger: "blur" }
        ],
        enterpriseId: [
          { required: true, message: "企业名称不能为空", trigger: "blur" }
        ],
        warehouseId: [
          { required: true, message: "仓库名称不能为空", trigger: "blur" }
        ],
        billDate: [
          { required: true, message: "报损日期不能为空", trigger: "blur" }
        ],
        totalQtyPiece: [
          { required: true, message: "总数量(片)不能为空", trigger: "blur" }
        ],
        totalAmount: [
          { required: true, message: "总金额(按成本)不能为空", trigger: "blur" }
        ],
        billStatus: [
          { required: true, message: "单据状态不能为空", trigger: "change" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询报损单列表 */
    getList() {
      this.loading = true
      let query = this.queryParams
      query = this.addDateRange(query, this.dateRangeBillDate, 'BillDate')
      listIms_damage(query).then(response => {
        this.ims_damageList = response.rows
        this.total = response.total
        this.loading = false
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
        id: null,
        billNo: null,
        enterpriseId: null,
        warehouseId: null,
        billDate: null,
        reason: null,
        totalQtyPiece: null,
        totalAmount: null,
        billStatus: '0',
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      }
      this.imsDamageItemList = []
      this.resetForm("form")
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm")
      this.dateRangeBillDate = []
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.setSelectionRows(selection)
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加报损单"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getIms_damage(id).then(response => {
        this.form = response.data
        this.imsDamageItemList = response.data.imsDamageItemList || []
        this.recalcDetailTotals(this.imsDamageItemList, 'costPrice')
        this.open = true
        this.title = "修改报损单"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.recalcDetailTotals(this.imsDamageItemList, 'costPrice')
          this.form.imsDamageItemList = this.imsDamageItemList
          if (this.form.id != null) {
            updateIms_damage(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
              this.refreshInventoryOptions && this.refreshInventoryOptions()
            })
          } else {
            addIms_damage(this.form).then(response => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
              this.refreshInventoryOptions && this.refreshInventoryOptions()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      this.confirmDeleteData(row, { label: "报损单", nameKey: "billNo" }, delIms_damage).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 报损明细添加按钮操作 */
    handleAddImsDamageItem() {
      let obj = {}
      obj.productId = ""
      obj.qtyPiece = ""
      obj.costPrice = ""
      obj.amount = ""
      obj.remark = ""
      this.imsDamageItemList.push(obj)
      this.recalcDetailTotals(this.imsDamageItemList, 'costPrice')
    },
    /** 报损明细删除按钮操作 */
    handleDeleteImsDamageItem() {
      if (this.checkedImsDamageItem.length == 0) {
        this.$modal.msgError("请先选择要删除的报损明细数据")
      } else {
        const imsDamageItemList = this.imsDamageItemList
        const checkedImsDamageItem = this.checkedImsDamageItem
        this.imsDamageItemList = imsDamageItemList.filter(function(item) {
          return checkedImsDamageItem.indexOf(item.index) == -1
        })
        this.checkedImsDamageItem = []
        this.$refs.imsDamageItem && this.$refs.imsDamageItem.clearSelection()
        this.recalcDetailTotals(this.imsDamageItemList, 'costPrice')
      }
    },
    handleDetailInput(row) {
      this.recalcLineAmount(row, 'costPrice')
      this.recalcDetailTotals(this.imsDamageItemList, 'costPrice')
    },
    /** 复选框选中数据 */
    handleImsDamageItemSelectionChange(selection) {
      this.checkedImsDamageItem = selection.map(item => item.index)
    },
    /** 详情按钮操作 */
    handleViewData(row) {
      this.$refs["ims_damageViewRef"].open(row.id)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('ims/ims_damage/export', {
        ...this.queryParams
      }, `ims_damage_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
