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
 * 送货单对象 ims_delivery
 * 
 * @author tzq
 * @date 2026-06-02
 */
public class ImsDelivery extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

    /** 送货单号 */
    @Excel(name = "送货单号")
    private String billNo;

    /** 企业ID */
    @Excel(name = "企业ID", type = Excel.Type.IMPORT)
    private Long enterpriseId;

    /** 企业名称 */
    @Excel(name = "企业名称")
    private String enterpriseName;

    /** 发货仓库ID */
    @Excel(name = "发货仓库ID", type = Excel.Type.IMPORT)
    private Long warehouseId;

    /** 发货仓库 */
    @Excel(name = "发货仓库")
    private String warehouseName;

    /** 经手人 */
    @Excel(name = "经手人ID", type = Excel.Type.IMPORT)
    private Long handlerId;

    /** 经手人 */
    @Excel(name = "经手人")
    private String handlerName;

    /** 客户ID */
    @Excel(name = "客户ID", type = Excel.Type.IMPORT)
    private Long customerId;

    /** 客户名称 */
    @Excel(name = "客户名称")
    private String customerName;

    /** 开始日期 */
    private String beginTime;

    /** 结束日期 */
    private String endTime;

    /** 送货日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "送货日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date billDate;

    /** 送货类型 */
    @Excel(name = "送货类型")
    private String deliveryType;

    /** 总数量 */
    @Excel(name = "总数量")
    private BigDecimal totalQtyPiece;

    /** 总金额 */
    @Excel(name = "总金额")
    private BigDecimal totalAmount;

    /** 单据状态 */
    @Excel(name = "单据状态", dictType = "ims_bill_status")
    private String billStatus;

    /** 送货单明细信息 */
    private List<ImsDeliveryItem> imsDeliveryItemList;

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

    public String getBeginTime() 
    {
        return beginTime;
    }

    public void setBeginTime(String beginTime) 
    {
        this.beginTime = beginTime;
    }

    public String getEndTime() 
    {
        return endTime;
    }

    public void setEndTime(String endTime) 
    {
        this.endTime = endTime;
    }

    public void setBillDate(Date billDate) 
    {
        this.billDate = billDate;
    }

    public Date getBillDate() 
    {
        return billDate;
    }

    public void setDeliveryType(String deliveryType) 
    {
        this.deliveryType = deliveryType;
    }

    public String getDeliveryType() 
    {
        return deliveryType;
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

    public List<ImsDeliveryItem> getImsDeliveryItemList()
    {
        return imsDeliveryItemList;
    }

    public void setImsDeliveryItemList(List<ImsDeliveryItem> imsDeliveryItemList)
    {
        this.imsDeliveryItemList = imsDeliveryItemList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("billNo", getBillNo())
            .append("enterpriseId", getEnterpriseId())
            .append("enterpriseName", getEnterpriseName())
            .append("warehouseId", getWarehouseId())
            .append("warehouseName", getWarehouseName())
            .append("handlerId", getHandlerId())
            .append("handlerName", getHandlerName())
            .append("customerId", getCustomerId())
            .append("customerName", getCustomerName())
            .append("billDate", getBillDate())
            .append("deliveryType", getDeliveryType())
            .append("totalQtyPiece", getTotalQtyPiece())
            .append("totalAmount", getTotalAmount())
            .append("billStatus", getBillStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("imsDeliveryItemList", getImsDeliveryItemList())
            .toString();
    }
}
