package com.ruoyi.ims.service;

import java.util.List;
import com.ruoyi.ims.domain.ImsInbound;

/**
 * 入库单Service接口
 * 
 * @author tzq
 * @date 2026-05-29
 */
public interface IImsInboundService 
{
    /**
     * 查询入库单
     * 
     * @param id 入库单主键
     * @return 入库单
     */
    public ImsInbound selectImsInboundById(Long id);

    /**
     * 查询入库单列表
     * 
     * @param imsInbound 入库单
     * @return 入库单集合
     */
    public List<ImsInbound> selectImsInboundList(ImsInbound imsInbound);

    /**
     * 新增入库单
     * 
     * @param imsInbound 入库单
     * @return 结果
     */
    public int insertImsInbound(ImsInbound imsInbound);

    /**
     * 修改入库单
     * 
     * @param imsInbound 入库单
     * @return 结果
     */
    public int updateImsInbound(ImsInbound imsInbound);

    /**
     * 批量删除入库单
     * 
     * @param ids 需要删除的入库单主键集合
     * @return 结果
     */
    public int deleteImsInboundByIds(Long[] ids);

    /**
     * 删除入库单信息
     * 
     * @param id 入库单主键
     * @return 结果
     */
    public int deleteImsInboundById(Long id);
}
