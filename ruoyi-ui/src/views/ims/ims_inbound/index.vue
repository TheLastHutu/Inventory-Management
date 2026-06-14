<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="auto">
      <el-form-item label="入库单号" prop="billNo" >
        <el-input
          v-model="queryParams.billNo"
          placeholder="请输入入库单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="入库类型" prop="inType">
        <el-select v-model="queryParams.inType" placeholder="请选择入库类型" clearable>
          <el-option
            v-for="dict in dict.type.ims_inbound"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
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
      <el-form-item label="厂家名称" prop="supplierId" >
        <el-select v-model="queryParams.supplierId" placeholder="请选择厂家名称" clearable filterable>
                <el-option
                  v-for="item in supplierOptions"
                  :key="item.id"
                  :label="supplierName(item.id)"
                  :value="item.id"
                />
              </el-select>
      </el-form-item>
      <el-form-item label="客户名称" prop="customerId">
        <el-select v-model="queryParams.customerId" placeholder="请选择客户名称" clearable filterable>
                <el-option
                  v-for="item in customerOptions"
                  :key="item.id"
                  :label="customerName(item.id)"
                  :value="item.id"
                />
              </el-select>
      </el-form-item>
      <el-form-item label="经手人" prop="handlerId">
        <el-select v-model="queryParams.handlerId" placeholder="请选择经手人" clearable filterable>
                <el-option
                  v-for="item in employeeOptions"
                  :key="item.id"
                  :label="employeeName(item.id)"
                  :value="item.id"
                />
              </el-select>
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

      <el-form-item label="入库日期" prop="billDate">
        <el-date-picker clearable
                        v-model="dateRangeBillDate"
                        type="daterange"
                        value-format="yyyy-MM-dd"
                        range-separator="-"
                        start-placeholder="开始日期"
                        end-placeholder="结束日期">
        </el-date-picker>
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
          v-hasPermi="['ims:ims_inbound:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single || hasApprovedSelected"
          @click="handleUpdate"
          v-hasPermi="['ims:ims_inbound:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple || hasApprovedSelected"
          @click="handleDelete"
          v-hasPermi="['ims:ims_inbound:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['ims:ims_inbound:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="ims_inboundList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" width="80">
        <template slot-scope="scope">
          <span>{{ (queryParams.pageNum - 1) * queryParams.pageSize + scope.$index + 1 }}</span>
        </template>
      </el-table-column>
      <el-table-column label="入库单号" align="center" prop="billNo" />
      <el-table-column label="入库日期" align="center" prop="billDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.billDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="入库类型" align="center" prop="inType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.ims_inbound" :value="scope.row.inType"/>
        </template>
      </el-table-column>
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
      <el-table-column label="厂家名称" align="center" prop="supplierId">
        <template slot-scope="scope">
          <span>{{ supplierName(scope.row.supplierId) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="客户名称" align="center" prop="customerId">
        <template slot-scope="scope">
          <span>{{ customerName(scope.row.customerId) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="经手人" align="center" prop="handlerId">
        <template slot-scope="scope">
          <span>{{ employeeName(scope.row.handlerId) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="总数量(片)" align="center" prop="totalQtyPiece" />
      <el-table-column label="总金额" align="center" prop="totalAmount" />
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
            v-hasPermi="['ims:ims_inbound:query']"
          >详情</el-button>
          <el-button
            v-if="!isApprovedBill(scope.row)"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['ims:ims_inbound:edit']"
          >修改</el-button>
          <el-button
            v-if="!isApprovedBill(scope.row)"
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['ims:ims_inbound:remove']"
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

    <!-- 入库单详情抽屉 -->
    <ims_inbound-view-drawer ref="ims_inboundViewRef" />
    <!-- 添加或修改入库单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="900px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
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
              <el-select v-model="form.warehouseId" placeholder="请选择仓库名称" clearable filterable style="width: 100%">
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
            <el-form-item label="入库单号" prop="billNo">
              <el-input v-model="form.billNo" placeholder="请输入入库单号" maxlength="30" show-word-limit/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="入库日期" prop="billDate">
              <el-date-picker clearable
                v-model="form.billDate"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择入库日期" style="width: 100%">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="入库类型" prop="inType">
              <el-select v-model="form.inType" placeholder="请选择入库类型" style="width: 100%">
                <el-option
                  v-for="dict in dict.type.ims_inbound"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="厂家名称" prop="supplierId">
              <el-select v-model="form.supplierId" placeholder="请选择厂家名称(采购入库用)" clearable filterable style="width: 100%">
                <el-option
                  v-for="item in supplierOptions"
                  :key="item.id"
                  :label="supplierName(item.id)"
                  :value="item.id"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="客户名称" prop="customerId">
              <el-select v-model="form.customerId" placeholder="请选择客户名称" clearable filterable style="width: 100%">
                <el-option
                  v-for="item in customerOptions"
                  :key="item.id"
                  :label="customerName(item.id)"
                  :value="item.id"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="经手人" prop="handlerId">
              <el-select v-model="form.handlerId" placeholder="请选择经手人" clearable filterable style="width: 100%">
                <el-option
                  v-for="item in employeeOptions"
                  :key="item.id"
                  :label="employeeName(item.id)"
                  :value="item.id"
                />
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="单据状态" prop="billStatus">
              <el-select v-model="form.billStatus" placeholder="请选择单据状态" style="width: 100%">
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
        <el-divider content-position="center">入库单明细信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddImsInboundItem">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteImsInboundItem">删除</el-button>
          </el-col>
        </el-row>
        <el-table :data="imsInboundItemList" @selection-change="handleImsInboundItemSelectionChange" ref="imsInboundItem" width="100%" >
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="产品名称" prop="productId" width="300px">
            <template slot-scope="scope">
              <el-select
                v-model="scope.row.productId"
                placeholder="请选择产品名称"
                clearable
                filterable
                :disabled="!canSelectDetailProduct"
                @visible-change="handleDetailProductVisibleChange"
                @change="handleDetailProductChange(scope.row)"
              >
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
              <div class="qty-stock-cell"><el-input v-model="scope.row.qtyPiece" placeholder="请输入正整数" @input="handleDetailInput(scope.row)" /><div class="stock-hint">剩余库存：{{ stockAfterInput(scope.row, imsInboundItemList, 'in', form.warehouseId, form.enterpriseId) }}</div></div>
            </template>
          </el-table-column>
          <el-table-column label="单价" prop="unitPrice" >
            <template slot-scope="scope">
              <el-input v-model="scope.row.unitPrice" placeholder="最多两位小数" @input="handleDetailInput(scope.row)" />
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
import { listIms_inbound, getIms_inbound, delIms_inbound, addIms_inbound, updateIms_inbound } from "@/api/ims/ims_inbound"
import Ims_inboundViewDrawer from "./view"
import referenceData from "@/views/ims/mixins/referenceData"
import amountCalc from "@/views/ims/mixins/amountCalc"
import listActions from "@/views/ims/mixins/listActions"

export default {
  mixins: [referenceData, amountCalc, listActions],
  name: "Ims_inbound",
  components: { Ims_inboundViewDrawer },
  dicts: ['ims_bill_status', 'ims_inbound'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedImsInboundItem: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 入库单表格数据
      ims_inboundList: [],
      // 入库单明细表格数据
      imsInboundItemList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      loadingDetailData: false,
      dateRangeBillDate: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        billNo: null,
        billDate: null,
        inType: null,
        enterpriseId: null,
        warehouseId: null,
        supplierId: null,
        customerId: null,
        handlerId: null,
        billStatus: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        billNo: [
          { required: true, message: "入库单号不能为空", trigger: "blur" }
        ],
        billDate: [
          { required: true, message: "入库日期不能为空", trigger: "blur" }
        ],
        inType: [
          { required: true, message: "入库类型不能为空", trigger: "change" }
        ],
        enterpriseId: [
          { required: true, message: "企业名称不能为空", trigger: "blur" }
        ],
        warehouseId: [
          { required: true, message: "仓库名称不能为空", trigger: "blur" }
        ],
        totalQtyPiece: [
          { required: true, message: "总数量(片)不能为空", trigger: "blur" }
        ],
        totalAmount: [
          { required: true, message: "总金额不能为空", trigger: "blur" }
        ],
        billStatus: [
          { required: true, message: "单据状态不能为空", trigger: "change" }
        ],
      }
    }
  },
  computed: {
    hasApprovedSelected() {
      return (this.selectionRows || []).some(row => this.isApprovedBill(row))
    },
    canSelectDetailProduct() {
      return !!(this.form && this.form.enterpriseId && this.form.warehouseId)
    }
  },
  created() {
    this.getList()
  },
  watch: {
    "form.enterpriseId"() {
      if (!this.loadingDetailData) {
        this.clearInboundDetailProducts()
      }
    },
    "form.warehouseId"() {
      if (!this.loadingDetailData) {
        this.clearInboundDetailProducts()
      }
    }
  },
  methods: {
    isApprovedBill(row) {
      return row && String(row.billStatus) === '1'
    },
    /** 查询入库单列表 */
    getList() {
      this.loading = true
      let query = this.queryParams
      query = this.addDateRange(query, this.dateRangeBillDate, 'BillDate')
      listIms_inbound(query).then(response => {
        this.ims_inboundList = response.rows
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
        billDate: null,
        inType: null,
        enterpriseId: null,
        warehouseId: null,
        supplierId: null,
        customerId: null,
        handlerId: null,
        totalQtyPiece: null,
        totalAmount: null,
        billStatus: '0',
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      }
      this.imsInboundItemList = []
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
      this.title = "添加入库单"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      this.loadingDetailData = true
      getIms_inbound(id).then(response => {
        this.form = response.data
        this.imsInboundItemList = response.data.imsInboundItemList || []
        this.recalcDetailTotals(this.imsInboundItemList)
        this.open = true
        this.title = "修改入库单"
      }).finally(() => {
        this.$nextTick(() => {
          this.loadingDetailData = false
        })
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.recalcDetailTotals(this.imsInboundItemList)
          this.form.imsInboundItemList = this.imsInboundItemList
          if (this.form.id != null) {
            updateIms_inbound(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
              this.refreshInventoryOptions && this.refreshInventoryOptions()
            })
          } else {
            addIms_inbound(this.form).then(response => {
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
      this.confirmDeleteData(row, { label: "入库单", nameKey: "billNo" }, delIms_inbound).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 入库单明细添加按钮操作 */
    handleAddImsInboundItem() {
      if (!this.canSelectDetailProduct) {
        this.$modal.msgWarning("请先选择企业和仓库")
        return
      }
      let obj = {}
      obj.productId = ""
      obj.qtyPiece = ""
      obj.unitPrice = ""
      obj.amount = ""
      obj.remark = ""
      this.imsInboundItemList.push(obj)
      this.recalcDetailTotals(this.imsInboundItemList)
    },
    /** 入库单明细删除按钮操作 */
    handleDeleteImsInboundItem() {
      if (this.checkedImsInboundItem.length == 0) {
        this.$modal.msgError("请先选择要删除的入库单明细数据")
      } else {
        const imsInboundItemList = this.imsInboundItemList
        const checkedImsInboundItem = this.checkedImsInboundItem
        this.imsInboundItemList = imsInboundItemList.filter(function(item) {
          return checkedImsInboundItem.indexOf(item.index) == -1
        })
        this.checkedImsInboundItem = []
        this.$refs.imsInboundItem && this.$refs.imsInboundItem.clearSelection()
        this.recalcDetailTotals(this.imsInboundItemList)
      }
    },
    handleDetailProductVisibleChange(visible) {
      if (visible && !this.canSelectDetailProduct) {
        this.$modal.msgWarning("请先选择企业和仓库")
      }
    },
    handleDetailProductChange(row) {
      this.handleDetailInput(row)
    },
    clearInboundDetailProducts() {
      ;(this.imsInboundItemList || []).forEach(item => {
        item.productId = ""
        item.qtyPiece = ""
        item.amount = ""
      })
      this.recalcDetailTotals(this.imsInboundItemList)
    },
    handleDetailInput(row) {
      this.recalcLineAmount(row)
      this.recalcDetailTotals(this.imsInboundItemList)
    },
    /** 复选框选中数据 */
    handleImsInboundItemSelectionChange(selection) {
      this.checkedImsInboundItem = selection.map(item => item.index)
    },
    /** 详情按钮操作 */
    handleViewData(row) {
      this.$refs["ims_inboundViewRef"].open(row.id)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('ims/ims_inbound/export', {
        ...this.queryParams
      }, `ims_inbound_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
