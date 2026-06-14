package com.ruoyi.ims.mapper;

import java.util.List;
import com.ruoyi.ims.domain.ImsDelivery;
import com.ruoyi.ims.domain.ImsDeliveryItem;

/**
 * 送货单Mapper接口
 * 
 * @author tzq
 * @date 2026-06-02
 */
public interface ImsDeliveryMapper 
{
    /**
     * 查询送货单
     * 
     * @param id 送货单主键
     * @return 送货单
     */
    public ImsDelivery selectImsDeliveryById(Long id);

    /**
     * 查询送货单列表
     * 
     * @param imsDelivery 送货单
     * @return 送货单集合
     */
    public List<ImsDelivery> selectImsDeliveryList(ImsDelivery imsDelivery);

    /**
     * 新增送货单
     * 
     * @param imsDelivery 送货单
     * @return 结果
     */
    public int insertImsDelivery(ImsDelivery imsDelivery);

    /**
     * 修改送货单
     * 
     * @param imsDelivery 送货单
     * @return 结果
     */
    public int updateImsDelivery(ImsDelivery imsDelivery);

    /**
     * 删除送货单
     * 
     * @param id 送货单主键
     * @return 结果
     */
    public int deleteImsDeliveryById(Long id);

    /**
     * 批量删除送货单
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteImsDeliveryByIds(Long[] ids);

    /**
     * 批量删除送货单明细
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteImsDeliveryItemByDeliveryIds(Long[] ids);
    
    /**
     * 批量新增送货单明细
     * 
     * @param imsDeliveryItemList 送货单明细列表
     * @return 结果
     */
    public int batchImsDeliveryItem(List<ImsDeliveryItem> imsDeliveryItemList);
    

    /**
     * 通过送货单主键删除送货单明细信息
     * 
     * @param id 送货单ID
     * @return 结果
     */
    public int deleteImsDeliveryItemByDeliveryId(Long id);
}
