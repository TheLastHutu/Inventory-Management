package com.ruoyi.ims.service;

import java.util.List;
import com.ruoyi.ims.domain.ImsDelivery;

/**
 * 送货单Service接口
 * 
 * @author tzq
 * @date 2026-06-02
 */
public interface IImsDeliveryService 
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
     * 批量删除送货单
     * 
     * @param ids 需要删除的送货单主键集合
     * @return 结果
     */
    public int deleteImsDeliveryByIds(Long[] ids);

    /**
     * 删除送货单信息
     * 
     * @param id 送货单主键
     * @return 结果
     */
    public int deleteImsDeliveryById(Long id);
}
