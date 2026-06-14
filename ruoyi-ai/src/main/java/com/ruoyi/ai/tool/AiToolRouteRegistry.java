package com.ruoyi.ai.tool;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import org.springframework.stereotype.Component;
import com.ruoyi.common.utils.StringUtils;

@Component
public class AiToolRouteRegistry
{
    private static final Set<String> DAMAGE_TOOLS = setOf("damageMonthlySummary", "damageProductAnalysis");
    private static final Set<String> STOCK_FLOW_TOOLS = setOf("stockFlowTrend", "stockFlowProductRanking");
    private static final Set<String> INVENTORY_TOOLS = setOf("inventoryCurrentSummary", "inventoryByWarehouse",
            "inventoryLowStockList");
    private static final Set<String> FIXED_ASSET_TOOLS = setOf("fixedAssetSummary", "fixedAssetByDept",
            "fixedAssetDepreciationTrend", "fixedAssetRiskList");
    private static final Set<String> PAYROLL_TOOLS = setOf("payrollMonthlySummary", "payrollDeptCompare",
            "payrollEmployeeRanking", "payrollAbnormalCheck", "payrollStatusSummary");
    private static final Set<String> UNIT_TOOLS = setOf("unitStatusSummary", "masterDataHealthCheck");
    private static final Set<String> WAREHOUSE_TOOLS = setOf("warehouseLocationSummary", "masterDataHealthCheck");
    private static final Set<String> SUPPLIER_TOOLS = setOf("supplierHealthCheck", "masterDataHealthCheck");
    private static final Set<String> PRODUCT_TOOLS = setOf("productMasterAnalysis", "categoryBrandAnalysis",
            "masterDataHealthCheck");
    private static final Set<String> CATEGORY_TOOLS = setOf("categoryBrandAnalysis", "productMasterAnalysis",
            "masterDataHealthCheck");
    private static final Set<String> ENTERPRISE_TOOLS = setOf("enterpriseHealthCheck", "masterDataHealthCheck");
    private static final Set<String> EMPLOYEE_TOOLS = setOf("employeeHealthCheck", "masterDataHealthCheck");
    private static final Set<String> CUSTOMER_TOOLS = setOf("customerHealthCheck", "masterDataHealthCheck");
    private static final Set<String> BRAND_TOOLS = setOf("categoryBrandAnalysis", "productMasterAnalysis",
            "masterDataHealthCheck");
    private static final Set<String> INBOUND_TOOLS = setOf("inboundMonthlySummary", "inboundProductRanking");
    private static final Set<String> DELIVERY_TOOLS = setOf("deliveryMonthlySummary", "deliveryProductRanking");
    private static final Set<String> OUTBOUND_TOOLS = setOf("outboundMonthlySummary", "outboundProductRanking");
    private static final Set<String> SALE_RETURN_TOOLS = setOf("saleReturnMonthlySummary", "saleReturnProductRanking");
    private static final Set<String> SAMPLE_TOOLS = setOf("sampleMonthlySummary", "sampleProductRanking");
    private static final Set<String> INVENTORY_MANAGEMENT_TOOLS = setOf("damageMonthlySummary", "damageProductAnalysis",
            "stockFlowTrend", "stockFlowProductRanking", "inventoryCurrentSummary", "inventoryByWarehouse",
            "inventoryLowStockList");

    public Set<String> getAllowedTools(String route)
    {
        if (StringUtils.isEmpty(route))
        {
            return Collections.emptySet();
        }
        if (route.contains("ims_damage"))
        {
            return DAMAGE_TOOLS;
        }
        if (route.contains("ims_stock_flow"))
        {
            return STOCK_FLOW_TOOLS;
        }
        if (route.contains("ims_inventory"))
        {
            return INVENTORY_TOOLS;
        }
        if (route.contains("ims_fixed_asset"))
        {
            return FIXED_ASSET_TOOLS;
        }
        if (route.contains("ims_payroll_period"))
        {
            return PAYROLL_TOOLS;
        }
        if (route.contains("ims_inbound"))
        {
            return INBOUND_TOOLS;
        }
        if (route.contains("ims_delivery"))
        {
            return DELIVERY_TOOLS;
        }
        if (route.contains("ims_outbound"))
        {
            return OUTBOUND_TOOLS;
        }
        if (route.contains("ims_sale_return"))
        {
            return SALE_RETURN_TOOLS;
        }
        if (route.contains("ims_sample"))
        {
            return SAMPLE_TOOLS;
        }
        if (route.contains("ims_unit"))
        {
            return UNIT_TOOLS;
        }
        if (route.contains("ims_warehouse_location") || route.contains("ims_warehouse"))
        {
            return WAREHOUSE_TOOLS;
        }
        if (route.contains("ims_supplier"))
        {
            return SUPPLIER_TOOLS;
        }
        if (route.contains("ims_product_category"))
        {
            return CATEGORY_TOOLS;
        }
        if (route.contains("ims_product"))
        {
            return PRODUCT_TOOLS;
        }
        if (route.contains("ims_enterprise"))
        {
            return ENTERPRISE_TOOLS;
        }
        if (route.contains("ims_employee"))
        {
            return EMPLOYEE_TOOLS;
        }
        if (route.contains("ims_customer"))
        {
            return CUSTOMER_TOOLS;
        }
        if (route.contains("ims_brand"))
        {
            return BRAND_TOOLS;
        }
        if (route.contains("stockcheck") || route.contains("inventory"))
        {
            return INVENTORY_MANAGEMENT_TOOLS;
        }
        return Collections.emptySet();
    }

    public String describeAllowedTools(String route)
    {
        Set<String> tools = getAllowedTools(route);
        if (tools.isEmpty())
        {
            return "Current page has no enabled read-only business tools.";
        }
        StringBuilder builder = new StringBuilder();
        builder.append("Current page allowed read-only tools:\n");
        for (String tool : tools)
        {
            builder.append("- ").append(tool).append(": ").append(describeTool(tool)).append("\n");
        }
        return builder.toString();
    }

    public boolean isAllowed(String route, String tool)
    {
        return getAllowedTools(route).contains(tool);
    }

    private String describeTool(String tool)
    {
        if ("damageMonthlySummary".equals(tool))
        {
            return "Damage bill summary: bill count, quantity, amount, status distribution and reason distribution.";
        }
        if ("damageProductAnalysis".equals(tool))
        {
            return "Damage product ranking by damage count, quantity and amount.";
        }
        if ("stockFlowTrend".equals(tool))
        {
            return "Stock flow trend by date, including inbound/outbound quantity and amount.";
        }
        if ("stockFlowProductRanking".equals(tool))
        {
            return "Stock flow product ranking by frequency, quantity and amount.";
        }
        if ("inventoryCurrentSummary".equals(tool))
        {
            return "Inventory summary: current quantity, locked quantity, available quantity and abnormal rows.";
        }
        if ("inventoryByWarehouse".equals(tool))
        {
            return "Inventory summary grouped by warehouse.";
        }
        if ("inventoryLowStockList".equals(tool))
        {
            return "Low stock list where available quantity is less than or equal to zero.";
        }
        if ("fixedAssetSummary".equals(tool))
        {
            return "Fixed asset summary: count, original value, net value, status distribution and net value ratio.";
        }
        if ("fixedAssetByDept".equals(tool))
        {
            return "Fixed asset count, original value and net value grouped by using department.";
        }
        if ("fixedAssetDepreciationTrend".equals(tool))
        {
            return "Fixed asset depreciation trend by period.";
        }
        if ("fixedAssetRiskList".equals(tool))
        {
            return "Fixed asset risk list: stopped assets, low net value assets and long-time not updated assets.";
        }
        if ("payrollMonthlySummary".equals(tool))
        {
            return "Monthly payroll summary: base salary, commission, allowance, bonus, deduction and net pay.";
        }
        if ("payrollDeptCompare".equals(tool))
        {
            return "Payroll cost comparison grouped by department.";
        }
        if ("payrollEmployeeRanking".equals(tool))
        {
            return "Employee payroll ranking by net pay.";
        }
        if ("payrollAbnormalCheck".equals(tool))
        {
            return "Payroll abnormal check: low attendance, high deduction and negative net pay.";
        }
        if ("payrollStatusSummary".equals(tool))
        {
            return "Payroll status summary.";
        }
        if ("masterDataHealthCheck".equals(tool))
        {
            return "Overall master data health check across units, warehouses, suppliers, products, categories, brands, enterprises, employees and customers.";
        }
        if ("unitStatusSummary".equals(tool))
        {
            return "Unit status and product usage summary.";
        }
        if ("warehouseLocationSummary".equals(tool))
        {
            return "Warehouse and location summary, including warehouses without locations and disabled locations.";
        }
        if ("supplierHealthCheck".equals(tool))
        {
            return "Supplier master data completeness check, including missing contacts, phone, email and address.";
        }
        if ("productMasterAnalysis".equals(tool))
        {
            return "Product master data completeness and category usage analysis.";
        }
        if ("categoryBrandAnalysis".equals(tool))
        {
            return "Product category and brand usage analysis, including unused or disabled categories and brands.";
        }
        if ("enterpriseHealthCheck".equals(tool))
        {
            return "Enterprise master data completeness check, including tax, bank, phone and address fields.";
        }
        if ("employeeHealthCheck".equals(tool))
        {
            return "Employee master data health check, department distribution and incomplete employee records.";
        }
        if ("customerHealthCheck".equals(tool))
        {
            return "Customer master data completeness check, including missing contacts, phone, email and address.";
        }
        if ("inboundMonthlySummary".equals(tool))
        {
            return "Inbound bill summary by date range, including bill count, quantity, amount, status and supplier ranking.";
        }
        if ("inboundProductRanking".equals(tool))
        {
            return "Inbound product ranking by quantity and amount.";
        }
        if ("deliveryMonthlySummary".equals(tool))
        {
            return "Delivery bill summary by date range, including bill count, quantity, amount, status and customer ranking.";
        }
        if ("deliveryProductRanking".equals(tool))
        {
            return "Delivery product ranking by quantity and amount.";
        }
        if ("outboundMonthlySummary".equals(tool))
        {
            return "Outbound bill summary by date range, including bill count, quantity, amount, status and outbound type distribution.";
        }
        if ("outboundProductRanking".equals(tool))
        {
            return "Outbound product ranking by quantity and amount.";
        }
        if ("saleReturnMonthlySummary".equals(tool))
        {
            return "Sale return bill summary by date range, including bill count, quantity, amount, status and return reason distribution.";
        }
        if ("saleReturnProductRanking".equals(tool))
        {
            return "Sale return product ranking by quantity and amount.";
        }
        if ("sampleMonthlySummary".equals(tool))
        {
            return "Sample bill summary by date range, including bill count, sample quantity, status, customer count and purpose distribution.";
        }
        if ("sampleProductRanking".equals(tool))
        {
            return "Sample product ranking by quantity.";
        }
        return "Read-only business query tool.";
    }

    private static Set<String> setOf(String... values)
    {
        return Collections.unmodifiableSet(new LinkedHashSet<String>(Arrays.asList(values)));
    }
}
