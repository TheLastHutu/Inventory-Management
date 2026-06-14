package com.ruoyi.ims.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.ims.domain.ImsSampleItem;
import com.ruoyi.ims.mapper.ImsSampleMapper;
import com.ruoyi.ims.domain.ImsSample;
import com.ruoyi.ims.service.IImsSampleService;

/**
 * 样品单Service业务层处理
 * 
 * @author tzq
 * @date 2026-05-29
 */
@Service
public class ImsSampleServiceImpl implements IImsSampleService 
{
    @Autowired
    private ImsSampleMapper imsSampleMapper;

    /**
     * 查询样品单
     * 
     * @param id 样品单主键
     * @return 样品单
     */
    @Override
    public ImsSample selectImsSampleById(Long id)
    {
        return imsSampleMapper.selectImsSampleById(id);
    }

    /**
     * 查询样品单列表
     * 
     * @param imsSample 样品单
     * @return 样品单
     */
    @Override
    public List<ImsSample> selectImsSampleList(ImsSample imsSample)
    {
        return imsSampleMapper.selectImsSampleList(imsSample);
    }

    /**
     * 新增样品单
     * 
     * @param imsSample 样品单
     * @return 结果
     */
    @Transactional
    @Override
    public int insertImsSample(ImsSample imsSample)
    {
        calculateSample(imsSample);
        imsSample.setCreateTime(DateUtils.getNowDate());
        int rows = imsSampleMapper.insertImsSample(imsSample);
        insertImsSampleItem(imsSample);
        return rows;
    }

    /**
     * 修改样品单
     * 
     * @param imsSample 样品单
     * @return 结果
     */
    @Transactional
    @Override
    public int updateImsSample(ImsSample imsSample)
    {
        calculateSample(imsSample);
        imsSample.setUpdateTime(DateUtils.getNowDate());
        imsSampleMapper.deleteImsSampleItemBySampleId(imsSample.getId());
        insertImsSampleItem(imsSample);
        return imsSampleMapper.updateImsSample(imsSample);
    }

    /**
     * 批量删除样品单
     * 
     * @param ids 需要删除的样品单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteImsSampleByIds(Long[] ids)
    {
        imsSampleMapper.deleteImsSampleItemBySampleIds(ids);
        return imsSampleMapper.deleteImsSampleByIds(ids);
    }

    /**
     * 删除样品单信息
     * 
     * @param id 样品单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteImsSampleById(Long id)
    {
        imsSampleMapper.deleteImsSampleItemBySampleId(id);
        return imsSampleMapper.deleteImsSampleById(id);
    }

    /**
     * 新增样品明细信息
     * 
     * @param imsSample 样品单对象
     */
    public void insertImsSampleItem(ImsSample imsSample)
    {
        List<ImsSampleItem> imsSampleItemList = imsSample.getImsSampleItemList();
        Long id = imsSample.getId();
        if (StringUtils.isNotNull(imsSampleItemList))
        {
            List<ImsSampleItem> list = new ArrayList<ImsSampleItem>();
            long lineNo = 1L;
            for (ImsSampleItem imsSampleItem : imsSampleItemList)
            {
                imsSampleItem.setSampleId(id);
                imsSampleItem.setLineNo(lineNo++);
                list.add(imsSampleItem);
            }
            if (list.size() > 0)
            {
                imsSampleMapper.batchImsSampleItem(list);
            }
        }
    }

    private void calculateSample(ImsSample imsSample)
    {
        List<ImsSampleItem> items = imsSample.getImsSampleItemList();
        java.math.BigDecimal totalQty = java.math.BigDecimal.ZERO;
        if (StringUtils.isNotNull(items))
        {
            for (ImsSampleItem item : items)
            {
                item.setQtyPiece(ImsAmountUtils.qty(item.getQtyPiece()));
                totalQty = totalQty.add(item.getQtyPiece());
            }
        }
        imsSample.setTotalQtyPiece(totalQty);
    }
}
