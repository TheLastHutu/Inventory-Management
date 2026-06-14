package com.ruoyi.ims.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 企业管理对象 ims_enterprise
 * 
 * @author tzq
 * @date 2026-05-29
 */
public class ImsEnterprise extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

    /** 企业编码 */
    @Excel(name = "企业编码")
    private String enterpriseCode;

    /** 企业名称 */
    @Excel(name = "企业名称")
    private String enterpriseName;

    /** 法人/负责人 */
    @Excel(name = "法人/负责人")
    private String legalPerson;

    /** 联系电话 */
    @Excel(name = "联系电话")
    private String phone;

    /** 税号 */
    @Excel(name = "税号")
    private String taxNo;

    /** 开户行 */
    @Excel(name = "开户行")
    private String bankName;

    /** 银行账号 */
    @Excel(name = "银行账号")
    private String bankAccount;

    /** 地址 */
    @Excel(name = "地址")
    private String address;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setEnterpriseCode(String enterpriseCode) 
    {
        this.enterpriseCode = enterpriseCode;
    }

    public String getEnterpriseCode() 
    {
        return enterpriseCode;
    }

    public void setEnterpriseName(String enterpriseName) 
    {
        this.enterpriseName = enterpriseName;
    }

    public String getEnterpriseName() 
    {
        return enterpriseName;
    }

    public void setLegalPerson(String legalPerson) 
    {
        this.legalPerson = legalPerson;
    }

    public String getLegalPerson() 
    {
        return legalPerson;
    }

    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getPhone() 
    {
        return phone;
    }

    public void setTaxNo(String taxNo) 
    {
        this.taxNo = taxNo;
    }

    public String getTaxNo() 
    {
        return taxNo;
    }

    public void setBankName(String bankName) 
    {
        this.bankName = bankName;
    }

    public String getBankName() 
    {
        return bankName;
    }

    public void setBankAccount(String bankAccount) 
    {
        this.bankAccount = bankAccount;
    }

    public String getBankAccount() 
    {
        return bankAccount;
    }

    public void setAddress(String address) 
    {
        this.address = address;
    }

    public String getAddress() 
    {
        return address;
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
            .append("enterpriseCode", getEnterpriseCode())
            .append("enterpriseName", getEnterpriseName())
            .append("legalPerson", getLegalPerson())
            .append("phone", getPhone())
            .append("taxNo", getTaxNo())
            .append("bankName", getBankName())
            .append("bankAccount", getBankAccount())
            .append("address", getAddress())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
