package com.ruoyi.ims.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.ims.domain.ImsPayrollDetail;
import com.ruoyi.ims.mapper.ImsPayrollPeriodMapper;
import com.ruoyi.ims.domain.ImsPayrollPeriod;
import com.ruoyi.ims.service.IImsPayrollPeriodService;

/**
 * 工资管理Service业务层处理
 * 
 * @author tzq
 * @date 2026-06-04
 */
@Service
public class ImsPayrollPeriodServiceImpl implements IImsPayrollPeriodService 
{
    @Autowired
    private ImsPayrollPeriodMapper imsPayrollPeriodMapper;

    /**
     * 查询工资管理
     * 
     * @param periodId 工资管理主键
     * @return 工资管理
     */
    @Override
    public ImsPayrollPeriod selectImsPayrollPeriodByPeriodId(Long periodId)
    {
        return imsPayrollPeriodMapper.selectImsPayrollPeriodByPeriodId(periodId);
    }

    /**
     * 查询工资管理列表
     * 
     * @param imsPayrollPeriod 工资管理
     * @return 工资管理
     */
    @Override
    public List<ImsPayrollPeriod> selectImsPayrollPeriodList(ImsPayrollPeriod imsPayrollPeriod)
    {
        return imsPayrollPeriodMapper.selectImsPayrollPeriodList(imsPayrollPeriod);
    }

    /**
     * 新增工资管理
     * 
     * @param imsPayrollPeriod 工资管理
     * @return 结果
     */
    @Transactional
    @Override
    public int insertImsPayrollPeriod(ImsPayrollPeriod imsPayrollPeriod)
    {
        calculatePayroll(imsPayrollPeriod);
        imsPayrollPeriod.setCreateTime(DateUtils.getNowDate());
        int rows = imsPayrollPeriodMapper.insertImsPayrollPeriod(imsPayrollPeriod);
        insertImsPayrollDetail(imsPayrollPeriod);
        return rows;
    }

    /**
     * 修改工资管理
     * 
     * @param imsPayrollPeriod 工资管理
     * @return 结果
     */
    @Transactional
    @Override
    public int updateImsPayrollPeriod(ImsPayrollPeriod imsPayrollPeriod)
    {
        checkPayrollEditable(imsPayrollPeriod.getPeriodId());
        calculatePayroll(imsPayrollPeriod);
        imsPayrollPeriod.setUpdateTime(DateUtils.getNowDate());
        imsPayrollPeriodMapper.deleteImsPayrollDetailByPeriodId(imsPayrollPeriod.getPeriodId());
        insertImsPayrollDetail(imsPayrollPeriod);
        return imsPayrollPeriodMapper.updateImsPayrollPeriod(imsPayrollPeriod);
    }

    /**
     * 批量删除工资管理
     * 
     * @param periodIds 需要删除的工资管理主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteImsPayrollPeriodByPeriodIds(Long[] periodIds)
    {
        for (Long periodId : periodIds)
        {
            checkPayrollEditable(periodId);
        }
        imsPayrollPeriodMapper.deleteImsPayrollDetailByPeriodIds(periodIds);
        return imsPayrollPeriodMapper.deleteImsPayrollPeriodByPeriodIds(periodIds);
    }

    /**
     * 删除工资管理信息
     * 
     * @param periodId 工资管理主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteImsPayrollPeriodByPeriodId(Long periodId)
    {
        checkPayrollEditable(periodId);
        imsPayrollPeriodMapper.deleteImsPayrollDetailByPeriodId(periodId);
        return imsPayrollPeriodMapper.deleteImsPayrollPeriodByPeriodId(periodId);
    }

    /**
     * 新增工资明细信息
     * 
     * @param imsPayrollPeriod 工资管理对象
     */
    public void insertImsPayrollDetail(ImsPayrollPeriod imsPayrollPeriod)
    {
        List<ImsPayrollDetail> imsPayrollDetailList = imsPayrollPeriod.getImsPayrollDetailList();
        Long periodId = imsPayrollPeriod.getPeriodId();
        if (StringUtils.isNotNull(imsPayrollDetailList))
        {
            List<ImsPayrollDetail> list = new ArrayList<ImsPayrollDetail>();
            for (ImsPayrollDetail imsPayrollDetail : imsPayrollDetailList)
            {
                imsPayrollDetail.setPeriodId(periodId);
                list.add(imsPayrollDetail);
            }
            if (list.size() > 0)
            {
                imsPayrollPeriodMapper.batchImsPayrollDetail(list);
            }
        }
    }

    private void calculatePayroll(ImsPayrollPeriod imsPayrollPeriod)
    {
        List<ImsPayrollDetail> items = imsPayrollPeriod.getImsPayrollDetailList();
        if (StringUtils.isNotNull(items))
        {
            for (ImsPayrollDetail item : items)
            {
                item.setAttendanceDays(ImsAmountUtils.qty(item.getAttendanceDays()));
                item.setBaseSalary(ImsAmountUtils.money(item.getBaseSalary()));
                item.setCommission(ImsAmountUtils.money(item.getCommission()));
                item.setAllowance(ImsAmountUtils.money(item.getAllowance()));
                item.setBonus(ImsAmountUtils.money(item.getBonus()));
                item.setDeduction(ImsAmountUtils.money(item.getDeduction()));
                item.setNetPay(item.getBaseSalary()
                        .add(item.getCommission())
                        .add(item.getAllowance())
                        .add(item.getBonus())
                        .subtract(item.getDeduction())
                        .setScale(2, java.math.RoundingMode.HALF_UP));
            }
        }
    }

    private void checkPayrollEditable(Long periodId)
    {
        ImsPayrollPeriod old = imsPayrollPeriodMapper.selectImsPayrollPeriodByPeriodId(periodId);
        if (old != null && "1".equals(old.getStatus()))
        {
            throw new ServiceException("工资表已发放，不能修改或删除");
        }
    }
}
