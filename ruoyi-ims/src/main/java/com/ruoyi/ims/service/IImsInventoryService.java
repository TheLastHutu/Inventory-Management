package com.ruoyi.ims.service;

import java.util.List;
import com.ruoyi.ims.domain.ImsInventory;

/**
 * 库存(现存量)Service接口
 * 
 * @author tzq
 * @date 2026-05-29
 */
public interface IImsInventoryService 
{
    /**
     * 查询库存(现存量)
     * 
     * @param id 库存(现存量)主键
     * @return 库存(现存量)
     */
    public ImsInventory selectImsInventoryById(Long id);

    /**
     * 查询库存(现存量)列表
     * 
     * @param imsInventory 库存(现存量)
     * @return 库存(现存量)集合
     */
    public List<ImsInventory> selectImsInventoryList(ImsInventory imsInventory);

    /**
     * 新增库存(现存量)
     * 
     * @param imsInventory 库存(现存量)
     * @return 结果
     */
    public int insertImsInventory(ImsInventory imsInventory);

    /**
     * 修改库存(现存量)
     * 
     * @param imsInventory 库存(现存量)
     * @return 结果
     */
    public int updateImsInventory(ImsInventory imsInventory);

    /**
     * 批量删除库存(现存量)
     * 
     * @param ids 需要删除的库存(现存量)主键集合
     * @return 结果
     */
    public int deleteImsInventoryByIds(Long[] ids);

    /**
     * 删除库存(现存量)信息
     * 
     * @param id 库存(现存量)主键
     * @return 结果
     */
    public int deleteImsInventoryById(Long id);
}
