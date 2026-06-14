package com.ruoyi.ims.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 库存(现存量)对象 ims_inventory
 * 
 * @author tzq
 * @date 2026-05-29
 */
public class ImsInventory extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

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

    /** 现存量(片) */
    @Excel(name = "现存量(片)")
    private BigDecimal qtyPiece;

    /** 锁定量(片) */
    @Excel(name = "锁定量(片)")
    private BigDecimal lockedQtyPiece;

    /** 可用量(片) */
    @Excel(name = "可用量(片)")
    private BigDecimal availQtyPiece;

    /** 状态 */
    @Excel(name = "状态", dictType = "ims_status")
    private String status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
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

    public void setQtyPiece(BigDecimal qtyPiece) 
    {
        this.qtyPiece = qtyPiece;
    }

    public BigDecimal getQtyPiece() 
    {
        return qtyPiece;
    }

    public void setLockedQtyPiece(BigDecimal lockedQtyPiece) 
    {
        this.lockedQtyPiece = lockedQtyPiece;
    }

    public BigDecimal getLockedQtyPiece() 
    {
        return lockedQtyPiece;
    }

    public void setAvailQtyPiece(BigDecimal availQtyPiece) 
    {
        this.availQtyPiece = availQtyPiece;
    }

    public BigDecimal getAvailQtyPiece() 
    {
        return availQtyPiece;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("enterpriseId", getEnterpriseId())
            .append("enterpriseName", getEnterpriseName())
            .append("warehouseId", getWarehouseId())
            .append("warehouseName", getWarehouseName())
            .append("locationId", getLocationId())
            .append("locationName", getLocationName())
            .append("productId", getProductId())
            .append("productName", getProductName())
            .append("qtyPiece", getQtyPiece())
            .append("lockedQtyPiece", getLockedQtyPiece())
            .append("availQtyPiece", getAvailQtyPiece())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
