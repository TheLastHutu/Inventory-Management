package com.ruoyi.ims.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 样品明细对象 ims_sample_item
 * 
 * @author tzq
 * @date 2026-05-29
 */
public class ImsSampleItem extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 样品单ID */
    @Excel(name = "样品单ID", type = Excel.Type.IMPORT)
    private Long sampleId;

    /** 行号 */
    @Excel(name = "行号", type = Excel.Type.IMPORT)
    private Long lineNo;

    /** 产品ID */
    @Excel(name = "产品ID", type = Excel.Type.IMPORT)
    private Long productId;

    /** 数量(片) */
    @Excel(name = "数量(片)")
    private BigDecimal qtyPiece;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setSampleId(Long sampleId) 
    {
        this.sampleId = sampleId;
    }

    public Long getSampleId() 
    {
        return sampleId;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("sampleId", getSampleId())
            .append("lineNo", getLineNo())
            .append("productId", getProductId())
            .append("qtyPiece", getQtyPiece())
            .append("remark", getRemark())
            .toString();
    }
}
