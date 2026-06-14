package com.ruoyi.ims.mapper;

import java.util.List;
import com.ruoyi.ims.domain.ImsDamage;
import com.ruoyi.ims.domain.ImsDamageItem;

/**
 * 报损单Mapper接口
 * 
 * @author tzq
 * @date 2026-05-29
 */
public interface ImsDamageMapper 
{
    /**
     * 查询报损单
     * 
     * @param id 报损单主键
     * @return 报损单
     */
    public ImsDamage selectImsDamageById(Long id);

    /**
     * 查询报损单列表
     * 
     * @param imsDamage 报损单
     * @return 报损单集合
     */
    public List<ImsDamage> selectImsDamageList(ImsDamage imsDamage);

    /**
     * 新增报损单
     * 
     * @param imsDamage 报损单
     * @return 结果
     */
    public int insertImsDamage(ImsDamage imsDamage);

    /**
     * 修改报损单
     * 
     * @param imsDamage 报损单
     * @return 结果
     */
    public int updateImsDamage(ImsDamage imsDamage);

    /**
     * 删除报损单
     * 
     * @param id 报损单主键
     * @return 结果
     */
    public int deleteImsDamageById(Long id);

    /**
     * 批量删除报损单
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteImsDamageByIds(Long[] ids);

    /**
     * 批量删除报损明细
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteImsDamageItemByDamageIds(Long[] ids);
    
    /**
     * 批量新增报损明细
     * 
     * @param imsDamageItemList 报损明细列表
     * @return 结果
     */
    public int batchImsDamageItem(List<ImsDamageItem> imsDamageItemList);
    

    /**
     * 通过报损单主键删除报损明细信息
     * 
     * @param id 报损单ID
     * @return 结果
     */
    public int deleteImsDamageItemByDamageId(Long id);
}
