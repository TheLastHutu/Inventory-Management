package com.ruoyi.ims.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 固定资产对象 ims_fixed_asset
 * 
 * @author tzq
 * @date 2026-05-29
 */
public class ImsFixedAsset extends BaseEntity
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

    /** 资产编号 */
    @Excel(name = "资产编号")
    private String assetCode;

    /** 资产名称 */
    @Excel(name = "资产名称")
    private String assetName;

    /** 资产类别 */
    @Excel(name = "资产类别")
    private String assetCategory;

    /** 购置日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "购置日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date purchaseDate;

    /** 原值 */
    @Excel(name = "原值")
    private BigDecimal originalValue;

    /** 净值 */
    @Excel(name = "净值")
    private BigDecimal netValue;

    /** 使用部门 */
    @Excel(name = "使用部门")
    private String useDept;

    /** 保管人 */
    @Excel(name = "保管人")
    private String keeper;

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

    public void setAssetCode(String assetCode) 
    {
        this.assetCode = assetCode;
    }

    public String getAssetCode() 
    {
        return assetCode;
    }

    public void setAssetName(String assetName) 
    {
        this.assetName = assetName;
    }

    public String getAssetName() 
    {
        return assetName;
    }

    public void setAssetCategory(String assetCategory) 
    {
        this.assetCategory = assetCategory;
    }

    public String getAssetCategory() 
    {
        return assetCategory;
    }

    public void setPurchaseDate(Date purchaseDate) 
    {
        this.purchaseDate = purchaseDate;
    }

    public Date getPurchaseDate() 
    {
        return purchaseDate;
    }

    public void setOriginalValue(BigDecimal originalValue) 
    {
        this.originalValue = originalValue;
    }

    public BigDecimal getOriginalValue() 
    {
        return originalValue;
    }

    public void setNetValue(BigDecimal netValue) 
    {
        this.netValue = netValue;
    }

    public BigDecimal getNetValue() 
    {
        return netValue;
    }

    public void setUseDept(String useDept) 
    {
        this.useDept = useDept;
    }

    public String getUseDept() 
    {
        return useDept;
    }

    public void setKeeper(String keeper) 
    {
        this.keeper = keeper;
    }

    public String getKeeper() 
    {
        return keeper;
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
            .append("assetCode", getAssetCode())
            .append("assetName", getAssetName())
            .append("assetCategory", getAssetCategory())
            .append("purchaseDate", getPurchaseDate())
            .append("originalValue", getOriginalValue())
            .append("netValue", getNetValue())
            .append("useDept", getUseDept())
            .append("keeper", getKeeper())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
