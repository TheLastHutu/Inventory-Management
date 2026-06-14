package com.ruoyi.ims.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.ims.mapper.ImsStockFlowMapper;
import com.ruoyi.ims.domain.ImsStockFlow;
import com.ruoyi.ims.service.IImsStockFlowService;

/**
 * 库存流水Service业务层处理
 * 
 * @author tzq
 * @date 2026-05-29
 */
@Service
public class ImsStockFlowServiceImpl implements IImsStockFlowService 
{
    @Autowired
    private ImsStockFlowMapper imsStockFlowMapper;

    /**
     * 查询库存流水
     * 
     * @param id 库存流水主键
     * @return 库存流水
     */
    @Override
    public ImsStockFlow selectImsStockFlowById(Long id)
    {
        return imsStockFlowMapper.selectImsStockFlowById(id);
    }

    /**
     * 查询库存流水列表
     * 
     * @param imsStockFlow 库存流水
     * @return 库存流水
     */
    @Override
    public List<ImsStockFlow> selectImsStockFlowList(ImsStockFlow imsStockFlow)
    {
        return imsStockFlowMapper.selectImsStockFlowList(imsStockFlow);
    }

    /**
     * 新增库存流水
     * 
     * @param imsStockFlow 库存流水
     * @return 结果
     */
    @Override
    public int insertImsStockFlow(ImsStockFlow imsStockFlow)
    {
        calculateStockFlow(imsStockFlow);
        imsStockFlow.setCreateTime(DateUtils.getNowDate());
        return imsStockFlowMapper.insertImsStockFlow(imsStockFlow);
    }

    /**
     * 修改库存流水
     * 
     * @param imsStockFlow 库存流水
     * @return 结果
     */
    @Override
    public int updateImsStockFlow(ImsStockFlow imsStockFlow)
    {
        calculateStockFlow(imsStockFlow);
        return imsStockFlowMapper.updateImsStockFlow(imsStockFlow);
    }

    /**
     * 批量删除库存流水
     * 
     * @param ids 需要删除的库存流水主键
     * @return 结果
     */
    @Override
    public int deleteImsStockFlowByIds(Long[] ids)
    {
        return imsStockFlowMapper.deleteImsStockFlowByIds(ids);
    }

    /**
     * 删除库存流水信息
     * 
     * @param id 库存流水主键
     * @return 结果
     */
    @Override
    public int deleteImsStockFlowById(Long id)
    {
        return imsStockFlowMapper.deleteImsStockFlowById(id);
    }

    private void calculateStockFlow(ImsStockFlow imsStockFlow)
    {
        imsStockFlow.setQtyPiece(ImsAmountUtils.qty(imsStockFlow.getQtyPiece()));
        imsStockFlow.setUnitPrice(ImsAmountUtils.money(imsStockFlow.getUnitPrice()));
        imsStockFlow.setAmount(ImsAmountUtils.amount(imsStockFlow.getQtyPiece(), imsStockFlow.getUnitPrice()));
    }
}
