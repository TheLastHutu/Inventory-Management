<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="auto">
      <el-form-item label="样品单号" prop="billNo">
        <el-input
          v-model="queryParams.billNo"
          placeholder="请输入样品单号"
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

      <el-form-item label="收货地点" prop="receiver">
        <el-input
          v-model="queryParams.receiver"
          placeholder="请输入收货地点"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="用途" prop="purpose">
        <el-input
          v-model="queryParams.purpose"
          placeholder="请输入用途"
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

      <el-form-item label="日期" prop="billDate">
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
          v-hasPermi="['ims:ims_sample:add']"
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
          v-hasPermi="['ims:ims_sample:edit']"
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
          v-hasPermi="['ims:ims_sample:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['ims:ims_sample:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="ims_sampleList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" width="80">
        <template slot-scope="scope">
          <span>{{ (queryParams.pageNum - 1) * queryParams.pageSize + scope.$index + 1 }}</span>
        </template>
      </el-table-column>
      <el-table-column label="样品单号" align="center" prop="billNo" />
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
      <el-table-column label="日期" align="center" prop="billDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.billDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="收货地点" align="center" prop="receiver" />
      <el-table-column label="用途" align="center" prop="purpose" />
      <el-table-column label="总数量(片)" align="center" prop="totalQtyPiece" />
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
            v-hasPermi="['ims:ims_sample:query']"
          >详情</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['ims:ims_sample:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['ims:ims_sample:remove']"
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

    <!-- 样品单详情抽屉 -->
    <ims_sample-view-drawer ref="ims_sampleViewRef" />
    <!-- 添加或修改样品单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="900px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="样品单号" prop="billNo">
              <el-input v-model="form.billNo" placeholder="请输入样品单号" />
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
            <el-form-item label="日期" prop="billDate">
              <el-date-picker clearable
                v-model="form.billDate"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择日期" style="width: 100%">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="收货地点" prop="receiver">
              <el-input v-model="form.receiver" placeholder="请输入收货地点" maxlength="30" show-word-limit/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="用途" prop="purpose">
              <el-input v-model="form.purpose" placeholder="请输入用途" maxlength="30" show-word-limit/>
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
        </el-row>
        <el-divider content-position="center">样品明细信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddImsSampleItem">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteImsSampleItem">删除</el-button>
          </el-col>
        </el-row>
        <el-table :data="imsSampleItemList" @selection-change="handleImsSampleItemSelectionChange" ref="imsSampleItem" style="width: 100%">
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
              <div class="qty-stock-cell"><el-input v-model="scope.row.qtyPiece" placeholder="请输入正整数" @input="handleDetailInput" /><div class="stock-hint">剩余库存：{{ stockAfterInput(scope.row, imsSampleItemList, 'out', form.warehouseId, form.enterpriseId) }}</div></div>
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
import { listIms_sample, getIms_sample, delIms_sample, addIms_sample, updateIms_sample } from "@/api/ims/ims_sample"
import Ims_sampleViewDrawer from "./view"
import referenceData from "@/views/ims/mixins/referenceData"
import amountCalc from "@/views/ims/mixins/amountCalc"
import listActions from "@/views/ims/mixins/listActions"

export default {
  mixins: [referenceData, amountCalc, listActions],
  name: "Ims_sample",
  components: { Ims_sampleViewDrawer },
  dicts: ['ims_bill_status'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedImsSampleItem: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 样品单表格数据
      ims_sampleList: [],
      // 样品明细表格数据
      imsSampleItemList: [],
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
        customerId: null,
        billDate: null,
        receiver: null,
        purpose: null,
        billStatus: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        billNo: [
          { required: true, message: "样品单号不能为空", trigger: "blur" }
        ],
        enterpriseId: [
          { required: true, message: "企业名称不能为空", trigger: "blur" }
        ],
        warehouseId: [
          { required: true, message: "仓库名称不能为空", trigger: "blur" }
        ],
        billDate: [
          { required: true, message: "日期不能为空", trigger: "blur" }
        ],
        totalQtyPiece: [
          { required: true, message: "总数量(片)不能为空", trigger: "blur" }
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
    /** 查询样品单列表 */
    getList() {
      this.loading = true
      let query = this.queryParams
      query = this.addDateRange(query, this.dateRangeBillDate, 'BillDate')
      listIms_sample(query).then(response => {
        this.ims_sampleList = response.rows
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
        customerId: null,
        billDate: null,
        receiver: null,
        purpose: null,
        totalQtyPiece: null,
        billStatus: '0',
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      }
      this.imsSampleItemList = []
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
      this.title = "添加样品单"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getIms_sample(id).then(response => {
        this.form = response.data
        this.imsSampleItemList = response.data.imsSampleItemList || []
        this.recalcDetailTotals(this.imsSampleItemList, 'unitPrice', false)
        this.open = true
        this.title = "修改样品单"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.recalcDetailTotals(this.imsSampleItemList, 'unitPrice', false)
          this.form.imsSampleItemList = this.imsSampleItemList
          if (this.form.id != null) {
            updateIms_sample(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
              this.refreshInventoryOptions && this.refreshInventoryOptions()
            })
          } else {
            addIms_sample(this.form).then(response => {
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
      this.confirmDeleteData(row, { label: "样品单", nameKey: "billNo" }, delIms_sample).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 样品明细添加按钮操作 */
    handleAddImsSampleItem() {
      let obj = {}
      obj.productId = ""
      obj.qtyPiece = ""
      obj.remark = ""
      this.imsSampleItemList.push(obj)
      this.recalcDetailTotals(this.imsSampleItemList, 'unitPrice', false)
    },
    /** 样品明细删除按钮操作 */
    handleDeleteImsSampleItem() {
      if (this.checkedImsSampleItem.length == 0) {
        this.$modal.msgError("请先选择要删除的样品明细数据")
      } else {
        const imsSampleItemList = this.imsSampleItemList
        const checkedImsSampleItem = this.checkedImsSampleItem
        this.imsSampleItemList = imsSampleItemList.filter(function(item) {
          return checkedImsSampleItem.indexOf(item.index) == -1
        })
        this.checkedImsSampleItem = []
        this.$refs.imsSampleItem && this.$refs.imsSampleItem.clearSelection()
        this.recalcDetailTotals(this.imsSampleItemList, 'unitPrice', false)
      }
    },
    handleDetailInput() {
      this.recalcDetailTotals(this.imsSampleItemList, 'unitPrice', false)
    },
    /** 复选框选中数据 */
    handleImsSampleItemSelectionChange(selection) {
      this.checkedImsSampleItem = selection.map(item => item.index)
    },
    /** 详情按钮操作 */
    handleViewData(row) {
      this.$refs["ims_sampleViewRef"].open(row.id)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('ims/ims_sample/export', {
        ...this.queryParams
      }, `ims_sample_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
