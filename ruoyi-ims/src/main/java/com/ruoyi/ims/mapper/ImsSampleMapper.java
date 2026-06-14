package com.ruoyi.ims.mapper;

import java.util.List;
import com.ruoyi.ims.domain.ImsSample;
import com.ruoyi.ims.domain.ImsSampleItem;

/**
 * 样品单Mapper接口
 * 
 * @author tzq
 * @date 2026-05-29
 */
public interface ImsSampleMapper 
{
    /**
     * 查询样品单
     * 
     * @param id 样品单主键
     * @return 样品单
     */
    public ImsSample selectImsSampleById(Long id);

    /**
     * 查询样品单列表
     * 
     * @param imsSample 样品单
     * @return 样品单集合
     */
    public List<ImsSample> selectImsSampleList(ImsSample imsSample);

    /**
     * 新增样品单
     * 
     * @param imsSample 样品单
     * @return 结果
     */
    public int insertImsSample(ImsSample imsSample);

    /**
     * 修改样品单
     * 
     * @param imsSample 样品单
     * @return 结果
     */
    public int updateImsSample(ImsSample imsSample);

    /**
     * 删除样品单
     * 
     * @param id 样品单主键
     * @return 结果
     */
    public int deleteImsSampleById(Long id);

    /**
     * 批量删除样品单
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteImsSampleByIds(Long[] ids);

    /**
     * 批量删除样品明细
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteImsSampleItemBySampleIds(Long[] ids);
    
    /**
     * 批量新增样品明细
     * 
     * @param imsSampleItemList 样品明细列表
     * @return 结果
     */
    public int batchImsSampleItem(List<ImsSampleItem> imsSampleItemList);
    

    /**
     * 通过样品单主键删除样品明细信息
     * 
     * @param id 样品单ID
     * @return 结果
     */
    public int deleteImsSampleItemBySampleId(Long id);
}
