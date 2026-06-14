package com.ruoyi.ims.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 入库单对象 ims_inbound
 * 
 * @author tzq
 * @date 2026-05-29
 */
public class ImsInbound extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

    /** 入库单号 */
    @Excel(name = "入库单号")
    private String billNo;

    /** 入库日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "入库日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date billDate;

    /** 入库类型 */
    @Excel(name = "入库类型", dictType = "ims_inbound")
    private String inType;

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

    /** 供应商ID(采购入库用) */
    @Excel(name = "供应商ID", type = Excel.Type.IMPORT)
    private Long supplierId;

    /** 供应商名称 */
    @Excel(name = "供应商名称")
    private String supplierName;

    /** 客户ID(退货入库用) */
    @Excel(name = "客户ID", type = Excel.Type.IMPORT)
    private Long customerId;

    /** 客户名称 */
    @Excel(name = "客户名称")
    private String customerName;

    /** 经手人(员工ID) */
    @Excel(name = "经手人ID", type = Excel.Type.IMPORT)
    private Long handlerId;

    /** 经手人 */
    @Excel(name = "经手人")
    private String handlerName;

    /** 总数量(片) */
    @Excel(name = "总数量(片)")
    private BigDecimal totalQtyPiece;

    /** 总金额 */
    @Excel(name = "总金额")
    private BigDecimal totalAmount;

    /** 单据状态 */
    @Excel(name = "单据状态", dictType = "ims_bill_status")
    private String billStatus;

    /** 入库单明细信息 */
    private List<ImsInboundItem> imsInboundItemList;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setBillNo(String billNo) 
    {
        this.billNo = billNo;
    }

    public String getBillNo() 
    {
        return billNo;
    }

    public void setBillDate(Date billDate) 
    {
        this.billDate = billDate;
    }

    public Date getBillDate() 
    {
        return billDate;
    }

    public void setInType(String inType) 
    {
        this.inType = inType;
    }

    public String getInType() 
    {
        return inType;
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

    public void setCustomerId(Long customerId) 
    {
        this.customerId = customerId;
    }

    public Long getCustomerId() 
    {
        return customerId;
    }

    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }

    public String getCustomerName()
    {
        return customerName;
    }

    public void setHandlerId(Long handlerId) 
    {
        this.handlerId = handlerId;
    }

    public Long getHandlerId() 
    {
        return handlerId;
    }

    public void setHandlerName(String handlerName)
    {
        this.handlerName = handlerName;
    }

    public String getHandlerName()
    {
        return handlerName;
    }

    public void setTotalQtyPiece(BigDecimal totalQtyPiece) 
    {
        this.totalQtyPiece = totalQtyPiece;
    }

    public BigDecimal getTotalQtyPiece() 
    {
        return totalQtyPiece;
    }

    public void setTotalAmount(BigDecimal totalAmount) 
    {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTotalAmount() 
    {
        return totalAmount;
    }

    public void setBillStatus(String billStatus) 
    {
        this.billStatus = billStatus;
    }

    public String getBillStatus() 
    {
        return billStatus;
    }

    public List<ImsInboundItem> getImsInboundItemList()
    {
        return imsInboundItemList;
    }

    public void setImsInboundItemList(List<ImsInboundItem> imsInboundItemList)
    {
        this.imsInboundItemList = imsInboundItemList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("billNo", getBillNo())
            .append("billDate", getBillDate())
            .append("inType", getInType())
            .append("enterpriseId", getEnterpriseId())
            .append("enterpriseName", getEnterpriseName())
            .append("warehouseId", getWarehouseId())
            .append("warehouseName", getWarehouseName())
            .append("supplierId", getSupplierId())
            .append("supplierName", getSupplierName())
            .append("customerId", getCustomerId())
            .append("customerName", getCustomerName())
            .append("handlerId", getHandlerId())
            .append("handlerName", getHandlerName())
            .append("totalQtyPiece", getTotalQtyPiece())
            .append("totalAmount", getTotalAmount())
            .append("billStatus", getBillStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("imsInboundItemList", getImsInboundItemList())
            .toString();
    }
}
