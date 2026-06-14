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
 * 报损单对象 ims_damage
 * 
 * @author tzq
 * @date 2026-05-29
 */
public class ImsDamage extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

    /** 报损单号 */
    @Excel(name = "报损单号")
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

    /** 报损日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "报损日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date billDate;

    /** 报损原因 */
    @Excel(name = "报损原因")
    private String reason;

    /** 总数量(片) */
    @Excel(name = "总数量(片)")
    private BigDecimal totalQtyPiece;

    /** 总金额(按成本) */
    @Excel(name = "总金额(按成本)")
    private BigDecimal totalAmount;

    /** 单据状态 */
    @Excel(name = "单据状态", dictType = "ims_bill_status")
    private String billStatus;

    /** 报损明细信息 */
    private List<ImsDamageItem> imsDamageItemList;

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

    public void setBillDate(Date billDate) 
    {
        this.billDate = billDate;
    }

    public Date getBillDate() 
    {
        return billDate;
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

    public List<ImsDamageItem> getImsDamageItemList()
    {
        return imsDamageItemList;
    }

    public void setImsDamageItemList(List<ImsDamageItem> imsDamageItemList)
    {
        this.imsDamageItemList = imsDamageItemList;
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
            .append("billDate", getBillDate())
            .append("reason", getReason())
            .append("totalQtyPiece", getTotalQtyPiece())
            .append("totalAmount", getTotalAmount())
            .append("billStatus", getBillStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("imsDamageItemList", getImsDamageItemList())
            .toString();
    }
}
