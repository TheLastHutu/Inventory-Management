package com.ruoyi.ims.mapper;

import java.util.List;
import com.ruoyi.ims.domain.ImsOutbound;
import com.ruoyi.ims.domain.ImsOutboundItem;

/**
 * 出库单Mapper接口
 * 
 * @author tzq
 * @date 2026-05-29
 */
public interface ImsOutboundMapper 
{
    /**
     * 查询出库单
     * 
     * @param id 出库单主键
     * @return 出库单
     */
    public ImsOutbound selectImsOutboundById(Long id);

    /**
     * 查询出库单列表
     * 
     * @param imsOutbound 出库单
     * @return 出库单集合
     */
    public List<ImsOutbound> selectImsOutboundList(ImsOutbound imsOutbound);

    /**
     * 新增出库单
     * 
     * @param imsOutbound 出库单
     * @return 结果
     */
    public int insertImsOutbound(ImsOutbound imsOutbound);

    /**
     * 修改出库单
     * 
     * @param imsOutbound 出库单
     * @return 结果
     */
    public int updateImsOutbound(ImsOutbound imsOutbound);

    /**
     * 删除出库单
     * 
     * @param id 出库单主键
     * @return 结果
     */
    public int deleteImsOutboundById(Long id);

    /**
     * 批量删除出库单
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteImsOutboundByIds(Long[] ids);

    /**
     * 批量删除出库单明细
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteImsOutboundItemByOutboundIds(Long[] ids);
    
    /**
     * 批量新增出库单明细
     * 
     * @param imsOutboundItemList 出库单明细列表
     * @return 结果
     */
    public int batchImsOutboundItem(List<ImsOutboundItem> imsOutboundItemList);
    

    /**
     * 通过出库单主键删除出库单明细信息
     * 
     * @param id 出库单ID
     * @return 结果
     */
    public int deleteImsOutboundItemByOutboundId(Long id);
}
