<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="auto">
      <el-form-item label="产品编号" prop="productCode">
        <el-input
          v-model="queryParams.productCode"
          placeholder="请输入产品编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="产品名称" prop="productName">
        <el-input
          v-model="queryParams.productName"
          placeholder="请输入产品名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
<!--      <el-form-item label="分类名称" prop="categoryId">-->
<!--        <el-select v-model="queryParams.categoryId" placeholder="请选择分类名称" clearable filterable>-->
<!--                <el-option-->
<!--                  v-for="item in categoryOptions"-->
<!--                  :key="item.id"-->
<!--                  :label="categoryName(item.id)"-->
<!--                  :value="item.id"-->
<!--                />-->
<!--              </el-select>-->
<!--      </el-form-item>-->
      <el-form-item label="厂家名称" prop="supplierId">
        <el-select v-model="queryParams.supplierId" placeholder="请选择厂家名称" clearable filterable>
                <el-option
                  v-for="item in supplierOptions"
                  :key="item.id"
                  :label="supplierName(item.id)"
                  :value="item.id"
                />
              </el-select>
      </el-form-item>
      <el-form-item label="单位名称" prop="unitId">
        <el-select v-model="queryParams.unitId" placeholder="请选择单位名称" clearable filterable>
                <el-option
                  v-for="item in unitOptions"
                  :key="item.id"
                  :label="unitName(item.id)"
                  :value="item.id"
                />
              </el-select>
      </el-form-item>
      <el-form-item label="品牌名称" prop="brandId">
        <el-select v-model="queryParams.brandId" placeholder="请选择品牌名称" clearable filterable>
          <el-option
            v-for="item in brandOptions"
            :key="item.id"
            :label="brandName(item.id)"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="规格" prop="spec">
        <el-input
          v-model="queryParams.spec"
          placeholder="请输入规格"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="色号" prop="colorNo">
        <el-input
          v-model="queryParams.colorNo"
          placeholder="请输入色号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="等级" prop="grade">
        <el-select v-model="queryParams.grade" placeholder="请选择等级" clearable>
          <el-option
            v-for="dict in dict.type.ims_level"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
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
          v-hasPermi="['ims:ims_product:add']"
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
          v-hasPermi="['ims:ims_product:edit']"
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
          v-hasPermi="['ims:ims_product:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['ims:ims_product:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="ims_productList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" width="80">
        <template slot-scope="scope">
          <span>{{ pageIndex(scope.$index) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="产品编号" align="center" prop="productCode" />
      <el-table-column label="产品名称" align="center" prop="productName" />
<!--      <el-table-column label="分类名称" align="center" prop="categoryId">-->
<!--        <template slot-scope="scope">-->
<!--          <span>{{ categoryName(scope.row.categoryId) }}</span>-->
<!--        </template>-->
<!--      </el-table-column>-->
      <el-table-column label="厂家名称" align="center" prop="supplierId">
        <template slot-scope="scope">
          <span>{{ supplierName(scope.row.supplierId) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="单位名称" align="center" prop="unitId">
        <template slot-scope="scope">
          <span>{{ unitName(scope.row.unitId) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="品牌名称" align="center" prop="brandId">
        <template slot-scope="scope">
          <span>{{ brandName(scope.row.brandId) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="规格" align="center" prop="spec" />
      <el-table-column label="色号" align="center" prop="colorNo" />
      <el-table-column label="等级" align="center" prop="grade">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.ims_level" :value="scope.row.grade"/>
        </template>
      </el-table-column>
      <el-table-column label="装箱系数" align="center" prop="boxRate" />
      <el-table-column label="参考进价单价" align="center" prop="purchasePrice" />
      <el-table-column label="参考售价单价" align="center" prop="salePrice" />
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
            v-hasPermi="['ims:ims_product:query']"
          >详情</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['ims:ims_product:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['ims:ims_product:remove']"
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

    <!-- 产品管理详情抽屉 -->
    <ims_product-view-drawer ref="ims_productViewRef" />
    <!-- 添加或修改产品管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="900px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="产品编号" prop="productCode">
              <el-input v-model="form.productCode" placeholder="请输入产品编号" maxlength="30" show-word-limit/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="产品名称" prop="productName">
              <el-input v-model="form.productName" placeholder="请输入产品名称"  maxlength="30" show-word-limit/>
            </el-form-item>
          </el-col>
<!--          <el-col :span="12">-->
<!--            <el-form-item label="分类名称" prop="categoryId">-->
<!--              <el-select v-model="form.categoryId" placeholder="请选择分类名称" clearable filterable>-->
<!--                <el-option-->
<!--                  v-for="item in categoryOptions"-->
<!--                  :key="item.id"-->
<!--                  :label="categoryName(item.id)"-->
<!--                  :value="item.id"-->
<!--                />-->
<!--              </el-select>-->
<!--            </el-form-item>-->
<!--          </el-col>-->
          <el-col :span="12">
            <el-form-item label="厂家名称" prop="supplierId">
              <el-select v-model="form.supplierId" placeholder="请选择厂家名称" clearable filterable  style="width: 100%">
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
            <el-form-item label="单位名称" prop="unitId">
              <el-select v-model="form.unitId" placeholder="请选择单位名称" clearable filterable style="width: 100%">
                <el-option
                  v-for="item in unitOptions"
                  :key="item.id"
                  :label="unitName(item.id)"
                  :value="item.id"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="品牌名称" prop="brandId">
              <el-select v-model="form.brandId" placeholder="请选择品牌名称" clearable filterable style="width: 100%">
                <el-option
                  v-for="item in brandOptions"
                  :key="item.id"
                  :label="brandName(item.id)"
                  :value="item.id"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="规格" prop="spec">
              <el-input v-model="form.spec" placeholder="请输入规格"  maxlength="20" show-word-limit/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="色号" prop="colorNo">
              <el-input v-model="form.colorNo" placeholder="请输入色号"  maxlength="20" show-word-limit/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="等级" prop="grade">
              <el-select v-model="form.grade" placeholder="请选择等级" style="width: 100%">
                <el-option
                  v-for="dict in dict.type.ims_level"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="装箱系数" prop="boxRate">
              <el-input v-model="form.boxRate" placeholder="请输入装箱系数"  maxlength="20" show-word-limit/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="参考进价" prop="purchasePrice">
              <el-input v-model="form.purchasePrice" placeholder="请输入参考进价单价" maxlength="20" show-word-limit/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="参考售价" prop="salePrice">
              <el-input v-model="form.salePrice" placeholder="请输入参考售价单价" maxlength="20" show-word-limit/>
            </el-form-item>
          </el-col>
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
import { listIms_product, getIms_product, delIms_product, addIms_product, updateIms_product } from "@/api/ims/ims_product"
import Ims_productViewDrawer from "./view"
import referenceData from "@/views/ims/mixins/referenceData"
import listActions from "@/views/ims/mixins/listActions"

export default {
  mixins: [referenceData, listActions],
  name: "Ims_product",
  components: { Ims_productViewDrawer },
  dicts: ['ims_status', 'ims_brand', 'ims_level'],
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
      // 产品管理表格数据
      ims_productList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        productCode: null,
        productName: null,
        categoryId: null,
        supplierId: null,
        unitId: null,
        brandId: null,
        spec: null,
        colorNo: null,
        grade: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        productCode: [
          { required: true, message: "产品编号不能为空", trigger: "blur" }
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
    /** 查询产品管理列表 */
    getList() {
      this.loading = true
      listIms_product(this.queryParams).then(response => {
        this.ims_productList = response.rows
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
        productCode: null,
        productName: null,
        categoryId: null,
        supplierId: null,
        unitId: null,
        brandId: null,
        spec: null,
        colorNo: null,
        grade: null,
        boxRate: null,
        purchasePrice: null,
        salePrice: null,
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
      this.title = "添加产品管理"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getIms_product(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改产品管理"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateIms_product(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addIms_product(this.form).then(response => {
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
      this.confirmDeleteData(row, { label: "产品", nameKey: "productName" }, delIms_product).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 详情按钮操作 */
    handleViewData(row) {
      this.$refs["ims_productViewRef"].open(row.id)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('ims/ims_product/export', {
        ...this.queryParams
      }, `ims_product_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
