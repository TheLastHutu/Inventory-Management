package com.ruoyi.ims.mapper;

import java.util.List;
import com.ruoyi.ims.domain.ImsInbound;
import com.ruoyi.ims.domain.ImsInboundItem;

/**
 * 入库单Mapper接口
 * 
 * @author tzq
 * @date 2026-05-29
 */
public interface ImsInboundMapper 
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
     * 删除入库单
     * 
     * @param id 入库单主键
     * @return 结果
     */
    public int deleteImsInboundById(Long id);

    /**
     * 批量删除入库单
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteImsInboundByIds(Long[] ids);

    /**
     * 批量删除入库单明细
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteImsInboundItemByInboundIds(Long[] ids);
    
    /**
     * 批量新增入库单明细
     * 
     * @param imsInboundItemList 入库单明细列表
     * @return 结果
     */
    public int batchImsInboundItem(List<ImsInboundItem> imsInboundItemList);
    

    /**
     * 通过入库单主键删除入库单明细信息
     * 
     * @param id 入库单ID
     * @return 结果
     */
    public int deleteImsInboundItemByInboundId(Long id);
}
