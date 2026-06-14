package com.ruoyi.ims.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 库存流水对象 ims_stock_flow
 * 
 * @author tzq
 * @date 2026-05-29
 */
public class ImsStockFlow extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

    /** 业务单据编号 */
    @Excel(name = "业务单据编号")
    private String bizNo;

    /** 业务类型 */
    @Excel(name = "业务类型", dictType = "ims_business_type")
    private String bizType;

    /** 业务单据ID */
    @Excel(name = "业务单据ID", type = Excel.Type.IMPORT)
    private Long bizId;

    /** 企业ID */
    @Excel(name = "企业ID", type = Excel.Type.IMPORT)
    private Long enterpriseId;

    /** 企业名称 */
    @Excel(name = "企业名称")
    private String enterpriseName;

    /** 仓库ID */
    @Excel(name = "仓库ID", type = Excel.Type.IMPORT)
    private Long warehouseId;

    /** 仓库名称 */
    @Excel(name = "仓库名称")
    private String warehouseName;

    /** 货位ID */
    @Excel(name = "货位ID", type = Excel.Type.IMPORT)
    private Long locationId;

    /** 货位名称 */
    @Excel(name = "货位名称")
    private String locationName;

    /** 产品ID */
    @Excel(name = "产品ID", type = Excel.Type.IMPORT)
    private Long productId;

    /** 产品名称 */
    @Excel(name = "产品名称")
    private String productName;

    /** 发生时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发生时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date occurTime;

    /** 方向 */
    @Excel(name = "方向", dictType = "ims_inout")
    private String inout;

    /** 数量(片) */
    @Excel(name = "数量(片)")
    private BigDecimal qtyPiece;

    /** 单价(片) */
    @Excel(name = "单价(片)")
    private BigDecimal unitPrice;

    /** 金额 */
    @Excel(name = "金额")
    private BigDecimal amount;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setBizNo(String bizNo) 
    {
        this.bizNo = bizNo;
    }

    public String getBizNo() 
    {
        return bizNo;
    }

    public void setBizType(String bizType) 
    {
        this.bizType = bizType;
    }

    public String getBizType() 
    {
        return bizType;
    }

    public void setBizId(Long bizId) 
    {
        this.bizId = bizId;
    }

    public Long getBizId() 
    {
        return bizId;
    }

    public void setEnterpriseId(Long enterpriseId) 
    {
        this.enterpriseId = enterpriseId;
    }

    public Long getEnterpriseId() 
    {
        return enterpriseId;
    }

    public void setEnterpriseName(String enterpriseName)
    {
        this.enterpriseName = enterpriseName;
    }

    public String getEnterpriseName()
    {
        return enterpriseName;
    }

    public void setWarehouseId(Long warehouseId) 
    {
        this.warehouseId = warehouseId;
    }

    public Long getWarehouseId() 
    {
        return warehouseId;
    }

    public void setWarehouseName(String warehouseName)
    {
        this.warehouseName = warehouseName;
    }

    public String getWarehouseName()
    {
        return warehouseName;
    }

    public void setLocationId(Long locationId) 
    {
        this.locationId = locationId;
    }

    public Long getLocationId() 
    {
        return locationId;
    }

    public void setLocationName(String locationName)
    {
        this.locationName = locationName;
    }

    public String getLocationName()
    {
        return locationName;
    }

    public void setProductId(Long productId) 
    {
        this.productId = productId;
    }

    public Long getProductId() 
    {
        return productId;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public String getProductName()
    {
        return productName;
    }

    public void setOccurTime(Date occurTime) 
    {
        this.occurTime = occurTime;
    }

    public Date getOccurTime() 
    {
        return occurTime;
    }

    public void setInout(String inout) 
    {
        this.inout = inout;
    }

    public String getInout() 
    {
        return inout;
    }

    public void setQtyPiece(BigDecimal qtyPiece) 
    {
        this.qtyPiece = qtyPiece;
    }

    public BigDecimal getQtyPiece() 
    {
        return qtyPiece;
    }

    public void setUnitPrice(BigDecimal unitPrice) 
    {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getUnitPrice() 
    {
        return unitPrice;
    }

    public void setAmount(BigDecimal amount) 
    {
        this.amount = amount;
    }

    public BigDecimal getAmount() 
    {
        return amount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("bizNo", getBizNo())
            .append("bizType", getBizType())
            .append("bizId", getBizId())
            .append("enterpriseId", getEnterpriseId())
            .append("enterpriseName", getEnterpriseName())
            .append("warehouseId", getWarehouseId())
            .append("warehouseName", getWarehouseName())
            .append("locationId", getLocationId())
            .append("locationName", getLocationName())
            .append("productId", getProductId())
            .append("productName", getProductName())
            .append("occurTime", getOccurTime())
            .append("inout", getInout())
            .append("qtyPiece", getQtyPiece())
            .append("unitPrice", getUnitPrice())
            .append("amount", getAmount())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("remark", getRemark())
            .toString();
    }
}
