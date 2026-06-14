package com.ruoyi.ai.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface AiInventoryToolMapper
{
    Map<String, Object> selectInventoryCurrentSummary(@Param("enterpriseId") Long enterpriseId,
            @Param("warehouseId") Long warehouseId);

    List<Map<String, Object>> selectInventoryByWarehouse(@Param("enterpriseId") Long enterpriseId);

    List<Map<String, Object>> selectInventoryLowStockList(@Param("enterpriseId") Long enterpriseId,
            @Param("warehouseId") Long warehouseId, @Param("limit") Integer limit);

    List<Map<String, Object>> selectStockFlowTrend(@Param("beginDate") String beginDate,
            @Param("endDate") String endDate, @Param("enterpriseId") Long enterpriseId,
            @Param("warehouseId") Long warehouseId);

    List<Map<String, Object>> selectStockFlowProductRanking(@Param("beginDate") String beginDate,
            @Param("endDate") String endDate, @Param("enterpriseId") Long enterpriseId,
            @Param("warehouseId") Long warehouseId, @Param("limit") Integer limit);

    Map<String, Object> selectDamageMonthlySummary(@Param("beginDate") String beginDate,
            @Param("endDate") String endDate, @Param("enterpriseId") Long enterpriseId,
            @Param("warehouseId") Long warehouseId);

    List<Map<String, Object>> selectDamageReasonSummary(@Param("beginDate") String beginDate,
            @Param("endDate") String endDate, @Param("enterpriseId") Long enterpriseId,
            @Param("warehouseId") Long warehouseId);

    List<Map<String, Object>> selectDamageProductAnalysis(@Param("beginDate") String beginDate,
            @Param("endDate") String endDate, @Param("enterpriseId") Long enterpriseId,
            @Param("warehouseId") Long warehouseId, @Param("limit") Integer limit);

    Map<String, Object> selectFixedAssetSummary(@Param("enterpriseId") Long enterpriseId);

    List<Map<String, Object>> selectFixedAssetByDept(@Param("enterpriseId") Long enterpriseId);

    List<Map<String, Object>> selectFixedAssetDepreciationTrend(@Param("beginPeriod") String beginPeriod,
            @Param("endPeriod") String endPeriod, @Param("enterpriseId") Long enterpriseId);

    List<Map<String, Object>> selectFixedAssetRiskList(@Param("enterpriseId") Long enterpriseId,
            @Param("limit") Integer limit);

    Map<String, Object> selectPayrollMonthlySummary(@Param("periodCode") String periodCode,
            @Param("deptId") Long deptId);

    List<Map<String, Object>> selectPayrollDeptCompare(@Param("periodCode") String periodCode);

    List<Map<String, Object>> selectPayrollEmployeeRanking(@Param("periodCode") String periodCode,
            @Param("limit") Integer limit);

    List<Map<String, Object>> selectPayrollAbnormalCheck(@Param("periodCode") String periodCode,
            @Param("limit") Integer limit);

    List<Map<String, Object>> selectPayrollStatusSummary(@Param("periodCode") String periodCode);

    Map<String, Object> selectMasterDataHealthSummary();

    Map<String, Object> selectUnitStatusSummary();

    List<Map<String, Object>> selectUnitProductUsage(@Param("limit") Integer limit);

    Map<String, Object> selectWarehouseLocationSummary(@Param("enterpriseId") Long enterpriseId,
            @Param("warehouseId") Long warehouseId);

    List<Map<String, Object>> selectWarehouseLocationDetail(@Param("enterpriseId") Long enterpriseId,
            @Param("warehouseId") Long warehouseId, @Param("limit") Integer limit);

    Map<String, Object> selectSupplierHealthSummary();

    List<Map<String, Object>> selectSupplierIncompleteList(@Param("limit") Integer limit);

    Map<String, Object> selectProductMasterSummary();

    List<Map<String, Object>> selectProductIncompleteList(@Param("limit") Integer limit);

    List<Map<String, Object>> selectProductCategoryUsage(@Param("limit") Integer limit);

    Map<String, Object> selectCategoryBrandSummary();

    List<Map<String, Object>> selectCategoryUsageList(@Param("limit") Integer limit);

    List<Map<String, Object>> selectBrandUsageList(@Param("limit") Integer limit);

    Map<String, Object> selectEnterpriseHealthSummary();

    List<Map<String, Object>> selectEnterpriseIncompleteList(@Param("limit") Integer limit);

    Map<String, Object> selectEmployeeHealthSummary();

    List<Map<String, Object>> selectEmployeeDeptSummary(@Param("limit") Integer limit);

    List<Map<String, Object>> selectEmployeeIncompleteList(@Param("limit") Integer limit);

    Map<String, Object> selectCustomerHealthSummary();

    List<Map<String, Object>> selectCustomerIncompleteList(@Param("limit") Integer limit);

    Map<String, Object> selectInboundMonthlySummary(@Param("beginDate") String beginDate,
            @Param("endDate") String endDate, @Param("enterpriseId") Long enterpriseId,
            @Param("warehouseId") Long warehouseId);

    List<Map<String, Object>> selectInboundSupplierRanking(@Param("beginDate") String beginDate,
            @Param("endDate") String endDate, @Param("enterpriseId") Long enterpriseId,
            @Param("warehouseId") Long warehouseId, @Param("limit") Integer limit);

    List<Map<String, Object>> selectInboundProductRanking(@Param("beginDate") String beginDate,
            @Param("endDate") String endDate, @Param("enterpriseId") Long enterpriseId,
            @Param("warehouseId") Long warehouseId, @Param("limit") Integer limit);

    Map<String, Object> selectDeliveryMonthlySummary(@Param("beginDate") String beginDate,
            @Param("endDate") String endDate, @Param("enterpriseId") Long enterpriseId,
            @Param("warehouseId") Long warehouseId);

    List<Map<String, Object>> selectDeliveryCustomerRanking(@Param("beginDate") String beginDate,
            @Param("endDate") String endDate, @Param("enterpriseId") Long enterpriseId,
            @Param("warehouseId") Long warehouseId, @Param("limit") Integer limit);

    List<Map<String, Object>> selectDeliveryProductRanking(@Param("beginDate") String beginDate,
            @Param("endDate") String endDate, @Param("enterpriseId") Long enterpriseId,
            @Param("warehouseId") Long warehouseId, @Param("limit") Integer limit);

    Map<String, Object> selectOutboundMonthlySummary(@Param("beginDate") String beginDate,
            @Param("endDate") String endDate, @Param("enterpriseId") Long enterpriseId,
            @Param("warehouseId") Long warehouseId);

    List<Map<String, Object>> selectOutboundTypeSummary(@Param("beginDate") String beginDate,
            @Param("endDate") String endDate, @Param("enterpriseId") Long enterpriseId,
            @Param("warehouseId") Long warehouseId);

    List<Map<String, Object>> selectOutboundProductRanking(@Param("beginDate") String beginDate,
            @Param("endDate") String endDate, @Param("enterpriseId") Long enterpriseId,
            @Param("warehouseId") Long warehouseId, @Param("limit") Integer limit);

    Map<String, Object> selectSaleReturnMonthlySummary(@Param("beginDate") String beginDate,
            @Param("endDate") String endDate, @Param("enterpriseId") Long enterpriseId,
            @Param("warehouseId") Long warehouseId);

    List<Map<String, Object>> selectSaleReturnReasonSummary(@Param("beginDate") String beginDate,
            @Param("endDate") String endDate, @Param("enterpriseId") Long enterpriseId,
            @Param("warehouseId") Long warehouseId);

    List<Map<String, Object>> selectSaleReturnProductRanking(@Param("beginDate") String beginDate,
            @Param("endDate") String endDate, @Param("enterpriseId") Long enterpriseId,
            @Param("warehouseId") Long warehouseId, @Param("limit") Integer limit);

    Map<String, Object> selectSampleMonthlySummary(@Param("beginDate") String beginDate,
            @Param("endDate") String endDate, @Param("enterpriseId") Long enterpriseId,
            @Param("warehouseId") Long warehouseId);

    List<Map<String, Object>> selectSamplePurposeSummary(@Param("beginDate") String beginDate,
            @Param("endDate") String endDate, @Param("enterpriseId") Long enterpriseId,
            @Param("warehouseId") Long warehouseId);

    List<Map<String, Object>> selectSampleProductRanking(@Param("beginDate") String beginDate,
            @Param("endDate") String endDate, @Param("enterpriseId") Long enterpriseId,
            @Param("warehouseId") Long warehouseId, @Param("limit") Integer limit);
}
