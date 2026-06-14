<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="auto">
      <el-form-item label="出库单号" prop="billNo">
        <el-input
          v-model="queryParams.billNo"
          placeholder="请输入出库单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="出库类型" prop="outType">
        <el-select v-model="queryParams.outType" placeholder="请选择出库类型" clearable>
          <el-option
            v-for="dict in dict.type.ims_outbound"
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

      <el-form-item label="出库日期" prop="billDate">
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
          v-hasPermi="['ims:ims_outbound:add']"
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
          v-hasPermi="['ims:ims_outbound:edit']"
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
          v-hasPermi="['ims:ims_outbound:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['ims:ims_outbound:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="ims_outboundList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" width="80">
        <template slot-scope="scope">
          <span>{{ (queryParams.pageNum - 1) * queryParams.pageSize + scope.$index + 1 }}</span>
        </template>
      </el-table-column>
      <el-table-column label="出库单号" align="center" prop="billNo" />
      <el-table-column label="出库日期" align="center" prop="billDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.billDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="出库类型" align="center" prop="outType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.ims_outbound" :value="scope.row.outType"/>
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
            v-hasPermi="['ims:ims_outbound:query']"
          >详情</el-button>
          <el-button
            v-if="!isApprovedBill(scope.row)"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['ims:ims_outbound:edit']"
          >修改</el-button>
          <el-button
            v-if="!isApprovedBill(scope.row)"
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['ims:ims_outbound:remove']"
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

    <!-- 出库单详情抽屉 -->
    <ims_outbound-view-drawer ref="ims_outboundViewRef" />
    <!-- 添加或修改出库单对话框 -->
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
            <el-form-item label="出库单号" prop="billNo">
              <el-input v-model="form.billNo" placeholder="请输入出库单号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="出库日期" prop="billDate">
              <el-date-picker clearable
                v-model="form.billDate"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择出库日期" style="width: 100%">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="出库类型" prop="outType">
              <el-select v-model="form.outType" placeholder="请选择出库类型" style="width: 100%">
                <el-option
                  v-for="dict in dict.type.ims_outbound"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
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
        <el-divider content-position="center">出库单明细信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddImsOutboundItem">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteImsOutboundItem">删除</el-button>
          </el-col>
        </el-row>
        <el-table :data="imsOutboundItemList" @selection-change="handleImsOutboundItemSelectionChange" ref="imsOutboundItem" width="100%">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="产品名称" prop="productId" width="250">
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
                  v-for="item in outboundProductOptions"
                  :key="item.id"
                  :label="productName(item.id)"
                  :value="item.id"
                />
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="数量(片)" prop="qtyPiece" >
            <template slot-scope="scope">
              <div class="qty-stock-cell"><el-input v-model="scope.row.qtyPiece" placeholder="请输入正整数"  @input="handleDetailInput(scope.row)" /><div class="stock-hint">剩余库存：{{ outboundStockAfterInput(scope.row) }}</div></div>
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
import { listIms_outbound, getIms_outbound, delIms_outbound, addIms_outbound, updateIms_outbound } from "@/api/ims/ims_outbound"
import Ims_outboundViewDrawer from "./view"
import referenceData from "@/views/ims/mixins/referenceData"
import amountCalc from "@/views/ims/mixins/amountCalc"
import listActions from "@/views/ims/mixins/listActions"

export default {
  mixins: [referenceData, amountCalc, listActions],
  name: "Ims_outbound",
  components: { Ims_outboundViewDrawer },
  dicts: ['ims_bill_status','ims_outbound'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedImsOutboundItem: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 出库单表格数据
      ims_outboundList: [],
      // 出库单明细表格数据
      imsOutboundItemList: [],
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
        outType: null,
        enterpriseId: null,
        warehouseId: null,
        customerId: null,
        handlerId: null,
        billStatus: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        billNo: [
          { required: true, message: "出库单号不能为空", trigger: "blur" }
        ],
        billDate: [
          { required: true, message: "出库日期不能为空", trigger: "blur" }
        ],
        outType: [
          { required: true, message: "出库类型不能为空", trigger: "change" }
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
    },
    outboundProductOptions() {
      if (!this.canSelectDetailProduct) {
        return []
      }
      const productIds = new Set()
      ;(this.inventoryOptions || []).forEach(item => {
        const sameEnterprise = String(item.enterpriseId) === String(this.form.enterpriseId)
        const sameWarehouse = String(item.warehouseId) === String(this.form.warehouseId)
        const availableQty = Number(item.availQtyPiece || item.qtyPiece || 0)
        if (sameEnterprise && sameWarehouse && availableQty > 0 && item.productId) {
          productIds.add(String(item.productId))
        }
      })
      return (this.productOptions || []).filter(item => productIds.has(String(item.id)))
    }
  },
  created() {
    this.getList()
  },
  watch: {
    "form.enterpriseId"() {
      if (!this.loadingDetailData) {
        this.clearOutboundDetailProducts()
      }
    },
    "form.warehouseId"() {
      if (!this.loadingDetailData) {
        this.clearOutboundDetailProducts()
      }
    }
  },
  methods: {
    isApprovedBill(row) {
      return row && String(row.billStatus) === '1'
    },
    /** 查询出库单列表 */
    getList() {
      this.loading = true
      let query = this.queryParams
      query = this.addDateRange(query, this.dateRangeBillDate, 'BillDate')
      listIms_outbound(query).then(response => {
        this.ims_outboundList = response.rows
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
        outType: null,
        enterpriseId: null,
        warehouseId: null,
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
      this.imsOutboundItemList = []
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
      this.title = "添加出库单"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      this.loadingDetailData = true
      getIms_outbound(id).then(response => {
        this.form = response.data
        this.imsOutboundItemList = response.data.imsOutboundItemList || []
        this.recalcDetailTotals(this.imsOutboundItemList)
        this.open = true
        this.title = "修改出库单"
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
          this.recalcDetailTotals(this.imsOutboundItemList)
          this.form.imsOutboundItemList = this.imsOutboundItemList
          if (!this.validateOutboundInventoryBeforeSubmit()) {
            return
          }
          if (this.form.id != null) {
            updateIms_outbound(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
              this.refreshInventoryOptions && this.refreshInventoryOptions()
            })
          } else {
            addIms_outbound(this.form).then(response => {
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
      this.confirmDeleteData(row, { label: "出库单", nameKey: "billNo" }, delIms_outbound).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 出库单明细添加按钮操作 */
    handleAddImsOutboundItem() {
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
      this.imsOutboundItemList.push(obj)
      this.recalcDetailTotals(this.imsOutboundItemList)
    },
    /** 出库单明细删除按钮操作 */
    handleDeleteImsOutboundItem() {
      if (this.checkedImsOutboundItem.length == 0) {
        this.$modal.msgError("请先选择要删除的出库单明细数据")
      } else {
        const imsOutboundItemList = this.imsOutboundItemList
        const checkedImsOutboundItem = this.checkedImsOutboundItem
        this.imsOutboundItemList = imsOutboundItemList.filter(function(item) {
          return checkedImsOutboundItem.indexOf(item.index) == -1
        })
        this.checkedImsOutboundItem = []
        this.$refs.imsOutboundItem && this.$refs.imsOutboundItem.clearSelection()
        this.recalcDetailTotals(this.imsOutboundItemList)
      }
    },
    handleDetailProductVisibleChange(visible) {
      if (visible && !this.canSelectDetailProduct) {
        this.$modal.msgWarning("请先选择企业和仓库")
      }
    },
    clearOutboundDetailProducts() {
      ;(this.imsOutboundItemList || []).forEach(item => {
        item.productId = ""
        item.qtyPiece = ""
        item.amount = ""
      })
      this.recalcDetailTotals(this.imsOutboundItemList)
    },
    handleDetailProductChange(row) {
      this.handleDetailInput(row)
    },
    outboundStockAfterInput(row) {
      if (!this.canSelectDetailProduct) {
        return "--"
      }
      return this.stockAfterInput(row, this.imsOutboundItemList, 'out', this.form.warehouseId, this.form.enterpriseId)
    },
    handleDetailInput(row) {
      this.recalcLineAmount(row)
      this.recalcDetailTotals(this.imsOutboundItemList)
      if (this.canSelectDetailProduct && row && row.productId && Number(this.outboundStockAfterInput(row)) < 0) {
        this.$modal.msgError("\u5e93\u5b58\u6570\u91cf\u4e0d\u8db3")
      }
    },
    validateOutboundInventoryBeforeSubmit() {
      if (!this.canSelectDetailProduct) {
        this.$modal.msgWarning("请先选择企业和仓库")
        return false
      }
      const qtyMap = {}
      ;(this.imsOutboundItemList || []).forEach(item => {
        if (!item.productId) {
          return
        }
        const key = String(item.productId)
        qtyMap[key] = (qtyMap[key] || 0) + Number(item.qtyPiece || 0)
      })
      for (const productId of Object.keys(qtyMap)) {
        const availableQty = Number(this.inventoryQty(productId, this.form.warehouseId, this.form.enterpriseId) || 0)
        if (qtyMap[productId] > availableQty) {
          this.$modal.msgError("\u5e93\u5b58\u6570\u91cf\u4e0d\u8db3")
          return false
        }
      }
      return true
    },
    /** 复选框选中数据 */
    handleImsOutboundItemSelectionChange(selection) {
      this.checkedImsOutboundItem = selection.map(item => item.index)
    },
    /** 详情按钮操作 */
    handleViewData(row) {
      this.$refs["ims_outboundViewRef"].open(row.id)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('ims/ims_outbound/export', {
        ...this.queryParams
      }, `ims_outbound_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
