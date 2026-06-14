package com.ruoyi.ims.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.ims.domain.ImsDeliveryItem;
import com.ruoyi.ims.mapper.ImsDeliveryMapper;
import com.ruoyi.ims.domain.ImsDelivery;
import com.ruoyi.ims.service.IImsDeliveryService;

/**
 * 送货单Service业务层处理
 * 
 * @author tzq
 * @date 2026-06-02
 */
@Service
public class ImsDeliveryServiceImpl implements IImsDeliveryService 
{
    @Autowired
    private ImsDeliveryMapper imsDeliveryMapper;

    /**
     * 查询送货单
     * 
     * @param id 送货单主键
     * @return 送货单
     */
    @Override
    public ImsDelivery selectImsDeliveryById(Long id)
    {
        return imsDeliveryMapper.selectImsDeliveryById(id);
    }

    /**
     * 查询送货单列表
     * 
     * @param imsDelivery 送货单
     * @return 送货单
     */
    @Override
    public List<ImsDelivery> selectImsDeliveryList(ImsDelivery imsDelivery)
    {
        return imsDeliveryMapper.selectImsDeliveryList(imsDelivery);
    }

    /**
     * 新增送货单
     * 
     * @param imsDelivery 送货单
     * @return 结果
     */
    @Transactional
    @Override
    public int insertImsDelivery(ImsDelivery imsDelivery)
    {
        calculateDelivery(imsDelivery);
        imsDelivery.setCreateTime(DateUtils.getNowDate());
        int rows = imsDeliveryMapper.insertImsDelivery(imsDelivery);
        insertImsDeliveryItem(imsDelivery);
        return rows;
    }

    /**
     * 修改送货单
     * 
     * @param imsDelivery 送货单
     * @return 结果
     */
    @Transactional
    @Override
    public int updateImsDelivery(ImsDelivery imsDelivery)
    {
        calculateDelivery(imsDelivery);
        imsDelivery.setUpdateTime(DateUtils.getNowDate());
        imsDeliveryMapper.deleteImsDeliveryItemByDeliveryId(imsDelivery.getId());
        insertImsDeliveryItem(imsDelivery);
        return imsDeliveryMapper.updateImsDelivery(imsDelivery);
    }

    /**
     * 批量删除送货单
     * 
     * @param ids 需要删除的送货单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteImsDeliveryByIds(Long[] ids)
    {
        imsDeliveryMapper.deleteImsDeliveryItemByDeliveryIds(ids);
        return imsDeliveryMapper.deleteImsDeliveryByIds(ids);
    }

    /**
     * 删除送货单信息
     * 
     * @param id 送货单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteImsDeliveryById(Long id)
    {
        imsDeliveryMapper.deleteImsDeliveryItemByDeliveryId(id);
        return imsDeliveryMapper.deleteImsDeliveryById(id);
    }

    /**
     * 新增送货单明细信息
     * 
     * @param imsDelivery 送货单对象
     */
    public void insertImsDeliveryItem(ImsDelivery imsDelivery)
    {
        List<ImsDeliveryItem> imsDeliveryItemList = imsDelivery.getImsDeliveryItemList();
        Long id = imsDelivery.getId();
        if (StringUtils.isNotNull(imsDeliveryItemList))
        {
            List<ImsDeliveryItem> list = new ArrayList<ImsDeliveryItem>();
            for (ImsDeliveryItem imsDeliveryItem : imsDeliveryItemList)
            {
                imsDeliveryItem.setDeliveryId(id);
                list.add(imsDeliveryItem);
            }
            if (list.size() > 0)
            {
                imsDeliveryMapper.batchImsDeliveryItem(list);
            }
        }
    }

    private void calculateDelivery(ImsDelivery imsDelivery)
    {
        List<ImsDeliveryItem> items = imsDelivery.getImsDeliveryItemList();
        java.math.BigDecimal totalQty = java.math.BigDecimal.ZERO;
        java.math.BigDecimal totalAmount = java.math.BigDecimal.ZERO;
        if (StringUtils.isNotNull(items))
        {
            for (ImsDeliveryItem item : items)
            {
                item.setQtyPiece(ImsAmountUtils.qty(item.getQtyPiece()));
                item.setUnitPrice(ImsAmountUtils.money(item.getUnitPrice()));
                item.setAmount(ImsAmountUtils.amount(item.getQtyPiece(), item.getUnitPrice()));
                totalQty = totalQty.add(item.getQtyPiece());
                totalAmount = totalAmount.add(item.getAmount());
            }
        }
        imsDelivery.setTotalQtyPiece(totalQty);
        imsDelivery.setTotalAmount(totalAmount.setScale(2, java.math.RoundingMode.HALF_UP));
    }
}
