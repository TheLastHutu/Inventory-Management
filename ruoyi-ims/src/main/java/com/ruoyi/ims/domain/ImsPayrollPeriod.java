package com.ruoyi.ims.domain;

import java.math.BigDecimal;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 工资管理对象 ims_payroll_period
 * 
 * @author tzq
 * @date 2026-06-04
 */
public class ImsPayrollPeriod extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long periodId;

    /** 部门id */
    @Excel(name = "部门ID", type = Excel.Type.IMPORT)
    private Long deptId;

    /** 部门名称 */
    @Excel(name = "部门")
    private String deptName;

    /** 员工ID */
    @Excel(name = "员工ID", type = Excel.Type.IMPORT)
    private Long employeeId;

    /** 员工名称 */
    @Excel(name = "员工")
    private String employeeName;

    /** 月份 */
    @Excel(name = "月份")
    private String periodCode;

    /** 状态 */
    @Excel(name = "状态", dictType = "ims_salary_status")
    private String status;

    /** 实发工资合计 */
    @Excel(name = "实发工资合计")
    private BigDecimal netPay;

    /** 工资明细信息 */
    private List<ImsPayrollDetail> imsPayrollDetailList;

    public void setPeriodId(Long periodId) 
    {
        this.periodId = periodId;
    }

    public Long getPeriodId() 
    {
        return periodId;
    }

    public void setDeptId(Long deptId) 
    {
        this.deptId = deptId;
    }

    public Long getDeptId() 
    {
        return deptId;
    }

    public void setDeptName(String deptName) 
    {
        this.deptName = deptName;
    }

    public String getDeptName() 
    {
        return deptName;
    }

    public void setEmployeeId(Long employeeId) 
    {
        this.employeeId = employeeId;
    }

    public Long getEmployeeId() 
    {
        return employeeId;
    }

    public void setEmployeeName(String employeeName) 
    {
        this.employeeName = employeeName;
    }

    public String getEmployeeName() 
    {
        return employeeName;
    }

    public void setPeriodCode(String periodCode) 
    {
        this.periodCode = periodCode;
    }

    public String getPeriodCode() 
    {
        return periodCode;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    public void setNetPay(BigDecimal netPay)
    {
        this.netPay = netPay;
    }

    public BigDecimal getNetPay()
    {
        return netPay;
    }

    public List<ImsPayrollDetail> getImsPayrollDetailList()
    {
        return imsPayrollDetailList;
    }

    public void setImsPayrollDetailList(List<ImsPayrollDetail> imsPayrollDetailList)
    {
        this.imsPayrollDetailList = imsPayrollDetailList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("periodId", getPeriodId())
            .append("deptId", getDeptId())
            .append("deptName", getDeptName())
            .append("employeeId", getEmployeeId())
            .append("employeeName", getEmployeeName())
            .append("periodCode", getPeriodCode())
            .append("status", getStatus())
            .append("netPay", getNetPay())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("imsPayrollDetailList", getImsPayrollDetailList())
            .toString();
    }
}
