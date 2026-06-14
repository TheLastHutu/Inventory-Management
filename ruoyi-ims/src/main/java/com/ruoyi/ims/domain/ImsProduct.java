package com.ruoyi.ims.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 产品管理对象 ims_product
 * 
 * @author tzq
 * @date 2026-05-29
 */
public class ImsProduct extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

    /** 产品编号 */
    @Excel(name = "产品编号")
    private String productCode;

    /** 产品名称 */
    @Excel(name = "产品名称")
    private String productName;

    /** 分类ID */
    @Excel(name = "分类ID", type = Excel.Type.IMPORT)
    private Long categoryId;

    /** 分类名称 */
    @Excel(name = "分类名称")
    private String categoryName;

    /** 供应商ID */
    @Excel(name = "供应商ID", type = Excel.Type.IMPORT)
    private Long supplierId;

    /** 供应商名称 */
    @Excel(name = "供应商名称")
    private String supplierName;

    /** 单位ID */
    @Excel(name = "单位ID", type = Excel.Type.IMPORT)
    private Long unitId;

    /** 单位名称 */
    @Excel(name = "单位名称")
    private String unitName;

    /** 品牌ID */
    @Excel(name = "品牌ID", type = Excel.Type.IMPORT)
    private Long brandId;

    /** 品牌名称 */
    @Excel(name = "品牌名称")
    private String brandName;

    /** 规格 */
    @Excel(name = "规格")
    private String spec;

    /** 色号 */
    @Excel(name = "色号")
    private String colorNo;

    /** 等级 */
    @Excel(name = "等级")
    private String grade;

    /** 装箱系数 */
    @Excel(name = "装箱系数")
    private BigDecimal boxRate;

    /** 参考进价/成本单价(片) */
    @Excel(name = "参考进价/成本单价(片)")
    private BigDecimal purchasePrice;

    /** 参考售价单价(片) */
    @Excel(name = "参考售价单价(片)")
    private BigDecimal salePrice;

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

    public void setProductCode(String productCode) 
    {
        this.productCode = productCode;
    }

    public String getProductCode() 
    {
        return productCode;
    }

    public void setProductName(String productName) 
    {
        this.productName = productName;
    }

    public String getProductName() 
    {
        return productName;
    }

    public void setCategoryId(Long categoryId) 
    {
        this.categoryId = categoryId;
    }

    public Long getCategoryId() 
    {
        return categoryId;
    }

    public void setCategoryName(String categoryName)
    {
        this.categoryName = categoryName;
    }

    public String getCategoryName()
    {
        return categoryName;
    }

    public void setSupplierId(Long supplierId) 
    {
        this.supplierId = supplierId;
    }

    public Long getSupplierId() 
    {
        return supplierId;
    }

    public void setSupplierName(String supplierName)
    {
        this.supplierName = supplierName;
    }

    public String getSupplierName()
    {
        return supplierName;
    }

    public void setUnitId(Long unitId) 
    {
        this.unitId = unitId;
    }

    public Long getUnitId() 
    {
        return unitId;
    }

    public void setUnitName(String unitName)
    {
        this.unitName = unitName;
    }

    public String getUnitName()
    {
        return unitName;
    }

    public void setBrandId(Long brandId) 
    {
        this.brandId = brandId;
    }

    public Long getBrandId() 
    {
        return brandId;
    }

    public void setBrandName(String brandName)
    {
        this.brandName = brandName;
    }

    public String getBrandName()
    {
        return brandName;
    }

    public void setSpec(String spec) 
    {
        this.spec = spec;
    }

    public String getSpec() 
    {
        return spec;
    }

    public void setColorNo(String colorNo) 
    {
        this.colorNo = colorNo;
    }

    public String getColorNo() 
    {
        return colorNo;
    }

    public void setGrade(String grade) 
    {
        this.grade = grade;
    }

    public String getGrade() 
    {
        return grade;
    }

    public void setBoxRate(BigDecimal boxRate) 
    {
        this.boxRate = boxRate;
    }

    public BigDecimal getBoxRate() 
    {
        return boxRate;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) 
    {
        this.purchasePrice = purchasePrice;
    }

    public BigDecimal getPurchasePrice() 
    {
        return purchasePrice;
    }

    public void setSalePrice(BigDecimal salePrice) 
    {
        this.salePrice = salePrice;
    }

    public BigDecimal getSalePrice() 
    {
        return salePrice;
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
            .append("productCode", getProductCode())
            .append("productName", getProductName())
            .append("categoryId", getCategoryId())
            .append("categoryName", getCategoryName())
            .append("supplierId", getSupplierId())
            .append("supplierName", getSupplierName())
            .append("unitId", getUnitId())
            .append("unitName", getUnitName())
            .append("brandId", getBrandId())
            .append("brandName", getBrandName())
            .append("spec", getSpec())
            .append("colorNo", getColorNo())
            .append("grade", getGrade())
            .append("boxRate", getBoxRate())
            .append("purchasePrice", getPurchasePrice())
            .append("salePrice", getSalePrice())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
