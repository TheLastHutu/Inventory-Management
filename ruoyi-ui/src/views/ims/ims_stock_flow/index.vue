<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="auto">
      <el-form-item label="业务单据编号" prop="bizNo" >
        <el-input
          v-model="queryParams.bizNo"
          placeholder="请输入业务单据编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="业务类型" prop="bizType">
        <el-select v-model="queryParams.bizType" placeholder="请选择业务类型" clearable>
          <el-option
            v-for="dict in dict.type.ims_business_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
<!--      <el-form-item label="业务单据ID" prop="bizId">-->
<!--        <el-input-->
<!--          v-model="queryParams.bizId"-->
<!--          placeholder="请输入业务单据ID"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
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
      <el-form-item label="货位名称" prop="locationId">
        <el-select v-model="queryParams.locationId" placeholder="请选择货位名称" clearable filterable>
                <el-option
                  v-for="item in locationOptions"
                  :key="item.id"
                  :label="locationName(item.id)"
                  :value="item.id"
                />
              </el-select>
      </el-form-item>
      <el-form-item label="产品名称" prop="productId">
        <el-select v-model="queryParams.productId" placeholder="请选择产品名称" clearable filterable>
                <el-option
                  v-for="item in productOptions"
                  :key="item.id"
                  :label="productName(item.id)"
                  :value="item.id"
                />
              </el-select>
      </el-form-item>

      <el-form-item label="方向" prop="inout">
        <el-select v-model="queryParams.inout" placeholder="请选择方向" clearable>
          <el-option
            v-for="dict in dict.type.ims_inout"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="发生时间" prop="occurTime">
        <el-date-picker clearable
                        v-model="dateRangeOccurTime"
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
          v-hasPermi="['ims:ims_stock_flow:add']"
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
          v-hasPermi="['ims:ims_stock_flow:edit']"
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
          v-hasPermi="['ims:ims_stock_flow:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['ims:ims_stock_flow:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="ims_stock_flowList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" width="80">
        <template slot-scope="scope">
          <span>{{ (queryParams.pageNum - 1) * queryParams.pageSize + scope.$index + 1 }}</span>
        </template>
      </el-table-column>
      <el-table-column label="业务单据编号" align="center" prop="bizNo" />
      <el-table-column label="业务类型" align="center" prop="bizType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.ims_business_type" :value="scope.row.bizType"/>
        </template>
      </el-table-column>
<!--      <el-table-column label="业务单据ID" align="center" prop="bizId" />-->
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
      <el-table-column label="货位名称" align="center" prop="locationId">
        <template slot-scope="scope">
          <span>{{ locationName(scope.row.locationId) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="产品名称" align="center" prop="productId">
        <template slot-scope="scope">
          <span>{{ productName(scope.row.productId) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="发生时间" align="center" prop="occurTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.occurTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="方向" align="center" prop="inout">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.ims_inout" :value="scope.row.inout"/>
        </template>
      </el-table-column>
      <el-table-column label="数量(片)" align="center" prop="qtyPiece" />
      <el-table-column label="单价" align="center" prop="unitPrice" />
      <el-table-column label="总金额" align="center" prop="amount" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="180">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleViewData(scope.row)"
            v-hasPermi="['ims:ims_stock_flow:query']"
          >详情</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['ims:ims_stock_flow:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['ims:ims_stock_flow:remove']"
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

    <!-- 库存流水详情抽屉 -->
    <ims_stock_flow-view-drawer ref="ims_stock_flowViewRef" />
    <!-- 添加或修改库存流水对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="900px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="业务单据编号" prop="bizNo" label-width="110px">
              <el-input v-model="form.bizNo" placeholder="请输入业务单据编号" maxlength="30" show-word-limit/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="业务类型" prop="bizType">
              <el-select v-model="form.bizType" placeholder="请选择业务类型"  style="width: 100%">
                <el-option
                  v-for="dict in dict.type.ims_business_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
<!--          <el-col :span="24">-->
<!--            <el-form-item label="业务单据ID" prop="bizId">-->
<!--              <el-input v-model="form.bizId" placeholder="请输入业务单据ID" />-->
<!--            </el-form-item>-->
<!--          </el-col>-->
          <el-col :span="12">
            <el-form-item label="企业名称" prop="enterpriseId" label-width="110px">
              <el-select v-model="form.enterpriseId" placeholder="请选择企业名称" clearable filterable  style="width: 100%">
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
            <el-form-item label="货位名称" prop="locationId" label-width="110px">
              <el-select v-model="form.locationId" placeholder="请选择货位名称" clearable filterable style="width: 100%">
                <el-option
                  v-for="item in locationOptions"
                  :key="item.id"
                  :label="locationName(item.id)"
                  :value="item.id"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="产品名称" prop="productId">
              <el-select v-model="form.productId" placeholder="请选择产品名称" clearable filterable style="width: 100%">
                <el-option
                  v-for="item in productOptions"
                  :key="item.id"
                  :label="productName(item.id)"
                  :value="item.id"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="发生时间" prop="occurTime" label-width="110px">
              <el-date-picker clearable
                v-model="form.occurTime"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择发生时间" style="width: 100%">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="方向" prop="inout">
              <el-select v-model="form.inout" placeholder="请选择方向" style="width: 100%">
                <el-option
                  v-for="dict in dict.type.ims_inout"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="数量(片)" prop="qtyPiece" label-width="110px">
              <div class="qty-stock-cell">
                <el-input v-model="form.qtyPiece" placeholder="请输入正整数" @input="recalcStockFlowAmount" />
                <div class="stock-hint">剩余库存：{{ stockFlowAfterInput }}</div>
              </div>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="单价" prop="unitPrice">
              <el-input v-model="form.unitPrice" placeholder="最多两位小数" @input="recalcStockFlowAmount" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="总金额" prop="amount" label-width="110px">
              <el-input v-model="form.amount" disabled placeholder="自动计算" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注" prop="remark" label-width="110px">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"   maxlength="200" show-word-limit/>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listIms_stock_flow, getIms_stock_flow, delIms_stock_flow, addIms_stock_flow, updateIms_stock_flow } from "@/api/ims/ims_stock_flow"
import Ims_stock_flowViewDrawer from "./view"
import referenceData from "@/views/ims/mixins/referenceData"
import amountCalc from "@/views/ims/mixins/amountCalc"
import listActions from "@/views/ims/mixins/listActions"

export default {
  mixins: [referenceData, amountCalc, listActions],
  name: "Ims_stock_flow",
  components: { Ims_stock_flowViewDrawer },
  dicts: ['ims_inout', 'ims_business_type'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 库存流水表格数据
      ims_stock_flowList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      dateRangeOccurTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        bizNo: null,
        bizType: null,
        bizId: null,
        enterpriseId: null,
        warehouseId: null,
        locationId: null,
        productId: null,
        occurTime: null,
        inout: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        bizNo: [
          { required: true, message: "业务单据编号不能为空", trigger: "blur" }
        ],
        bizType: [
          { required: true, message: "业务类型不能为空", trigger: "change" }
        ],
        // bizId: [
        //   { required: true, message: "业务单据ID不能为空", trigger: "blur" }
        // ],
        enterpriseId: [
          { required: true, message: "企业名称不能为空", trigger: "blur" }
        ],
        warehouseId: [
          { required: true, message: "仓库名称不能为空", trigger: "blur" }
        ],
        productId: [
          { required: true, message: "产品名称不能为空", trigger: "blur" }
        ],
        occurTime: [
          { required: true, message: "发生时间不能为空", trigger: "blur" }
        ],
        inout: [
          { required: true, message: "方向不能为空", trigger: "change" }
        ],
        qtyPiece: [
          { required: true, message: "数量(片)不能为空", trigger: "blur" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  computed: {
    stockFlowAfterInput() {
      if (!this.form || !this.form.productId) {
        return "--"
      }
      const direction = this.form.inout === "I" ? "in" : "out"
      return this.stockAfterInput(this.form, [this.form], direction, this.form.warehouseId, this.form.enterpriseId, this.form.locationId)
    }
  },
  methods: {
    /** 查询库存流水列表 */
    getList() {
      this.loading = true
      let query = this.queryParams
      query = this.addDateRange(query, this.dateRangeOccurTime, 'OccurTime')
      listIms_stock_flow(query).then(response => {
        this.ims_stock_flowList = response.rows
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
        bizNo: null,
        bizType: null,
        bizId: null,
        enterpriseId: null,
        warehouseId: null,
        locationId: null,
        productId: null,
        occurTime: null,
        inout: null,
        qtyPiece: null,
        unitPrice: null,
        amount: null,
        createBy: null,
        createTime: null,
        remark: null
      }
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
      this.dateRangeOccurTime = []
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
      this.title = "添加库存流水"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getIms_stock_flow(id).then(response => {
        this.form = response.data
        this.recalcStockFlowAmount()
        this.open = true
        this.title = "修改库存流水"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.recalcStockFlowAmount()
          if (this.form.id != null) {
            updateIms_stock_flow(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
              this.refreshInventoryOptions && this.refreshInventoryOptions()
            })
          } else {
            addIms_stock_flow(this.form).then(response => {
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
      this.confirmDeleteData(row, { label: "库存流水", nameKey: "bizNo" }, delIms_stock_flow).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 详情按钮操作 */
    handleViewData(row) {
      this.$refs["ims_stock_flowViewRef"].open(row.id)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('ims/ims_stock_flow/export', {
        ...this.queryParams
      }, `ims_stock_flow_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
