package com.ruoyi.ims.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.ims.domain.ImsInbound;
import com.ruoyi.ims.domain.ImsInboundItem;
import com.ruoyi.ims.domain.ImsStockFlow;
import com.ruoyi.ims.mapper.ImsInboundMapper;
import com.ruoyi.ims.service.IImsInboundService;
import com.ruoyi.ims.service.IImsStockFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ImsInboundServiceImpl implements IImsInboundService
{
    private static final String APPROVED_STATUS = "1";
    private static final String STOCK_FLOW_BIZ_TYPE_PURCHASE = "2";
    private static final String STOCK_FLOW_INOUT_IN = "I";

    @Autowired
    private ImsInboundMapper imsInboundMapper;

    @Autowired
    private ImsInventoryBalanceService imsInventoryBalanceService;

    @Autowired
    private IImsStockFlowService imsStockFlowService;

    @Override
    public ImsInbound selectImsInboundById(Long id)
    {
        return imsInboundMapper.selectImsInboundById(id);
    }

    @Override
    public List<ImsInbound> selectImsInboundList(ImsInbound imsInbound)
    {
        return imsInboundMapper.selectImsInboundList(imsInbound);
    }

    @Transactional
    @Override
    public int insertImsInbound(ImsInbound imsInbound)
    {
        calculateInbound(imsInbound);
        imsInbound.setCreateTime(DateUtils.getNowDate());
        int rows = imsInboundMapper.insertImsInbound(imsInbound);
        insertImsInboundItem(imsInbound);
        imsInventoryBalanceService.applyInbound(imsInbound, 1);
        if (isApproved(imsInbound))
        {
            insertInboundStockFlow(imsInbound);
        }
        return rows;
    }

    @Transactional
    @Override
    public int updateImsInbound(ImsInbound imsInbound)
    {
        ImsInbound oldInbound = imsInboundMapper.selectImsInboundById(imsInbound.getId());
        calculateInbound(imsInbound);
        imsInventoryBalanceService.applyInbound(oldInbound, -1);
        imsInbound.setUpdateTime(DateUtils.getNowDate());
        imsInboundMapper.deleteImsInboundItemByInboundId(imsInbound.getId());
        insertImsInboundItem(imsInbound);
        int rows = imsInboundMapper.updateImsInbound(imsInbound);
        imsInventoryBalanceService.applyInbound(imsInbound, 1);
        if (!isApproved(oldInbound) && isApproved(imsInbound))
        {
            insertInboundStockFlow(imsInbound);
        }
        return rows;
    }

    @Transactional
    @Override
    public int deleteImsInboundByIds(Long[] ids)
    {
        for (Long id : ids)
        {
            imsInventoryBalanceService.applyInbound(imsInboundMapper.selectImsInboundById(id), -1);
        }
        imsInboundMapper.deleteImsInboundItemByInboundIds(ids);
        return imsInboundMapper.deleteImsInboundByIds(ids);
    }

    @Transactional
    @Override
    public int deleteImsInboundById(Long id)
    {
        imsInventoryBalanceService.applyInbound(imsInboundMapper.selectImsInboundById(id), -1);
        imsInboundMapper.deleteImsInboundItemByInboundId(id);
        return imsInboundMapper.deleteImsInboundById(id);
    }

    public void insertImsInboundItem(ImsInbound imsInbound)
    {
        List<ImsInboundItem> imsInboundItemList = imsInbound.getImsInboundItemList();
        Long id = imsInbound.getId();
        if (StringUtils.isNotNull(imsInboundItemList))
        {
            List<ImsInboundItem> list = new ArrayList<ImsInboundItem>();
            long lineNo = 1L;
            for (ImsInboundItem imsInboundItem : imsInboundItemList)
            {
                imsInboundItem.setInboundId(id);
                imsInboundItem.setLineNo(lineNo++);
                list.add(imsInboundItem);
            }
            if (list.size() > 0)
            {
                imsInboundMapper.batchImsInboundItem(list);
            }
        }
    }

    private void insertInboundStockFlow(ImsInbound imsInbound)
    {
        List<ImsInboundItem> imsInboundItemList = imsInbound.getImsInboundItemList();
        if (StringUtils.isNull(imsInboundItemList))
        {
            return;
        }
        for (ImsInboundItem item : imsInboundItemList)
        {
            ImsStockFlow imsStockFlow = new ImsStockFlow();
            imsStockFlow.setBizNo(imsInbound.getBillNo());
            imsStockFlow.setBizType(STOCK_FLOW_BIZ_TYPE_PURCHASE);
            imsStockFlow.setBizId(imsInbound.getId());
            imsStockFlow.setEnterpriseId(imsInbound.getEnterpriseId());
            imsStockFlow.setWarehouseId(imsInbound.getWarehouseId());
            imsStockFlow.setProductId(item.getProductId());
            imsStockFlow.setOccurTime(imsInbound.getBillDate());
            imsStockFlow.setInout(STOCK_FLOW_INOUT_IN);
            imsStockFlow.setQtyPiece(item.getQtyPiece());
            imsStockFlow.setUnitPrice(item.getUnitPrice());
            imsStockFlow.setAmount(item.getAmount());
            imsStockFlow.setCreateBy(imsInbound.getCreateBy());
            imsStockFlow.setRemark(imsInbound.getRemark());
            imsStockFlowService.insertImsStockFlow(imsStockFlow);
        }
    }

    private boolean isApproved(ImsInbound imsInbound)
    {
        return imsInbound != null && APPROVED_STATUS.equals(imsInbound.getBillStatus());
    }

    private void calculateInbound(ImsInbound imsInbound)
    {
        List<ImsInboundItem> items = imsInbound.getImsInboundItemList();
        BigDecimal totalQty = BigDecimal.ZERO;
        BigDecimal totalAmount = BigDecimal.ZERO;
        if (StringUtils.isNotNull(items))
        {
            int lineNo = 1;
            for (ImsInboundItem item : items)
            {
                if (item.getProductId() == null)
                {
                    throw new ServiceException("入库单明细第" + lineNo + "行产品名称不能为空");
                }
                item.setQtyPiece(ImsAmountUtils.qty(item.getQtyPiece()));
                item.setUnitPrice(ImsAmountUtils.money(item.getUnitPrice()));
                item.setAmount(ImsAmountUtils.amount(item.getQtyPiece(), item.getUnitPrice()));
                totalQty = totalQty.add(item.getQtyPiece());
                totalAmount = totalAmount.add(item.getAmount());
                lineNo++;
            }
        }
        imsInbound.setTotalQtyPiece(totalQty);
        imsInbound.setTotalAmount(totalAmount.setScale(2, RoundingMode.HALF_UP));
    }
}
