package com.ruoyi.ims.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 销售退货明细对象 ims_sale_return_item
 * 
 * @author tzq
 * @date 2026-05-29
 */
public class ImsSaleReturnItem extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 退货单ID */
    @Excel(name = "退货单ID", type = Excel.Type.IMPORT)
    private Long saleReturnId;

    /** 行号 */
    @Excel(name = "行号", type = Excel.Type.IMPORT)
    private Long lineNo;

    /** 产品ID */
    @Excel(name = "产品ID", type = Excel.Type.IMPORT)
    private Long productId;

    /** 数量(片) */
    @Excel(name = "数量(片)")
    private BigDecimal qtyPiece;

    /** 退货单价(片) */
    @Excel(name = "退货单价(片)")
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
    public void setSaleReturnId(Long saleReturnId) 
    {
        this.saleReturnId = saleReturnId;
    }

    public Long getSaleReturnId() 
    {
        return saleReturnId;
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
            .append("saleReturnId", getSaleReturnId())
            .append("lineNo", getLineNo())
            .append("productId", getProductId())
            .append("qtyPiece", getQtyPiece())
            .append("unitPrice", getUnitPrice())
            .append("amount", getAmount())
            .append("remark", getRemark())
            .toString();
    }
}
