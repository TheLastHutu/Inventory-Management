package com.ruoyi.ims.mapper;

import java.util.List;
import com.ruoyi.ims.domain.ImsSupplier;

/**
 * 供应商管理Mapper接口
 * 
 * @author tzq
 * @date 2026-05-29
 */
public interface ImsSupplierMapper 
{
    /**
     * 查询供应商管理
     * 
     * @param id 供应商管理主键
     * @return 供应商管理
     */
    public ImsSupplier selectImsSupplierById(Long id);

    /**
     * 查询供应商管理列表
     * 
     * @param imsSupplier 供应商管理
     * @return 供应商管理集合
     */
    public List<ImsSupplier> selectImsSupplierList(ImsSupplier imsSupplier);

    /**
     * 新增供应商管理
     * 
     * @param imsSupplier 供应商管理
     * @return 结果
     */
    public int insertImsSupplier(ImsSupplier imsSupplier);

    /**
     * 修改供应商管理
     * 
     * @param imsSupplier 供应商管理
     * @return 结果
     */
    public int updateImsSupplier(ImsSupplier imsSupplier);

    /**
     * 删除供应商管理
     * 
     * @param id 供应商管理主键
     * @return 结果
     */
    public int deleteImsSupplierById(Long id);

    /**
     * 批量删除供应商管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteImsSupplierByIds(Long[] ids);
}
