package com.ruoyi.ims.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.ims.domain.ImsOutbound;
import com.ruoyi.ims.domain.ImsOutboundItem;
import com.ruoyi.ims.domain.ImsStockFlow;
import com.ruoyi.ims.mapper.ImsOutboundMapper;
import com.ruoyi.ims.service.IImsStockFlowService;
import com.ruoyi.ims.service.IImsOutboundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ImsOutboundServiceImpl implements IImsOutboundService
{
    private static final String APPROVED_STATUS = "1";
    private static final String STOCK_FLOW_BIZ_TYPE_SALE = "0";
    private static final String STOCK_FLOW_INOUT_OUT = "O";

    @Autowired
    private ImsOutboundMapper imsOutboundMapper;

    @Autowired
    private ImsInventoryBalanceService imsInventoryBalanceService;

    @Autowired
    private IImsStockFlowService imsStockFlowService;

    @Override
    public ImsOutbound selectImsOutboundById(Long id)
    {
        return imsOutboundMapper.selectImsOutboundById(id);
    }

    @Override
    public List<ImsOutbound> selectImsOutboundList(ImsOutbound imsOutbound)
    {
        return imsOutboundMapper.selectImsOutboundList(imsOutbound);
    }

    @Transactional
    @Override
    public int insertImsOutbound(ImsOutbound imsOutbound)
    {
        calculateOutbound(imsOutbound);
        imsInventoryBalanceService.applyOutbound(imsOutbound, -1);
        imsOutbound.setCreateTime(DateUtils.getNowDate());
        int rows = imsOutboundMapper.insertImsOutbound(imsOutbound);
        insertImsOutboundItem(imsOutbound);
        if (isApproved(imsOutbound))
        {
            insertOutboundStockFlow(imsOutbound);
        }
        return rows;
    }

    @Transactional
    @Override
    public int updateImsOutbound(ImsOutbound imsOutbound)
    {
        ImsOutbound oldOutbound = imsOutboundMapper.selectImsOutboundById(imsOutbound.getId());
        calculateOutbound(imsOutbound);
        imsInventoryBalanceService.applyOutbound(oldOutbound, 1);
        imsInventoryBalanceService.applyOutbound(imsOutbound, -1);
        imsOutbound.setUpdateTime(DateUtils.getNowDate());
        imsOutboundMapper.deleteImsOutboundItemByOutboundId(imsOutbound.getId());
        insertImsOutboundItem(imsOutbound);
        int rows = imsOutboundMapper.updateImsOutbound(imsOutbound);
        if (!isApproved(oldOutbound) && isApproved(imsOutbound))
        {
            insertOutboundStockFlow(imsOutbound);
        }
        return rows;
    }

    @Transactional
    @Override
    public int deleteImsOutboundByIds(Long[] ids)
    {
        for (Long id : ids)
        {
            imsInventoryBalanceService.applyOutbound(imsOutboundMapper.selectImsOutboundById(id), 1);
        }
        imsOutboundMapper.deleteImsOutboundItemByOutboundIds(ids);
        return imsOutboundMapper.deleteImsOutboundByIds(ids);
    }

    @Transactional
    @Override
    public int deleteImsOutboundById(Long id)
    {
        imsInventoryBalanceService.applyOutbound(imsOutboundMapper.selectImsOutboundById(id), 1);
        imsOutboundMapper.deleteImsOutboundItemByOutboundId(id);
        return imsOutboundMapper.deleteImsOutboundById(id);
    }

    public void insertImsOutboundItem(ImsOutbound imsOutbound)
    {
        List<ImsOutboundItem> imsOutboundItemList = imsOutbound.getImsOutboundItemList();
        Long id = imsOutbound.getId();
        if (StringUtils.isNotNull(imsOutboundItemList))
        {
            List<ImsOutboundItem> list = new ArrayList<ImsOutboundItem>();
            long lineNo = 1L;
            for (ImsOutboundItem imsOutboundItem : imsOutboundItemList)
            {
                imsOutboundItem.setOutboundId(id);
                imsOutboundItem.setLineNo(lineNo++);
                list.add(imsOutboundItem);
            }
            if (list.size() > 0)
            {
                imsOutboundMapper.batchImsOutboundItem(list);
            }
        }
    }

    private void insertOutboundStockFlow(ImsOutbound imsOutbound)
    {
        List<ImsOutboundItem> imsOutboundItemList = imsOutbound.getImsOutboundItemList();
        if (StringUtils.isNull(imsOutboundItemList))
        {
            return;
        }
        for (ImsOutboundItem item : imsOutboundItemList)
        {
            ImsStockFlow imsStockFlow = new ImsStockFlow();
            imsStockFlow.setBizNo(imsOutbound.getBillNo());
            imsStockFlow.setBizType(STOCK_FLOW_BIZ_TYPE_SALE);
            imsStockFlow.setBizId(imsOutbound.getId());
            imsStockFlow.setEnterpriseId(imsOutbound.getEnterpriseId());
            imsStockFlow.setWarehouseId(imsOutbound.getWarehouseId());
            imsStockFlow.setProductId(item.getProductId());
            imsStockFlow.setOccurTime(imsOutbound.getBillDate());
            imsStockFlow.setInout(STOCK_FLOW_INOUT_OUT);
            imsStockFlow.setQtyPiece(item.getQtyPiece());
            imsStockFlow.setUnitPrice(item.getUnitPrice());
            imsStockFlow.setAmount(item.getAmount());
            imsStockFlow.setCreateBy(imsOutbound.getCreateBy());
            imsStockFlow.setRemark(imsOutbound.getRemark());
            imsStockFlowService.insertImsStockFlow(imsStockFlow);
        }
    }

    private boolean isApproved(ImsOutbound imsOutbound)
    {
        return imsOutbound != null && APPROVED_STATUS.equals(imsOutbound.getBillStatus());
    }

    private void calculateOutbound(ImsOutbound imsOutbound)
    {
        List<ImsOutboundItem> items = imsOutbound.getImsOutboundItemList();
        BigDecimal totalQty = BigDecimal.ZERO;
        BigDecimal totalAmount = BigDecimal.ZERO;
        if (StringUtils.isNotNull(items))
        {
            int lineNo = 1;
            for (ImsOutboundItem item : items)
            {
                if (item.getProductId() == null)
                {
                    throw new ServiceException("出库单明细第" + lineNo + "行产品名称不能为空");
                }
                item.setQtyPiece(ImsAmountUtils.qty(item.getQtyPiece()));
                item.setUnitPrice(ImsAmountUtils.money(item.getUnitPrice()));
                item.setAmount(ImsAmountUtils.amount(item.getQtyPiece(), item.getUnitPrice()));
                totalQty = totalQty.add(item.getQtyPiece());
                totalAmount = totalAmount.add(item.getAmount());
                lineNo++;
            }
        }
        imsOutbound.setTotalQtyPiece(totalQty);
        imsOutbound.setTotalAmount(totalAmount.setScale(2, RoundingMode.HALF_UP));
    }
}
