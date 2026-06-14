package com.ruoyi.ims.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.ims.domain.ImsOutbound;
import com.ruoyi.ims.domain.ImsOutboundItem;
import com.ruoyi.ims.domain.ImsSaleReturnItem;
import com.ruoyi.ims.mapper.ImsOutboundMapper;
import com.ruoyi.ims.mapper.ImsSaleReturnMapper;
import com.ruoyi.ims.domain.ImsSaleReturn;
import com.ruoyi.ims.service.IImsSaleReturnService;

/**
 * 销售退货单Service业务层处理
 * 
 * @author tzq
 * @date 2026-05-29
 */
@Service
public class ImsSaleReturnServiceImpl implements IImsSaleReturnService 
{
    @Autowired
    private ImsSaleReturnMapper imsSaleReturnMapper;

    @Autowired
    private ImsOutboundMapper imsOutboundMapper;

    /**
     * 查询销售退货单
     * 
     * @param id 销售退货单主键
     * @return 销售退货单
     */
    @Override
    public ImsSaleReturn selectImsSaleReturnById(Long id)
    {
        return imsSaleReturnMapper.selectImsSaleReturnById(id);
    }

    /**
     * 查询销售退货单列表
     * 
     * @param imsSaleReturn 销售退货单
     * @return 销售退货单
     */
    @Override
    public List<ImsSaleReturn> selectImsSaleReturnList(ImsSaleReturn imsSaleReturn)
    {
        return imsSaleReturnMapper.selectImsSaleReturnList(imsSaleReturn);
    }

    /**
     * 新增销售退货单
     * 
     * @param imsSaleReturn 销售退货单
     * @return 结果
     */
    @Transactional
    @Override
    public int insertImsSaleReturn(ImsSaleReturn imsSaleReturn)
    {
        calculateSaleReturn(imsSaleReturn);
        validateSourceOutboundQty(imsSaleReturn);
        imsSaleReturn.setCreateTime(DateUtils.getNowDate());
        int rows = imsSaleReturnMapper.insertImsSaleReturn(imsSaleReturn);
        insertImsSaleReturnItem(imsSaleReturn);
        return rows;
    }

    /**
     * 修改销售退货单
     * 
     * @param imsSaleReturn 销售退货单
     * @return 结果
     */
    @Transactional
    @Override
    public int updateImsSaleReturn(ImsSaleReturn imsSaleReturn)
    {
        calculateSaleReturn(imsSaleReturn);
        validateSourceOutboundQty(imsSaleReturn);
        imsSaleReturn.setUpdateTime(DateUtils.getNowDate());
        imsSaleReturnMapper.deleteImsSaleReturnItemBySaleReturnId(imsSaleReturn.getId());
        insertImsSaleReturnItem(imsSaleReturn);
        return imsSaleReturnMapper.updateImsSaleReturn(imsSaleReturn);
    }

    /**
     * 批量删除销售退货单
     * 
     * @param ids 需要删除的销售退货单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteImsSaleReturnByIds(Long[] ids)
    {
        imsSaleReturnMapper.deleteImsSaleReturnItemBySaleReturnIds(ids);
        return imsSaleReturnMapper.deleteImsSaleReturnByIds(ids);
    }

    /**
     * 删除销售退货单信息
     * 
     * @param id 销售退货单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteImsSaleReturnById(Long id)
    {
        imsSaleReturnMapper.deleteImsSaleReturnItemBySaleReturnId(id);
        return imsSaleReturnMapper.deleteImsSaleReturnById(id);
    }

    /**
     * 新增销售退货明细信息
     * 
     * @param imsSaleReturn 销售退货单对象
     */
    public void insertImsSaleReturnItem(ImsSaleReturn imsSaleReturn)
    {
        List<ImsSaleReturnItem> imsSaleReturnItemList = imsSaleReturn.getImsSaleReturnItemList();
        Long id = imsSaleReturn.getId();
        if (StringUtils.isNotNull(imsSaleReturnItemList))
        {
            List<ImsSaleReturnItem> list = new ArrayList<ImsSaleReturnItem>();
            long lineNo = 1L;
            for (ImsSaleReturnItem imsSaleReturnItem : imsSaleReturnItemList)
            {
                imsSaleReturnItem.setSaleReturnId(id);
                imsSaleReturnItem.setLineNo(lineNo++);
                list.add(imsSaleReturnItem);
            }
            if (list.size() > 0)
            {
                imsSaleReturnMapper.batchImsSaleReturnItem(list);
            }
        }
    }

    private void calculateSaleReturn(ImsSaleReturn imsSaleReturn)
    {
        List<ImsSaleReturnItem> items = imsSaleReturn.getImsSaleReturnItemList();
        java.math.BigDecimal totalQty = java.math.BigDecimal.ZERO;
        java.math.BigDecimal totalAmount = java.math.BigDecimal.ZERO;
        if (StringUtils.isNotNull(items))
        {
            for (ImsSaleReturnItem item : items)
            {
                item.setQtyPiece(ImsAmountUtils.qty(item.getQtyPiece()));
                item.setUnitPrice(ImsAmountUtils.money(item.getUnitPrice()));
                item.setAmount(ImsAmountUtils.amount(item.getQtyPiece(), item.getUnitPrice()));
                totalQty = totalQty.add(item.getQtyPiece());
                totalAmount = totalAmount.add(item.getAmount());
            }
        }
        imsSaleReturn.setTotalQtyPiece(totalQty);
        imsSaleReturn.setTotalAmount(totalAmount.setScale(2, java.math.RoundingMode.HALF_UP));
    }

    private void validateSourceOutboundQty(ImsSaleReturn imsSaleReturn)
    {
        List<ImsSaleReturnItem> returnItems = imsSaleReturn.getImsSaleReturnItemList();
        if (StringUtils.isNull(returnItems) || returnItems.isEmpty())
        {
            return;
        }
        if (StringUtils.isEmpty(imsSaleReturn.getSourceOutboundNo()))
        {
            throw new ServiceException("原出库单号不能为空");
        }

        ImsOutbound query = new ImsOutbound();
        query.setBillNo(imsSaleReturn.getSourceOutboundNo());
        List<ImsOutbound> outboundList = imsOutboundMapper.selectImsOutboundList(query);
        if (StringUtils.isNull(outboundList) || outboundList.isEmpty())
        {
            throw new ServiceException("原出库单不存在");
        }

        ImsOutbound outbound = imsOutboundMapper.selectImsOutboundById(outboundList.get(0).getId());
        List<ImsOutboundItem> outboundItems = outbound.getImsOutboundItemList();
        Map<Long, BigDecimal> outboundQtyMap = new HashMap<Long, BigDecimal>();
        if (StringUtils.isNotNull(outboundItems))
        {
            for (ImsOutboundItem outboundItem : outboundItems)
            {
                if (outboundItem.getProductId() == null)
                {
                    continue;
                }
                BigDecimal qty = ImsAmountUtils.qty(outboundItem.getQtyPiece());
                outboundQtyMap.merge(outboundItem.getProductId(), qty, BigDecimal::add);
            }
        }

        Map<Long, BigDecimal> returnQtyMap = new HashMap<Long, BigDecimal>();
        for (ImsSaleReturnItem returnItem : returnItems)
        {
            if (returnItem.getProductId() == null)
            {
                throw new ServiceException("销售退货明细产品名称不能为空");
            }
            BigDecimal qty = ImsAmountUtils.qty(returnItem.getQtyPiece());
            returnQtyMap.merge(returnItem.getProductId(), qty, BigDecimal::add);
        }

        for (Map.Entry<Long, BigDecimal> entry : returnQtyMap.entrySet())
        {
            BigDecimal outboundQty = outboundQtyMap.get(entry.getKey());
            if (outboundQty == null || entry.getValue().compareTo(outboundQty) > 0)
            {
                throw new ServiceException("退货数量不能超过原出库单对应产品的购买数量");
            }
        }
    }
}
