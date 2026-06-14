package com.ruoyi.ims.service;

import java.util.List;
import com.ruoyi.ims.domain.ImsWarehouse;

/**
 * 仓库管理Service接口
 * 
 * @author tzq
 * @date 2026-05-29
 */
public interface IImsWarehouseService 
{
    /**
     * 查询仓库管理
     * 
     * @param id 仓库管理主键
     * @return 仓库管理
     */
    public ImsWarehouse selectImsWarehouseById(Long id);

    /**
     * 查询仓库管理列表
     * 
     * @param imsWarehouse 仓库管理
     * @return 仓库管理集合
     */
    public List<ImsWarehouse> selectImsWarehouseList(ImsWarehouse imsWarehouse);

    /**
     * 新增仓库管理
     * 
     * @param imsWarehouse 仓库管理
     * @return 结果
     */
    public int insertImsWarehouse(ImsWarehouse imsWarehouse);

    /**
     * 修改仓库管理
     * 
     * @param imsWarehouse 仓库管理
     * @return 结果
     */
    public int updateImsWarehouse(ImsWarehouse imsWarehouse);

    /**
     * 批量删除仓库管理
     * 
     * @param ids 需要删除的仓库管理主键集合
     * @return 结果
     */
    public int deleteImsWarehouseByIds(Long[] ids);

    /**
     * 删除仓库管理信息
     * 
     * @param id 仓库管理主键
     * @return 结果
     */
    public int deleteImsWarehouseById(Long id);
}
