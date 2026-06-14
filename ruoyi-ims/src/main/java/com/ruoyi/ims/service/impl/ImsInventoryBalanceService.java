package com.ruoyi.ims.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.ims.domain.ImsInbound;
import com.ruoyi.ims.domain.ImsInboundItem;
import com.ruoyi.ims.domain.ImsInventory;
import com.ruoyi.ims.domain.ImsOutbound;
import com.ruoyi.ims.domain.ImsOutboundItem;
import com.ruoyi.ims.mapper.ImsInventoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImsInventoryBalanceService
{
    private static final String NORMAL_STATUS = "0";

    @Autowired
    private ImsInventoryMapper imsInventoryMapper;

    public void applyInbound(ImsInbound inbound, int direction)
    {
        if (inbound == null || StringUtils.isNull(inbound.getEnterpriseId()) || StringUtils.isNull(inbound.getWarehouseId()))
        {
            return;
        }
        for (Map.Entry<Long, BigDecimal> entry : sumInboundQty(inbound.getImsInboundItemList()).entrySet())
        {
            changeInventory(inbound.getEnterpriseId(), inbound.getWarehouseId(), entry.getKey(), entry.getValue().multiply(BigDecimal.valueOf(direction)));
        }
    }

    public void applyOutbound(ImsOutbound outbound, int direction)
    {
        if (outbound == null || StringUtils.isNull(outbound.getEnterpriseId()) || StringUtils.isNull(outbound.getWarehouseId()))
        {
            return;
        }
        for (Map.Entry<Long, BigDecimal> entry : sumOutboundQty(outbound.getImsOutboundItemList()).entrySet())
        {
            changeInventory(outbound.getEnterpriseId(), outbound.getWarehouseId(), entry.getKey(), entry.getValue().multiply(BigDecimal.valueOf(direction)));
        }
    }

    private void changeInventory(Long enterpriseId, Long warehouseId, Long productId, BigDecimal changeQty)
    {
        if (StringUtils.isNull(productId) || changeQty == null || changeQty.compareTo(BigDecimal.ZERO) == 0)
        {
            return;
        }
        List<ImsInventory> rows = imsInventoryMapper.selectInventoryRowsForUpdate(enterpriseId, warehouseId, productId);
        if (changeQty.compareTo(BigDecimal.ZERO) > 0)
        {
            increaseInventory(enterpriseId, warehouseId, productId, changeQty, rows);
        }
        else
        {
            decreaseInventory(changeQty.abs(), rows);
        }
    }

    private void increaseInventory(Long enterpriseId, Long warehouseId, Long productId, BigDecimal qty, List<ImsInventory> rows)
    {
        if (StringUtils.isNotEmpty(rows))
        {
            imsInventoryMapper.updateInventoryQtyById(rows.get(0).getId(), qty, currentUsername());
            return;
        }
        ImsInventory inventory = new ImsInventory();
        inventory.setEnterpriseId(enterpriseId);
        inventory.setWarehouseId(warehouseId);
        inventory.setProductId(productId);
        inventory.setQtyPiece(qty);
        inventory.setLockedQtyPiece(BigDecimal.ZERO);
        inventory.setAvailQtyPiece(qty);
        inventory.setStatus(NORMAL_STATUS);
        inventory.setCreateBy(currentUsername());
        inventory.setCreateTime(DateUtils.getNowDate());
        imsInventoryMapper.insertImsInventory(inventory);
    }

    private void decreaseInventory(BigDecimal qty, List<ImsInventory> rows)
    {
        BigDecimal availableQty = BigDecimal.ZERO;
        for (ImsInventory row : rows)
        {
            availableQty = availableQty.add(nullToZero(row.getAvailQtyPiece()));
        }
        if (availableQty.compareTo(qty) < 0)
        {
            throw new ServiceException("\u5e93\u5b58\u6570\u91cf\u4e0d\u8db3");
        }

        BigDecimal remainQty = qty;
        for (ImsInventory row : rows)
        {
            if (remainQty.compareTo(BigDecimal.ZERO) <= 0)
            {
                break;
            }
            BigDecimal rowAvailQty = nullToZero(row.getAvailQtyPiece());
            if (rowAvailQty.compareTo(BigDecimal.ZERO) <= 0)
            {
                continue;
            }
            BigDecimal deductQty = rowAvailQty.min(remainQty);
            imsInventoryMapper.updateInventoryQtyById(row.getId(), deductQty.negate(), currentUsername());
            remainQty = remainQty.subtract(deductQty);
        }
    }

    private Map<Long, BigDecimal> sumInboundQty(List<ImsInboundItem> items)
    {
        Map<Long, BigDecimal> result = new HashMap<Long, BigDecimal>();
        if (StringUtils.isNull(items))
        {
            return result;
        }
        for (ImsInboundItem item : items)
        {
            if (StringUtils.isNull(item.getProductId()))
            {
                continue;
            }
            BigDecimal qty = nullToZero(item.getQtyPiece());
            result.put(item.getProductId(), result.getOrDefault(item.getProductId(), BigDecimal.ZERO).add(qty));
        }
        return result;
    }

    private Map<Long, BigDecimal> sumOutboundQty(List<ImsOutboundItem> items)
    {
        Map<Long, BigDecimal> result = new HashMap<Long, BigDecimal>();
        if (StringUtils.isNull(items))
        {
            return result;
        }
        for (ImsOutboundItem item : items)
        {
            if (StringUtils.isNull(item.getProductId()))
            {
                continue;
            }
            BigDecimal qty = nullToZero(item.getQtyPiece());
            result.put(item.getProductId(), result.getOrDefault(item.getProductId(), BigDecimal.ZERO).add(qty));
        }
        return result;
    }

    private BigDecimal nullToZero(BigDecimal value)
    {
        return value == null ? BigDecimal.ZERO : value;
    }

    private String currentUsername()
    {
        try
        {
            return SecurityUtils.getUsername();
        }
        catch (Exception e)
        {
            return null;
        }
    }
}
