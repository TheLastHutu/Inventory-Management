package com.ruoyi.ims.service;

import java.util.List;
import com.ruoyi.ims.domain.ImsOutbound;

/**
 * 出库单Service接口
 * 
 * @author tzq
 * @date 2026-05-29
 */
public interface IImsOutboundService 
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
     * 批量删除出库单
     * 
     * @param ids 需要删除的出库单主键集合
     * @return 结果
     */
    public int deleteImsOutboundByIds(Long[] ids);

    /**
     * 删除出库单信息
     * 
     * @param id 出库单主键
     * @return 结果
     */
    public int deleteImsOutboundById(Long id);
}
