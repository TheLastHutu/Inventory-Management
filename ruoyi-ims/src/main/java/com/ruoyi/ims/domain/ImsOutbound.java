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
 * 出库单对象 ims_outbound
 * 
 * @author tzq
 * @date 2026-05-29
 */
public class ImsOutbound extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

    /** 出库单号 */
    @Excel(name = "出库单号")
    private String billNo;

    /** 出库日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出库日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date billDate;

    /** 出库类型 */
    @Excel(name = "出库类型", dictType = "ims_outbound")
    private String outType;

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

    /** 客户ID(销售/样品用) */
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

    /** 出库单明细信息 */
    private List<ImsOutboundItem> imsOutboundItemList;

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

    public void setOutType(String outType) 
    {
        this.outType = outType;
    }

    public String getOutType() 
    {
        return outType;
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

    public List<ImsOutboundItem> getImsOutboundItemList()
    {
        return imsOutboundItemList;
    }

    public void setImsOutboundItemList(List<ImsOutboundItem> imsOutboundItemList)
    {
        this.imsOutboundItemList = imsOutboundItemList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("billNo", getBillNo())
            .append("billDate", getBillDate())
            .append("outType", getOutType())
            .append("enterpriseId", getEnterpriseId())
            .append("enterpriseName", getEnterpriseName())
            .append("warehouseId", getWarehouseId())
            .append("warehouseName", getWarehouseName())
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
            .append("imsOutboundItemList", getImsOutboundItemList())
            .toString();
    }
}
