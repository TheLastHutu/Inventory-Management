package com.ruoyi.ims.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.ims.mapper.ImsInventoryMapper;
import com.ruoyi.ims.domain.ImsInventory;
import com.ruoyi.ims.service.IImsInventoryService;

/**
 * 库存(现存量)Service业务层处理
 * 
 * @author tzq
 * @date 2026-05-29
 */
@Service
public class ImsInventoryServiceImpl implements IImsInventoryService 
{
    @Autowired
    private ImsInventoryMapper imsInventoryMapper;

    /**
     * 查询库存(现存量)
     * 
     * @param id 库存(现存量)主键
     * @return 库存(现存量)
     */
    @Override
    public ImsInventory selectImsInventoryById(Long id)
    {
        return imsInventoryMapper.selectImsInventoryById(id);
    }

    /**
     * 查询库存(现存量)列表
     * 
     * @param imsInventory 库存(现存量)
     * @return 库存(现存量)
     */
    @Override
    public List<ImsInventory> selectImsInventoryList(ImsInventory imsInventory)
    {
        return imsInventoryMapper.selectImsInventoryList(imsInventory);
    }

    /**
     * 新增库存(现存量)
     * 
     * @param imsInventory 库存(现存量)
     * @return 结果
     */
    @Override
    public int insertImsInventory(ImsInventory imsInventory)
    {
        imsInventory.setCreateTime(DateUtils.getNowDate());
        return imsInventoryMapper.insertImsInventory(imsInventory);
    }

    /**
     * 修改库存(现存量)
     * 
     * @param imsInventory 库存(现存量)
     * @return 结果
     */
    @Override
    public int updateImsInventory(ImsInventory imsInventory)
    {
        imsInventory.setUpdateTime(DateUtils.getNowDate());
        return imsInventoryMapper.updateImsInventory(imsInventory);
    }

    /**
     * 批量删除库存(现存量)
     * 
     * @param ids 需要删除的库存(现存量)主键
     * @return 结果
     */
    @Override
    public int deleteImsInventoryByIds(Long[] ids)
    {
        return imsInventoryMapper.deleteImsInventoryByIds(ids);
    }

    /**
     * 删除库存(现存量)信息
     * 
     * @param id 库存(现存量)主键
     * @return 结果
     */
    @Override
    public int deleteImsInventoryById(Long id)
    {
        return imsInventoryMapper.deleteImsInventoryById(id);
    }
}
