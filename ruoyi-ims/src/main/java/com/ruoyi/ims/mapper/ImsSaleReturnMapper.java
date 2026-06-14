package com.ruoyi.ims.mapper;

import java.util.List;
import com.ruoyi.ims.domain.ImsSaleReturn;
import com.ruoyi.ims.domain.ImsSaleReturnItem;

/**
 * 销售退货单Mapper接口
 * 
 * @author tzq
 * @date 2026-05-29
 */
public interface ImsSaleReturnMapper 
{
    /**
     * 查询销售退货单
     * 
     * @param id 销售退货单主键
     * @return 销售退货单
     */
    public ImsSaleReturn selectImsSaleReturnById(Long id);

    /**
     * 查询销售退货单列表
     * 
     * @param imsSaleReturn 销售退货单
     * @return 销售退货单集合
     */
    public List<ImsSaleReturn> selectImsSaleReturnList(ImsSaleReturn imsSaleReturn);

    /**
     * 新增销售退货单
     * 
     * @param imsSaleReturn 销售退货单
     * @return 结果
     */
    public int insertImsSaleReturn(ImsSaleReturn imsSaleReturn);

    /**
     * 修改销售退货单
     * 
     * @param imsSaleReturn 销售退货单
     * @return 结果
     */
    public int updateImsSaleReturn(ImsSaleReturn imsSaleReturn);

    /**
     * 删除销售退货单
     * 
     * @param id 销售退货单主键
     * @return 结果
     */
    public int deleteImsSaleReturnById(Long id);

    /**
     * 批量删除销售退货单
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteImsSaleReturnByIds(Long[] ids);

    /**
     * 批量删除销售退货明细
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteImsSaleReturnItemBySaleReturnIds(Long[] ids);
    
    /**
     * 批量新增销售退货明细
     * 
     * @param imsSaleReturnItemList 销售退货明细列表
     * @return 结果
     */
    public int batchImsSaleReturnItem(List<ImsSaleReturnItem> imsSaleReturnItemList);
    

    /**
     * 通过销售退货单主键删除销售退货明细信息
     * 
     * @param id 销售退货单ID
     * @return 结果
     */
    public int deleteImsSaleReturnItemBySaleReturnId(Long id);
}
