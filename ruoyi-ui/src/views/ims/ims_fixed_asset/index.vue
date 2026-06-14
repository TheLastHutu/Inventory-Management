<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="auto">
      <el-form-item label="企业名称" prop="enterpriseId">
        <el-select  v-model="queryParams.enterpriseId" placeholder="请选择企业名称" clearable filterable >
                <el-option
                  v-for="item in enterpriseOptions"
                  :key="item.id"
                  :label="enterpriseName(item.id)"
                  :value="item.id"
                />
        </el-select>
      </el-form-item>
      <el-form-item label="资产编号" prop="assetCode">
        <el-input
          v-model="queryParams.assetCode"
          placeholder="请输入资产编号"
          clearable
          @keyup.enter.native="handleQuery"

        />
      </el-form-item>
      <el-form-item label="资产名称" prop="assetName">
        <el-input
          v-model="queryParams.assetName"
          placeholder="请输入资产名称"
          clearable
          @keyup.enter.native="handleQuery"

        />
      </el-form-item>
      <el-form-item label="资产类别" prop="assetCategory">
        <el-input
          v-model="queryParams.assetCategory"
          placeholder="请输入资产类别"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="使用部门" prop="useDept">
        <treeselect v-model="queryParams.useDept" :options="deptOptions" :normalizer="deptNameNormalizer" placeholder="请选择使用部门" style="width: 240px" />
      </el-form-item>
      <el-form-item label="保管人" prop="keeper">
        <el-input
          v-model="queryParams.keeper"
          placeholder="请输入保管人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option
            v-for="dict in dict.type.ims_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="购置日期" prop="purchaseDate">
        <el-date-picker clearable
                        v-model="dateRangePurchaseDate"
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
          v-hasPermi="['ims:ims_fixed_asset:add']"
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
          v-hasPermi="['ims:ims_fixed_asset:edit']"
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
          v-hasPermi="['ims:ims_fixed_asset:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['ims:ims_fixed_asset:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="ims_fixed_assetList" @selection-change="handleSelectionChange" >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" width="80">
        <template slot-scope="scope">
          <span>{{ pageIndex(scope.$index) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="企业名称" align="center" prop="enterpriseId">
        <template slot-scope="scope">
          <span>{{ enterpriseName(scope.row.enterpriseId) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="资产编号" align="center" prop="assetCode" />
      <el-table-column label="资产名称" align="center" prop="assetName" />
      <el-table-column label="资产类别" align="center" prop="assetCategory" />
      <el-table-column label="购置日期" align="center" prop="purchaseDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.purchaseDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="价值" align="center" prop="originalValue" />
<!--      <el-table-column label="净值" align="center" prop="netValue" />-->
      <el-table-column label="使用部门" align="center" prop="useDept" />
      <el-table-column label="保管人" align="center" prop="keeper" />
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.ims_status" :value="scope.row.status"/>
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
            v-hasPermi="['ims:ims_fixed_asset:query']"
          >详情</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['ims:ims_fixed_asset:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['ims:ims_fixed_asset:remove']"
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

    <!-- 固定资产详情抽屉 -->
    <ims_fixed_asset-view-drawer ref="ims_fixed_assetViewRef" />
    <!-- 添加或修改固定资产对话框 -->
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
            <el-form-item label="资产编号" prop="assetCode">
              <el-input v-model="form.assetCode" placeholder="请输入资产编号" maxlength="30" show-word-limit/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="资产名称" prop="assetName">
              <el-input v-model="form.assetName" placeholder="请输入资产名称" maxlength="30" show-word-limit/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="资产类别" prop="assetCategory">
              <el-input v-model="form.assetCategory" placeholder="请输入资产类别" maxlength="30" show-word-limit/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="购置日期" prop="purchaseDate" >
              <el-date-picker clearable
                              style="width: 100%"
                v-model="form.purchaseDate"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择购置日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="价值" prop="originalValue">
              <el-input v-model="form.originalValue" placeholder="请输入价值" maxlength="20" show-word-limit/>
            </el-form-item>
          </el-col>
<!--          <el-col :span="12">-->
<!--            <el-form-item label="净值" prop="netValue">-->
<!--              <el-input v-model="form.netValue" placeholder="请输入净值" />-->
<!--            </el-form-item>-->
<!--          </el-col>-->
          <el-col :span="12">
            <el-form-item label="使用部门" prop="useDept">
              <treeselect v-model="form.useDept" :options="deptOptions" :normalizer="deptNameNormalizer" placeholder="请选择使用部门" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="保管人" prop="keeper">
              <el-input v-model="form.keeper" placeholder="请输入保管人" maxlength="20" show-word-limit/>
            </el-form-item>
          </el-col>
          <el-col>
          <el-col :span="12">
            <el-form-item label="状态" prop="status">
              <el-select v-model="form.status" placeholder="请选择状态" style="width: 100%">
                <el-option
                  v-for="dict in dict.type.ims_status"
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
import { listIms_fixed_asset, getIms_fixed_asset, delIms_fixed_asset, addIms_fixed_asset, updateIms_fixed_asset } from "@/api/ims/ims_fixed_asset"
import Ims_fixed_assetViewDrawer from "./view"
import referenceData from "@/views/ims/mixins/referenceData"
import listActions from "@/views/ims/mixins/listActions"
import Treeselect from "@riophae/vue-treeselect"
import "@riophae/vue-treeselect/dist/vue-treeselect.css"

export default {
  mixins: [referenceData, listActions],
  name: "Ims_fixed_asset",
  components: { Ims_fixed_assetViewDrawer, Treeselect },
  dicts: ['ims_status'],
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
      // 固定资产表格数据
      ims_fixed_assetList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      dateRangePurchaseDate: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        enterpriseId: null,
        assetCode: null,
        assetName: null,
        assetCategory: null,
        purchaseDate: null,
        useDept: null,
        keeper: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        enterpriseId: [
          { required: true, message: "企业名称不能为空", trigger: "blur" }
        ],
        assetCode: [
          { required: true, message: "资产编号不能为空", trigger: "blur" }
        ],
        assetName: [
          { required: true, message: "资产名称不能为空", trigger: "blur" }
        ],
        originalValue: [
          { required: true, message: "价值不能为空", trigger: "blur" }
        ],
        // netValue: [
        //   { required: true, message: "净值不能为空", trigger: "blur" }
        // ],
        status: [
          { required: true, message: "状态不能为空", trigger: "change" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询固定资产列表 */
    getList() {
      this.loading = true
      let query = this.queryParams
      query = this.addDateRange(query, this.dateRangePurchaseDate, 'PurchaseDate')
      listIms_fixed_asset(query).then(response => {
        this.ims_fixed_assetList = response.rows
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
        enterpriseId: null,
        assetCode: null,
        assetName: null,
        assetCategory: null,
        purchaseDate: null,
        originalValue: null,
        netValue: null,
        useDept: null,
        keeper: null,
        status: '0',
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
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
      this.dateRangePurchaseDate = []
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
      this.title = "添加固定资产"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getIms_fixed_asset(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改固定资产"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateIms_fixed_asset(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addIms_fixed_asset(this.form).then(response => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      this.confirmDeleteData(row, { label: "固定资产", nameKey: "assetName" }, delIms_fixed_asset).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 详情按钮操作 */
    handleViewData(row) {
      this.$refs["ims_fixed_assetViewRef"].open(row.id)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('ims/ims_fixed_asset/export', {
        ...this.queryParams
      }, `ims_fixed_asset_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
