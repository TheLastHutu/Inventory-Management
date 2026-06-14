import { listIms_enterprise } from "@/api/ims/ims_enterprise"
import { listIms_warehouse } from "@/api/ims/ims_warehouse"
import { listIms_warehouse_location } from "@/api/ims/ims_warehouse_location"
import { listIms_product } from "@/api/ims/ims_product"
import { listIms_product_category } from "@/api/ims/ims_product_category"
import { listIms_brand } from "@/api/ims/ims_brand"
import { listIms_supplier } from "@/api/ims/ims_supplier"
import { listIms_customer } from "@/api/ims/ims_customer"
import { listIms_employee } from "@/api/ims/ims_employee"
import { listIms_unit } from "@/api/ims/ims_unit"
import { listIms_fixed_asset } from "@/api/ims/ims_fixed_asset"
import { listIms_payroll_period } from "@/api/ims/ims_payroll_period"
import { listIms_inventory } from "@/api/ims/ims_inventory"
import { listDept } from "@/api/system/dept"

function rows(response) {
  return response && response.rows ? response.rows : []
}

export default {
  data() {
    return {
      enterpriseOptions: [],
      warehouseOptions: [],
      locationOptions: [],
      productOptions: [],
      categoryOptions: [],
      brandOptions: [],
      supplierOptions: [],
      customerOptions: [],
      employeeOptions: [],
      unitOptions: [],
      assetOptions: [],
      periodOptions: [],
      deptOptions: [],
      inventoryOptions: []
    }
  },
  created() {
    this.loadImsReferenceData()
  },
  methods: {
    loadImsReferenceData() {
      const params = { pageNum: 1, pageSize: 1000 }
      Promise.all([
        listIms_enterprise(params),
        listIms_warehouse(params),
        listIms_warehouse_location(params),
        listIms_product(params),
        listIms_product_category(params),
        listIms_brand(params),
        listIms_supplier(params),
        listIms_customer(params),
        listIms_employee(params),
        listIms_unit(params),
        listIms_fixed_asset(params),
        listIms_payroll_period(params),
        listIms_inventory(params),
        listDept({ status: '0' })
      ]).then(([enterprise, warehouse, location, product, category, brand, supplier, customer, employee, unit, asset, period, inventory, dept]) => {
        this.enterpriseOptions = rows(enterprise)
        this.warehouseOptions = rows(warehouse)
        this.locationOptions = rows(location)
        this.productOptions = rows(product)
        this.categoryOptions = rows(category)
        this.brandOptions = rows(brand)
        this.supplierOptions = rows(supplier)
        this.customerOptions = rows(customer)
        this.employeeOptions = rows(employee)
        this.unitOptions = rows(unit)
        this.assetOptions = rows(asset)
        this.periodOptions = rows(period)
        this.inventoryOptions = rows(inventory)
        this.deptOptions = this.handleTree(dept.data || [], "deptId")
      }).catch(() => {})
    },
    refreshInventoryOptions() {
      return listIms_inventory({ pageNum: 1, pageSize: 1000 }).then(response => {
        this.inventoryOptions = rows(response)
      })
    },
    deptNormalizer(node) {
      return {
        id: node.deptId,
        label: node.deptName,
        children: node.children && node.children.length ? node.children : undefined
      }
    },
    deptNameNormalizer(node) {
      return {
        id: node.deptName,
        label: node.deptName,
        children: node.children && node.children.length ? node.children : undefined
      }
    },
    findDeptById(list, value) {
      for (const item of list || []) {
        if (String(item.deptId) === String(value)) {
          return item
        }
        const child = this.findDeptById(item.children, value)
        if (child) {
          return child
        }
      }
      return null
    },
    optionLabel(options, value, labelKey, fallbackKey) {
      if (value === null || value === undefined || value === "") {
        return ""
      }
      const item = options.find(item => String(item.id) === String(value))
      if (!item) {
        return value
      }
      return item[labelKey] || (fallbackKey ? item[fallbackKey] : null) || value
    },
    enterpriseName(value) {
      return this.optionLabel(this.enterpriseOptions, value, "enterpriseName", "enterpriseCode")
    },
    warehouseName(value) {
      return this.optionLabel(this.warehouseOptions, value, "warehouseName", "warehouseCode")
    },
    locationName(value) {
      return this.optionLabel(this.locationOptions, value, "locationName", "locationCode")
    },
    productName(value) {
      return this.optionLabel(this.productOptions, value, "productName", "productCode")
    },
    categoryName(value) {
      return this.optionLabel(this.categoryOptions, value, "categoryName", "categoryCode")
    },
    brandName(value) {
      return this.optionLabel(this.brandOptions, value, "brandName", "brandCode")
    },
    supplierName(value) {
      return this.optionLabel(this.supplierOptions, value, "supplierName", "supplierCode")
    },
    customerName(value) {
      return this.optionLabel(this.customerOptions, value, "customerName", "customerCode")
    },
    employeeName(value) {
      return this.optionLabel(this.employeeOptions, value, "employeeName", "employeeCode")
    },
    unitName(value) {
      return this.optionLabel(this.unitOptions, value, "unitName", "unitCode")
    },
    assetName(value) {
      return this.optionLabel(this.assetOptions, value, "assetName", "assetCode")
    },
    periodName(value) {
      return this.optionLabel(this.periodOptions, value, "periodCode")
    },
    inventoryQty(productId, warehouseId, enterpriseId, locationId) {
      if (!productId || !warehouseId || !enterpriseId) {
        return ""
      }
      const total = (this.inventoryOptions || []).reduce((sum, item) => {
        const sameProduct = String(item.productId) === String(productId)
        const sameWarehouse = !warehouseId || String(item.warehouseId) === String(warehouseId)
        const sameEnterprise = !enterpriseId || String(item.enterpriseId) === String(enterpriseId)
        const sameLocation = !locationId || String(item.locationId) === String(locationId)
        if (sameProduct && sameWarehouse && sameEnterprise && sameLocation) {
          return sum + Number(item.availQtyPiece || item.qtyPiece || 0)
        }
        return sum
      }, 0)
      return Number.isInteger(total) ? String(total) : total.toFixed(2)
    },
    stockHint(productId, warehouseId, enterpriseId, locationId) {
      const qty = this.inventoryQty(productId, warehouseId, enterpriseId, locationId)
      return qty === "" ? "--" : qty
    },
    stockAfterInput(row, rows, direction, warehouseId, enterpriseId, locationId) {
      if (!row || !row.productId || !warehouseId || !enterpriseId) {
        return "--"
      }
      const current = Number(this.inventoryQty(row.productId, warehouseId, enterpriseId, locationId) || 0)
      const change = (rows || []).reduce((sum, item) => {
        if (String(item.productId) !== String(row.productId)) {
          return sum
        }
        const qty = Number(item.qtyPiece || 0)
        return direction === "in" ? sum + qty : sum - qty
      }, 0)
      const total = current + change
      return Number.isInteger(total) ? String(total) : total.toFixed(2)
    },
    deptName(value) {
      if (value === null || value === undefined || value === "") {
        return ""
      }
      const item = this.findDeptById(this.deptOptions, value)
      return item ? item.deptName : value
    }
  }
}
