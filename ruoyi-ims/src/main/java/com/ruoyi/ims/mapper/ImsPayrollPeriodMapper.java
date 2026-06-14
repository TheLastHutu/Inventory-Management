package com.ruoyi.ims.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.ruoyi.ims.domain.ImsPayrollPeriod;
import com.ruoyi.ims.domain.ImsPayrollDetail;

/**
 * 工资管理Mapper接口
 * 
 * @author tzq
 * @date 2026-06-04
 */
@Mapper
public interface ImsPayrollPeriodMapper 
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
     * 删除工资管理
     * 
     * @param periodId 工资管理主键
     * @return 结果
     */
    public int deleteImsPayrollPeriodByPeriodId(Long periodId);

    /**
     * 批量删除工资管理
     * 
     * @param periodIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteImsPayrollPeriodByPeriodIds(Long[] periodIds);

    /**
     * 批量删除工资明细
     * 
     * @param periodIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteImsPayrollDetailByDetailIds(Long[] periodIds);
    
    /**
     * 批量新增工资明细
     * 
     * @param imsPayrollDetailList 工资明细列表
     * @return 结果
     */
    public int batchImsPayrollDetail(List<ImsPayrollDetail> imsPayrollDetailList);
    

    /**
     * 通过工资明细主键删除工资明细信息
     * 
     * @param detailId 工资明细ID
     * @return 结果
     */
    public int deleteImsPayrollDetailByDetailId(Long detailId);

    /**
     * 通过工资管理主键删除工资明细信息
     * 
     * @param periodId 工资管理ID
     * @return 结果
     */
    public int deleteImsPayrollDetailByPeriodId(Long periodId);

    /**
     * 批量通过工资管理主键删除工资明细信息
     * 
     * @param periodIds 工资管理ID集合
     * @return 结果
     */
    public int deleteImsPayrollDetailByPeriodIds(Long[] periodIds);
}
