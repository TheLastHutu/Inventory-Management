package com.ruoyi.ims.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.ims.domain.ImsDamageItem;
import com.ruoyi.ims.mapper.ImsDamageMapper;
import com.ruoyi.ims.domain.ImsDamage;
import com.ruoyi.ims.service.IImsDamageService;

/**
 * 报损单Service业务层处理
 * 
 * @author tzq
 * @date 2026-05-29
 */
@Service
public class ImsDamageServiceImpl implements IImsDamageService 
{
    @Autowired
    private ImsDamageMapper imsDamageMapper;

    /**
     * 查询报损单
     * 
     * @param id 报损单主键
     * @return 报损单
     */
    @Override
    public ImsDamage selectImsDamageById(Long id)
    {
        return imsDamageMapper.selectImsDamageById(id);
    }

    /**
     * 查询报损单列表
     * 
     * @param imsDamage 报损单
     * @return 报损单
     */
    @Override
    public List<ImsDamage> selectImsDamageList(ImsDamage imsDamage)
    {
        return imsDamageMapper.selectImsDamageList(imsDamage);
    }

    /**
     * 新增报损单
     * 
     * @param imsDamage 报损单
     * @return 结果
     */
    @Transactional
    @Override
    public int insertImsDamage(ImsDamage imsDamage)
    {
        calculateDamage(imsDamage);
        imsDamage.setCreateTime(DateUtils.getNowDate());
        int rows = imsDamageMapper.insertImsDamage(imsDamage);
        insertImsDamageItem(imsDamage);
        return rows;
    }

    /**
     * 修改报损单
     * 
     * @param imsDamage 报损单
     * @return 结果
     */
    @Transactional
    @Override
    public int updateImsDamage(ImsDamage imsDamage)
    {
        calculateDamage(imsDamage);
        imsDamage.setUpdateTime(DateUtils.getNowDate());
        imsDamageMapper.deleteImsDamageItemByDamageId(imsDamage.getId());
        insertImsDamageItem(imsDamage);
        return imsDamageMapper.updateImsDamage(imsDamage);
    }

    /**
     * 批量删除报损单
     * 
     * @param ids 需要删除的报损单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteImsDamageByIds(Long[] ids)
    {
        imsDamageMapper.deleteImsDamageItemByDamageIds(ids);
        return imsDamageMapper.deleteImsDamageByIds(ids);
    }

    /**
     * 删除报损单信息
     * 
     * @param id 报损单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteImsDamageById(Long id)
    {
        imsDamageMapper.deleteImsDamageItemByDamageId(id);
        return imsDamageMapper.deleteImsDamageById(id);
    }

    /**
     * 新增报损明细信息
     * 
     * @param imsDamage 报损单对象
     */
    public void insertImsDamageItem(ImsDamage imsDamage)
    {
        List<ImsDamageItem> imsDamageItemList = imsDamage.getImsDamageItemList();
        Long id = imsDamage.getId();
        if (StringUtils.isNotNull(imsDamageItemList))
        {
            List<ImsDamageItem> list = new ArrayList<ImsDamageItem>();
            long lineNo = 1L;
            for (ImsDamageItem imsDamageItem : imsDamageItemList)
            {
                imsDamageItem.setDamageId(id);
                imsDamageItem.setLineNo(lineNo++);
                list.add(imsDamageItem);
            }
            if (list.size() > 0)
            {
                imsDamageMapper.batchImsDamageItem(list);
            }
        }
    }

    private void calculateDamage(ImsDamage imsDamage)
    {
        List<ImsDamageItem> items = imsDamage.getImsDamageItemList();
        java.math.BigDecimal totalQty = java.math.BigDecimal.ZERO;
        java.math.BigDecimal totalAmount = java.math.BigDecimal.ZERO;
        if (StringUtils.isNotNull(items))
        {
            for (ImsDamageItem item : items)
            {
                item.setQtyPiece(ImsAmountUtils.qty(item.getQtyPiece()));
                item.setCostPrice(ImsAmountUtils.money(item.getCostPrice()));
                item.setAmount(ImsAmountUtils.amount(item.getQtyPiece(), item.getCostPrice()));
                totalQty = totalQty.add(item.getQtyPiece());
                totalAmount = totalAmount.add(item.getAmount());
            }
        }
        imsDamage.setTotalQtyPiece(totalQty);
        imsDamage.setTotalAmount(totalAmount.setScale(2, java.math.RoundingMode.HALF_UP));
    }
}
