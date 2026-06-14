package com.ruoyi.ims.service;

import java.util.List;
import com.ruoyi.ims.domain.ImsUnit;

/**
 * 计量单位管理Service接口
 * 
 * @author tzq
 * @date 2026-05-29
 */
public interface IImsUnitService 
{
    /**
     * 查询计量单位管理
     * 
     * @param id 计量单位管理主键
     * @return 计量单位管理
     */
    public ImsUnit selectImsUnitById(Long id);

    /**
     * 查询计量单位管理列表
     * 
     * @param imsUnit 计量单位管理
     * @return 计量单位管理集合
     */
    public List<ImsUnit> selectImsUnitList(ImsUnit imsUnit);

    /**
     * 新增计量单位管理
     * 
     * @param imsUnit 计量单位管理
     * @return 结果
     */
    public int insertImsUnit(ImsUnit imsUnit);

    /**
     * 修改计量单位管理
     * 
     * @param imsUnit 计量单位管理
     * @return 结果
     */
    public int updateImsUnit(ImsUnit imsUnit);

    /**
     * 批量删除计量单位管理
     * 
     * @param ids 需要删除的计量单位管理主键集合
     * @return 结果
     */
    public int deleteImsUnitByIds(Long[] ids);

    /**
     * 删除计量单位管理信息
     * 
     * @param id 计量单位管理主键
     * @return 结果
     */
    public int deleteImsUnitById(Long id);
}
