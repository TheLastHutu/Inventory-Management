package com.ruoyi.ims.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.ims.mapper.ImsWarehouseLocationMapper;
import com.ruoyi.ims.domain.ImsWarehouseLocation;
import com.ruoyi.ims.service.IImsWarehouseLocationService;

/**
 * 货位管理Service业务层处理
 * 
 * @author tzq
 * @date 2026-05-29
 */
@Service
public class ImsWarehouseLocationServiceImpl implements IImsWarehouseLocationService 
{
    @Autowired
    private ImsWarehouseLocationMapper imsWarehouseLocationMapper;

    /**
     * 查询货位管理
     * 
     * @param id 货位管理主键
     * @return 货位管理
     */
    @Override
    public ImsWarehouseLocation selectImsWarehouseLocationById(Long id)
    {
        return imsWarehouseLocationMapper.selectImsWarehouseLocationById(id);
    }

    /**
     * 查询货位管理列表
     * 
     * @param imsWarehouseLocation 货位管理
     * @return 货位管理
     */
    @Override
    public List<ImsWarehouseLocation> selectImsWarehouseLocationList(ImsWarehouseLocation imsWarehouseLocation)
    {
        return imsWarehouseLocationMapper.selectImsWarehouseLocationList(imsWarehouseLocation);
    }

    /**
     * 新增货位管理
     * 
     * @param imsWarehouseLocation 货位管理
     * @return 结果
     */
    @Override
    public int insertImsWarehouseLocation(ImsWarehouseLocation imsWarehouseLocation)
    {
        imsWarehouseLocation.setCreateTime(DateUtils.getNowDate());
        return imsWarehouseLocationMapper.insertImsWarehouseLocation(imsWarehouseLocation);
    }

    /**
     * 修改货位管理
     * 
     * @param imsWarehouseLocation 货位管理
     * @return 结果
     */
    @Override
    public int updateImsWarehouseLocation(ImsWarehouseLocation imsWarehouseLocation)
    {
        imsWarehouseLocation.setUpdateTime(DateUtils.getNowDate());
        return imsWarehouseLocationMapper.updateImsWarehouseLocation(imsWarehouseLocation);
    }

    /**
     * 批量删除货位管理
     * 
     * @param ids 需要删除的货位管理主键
     * @return 结果
     */
    @Override
    public int deleteImsWarehouseLocationByIds(Long[] ids)
    {
        return imsWarehouseLocationMapper.deleteImsWarehouseLocationByIds(ids);
    }

    /**
     * 删除货位管理信息
     * 
     * @param id 货位管理主键
     * @return 结果
     */
    @Override
    public int deleteImsWarehouseLocationById(Long id)
    {
        return imsWarehouseLocationMapper.deleteImsWarehouseLocationById(id);
    }
}
