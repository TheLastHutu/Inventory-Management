package com.ruoyi.ims.service;

import java.util.List;
import com.ruoyi.ims.domain.ImsSample;

/**
 * 样品单Service接口
 * 
 * @author tzq
 * @date 2026-05-29
 */
public interface IImsSampleService 
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
     * 批量删除样品单
     * 
     * @param ids 需要删除的样品单主键集合
     * @return 结果
     */
    public int deleteImsSampleByIds(Long[] ids);

    /**
     * 删除样品单信息
     * 
     * @param id 样品单主键
     * @return 结果
     */
    public int deleteImsSampleById(Long id);
}
