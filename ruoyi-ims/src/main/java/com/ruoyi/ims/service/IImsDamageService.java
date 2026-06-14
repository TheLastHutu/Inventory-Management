package com.ruoyi.ims.service;

import java.util.List;
import com.ruoyi.ims.domain.ImsDamage;

/**
 * 报损单Service接口
 * 
 * @author tzq
 * @date 2026-05-29
 */
public interface IImsDamageService 
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
     * 批量删除报损单
     * 
     * @param ids 需要删除的报损单主键集合
     * @return 结果
     */
    public int deleteImsDamageByIds(Long[] ids);

    /**
     * 删除报损单信息
     * 
     * @param id 报损单主键
     * @return 结果
     */
    public int deleteImsDamageById(Long id);
}
