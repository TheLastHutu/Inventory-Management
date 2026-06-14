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
 * 销售退货单对象 ims_sale_return
 * 
 * @author tzq
 * @date 2026-05-29
 */
public class ImsSaleReturn extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

    /** 退货单号 */
    @Excel(name = "退货单号")
    private String billNo;

    /** 退货日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "退货日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date billDate;

    /** 企业ID */
    @Excel(name = "企业ID", type = Excel.Type.IMPORT)
    private Long enterpriseId;

    /** 企业名称 */
    @Excel(name = "企业名称")
    private String enterpriseName;

    /** 客户ID */
    @Excel(name = "客户ID", type = Excel.Type.IMPORT)
    private Long customerId;

    /** 客户名称 */
    @Excel(name = "客户名称")
    private String customerName;

    /** 退货入库仓库ID */
    @Excel(name = "退货入库仓库ID", type = Excel.Type.IMPORT)
    private Long warehouseId;

    /** 退货入库仓库 */
    @Excel(name = "退货入库仓库")
    private String warehouseName;

    /** 原出库单号 */
    @Excel(name = "原出库单号")
    private String sourceOutboundNo;

    /** 退货原因 */
    @Excel(name = "退货原因")
    private String reason;

    /** 总数量(片) */
    @Excel(name = "总数量(片)")
    private BigDecimal totalQtyPiece;

    /** 总金额(按退货价) */
    @Excel(name = "总金额(按退货价)")
    private BigDecimal totalAmount;

    /** 单据状态 */
    @Excel(name = "单据状态", dictType = "ims_bill_status")
    private String billStatus;

    /** 销售退货明细信息 */
    private List<ImsSaleReturnItem> imsSaleReturnItemList;

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

    public void setSourceOutboundNo(String sourceOutboundNo) 
    {
        this.sourceOutboundNo = sourceOutboundNo;
    }

    public String getSourceOutboundNo() 
    {
        return sourceOutboundNo;
    }

    public void setReason(String reason) 
    {
        this.reason = reason;
    }

    public String getReason() 
    {
        return reason;
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

    public List<ImsSaleReturnItem> getImsSaleReturnItemList()
    {
        return imsSaleReturnItemList;
    }

    public void setImsSaleReturnItemList(List<ImsSaleReturnItem> imsSaleReturnItemList)
    {
        this.imsSaleReturnItemList = imsSaleReturnItemList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("billNo", getBillNo())
            .append("billDate", getBillDate())
            .append("enterpriseId", getEnterpriseId())
            .append("enterpriseName", getEnterpriseName())
            .append("customerId", getCustomerId())
            .append("customerName", getCustomerName())
            .append("warehouseId", getWarehouseId())
            .append("warehouseName", getWarehouseName())
            .append("sourceOutboundNo", getSourceOutboundNo())
            .append("reason", getReason())
            .append("totalQtyPiece", getTotalQtyPiece())
            .append("totalAmount", getTotalAmount())
            .append("billStatus", getBillStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("imsSaleReturnItemList", getImsSaleReturnItemList())
            .toString();
    }
}
