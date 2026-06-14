package com.ruoyi.ai.tool;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ruoyi.ai.mapper.AiInventoryToolMapper;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;

@Component
public class AiInventoryToolExecutor
{
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Autowired
    private AiInventoryToolMapper inventoryToolMapper;

    public AiToolExecutionResult execute(AiToolCall call)
    {
        String tool = call.getTool();
        Map<String, Object> args = call.getArguments();
        if ("inventoryCurrentSummary".equals(tool))
        {
            Object data = inventoryToolMapper.selectInventoryCurrentSummary(longArg(args, "enterpriseId"),
                    longArg(args, "warehouseId"));
            return new AiToolExecutionResult(tool, "Inventory summary queried.", data);
        }
        if ("inventoryByWarehouse".equals(tool))
        {
            Object data = inventoryToolMapper.selectInventoryByWarehouse(longArg(args, "enterpriseId"));
            return new AiToolExecutionResult(tool, "Inventory by warehouse queried.", data);
        }
        if ("inventoryLowStockList".equals(tool))
        {
            Object data = inventoryToolMapper.selectInventoryLowStockList(longArg(args, "enterpriseId"),
                    longArg(args, "warehouseId"), limitArg(args));
            return new AiToolExecutionResult(tool, "Low stock list queried.", data);
        }
        if ("stockFlowTrend".equals(tool))
        {
            DateRange range = dateRange(args);
            Object data = inventoryToolMapper.selectStockFlowTrend(range.beginDate, range.endDate,
                    longArg(args, "enterpriseId"), longArg(args, "warehouseId"));
            return new AiToolExecutionResult(tool, "Stock flow trend queried.", data);
        }
        if ("stockFlowProductRanking".equals(tool))
        {
            DateRange range = dateRange(args);
            Object data = inventoryToolMapper.selectStockFlowProductRanking(range.beginDate, range.endDate,
                    longArg(args, "enterpriseId"), longArg(args, "warehouseId"), limitArg(args));
            return new AiToolExecutionResult(tool, "Stock flow product ranking queried.", data);
        }
        if ("damageMonthlySummary".equals(tool))
        {
            DateRange range = dateRange(args);
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("summary", inventoryToolMapper.selectDamageMonthlySummary(range.beginDate, range.endDate,
                    longArg(args, "enterpriseId"), longArg(args, "warehouseId")));
            data.put("reasonSummary", inventoryToolMapper.selectDamageReasonSummary(range.beginDate, range.endDate,
                    longArg(args, "enterpriseId"), longArg(args, "warehouseId")));
            return new AiToolExecutionResult(tool, "Damage monthly summary queried.", data);
        }
        if ("damageProductAnalysis".equals(tool))
        {
            DateRange range = dateRange(args);
            Object data = inventoryToolMapper.selectDamageProductAnalysis(range.beginDate, range.endDate,
                    longArg(args, "enterpriseId"), longArg(args, "warehouseId"), limitArg(args));
            return new AiToolExecutionResult(tool, "Damage product analysis queried.", data);
        }
        if ("fixedAssetSummary".equals(tool))
        {
            Object data = inventoryToolMapper.selectFixedAssetSummary(longArg(args, "enterpriseId"));
            return new AiToolExecutionResult(tool, "Fixed asset summary queried.", data);
        }
        if ("fixedAssetByDept".equals(tool))
        {
            Object data = inventoryToolMapper.selectFixedAssetByDept(longArg(args, "enterpriseId"));
            return new AiToolExecutionResult(tool, "Fixed asset by department queried.", data);
        }
        if ("fixedAssetDepreciationTrend".equals(tool))
        {
            PeriodRange range = periodRange(args);
            Object data = inventoryToolMapper.selectFixedAssetDepreciationTrend(range.beginPeriod, range.endPeriod,
                    longArg(args, "enterpriseId"));
            return new AiToolExecutionResult(tool, "Fixed asset depreciation trend queried.", data);
        }
        if ("fixedAssetRiskList".equals(tool))
        {
            Object data = inventoryToolMapper.selectFixedAssetRiskList(longArg(args, "enterpriseId"), limitArg(args));
            return new AiToolExecutionResult(tool, "Fixed asset risk list queried.", data);
        }
        if ("payrollMonthlySummary".equals(tool))
        {
            Object data = inventoryToolMapper.selectPayrollMonthlySummary(periodCode(args), longArg(args, "deptId"));
            return new AiToolExecutionResult(tool, "Payroll monthly summary queried.", data);
        }
        if ("payrollDeptCompare".equals(tool))
        {
            Object data = inventoryToolMapper.selectPayrollDeptCompare(periodCode(args));
            return new AiToolExecutionResult(tool, "Payroll department comparison queried.", data);
        }
        if ("payrollEmployeeRanking".equals(tool))
        {
            Object data = inventoryToolMapper.selectPayrollEmployeeRanking(periodCode(args), limitArg(args));
            return new AiToolExecutionResult(tool, "Payroll employee ranking queried.", data);
        }
        if ("payrollAbnormalCheck".equals(tool))
        {
            Object data = inventoryToolMapper.selectPayrollAbnormalCheck(periodCode(args), limitArg(args));
            return new AiToolExecutionResult(tool, "Payroll abnormal list queried.", data);
        }
        if ("payrollStatusSummary".equals(tool))
        {
            Object data = inventoryToolMapper.selectPayrollStatusSummary(periodCode(args));
            return new AiToolExecutionResult(tool, "Payroll status summary queried.", data);
        }
        if ("masterDataHealthCheck".equals(tool))
        {
            Object data = inventoryToolMapper.selectMasterDataHealthSummary();
            return new AiToolExecutionResult(tool, "Master data health summary queried.", data);
        }
        if ("unitStatusSummary".equals(tool))
        {
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("summary", inventoryToolMapper.selectUnitStatusSummary());
            data.put("productUsage", inventoryToolMapper.selectUnitProductUsage(limitArg(args)));
            return new AiToolExecutionResult(tool, "Unit status summary queried.", data);
        }
        if ("warehouseLocationSummary".equals(tool))
        {
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("summary", inventoryToolMapper.selectWarehouseLocationSummary(longArg(args, "enterpriseId"),
                    longArg(args, "warehouseId")));
            data.put("warehouseDetails", inventoryToolMapper.selectWarehouseLocationDetail(longArg(args, "enterpriseId"),
                    longArg(args, "warehouseId"), limitArg(args)));
            return new AiToolExecutionResult(tool, "Warehouse and location summary queried.", data);
        }
        if ("supplierHealthCheck".equals(tool))
        {
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("summary", inventoryToolMapper.selectSupplierHealthSummary());
            data.put("incompleteSuppliers", inventoryToolMapper.selectSupplierIncompleteList(limitArg(args)));
            return new AiToolExecutionResult(tool, "Supplier health check queried.", data);
        }
        if ("productMasterAnalysis".equals(tool))
        {
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("summary", inventoryToolMapper.selectProductMasterSummary());
            data.put("incompleteProducts", inventoryToolMapper.selectProductIncompleteList(limitArg(args)));
            data.put("categoryUsage", inventoryToolMapper.selectProductCategoryUsage(limitArg(args)));
            return new AiToolExecutionResult(tool, "Product master data analysis queried.", data);
        }
        if ("categoryBrandAnalysis".equals(tool))
        {
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("summary", inventoryToolMapper.selectCategoryBrandSummary());
            data.put("categoryUsage", inventoryToolMapper.selectCategoryUsageList(limitArg(args)));
            data.put("brandUsage", inventoryToolMapper.selectBrandUsageList(limitArg(args)));
            return new AiToolExecutionResult(tool, "Category and brand analysis queried.", data);
        }
        if ("enterpriseHealthCheck".equals(tool))
        {
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("summary", inventoryToolMapper.selectEnterpriseHealthSummary());
            data.put("incompleteEnterprises", inventoryToolMapper.selectEnterpriseIncompleteList(limitArg(args)));
            return new AiToolExecutionResult(tool, "Enterprise health check queried.", data);
        }
        if ("employeeHealthCheck".equals(tool))
        {
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("summary", inventoryToolMapper.selectEmployeeHealthSummary());
            data.put("deptSummary", inventoryToolMapper.selectEmployeeDeptSummary(limitArg(args)));
            data.put("incompleteEmployees", inventoryToolMapper.selectEmployeeIncompleteList(limitArg(args)));
            return new AiToolExecutionResult(tool, "Employee health check queried.", data);
        }
        if ("customerHealthCheck".equals(tool))
        {
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("summary", inventoryToolMapper.selectCustomerHealthSummary());
            data.put("incompleteCustomers", inventoryToolMapper.selectCustomerIncompleteList(limitArg(args)));
            return new AiToolExecutionResult(tool, "Customer health check queried.", data);
        }
        if ("inboundMonthlySummary".equals(tool))
        {
            DateRange range = dateRange(args);
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("summary", inventoryToolMapper.selectInboundMonthlySummary(range.beginDate, range.endDate,
                    longArg(args, "enterpriseId"), longArg(args, "warehouseId")));
            data.put("supplierRanking", inventoryToolMapper.selectInboundSupplierRanking(range.beginDate, range.endDate,
                    longArg(args, "enterpriseId"), longArg(args, "warehouseId"), limitArg(args)));
            return new AiToolExecutionResult(tool, "Inbound monthly summary queried.", data);
        }
        if ("inboundProductRanking".equals(tool))
        {
            DateRange range = dateRange(args);
            Object data = inventoryToolMapper.selectInboundProductRanking(range.beginDate, range.endDate,
                    longArg(args, "enterpriseId"), longArg(args, "warehouseId"), limitArg(args));
            return new AiToolExecutionResult(tool, "Inbound product ranking queried.", data);
        }
        if ("deliveryMonthlySummary".equals(tool))
        {
            DateRange range = dateRange(args);
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("summary", inventoryToolMapper.selectDeliveryMonthlySummary(range.beginDate, range.endDate,
                    longArg(args, "enterpriseId"), longArg(args, "warehouseId")));
            data.put("customerRanking", inventoryToolMapper.selectDeliveryCustomerRanking(range.beginDate, range.endDate,
                    longArg(args, "enterpriseId"), longArg(args, "warehouseId"), limitArg(args)));
            return new AiToolExecutionResult(tool, "Delivery monthly summary queried.", data);
        }
        if ("deliveryProductRanking".equals(tool))
        {
            DateRange range = dateRange(args);
            Object data = inventoryToolMapper.selectDeliveryProductRanking(range.beginDate, range.endDate,
                    longArg(args, "enterpriseId"), longArg(args, "warehouseId"), limitArg(args));
            return new AiToolExecutionResult(tool, "Delivery product ranking queried.", data);
        }
        if ("outboundMonthlySummary".equals(tool))
        {
            DateRange range = dateRange(args);
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("summary", inventoryToolMapper.selectOutboundMonthlySummary(range.beginDate, range.endDate,
                    longArg(args, "enterpriseId"), longArg(args, "warehouseId")));
            data.put("typeSummary", inventoryToolMapper.selectOutboundTypeSummary(range.beginDate, range.endDate,
                    longArg(args, "enterpriseId"), longArg(args, "warehouseId")));
            return new AiToolExecutionResult(tool, "Outbound monthly summary queried.", data);
        }
        if ("outboundProductRanking".equals(tool))
        {
            DateRange range = dateRange(args);
            Object data = inventoryToolMapper.selectOutboundProductRanking(range.beginDate, range.endDate,
                    longArg(args, "enterpriseId"), longArg(args, "warehouseId"), limitArg(args));
            return new AiToolExecutionResult(tool, "Outbound product ranking queried.", data);
        }
        if ("saleReturnMonthlySummary".equals(tool))
        {
            DateRange range = dateRange(args);
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("summary", inventoryToolMapper.selectSaleReturnMonthlySummary(range.beginDate, range.endDate,
                    longArg(args, "enterpriseId"), longArg(args, "warehouseId")));
            data.put("reasonSummary", inventoryToolMapper.selectSaleReturnReasonSummary(range.beginDate, range.endDate,
                    longArg(args, "enterpriseId"), longArg(args, "warehouseId")));
            return new AiToolExecutionResult(tool, "Sale return monthly summary queried.", data);
        }
        if ("saleReturnProductRanking".equals(tool))
        {
            DateRange range = dateRange(args);
            Object data = inventoryToolMapper.selectSaleReturnProductRanking(range.beginDate, range.endDate,
                    longArg(args, "enterpriseId"), longArg(args, "warehouseId"), limitArg(args));
            return new AiToolExecutionResult(tool, "Sale return product ranking queried.", data);
        }
        if ("sampleMonthlySummary".equals(tool))
        {
            DateRange range = dateRange(args);
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("summary", inventoryToolMapper.selectSampleMonthlySummary(range.beginDate, range.endDate,
                    longArg(args, "enterpriseId"), longArg(args, "warehouseId")));
            data.put("purposeSummary", inventoryToolMapper.selectSamplePurposeSummary(range.beginDate, range.endDate,
                    longArg(args, "enterpriseId"), longArg(args, "warehouseId")));
            return new AiToolExecutionResult(tool, "Sample monthly summary queried.", data);
        }
        if ("sampleProductRanking".equals(tool))
        {
            DateRange range = dateRange(args);
            Object data = inventoryToolMapper.selectSampleProductRanking(range.beginDate, range.endDate,
                    longArg(args, "enterpriseId"), longArg(args, "warehouseId"), limitArg(args));
            return new AiToolExecutionResult(tool, "Sample product ranking queried.", data);
        }
        throw new ServiceException("Unknown tool: " + tool);
    }

    public boolean supports(String tool)
    {
        return "inventoryCurrentSummary".equals(tool) || "inventoryByWarehouse".equals(tool)
                || "inventoryLowStockList".equals(tool) || "stockFlowTrend".equals(tool)
                || "stockFlowProductRanking".equals(tool) || "damageMonthlySummary".equals(tool)
                || "damageProductAnalysis".equals(tool) || "fixedAssetSummary".equals(tool)
                || "fixedAssetByDept".equals(tool) || "fixedAssetDepreciationTrend".equals(tool)
                || "fixedAssetRiskList".equals(tool) || "payrollMonthlySummary".equals(tool)
                || "payrollDeptCompare".equals(tool) || "payrollEmployeeRanking".equals(tool)
                || "payrollAbnormalCheck".equals(tool) || "payrollStatusSummary".equals(tool)
                || "masterDataHealthCheck".equals(tool) || "unitStatusSummary".equals(tool)
                || "warehouseLocationSummary".equals(tool) || "supplierHealthCheck".equals(tool)
                || "productMasterAnalysis".equals(tool) || "categoryBrandAnalysis".equals(tool)
                || "enterpriseHealthCheck".equals(tool) || "employeeHealthCheck".equals(tool)
                || "customerHealthCheck".equals(tool) || "inboundMonthlySummary".equals(tool)
                || "inboundProductRanking".equals(tool) || "deliveryMonthlySummary".equals(tool)
                || "deliveryProductRanking".equals(tool) || "outboundMonthlySummary".equals(tool)
                || "outboundProductRanking".equals(tool) || "saleReturnMonthlySummary".equals(tool)
                || "saleReturnProductRanking".equals(tool) || "sampleMonthlySummary".equals(tool)
                || "sampleProductRanking".equals(tool);
    }

    private DateRange dateRange(Map<String, Object> args)
    {
        String beginDate = stringArg(args, "beginDate");
        String endDate = stringArg(args, "endDate");
        if (StringUtils.isEmpty(beginDate) || StringUtils.isEmpty(endDate))
        {
            LocalDate today = LocalDate.now();
            beginDate = today.withDayOfMonth(1).format(DATE_FORMAT);
            endDate = today.format(DATE_FORMAT);
        }
        return new DateRange(beginDate, endDate);
    }

    private PeriodRange periodRange(Map<String, Object> args)
    {
        String beginPeriod = stringArg(args, "beginPeriod");
        String endPeriod = stringArg(args, "endPeriod");
        if (StringUtils.isEmpty(beginPeriod) || StringUtils.isEmpty(endPeriod))
        {
            YearMonth current = YearMonth.now();
            beginPeriod = current.minusMonths(5).toString();
            endPeriod = current.toString();
        }
        return new PeriodRange(beginPeriod, endPeriod);
    }

    private String periodCode(Map<String, Object> args)
    {
        String periodCode = stringArg(args, "periodCode");
        return StringUtils.isEmpty(periodCode) ? YearMonth.now().toString() : periodCode;
    }

    private Integer limitArg(Map<String, Object> args)
    {
        Long limit = longArg(args, "limit");
        if (limit == null)
        {
            return 10;
        }
        return Math.max(1, Math.min(50, limit.intValue()));
    }

    private Long longArg(Map<String, Object> args, String name)
    {
        if (args == null || args.get(name) == null)
        {
            return null;
        }
        Object value = args.get(name);
        if (value instanceof Number)
        {
            return ((Number) value).longValue();
        }
        String text = String.valueOf(value);
        if (StringUtils.isEmpty(text) || "null".equalsIgnoreCase(text))
        {
            return null;
        }
        return Long.valueOf(text);
    }

    private String stringArg(Map<String, Object> args, String name)
    {
        if (args == null || args.get(name) == null)
        {
            return null;
        }
        String text = String.valueOf(args.get(name)).trim();
        return StringUtils.isEmpty(text) || "null".equalsIgnoreCase(text) ? null : text;
    }

    private static class DateRange
    {
        private final String beginDate;
        private final String endDate;

        private DateRange(String beginDate, String endDate)
        {
            this.beginDate = beginDate;
            this.endDate = endDate;
        }
    }

    private static class PeriodRange
    {
        private final String beginPeriod;
        private final String endPeriod;

        private PeriodRange(String beginPeriod, String endPeriod)
        {
            this.beginPeriod = beginPeriod;
            this.endPeriod = endPeriod;
        }
    }
}
