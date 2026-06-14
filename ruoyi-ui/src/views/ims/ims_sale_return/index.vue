<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="auto">
      <el-form-item label="退货单号" prop="billNo">
        <el-input
          v-model="queryParams.billNo"
          placeholder="请输入退货单号"
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
      <el-form-item label="原出库单号" prop="sourceOutboundNo">
        <el-input
          v-model="queryParams.sourceOutboundNo"
          placeholder="请输入原出库单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
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

      <el-form-item label="退货日期" prop="billDate">
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
          v-hasPermi="['ims:ims_sale_return:add']"
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
          v-hasPermi="['ims:ims_sale_return:edit']"
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
          v-hasPermi="['ims:ims_sale_return:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['ims:ims_sale_return:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="ims_sale_returnList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" width="80">
        <template slot-scope="scope">
          <span>{{ (queryParams.pageNum - 1) * queryParams.pageSize + scope.$index + 1 }}</span>
        </template>
      </el-table-column>
      <el-table-column label="退货单号" align="center" prop="billNo" />
      <el-table-column label="退货日期" align="center" prop="billDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.billDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="企业名称" align="center" prop="enterpriseId">
        <template slot-scope="scope">
          <span>{{ enterpriseName(scope.row.enterpriseId) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="客户名称" align="center" prop="customerId">
        <template slot-scope="scope">
          <span>{{ customerName(scope.row.customerId) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="仓库名称" align="center" prop="warehouseId">
        <template slot-scope="scope">
          <span>{{ warehouseName(scope.row.warehouseId) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="原出库单号" align="center" prop="sourceOutboundNo" />
      <el-table-column label="退货原因" align="center" prop="reason" />
      <el-table-column label="总数量(片)" align="center" prop="totalQtyPiece" />
      <el-table-column label="总金额(按退货价)" align="center" prop="totalAmount" />
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
            v-hasPermi="['ims:ims_sale_return:query']"
          >详情</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['ims:ims_sale_return:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['ims:ims_sale_return:remove']"
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

    <!-- 销售退货单详情抽屉 -->
    <ims_sale_return-view-drawer ref="ims_sale_returnViewRef" />
    <!-- 添加或修改销售退货单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="900px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="退货单号" prop="billNo">
              <el-input v-model="form.billNo" placeholder="请输入退货单号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="退货日期" prop="billDate">
              <el-date-picker clearable
                v-model="form.billDate"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择退货日期" style="width: 100%">
              </el-date-picker>
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
            <el-form-item label="原出库单号" prop="sourceOutboundNo">
              <el-select
                v-model="form.sourceOutboundNo"
                placeholder="请选择原出库单号"
                clearable
                filterable
                style="width: 100%"
                @change="handleSourceOutboundChange"
              >
                <el-option
                  v-for="item in outboundOptions"
                  :key="item.id"
                  :label="outboundOptionLabel(item)"
                  :value="item.billNo"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="退货原因" prop="reason">
              <el-input v-model="form.reason" placeholder="请输入退货原因" />
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
        <el-divider content-position="center">销售退货明细信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddImsSaleReturnItem">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteImsSaleReturnItem">删除</el-button>
          </el-col>
        </el-row>
        <el-table class="sale-return-detail-table" :data="imsSaleReturnItemList" @selection-change="handleImsSaleReturnItemSelectionChange" ref="imsSaleReturnItem" width="100%">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="产品名称" prop="productId" width="170">
            <template slot-scope="scope">
              <el-select class="detail-control" v-model="scope.row.productId" placeholder="请选择产品名称" clearable filterable @change="handleDetailProductChange(scope.row)">
                <el-option
                  v-for="item in saleReturnProductOptions"
                  :key="item.id"
                  :label="productName(item.id)"
                  :value="item.id"
                />
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="数量(片)" prop="qtyPiece" >
            <template slot-scope="scope">
              <div class="qty-stock-cell detail-control">
                <el-input v-model="scope.row.qtyPiece" placeholder="请输入正整数" @input="handleDetailInput(scope.row)" />
                <div class="stock-hint">购买数量：{{ sourcePurchaseQty(scope.row) }}</div>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="退货单价" prop="unitPrice" >
            <template slot-scope="scope">
              <el-input class="detail-control" v-model="scope.row.unitPrice" placeholder="最多两位小数" @input="handleDetailInput(scope.row)" />
            </template>
          </el-table-column>
          <el-table-column label="金额" prop="amount" >
            <template slot-scope="scope">
              <el-input class="detail-control" v-model="scope.row.amount" disabled placeholder="自动计算" />
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
import { listIms_sale_return, getIms_sale_return, delIms_sale_return, addIms_sale_return, updateIms_sale_return } from "@/api/ims/ims_sale_return"
import { listIms_outbound, getIms_outbound } from "@/api/ims/ims_outbound"
import Ims_sale_returnViewDrawer from "./view"
import referenceData from "@/views/ims/mixins/referenceData"
import amountCalc from "@/views/ims/mixins/amountCalc"
import listActions from "@/views/ims/mixins/listActions"

export default {
  mixins: [referenceData, amountCalc, listActions],
  name: "Ims_sale_return",
  components: { Ims_sale_returnViewDrawer },
  dicts: ['ims_bill_status'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedImsSaleReturnItem: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 销售退货单表格数据
      ims_sale_returnList: [],
      // 原出库单下拉数据
      outboundOptions: [],
      // 当前原出库单明细数据
      sourceOutboundItemList: [],
      // 销售退货明细表格数据
      imsSaleReturnItemList: [],
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
        billDate: null,
        enterpriseId: null,
        customerId: null,
        warehouseId: null,
        sourceOutboundNo: null,
        billStatus: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        billNo: [
          { required: true, message: "退货单号不能为空", trigger: "blur" }
        ],
        billDate: [
          { required: true, message: "退货日期不能为空", trigger: "blur" }
        ],
        enterpriseId: [
          { required: true, message: "企业名称不能为空", trigger: "blur" }
        ],
        customerId: [
          { required: true, message: "客户名称不能为空", trigger: "blur" }
        ],
        warehouseId: [
          { required: true, message: "退货入库仓库名称不能为空", trigger: "blur" }
        ],
        sourceOutboundNo: [
          { required: true, message: "原出库单号不能为空", trigger: "change" }
        ],
        totalQtyPiece: [
          { required: true, message: "总数量(片)不能为空", trigger: "blur" }
        ],
        totalAmount: [
          { required: true, message: "总金额(按退货价)不能为空", trigger: "blur" }
        ],
        billStatus: [
          { required: true, message: "单据状态不能为空", trigger: "change" }
        ],
      }
    }
  },
  computed: {
    saleReturnProductOptions() {
      if (!this.form || !this.form.sourceOutboundNo || !this.sourceOutboundItemList.length) {
        return this.productOptions || []
      }
      const productIds = new Set(this.sourceOutboundItemList.map(item => String(item.productId)))
      return (this.productOptions || []).filter(item => productIds.has(String(item.id)))
    }
  },
  created() {
    this.getList()
    this.loadOutboundOptions()
  },
  methods: {
    /** 查询销售退货单列表 */
    getList() {
      this.loading = true
      let query = this.queryParams
      query = this.addDateRange(query, this.dateRangeBillDate, 'BillDate')
      listIms_sale_return(query).then(response => {
        this.ims_sale_returnList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    /** 查询可选择的出库单列表 */
    loadOutboundOptions() {
      listIms_outbound({
        pageNum: 1,
        pageSize: 1000
      }).then(response => {
        this.outboundOptions = response.rows || []
      }).catch(() => {
        this.outboundOptions = []
      })
    },
    outboundOptionLabel(item) {
      const parts = [
        item.billNo,
        this.parseTime(item.billDate, '{y}-{m}-{d}'),
        this.customerName(item.customerId)
      ].filter(Boolean)
      return parts.join(' / ')
    },
    /** 选择原出库单后自动带出销售退货单信息 */
    handleSourceOutboundChange(billNo) {
      if (!billNo) {
        this.sourceOutboundItemList = []
        this.imsSaleReturnItemList = []
        this.recalcDetailTotals(this.imsSaleReturnItemList)
        return
      }
      const outbound = this.outboundOptions.find(item => item.billNo === billNo)
      if (!outbound) {
        return
      }
      getIms_outbound(outbound.id).then(response => {
        const data = response.data || outbound
        this.sourceOutboundItemList = data.imsOutboundItemList || []
        this.form.sourceOutboundNo = data.billNo || billNo
        this.form.enterpriseId = data.enterpriseId
        this.form.customerId = data.customerId
        this.form.warehouseId = data.warehouseId
        if (!this.form.remark && data.remark) {
          this.form.remark = data.remark
        }
        this.imsSaleReturnItemList = this.sourceOutboundItemList.map(item => ({
          productId: item.productId,
          qtyPiece: item.qtyPiece,
          sourceQtyPiece: item.qtyPiece,
          unitPrice: item.unitPrice,
          amount: item.amount,
          remark: item.remark
        }))
        this.recalcDetailTotals(this.imsSaleReturnItemList)
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
        enterpriseId: null,
        customerId: null,
        warehouseId: null,
        sourceOutboundNo: null,
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
      this.sourceOutboundItemList = []
      this.imsSaleReturnItemList = []
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
      this.title = "添加销售退货单"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getIms_sale_return(id).then(response => {
        this.form = response.data
        this.imsSaleReturnItemList = response.data.imsSaleReturnItemList || []
        this.loadSourceOutboundDetail().finally(() => {
          this.recalcDetailTotals(this.imsSaleReturnItemList)
          this.open = true
          this.title = "修改销售退货单"
        })
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.recalcDetailTotals(this.imsSaleReturnItemList)
          this.form.imsSaleReturnItemList = this.imsSaleReturnItemList
          if (!this.validateSaleReturnQtyBeforeSubmit()) {
            return
          }
          if (this.form.id != null) {
            updateIms_sale_return(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
              this.refreshInventoryOptions && this.refreshInventoryOptions()
            })
          } else {
            addIms_sale_return(this.form).then(response => {
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
      this.confirmDeleteData(row, { label: "销售退货单", nameKey: "billNo" }, delIms_sale_return).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 销售退货明细添加按钮操作 */
    handleAddImsSaleReturnItem() {
      let obj = {}
      obj.productId = ""
      obj.qtyPiece = ""
      obj.sourceQtyPiece = ""
      obj.unitPrice = ""
      obj.amount = ""
      obj.remark = ""
      this.imsSaleReturnItemList.push(obj)
      this.recalcDetailTotals(this.imsSaleReturnItemList)
    },
    /** 销售退货明细删除按钮操作 */
    handleDeleteImsSaleReturnItem() {
      if (this.checkedImsSaleReturnItem.length == 0) {
        this.$modal.msgError("请先选择要删除的销售退货明细数据")
      } else {
        const imsSaleReturnItemList = this.imsSaleReturnItemList
        const checkedImsSaleReturnItem = this.checkedImsSaleReturnItem
        this.imsSaleReturnItemList = imsSaleReturnItemList.filter(function(item) {
          return checkedImsSaleReturnItem.indexOf(item.index) == -1
        })
        this.checkedImsSaleReturnItem = []
        this.$refs.imsSaleReturnItem && this.$refs.imsSaleReturnItem.clearSelection()
        this.recalcDetailTotals(this.imsSaleReturnItemList)
      }
    },
    handleDetailInput(row) {
      this.limitSaleReturnQty(row)
      this.recalcLineAmount(row)
      this.recalcDetailTotals(this.imsSaleReturnItemList)
    },
    handleDetailProductChange(row) {
      const sourceItem = this.findSourceOutboundItem(row.productId)
      if (sourceItem) {
        row.sourceQtyPiece = sourceItem.qtyPiece
        if (!row.unitPrice) {
          row.unitPrice = sourceItem.unitPrice
        }
      } else {
        row.sourceQtyPiece = ""
      }
      this.handleDetailInput(row)
    },
    loadSourceOutboundDetail() {
      if (!this.form.sourceOutboundNo) {
        this.sourceOutboundItemList = []
        return Promise.resolve()
      }
      const outbound = this.outboundOptions.find(item => item.billNo === this.form.sourceOutboundNo)
      const loadDetail = source => getIms_outbound(source.id).then(response => {
        const sourceItems = (response.data && response.data.imsOutboundItemList) || []
        this.sourceOutboundItemList = sourceItems
        ;(this.imsSaleReturnItemList || []).forEach(row => {
          const sourceItem = sourceItems.find(item => String(item.productId) === String(row.productId))
          if (sourceItem) {
            row.sourceQtyPiece = sourceItem.qtyPiece
          }
        })
      })
      if (outbound) {
        return loadDetail(outbound)
      }
      return listIms_outbound({
        pageNum: 1,
        pageSize: 1,
        billNo: this.form.sourceOutboundNo
      }).then(response => {
        const source = response.rows && response.rows[0]
        if (!source) {
          this.sourceOutboundItemList = []
          return
        }
        return loadDetail(source)
      })
    },
    findSourceOutboundItem(productId) {
      if (!this.form.sourceOutboundNo || !productId) {
        return null
      }
      return (this.sourceOutboundItemList || []).find(item => String(item.productId) === String(productId)) || null
    },
    sourcePurchaseQtyNumber(productId) {
      if (!this.form.sourceOutboundNo || !productId) {
        return NaN
      }
      return (this.sourceOutboundItemList || []).reduce((total, item) => {
        if (String(item.productId) !== String(productId)) {
          return total
        }
        return total + Number(item.qtyPiece || 0)
      }, 0)
    },
    sourcePurchaseQty(row) {
      const sourceQty = this.sourcePurchaseQtyNumber(row && row.productId)
      if (Number.isFinite(sourceQty) && sourceQty > 0) {
        return sourceQty
      }
      if (row && row.sourceQtyPiece !== undefined && row.sourceQtyPiece !== null && row.sourceQtyPiece !== "") {
        return row.sourceQtyPiece
      }
      return "--"
    },
    limitSaleReturnQty(row) {
      if (!row) {
        return
      }
      row.qtyPiece = this.normalizePositiveInteger(row.qtyPiece)
      const maxQty = Number(this.sourcePurchaseQty(row))
      const currentQty = Number(row.qtyPiece || 0)
      if (Number.isFinite(maxQty) && maxQty >= 0 && currentQty > maxQty) {
        row.qtyPiece = String(maxQty)
        this.$modal.msgError("退货数量不能超过原出库单对应产品的购买数量")
      }
    },
    validateSaleReturnQtyBeforeSubmit() {
      const qtyMap = {}
      const sourceQtyMap = {}
      ;(this.imsSaleReturnItemList || []).forEach(item => {
        if (!item.productId) {
          return
        }
        const key = String(item.productId)
        qtyMap[key] = (qtyMap[key] || 0) + Number(item.qtyPiece || 0)
        const sourceQty = Number(this.sourcePurchaseQty(item))
        if (Number.isFinite(sourceQty)) {
          sourceQtyMap[key] = Math.max(sourceQtyMap[key] || 0, sourceQty)
        }
      })
      for (const productId of Object.keys(qtyMap)) {
        if (qtyMap[productId] > (sourceQtyMap[productId] || 0)) {
          this.$modal.msgError(`${this.productName(productId)}退货数量不能超过原出库购买数量`)
          return false
        }
      }
      return true
    },
    /** 复选框选中数据 */
    handleImsSaleReturnItemSelectionChange(selection) {
      this.checkedImsSaleReturnItem = selection.map(item => item.index)
    },
    /** 详情按钮操作 */
    handleViewData(row) {
      this.$refs["ims_sale_returnViewRef"].open(row.id)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('ims/ims_sale_return/export', {
        ...this.queryParams
      }, `ims_sale_return_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>

<style scoped>
.sale-return-detail-table .detail-control {
  width: 130px;
}

.sale-return-detail-table /deep/ .el-table__cell {
  vertical-align: top;
}

.sale-return-detail-table /deep/ .cell {
  display: flex;
  justify-content: center;
  align-items: flex-start;
  min-height: 54px;
}

.sale-return-detail-table .qty-stock-cell {
  align-items: stretch;
}

.sale-return-detail-table .stock-hint {
  width: 130px;
  overflow: hidden;
  text-overflow: ellipsis;
}
</style>
