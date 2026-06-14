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
 * 样品单对象 ims_sample
 * 
 * @author tzq
 * @date 2026-05-29
 */
public class ImsSample extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

    /** 样品单号 */
    @Excel(name = "样品单号")
    private String billNo;

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

    /** 客户ID */
    @Excel(name = "客户ID", type = Excel.Type.IMPORT)
    private Long customerId;

    /** 客户名称 */
    @Excel(name = "客户名称")
    private String customerName;

    /** 日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date billDate;

    /** 领用人/接收人 */
    @Excel(name = "领用人/接收人")
    private String receiver;

    /** 用途 */
    @Excel(name = "用途")
    private String purpose;

    /** 总数量(片) */
    @Excel(name = "总数量(片)")
    private BigDecimal totalQtyPiece;

    /** 单据状态 */
    @Excel(name = "单据状态", dictType = "ims_bill_status")
    private String billStatus;

    /** 样品明细信息 */
    private List<ImsSampleItem> imsSampleItemList;

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

    public void setBillDate(Date billDate) 
    {
        this.billDate = billDate;
    }

    public Date getBillDate() 
    {
        return billDate;
    }

    public void setReceiver(String receiver) 
    {
        this.receiver = receiver;
    }

    public String getReceiver() 
    {
        return receiver;
    }

    public void setPurpose(String purpose) 
    {
        this.purpose = purpose;
    }

    public String getPurpose() 
    {
        return purpose;
    }

    public void setTotalQtyPiece(BigDecimal totalQtyPiece) 
    {
        this.totalQtyPiece = totalQtyPiece;
    }

    public BigDecimal getTotalQtyPiece() 
    {
        return totalQtyPiece;
    }

    public void setBillStatus(String billStatus) 
    {
        this.billStatus = billStatus;
    }

    public String getBillStatus() 
    {
        return billStatus;
    }

    public List<ImsSampleItem> getImsSampleItemList()
    {
        return imsSampleItemList;
    }

    public void setImsSampleItemList(List<ImsSampleItem> imsSampleItemList)
    {
        this.imsSampleItemList = imsSampleItemList;
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
            .append("customerId", getCustomerId())
            .append("customerName", getCustomerName())
            .append("billDate", getBillDate())
            .append("receiver", getReceiver())
            .append("purpose", getPurpose())
            .append("totalQtyPiece", getTotalQtyPiece())
            .append("billStatus", getBillStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("imsSampleItemList", getImsSampleItemList())
            .toString();
    }
}
