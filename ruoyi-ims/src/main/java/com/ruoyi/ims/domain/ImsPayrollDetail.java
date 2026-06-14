package com.ruoyi.ims.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 工资明细对象 ims_payroll_detail
 * 
 * @author tzq
 * @date 2026-06-04
 */
public class ImsPayrollDetail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 明细ID（主键） */
    private Long detailId;

    /** 工资期间ID（外键，关联ims_payroll_period.period_id） */
    @Excel(name = "工资期间ID", readConverterExp = "外=键，关联ims_payroll_period.period_id", type = Excel.Type.IMPORT)
    private Long periodId;

    /** 员工ID（外键，关联ims_employee.id） */
    @Excel(name = "员工ID", readConverterExp = "外=键，关联ims_employee.id", type = Excel.Type.IMPORT)
    private Long employeeId;

    /** 员工名称 */
    private String employeeName;

    /** 出勤天数 */
    @Excel(name = "出勤天数")
    private BigDecimal attendanceDays;

    /** 基础工资 */
    @Excel(name = "基础工资")
    private BigDecimal baseSalary;

    /** 提成 */
    @Excel(name = "提成")
    private BigDecimal commission;

    /** 补贴 */
    @Excel(name = "补贴")
    private BigDecimal allowance;

    /** 奖金 */
    @Excel(name = "奖金")
    private BigDecimal bonus;

    /** 扣款 */
    @Excel(name = "扣款")
    private BigDecimal deduction;

    /** 实发工资 */
    @Excel(name = "实发工资")
    private BigDecimal netPay;

    public void setDetailId(Long detailId) 
    {
        this.detailId = detailId;
    }

    public Long getDetailId() 
    {
        return detailId;
    }
    public void setPeriodId(Long periodId) 
    {
        this.periodId = periodId;
    }

    public Long getPeriodId() 
    {
        return periodId;
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

    public void setAttendanceDays(BigDecimal attendanceDays) 
    {
        this.attendanceDays = attendanceDays;
    }

    public BigDecimal getAttendanceDays() 
    {
        return attendanceDays;
    }
    public void setBaseSalary(BigDecimal baseSalary) 
    {
        this.baseSalary = baseSalary;
    }

    public BigDecimal getBaseSalary() 
    {
        return baseSalary;
    }
    public void setCommission(BigDecimal commission) 
    {
        this.commission = commission;
    }

    public BigDecimal getCommission() 
    {
        return commission;
    }
    public void setAllowance(BigDecimal allowance) 
    {
        this.allowance = allowance;
    }

    public BigDecimal getAllowance() 
    {
        return allowance;
    }
    public void setBonus(BigDecimal bonus) 
    {
        this.bonus = bonus;
    }

    public BigDecimal getBonus() 
    {
        return bonus;
    }
    public void setDeduction(BigDecimal deduction) 
    {
        this.deduction = deduction;
    }

    public BigDecimal getDeduction() 
    {
        return deduction;
    }
    public void setNetPay(BigDecimal netPay) 
    {
        this.netPay = netPay;
    }

    public BigDecimal getNetPay() 
    {
        return netPay;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("detailId", getDetailId())
            .append("periodId", getPeriodId())
            .append("employeeId", getEmployeeId())
            .append("employeeName", getEmployeeName())
            .append("attendanceDays", getAttendanceDays())
            .append("baseSalary", getBaseSalary())
            .append("commission", getCommission())
            .append("allowance", getAllowance())
            .append("bonus", getBonus())
            .append("deduction", getDeduction())
            .append("netPay", getNetPay())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
