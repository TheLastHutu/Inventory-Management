package com.ruoyi.ims.service;

import java.util.List;
import com.ruoyi.ims.domain.ImsSaleReturn;

/**
 * 销售退货单Service接口
 * 
 * @author tzq
 * @date 2026-05-29
 */
public interface IImsSaleReturnService 
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
     * 批量删除销售退货单
     * 
     * @param ids 需要删除的销售退货单主键集合
     * @return 结果
     */
    public int deleteImsSaleReturnByIds(Long[] ids);

    /**
     * 删除销售退货单信息
     * 
     * @param id 销售退货单主键
     * @return 结果
     */
    public int deleteImsSaleReturnById(Long id);
}
