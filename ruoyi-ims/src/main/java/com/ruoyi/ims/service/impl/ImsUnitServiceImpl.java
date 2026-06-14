package com.ruoyi.ims.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.ims.mapper.ImsUnitMapper;
import com.ruoyi.ims.domain.ImsUnit;
import com.ruoyi.ims.service.IImsUnitService;

/**
 * 计量单位管理Service业务层处理
 * 
 * @author tzq
 * @date 2026-05-29
 */
@Service
public class ImsUnitServiceImpl implements IImsUnitService 
{
    @Autowired
    private ImsUnitMapper imsUnitMapper;

    /**
     * 查询计量单位管理
     * 
     * @param id 计量单位管理主键
     * @return 计量单位管理
     */
    @Override
    public ImsUnit selectImsUnitById(Long id)
    {
        return imsUnitMapper.selectImsUnitById(id);
    }

    /**
     * 查询计量单位管理列表
     * 
     * @param imsUnit 计量单位管理
     * @return 计量单位管理
     */
    @Override
    public List<ImsUnit> selectImsUnitList(ImsUnit imsUnit)
    {
        return imsUnitMapper.selectImsUnitList(imsUnit);
    }

    /**
     * 新增计量单位管理
     * 
     * @param imsUnit 计量单位管理
     * @return 结果
     */
    @Override
    public int insertImsUnit(ImsUnit imsUnit)
    {
        imsUnit.setCreateTime(DateUtils.getNowDate());
        return imsUnitMapper.insertImsUnit(imsUnit);
    }

    /**
     * 修改计量单位管理
     * 
     * @param imsUnit 计量单位管理
     * @return 结果
     */
    @Override
    public int updateImsUnit(ImsUnit imsUnit)
    {
        imsUnit.setUpdateTime(DateUtils.getNowDate());
        return imsUnitMapper.updateImsUnit(imsUnit);
    }

    /**
     * 批量删除计量单位管理
     * 
     * @param ids 需要删除的计量单位管理主键
     * @return 结果
     */
    @Override
    public int deleteImsUnitByIds(Long[] ids)
    {
        return imsUnitMapper.deleteImsUnitByIds(ids);
    }

    /**
     * 删除计量单位管理信息
     * 
     * @param id 计量单位管理主键
     * @return 结果
     */
    @Override
    public int deleteImsUnitById(Long id)
    {
        return imsUnitMapper.deleteImsUnitById(id);
    }
}
