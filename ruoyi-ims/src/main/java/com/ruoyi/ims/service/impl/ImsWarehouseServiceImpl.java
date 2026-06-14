package com.ruoyi.ims.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.ims.mapper.ImsWarehouseMapper;
import com.ruoyi.ims.domain.ImsWarehouse;
import com.ruoyi.ims.service.IImsWarehouseService;

/**
 * 仓库管理Service业务层处理
 * 
 * @author tzq
 * @date 2026-05-29
 */
@Service
public class ImsWarehouseServiceImpl implements IImsWarehouseService 
{
    @Autowired
    private ImsWarehouseMapper imsWarehouseMapper;

    /**
     * 查询仓库管理
     * 
     * @param id 仓库管理主键
     * @return 仓库管理
     */
    @Override
    public ImsWarehouse selectImsWarehouseById(Long id)
    {
        return imsWarehouseMapper.selectImsWarehouseById(id);
    }

    /**
     * 查询仓库管理列表
     * 
     * @param imsWarehouse 仓库管理
     * @return 仓库管理
     */
    @Override
    public List<ImsWarehouse> selectImsWarehouseList(ImsWarehouse imsWarehouse)
    {
        return imsWarehouseMapper.selectImsWarehouseList(imsWarehouse);
    }

    /**
     * 新增仓库管理
     * 
     * @param imsWarehouse 仓库管理
     * @return 结果
     */
    @Override
    public int insertImsWarehouse(ImsWarehouse imsWarehouse)
    {
        imsWarehouse.setCreateTime(DateUtils.getNowDate());
        return imsWarehouseMapper.insertImsWarehouse(imsWarehouse);
    }

    /**
     * 修改仓库管理
     * 
     * @param imsWarehouse 仓库管理
     * @return 结果
     */
    @Override
    public int updateImsWarehouse(ImsWarehouse imsWarehouse)
    {
        imsWarehouse.setUpdateTime(DateUtils.getNowDate());
        return imsWarehouseMapper.updateImsWarehouse(imsWarehouse);
    }

    /**
     * 批量删除仓库管理
     * 
     * @param ids 需要删除的仓库管理主键
     * @return 结果
     */
    @Override
    public int deleteImsWarehouseByIds(Long[] ids)
    {
        return imsWarehouseMapper.deleteImsWarehouseByIds(ids);
    }

    /**
     * 删除仓库管理信息
     * 
     * @param id 仓库管理主键
     * @return 结果
     */
    @Override
    public int deleteImsWarehouseById(Long id)
    {
        return imsWarehouseMapper.deleteImsWarehouseById(id);
    }
}
