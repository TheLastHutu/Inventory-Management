package com.ruoyi.ims.service;

import java.util.List;
import com.ruoyi.ims.domain.ImsPayrollPeriod;

/**
 * 工资管理Service接口
 * 
 * @author tzq
 * @date 2026-06-04
 */
public interface IImsPayrollPeriodService 
{
    /**
     * 查询工资管理
     * 
     * @param periodId 工资管理主键
     * @return 工资管理
     */
    public ImsPayrollPeriod selectImsPayrollPeriodByPeriodId(Long periodId);

    /**
     * 查询工资管理列表
     * 
     * @param imsPayrollPeriod 工资管理
     * @return 工资管理集合
     */
    public List<ImsPayrollPeriod> selectImsPayrollPeriodList(ImsPayrollPeriod imsPayrollPeriod);

    /**
     * 新增工资管理
     * 
     * @param imsPayrollPeriod 工资管理
     * @return 结果
     */
    public int insertImsPayrollPeriod(ImsPayrollPeriod imsPayrollPeriod);

    /**
     * 修改工资管理
     * 
     * @param imsPayrollPeriod 工资管理
     * @return 结果
     */
    public int updateImsPayrollPeriod(ImsPayrollPeriod imsPayrollPeriod);

    /**
     * 批量删除工资管理
     * 
     * @param periodIds 需要删除的工资管理主键集合
     * @return 结果
     */
    public int deleteImsPayrollPeriodByPeriodIds(Long[] periodIds);

    /**
     * 删除工资管理信息
     * 
     * @param periodId 工资管理主键
     * @return 结果
     */
    public int deleteImsPayrollPeriodByPeriodId(Long periodId);
}
