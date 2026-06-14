package com.ruoyi.ims.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.ims.mapper.ImsSupplierMapper;
import com.ruoyi.ims.domain.ImsSupplier;
import com.ruoyi.ims.service.IImsSupplierService;

/**
 * 供应商管理Service业务层处理
 * 
 * @author tzq
 * @date 2026-05-29
 */
@Service
public class ImsSupplierServiceImpl implements IImsSupplierService 
{
    @Autowired
    private ImsSupplierMapper imsSupplierMapper;

    /**
     * 查询供应商管理
     * 
     * @param id 供应商管理主键
     * @return 供应商管理
     */
    @Override
    public ImsSupplier selectImsSupplierById(Long id)
    {
        return imsSupplierMapper.selectImsSupplierById(id);
    }

    /**
     * 查询供应商管理列表
     * 
     * @param imsSupplier 供应商管理
     * @return 供应商管理
     */
    @Override
    public List<ImsSupplier> selectImsSupplierList(ImsSupplier imsSupplier)
    {
        return imsSupplierMapper.selectImsSupplierList(imsSupplier);
    }

    /**
     * 新增供应商管理
     * 
     * @param imsSupplier 供应商管理
     * @return 结果
     */
    @Override
    public int insertImsSupplier(ImsSupplier imsSupplier)
    {
        imsSupplier.setCreateTime(DateUtils.getNowDate());
        return imsSupplierMapper.insertImsSupplier(imsSupplier);
    }

    /**
     * 修改供应商管理
     * 
     * @param imsSupplier 供应商管理
     * @return 结果
     */
    @Override
    public int updateImsSupplier(ImsSupplier imsSupplier)
    {
        imsSupplier.setUpdateTime(DateUtils.getNowDate());
        return imsSupplierMapper.updateImsSupplier(imsSupplier);
    }

    /**
     * 批量删除供应商管理
     * 
     * @param ids 需要删除的供应商管理主键
     * @return 结果
     */
    @Override
    public int deleteImsSupplierByIds(Long[] ids)
    {
        return imsSupplierMapper.deleteImsSupplierByIds(ids);
    }

    /**
     * 删除供应商管理信息
     * 
     * @param id 供应商管理主键
     * @return 结果
     */
    @Override
    public int deleteImsSupplierById(Long id)
    {
        return imsSupplierMapper.deleteImsSupplierById(id);
    }
}
