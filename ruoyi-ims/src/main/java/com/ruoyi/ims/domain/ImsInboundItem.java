package com.ruoyi.ims.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 入库单明细对象 ims_inbound_item
 * 
 * @author tzq
 * @date 2026-05-29
 */
public class ImsInboundItem extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 入库单ID */
    @Excel(name = "入库单ID", type = Excel.Type.IMPORT)
    private Long inboundId;

    /** 行号 */
    @Excel(name = "行号", type = Excel.Type.IMPORT)
    private Long lineNo;

    /** 产品ID */
    @Excel(name = "产品ID", type = Excel.Type.IMPORT)
    private Long productId;

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
    public void setInboundId(Long inboundId) 
    {
        this.inboundId = inboundId;
    }

    public Long getInboundId() 
    {
        return inboundId;
    }
    public void setLineNo(Long lineNo) 
    {
        this.lineNo = lineNo;
    }

    public Long getLineNo() 
    {
        return lineNo;
    }
    public void setProductId(Long productId) 
    {
        this.productId = productId;
    }

    public Long getProductId() 
    {
        return productId;
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
            .append("inboundId", getInboundId())
            .append("lineNo", getLineNo())
            .append("productId", getProductId())
            .append("qtyPiece", getQtyPiece())
            .append("unitPrice", getUnitPrice())
            .append("amount", getAmount())
            .append("remark", getRemark())
            .toString();
    }
}
