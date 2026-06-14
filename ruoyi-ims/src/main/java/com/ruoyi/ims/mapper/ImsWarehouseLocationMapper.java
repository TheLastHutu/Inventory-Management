package com.ruoyi.ims.mapper;

import java.util.List;
import com.ruoyi.ims.domain.ImsWarehouseLocation;

/**
 * 货位管理Mapper接口
 * 
 * @author tzq
 * @date 2026-05-29
 */
public interface ImsWarehouseLocationMapper 
{
    /**
     * 查询货位管理
     * 
     * @param id 货位管理主键
     * @return 货位管理
     */
    public ImsWarehouseLocation selectImsWarehouseLocationById(Long id);

    /**
     * 查询货位管理列表
     * 
     * @param imsWarehouseLocation 货位管理
     * @return 货位管理集合
     */
    public List<ImsWarehouseLocation> selectImsWarehouseLocationList(ImsWarehouseLocation imsWarehouseLocation);

    /**
     * 新增货位管理
     * 
     * @param imsWarehouseLocation 货位管理
     * @return 结果
     */
    public int insertImsWarehouseLocation(ImsWarehouseLocation imsWarehouseLocation);

    /**
     * 修改货位管理
     * 
     * @param imsWarehouseLocation 货位管理
     * @return 结果
     */
    public int updateImsWarehouseLocation(ImsWarehouseLocation imsWarehouseLocation);

    /**
     * 删除货位管理
     * 
     * @param id 货位管理主键
     * @return 结果
     */
    public int deleteImsWarehouseLocationById(Long id);

    /**
     * 批量删除货位管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteImsWarehouseLocationByIds(Long[] ids);
}
