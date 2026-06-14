<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="auto">
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
      <el-form-item label="仓库编码" prop="warehouseCode">
        <el-input
          v-model="queryParams.warehouseCode"
          placeholder="请输入仓库编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="仓库名称" prop="warehouseName">
        <el-input
          v-model="queryParams.warehouseName"
          placeholder="请输入仓库名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="负责人" prop="managerId">
        <el-select v-model="queryParams.managerId" placeholder="请选择负责人" clearable filterable>
                <el-option
                  v-for="item in employeeOptions"
                  :key="item.id"
                  :label="employeeName(item.id)"
                  :value="item.id"
                />
              </el-select>
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
          v-hasPermi="['ims:ims_warehouse:add']"
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
          v-hasPermi="['ims:ims_warehouse:edit']"
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
          v-hasPermi="['ims:ims_warehouse:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['ims:ims_warehouse:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="ims_warehouseList" @selection-change="handleSelectionChange">
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
      <el-table-column label="仓库编码" align="center" prop="warehouseCode" />
      <el-table-column label="仓库名称" align="center" prop="warehouseName" />
      <el-table-column label="地址" align="center" prop="address" />
      <el-table-column label="负责人" align="center" prop="managerId">
        <template slot-scope="scope">
          <span>{{ employeeName(scope.row.managerId) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.ims_status" :value="scope.row.status"/>
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
            v-hasPermi="['ims:ims_warehouse:query']"
          >详情</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['ims:ims_warehouse:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['ims:ims_warehouse:remove']"
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

    <!-- 仓库管理详情抽屉 -->
    <ims_warehouse-view-drawer ref="ims_warehouseViewRef" />
    <!-- 添加或修改仓库管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="24">
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
          <el-col :span="24">
            <el-form-item label="仓库编码" prop="warehouseCode">
              <el-input v-model="form.warehouseCode" placeholder="请输入仓库编码"  maxlength="30" show-word-limit/>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="仓库名称" prop="warehouseName">
              <el-input v-model="form.warehouseName" placeholder="请输入仓库名称"  maxlength="30" show-word-limit/>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="地址" prop="address">
              <el-input v-model="form.address" placeholder="请输入地址"  maxlength="30" show-word-limit/>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="负责人" prop="managerId">
              <el-select v-model="form.managerId" placeholder="请选择负责人" clearable filterable style="width: 100%">
                <el-option
                  v-for="item in employeeOptions"
                  :key="item.id"
                  :label="employeeName(item.id)"
                  :value="item.id"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
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
import { listIms_warehouse, getIms_warehouse, delIms_warehouse, addIms_warehouse, updateIms_warehouse } from "@/api/ims/ims_warehouse"
import Ims_warehouseViewDrawer from "./view"
import referenceData from "@/views/ims/mixins/referenceData"
import listActions from "@/views/ims/mixins/listActions"

export default {
  mixins: [referenceData, listActions],
  name: "Ims_warehouse",
  components: { Ims_warehouseViewDrawer },
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
      // 仓库管理表格数据
      ims_warehouseList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        enterpriseId: null,
        warehouseCode: null,
        warehouseName: null,
        managerId: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        enterpriseId: [
          { required: true, message: "企业名称不能为空", trigger: "blur" }
        ],
        warehouseCode: [
          { required: true, message: "仓库编码不能为空", trigger: "blur" }
        ],
        warehouseName: [
          { required: true, message: "仓库名称不能为空", trigger: "blur" }
        ],
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
    /** 查询仓库管理列表 */
    getList() {
      this.loading = true
      listIms_warehouse(this.queryParams).then(response => {
        this.ims_warehouseList = response.rows
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
        warehouseCode: null,
        warehouseName: null,
        address: null,
        managerId: null,
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
      this.title = "添加仓库管理"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getIms_warehouse(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改仓库管理"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateIms_warehouse(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addIms_warehouse(this.form).then(response => {
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
      this.confirmDeleteData(row, { label: "仓库", nameKey: "warehouseName" }, delIms_warehouse).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 详情按钮操作 */
    handleViewData(row) {
      this.$refs["ims_warehouseViewRef"].open(row.id)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('ims/ims_warehouse/export', {
        ...this.queryParams
      }, `ims_warehouse_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
