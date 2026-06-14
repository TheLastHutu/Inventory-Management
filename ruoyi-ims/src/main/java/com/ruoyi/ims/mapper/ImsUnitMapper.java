package com.ruoyi.ims.mapper;

import java.util.List;
import com.ruoyi.ims.domain.ImsUnit;

/**
 * 计量单位管理Mapper接口
 * 
 * @author tzq
 * @date 2026-05-29
 */
public interface ImsUnitMapper 
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
     * 删除计量单位管理
     * 
     * @param id 计量单位管理主键
     * @return 结果
     */
    public int deleteImsUnitById(Long id);

    /**
     * 批量删除计量单位管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteImsUnitByIds(Long[] ids);
}
