package com.ruoyi.ims.service;

import java.util.List;
import com.ruoyi.ims.domain.ImsStockFlow;

/**
 * 库存流水Service接口
 * 
 * @author tzq
 * @date 2026-05-29
 */
public interface IImsStockFlowService 
{
    /**
     * 查询库存流水
     * 
     * @param id 库存流水主键
     * @return 库存流水
     */
    public ImsStockFlow selectImsStockFlowById(Long id);

    /**
     * 查询库存流水列表
     * 
     * @param imsStockFlow 库存流水
     * @return 库存流水集合
     */
    public List<ImsStockFlow> selectImsStockFlowList(ImsStockFlow imsStockFlow);

    /**
     * 新增库存流水
     * 
     * @param imsStockFlow 库存流水
     * @return 结果
     */
    public int insertImsStockFlow(ImsStockFlow imsStockFlow);

    /**
     * 修改库存流水
     * 
     * @param imsStockFlow 库存流水
     * @return 结果
     */
    public int updateImsStockFlow(ImsStockFlow imsStockFlow);

    /**
     * 批量删除库存流水
     * 
     * @param ids 需要删除的库存流水主键集合
     * @return 结果
     */
    public int deleteImsStockFlowByIds(Long[] ids);

    /**
     * 删除库存流水信息
     * 
     * @param id 库存流水主键
     * @return 结果
     */
    public int deleteImsStockFlowById(Long id);
}
