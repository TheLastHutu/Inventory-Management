package com.ruoyi.ims.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.ims.mapper.ImsProductMapper;
import com.ruoyi.ims.domain.ImsProduct;
import com.ruoyi.ims.service.IImsProductService;

/**
 * 产品管理Service业务层处理
 * 
 * @author tzq
 * @date 2026-05-29
 */
@Service
public class ImsProductServiceImpl implements IImsProductService 
{
    @Autowired
    private ImsProductMapper imsProductMapper;

    /**
     * 查询产品管理
     * 
     * @param id 产品管理主键
     * @return 产品管理
     */
    @Override
    public ImsProduct selectImsProductById(Long id)
    {
        return imsProductMapper.selectImsProductById(id);
    }

    /**
     * 查询产品管理列表
     * 
     * @param imsProduct 产品管理
     * @return 产品管理
     */
    @Override
    public List<ImsProduct> selectImsProductList(ImsProduct imsProduct)
    {
        return imsProductMapper.selectImsProductList(imsProduct);
    }

    /**
     * 新增产品管理
     * 
     * @param imsProduct 产品管理
     * @return 结果
     */
    @Override
    public int insertImsProduct(ImsProduct imsProduct)
    {
        imsProduct.setCreateTime(DateUtils.getNowDate());
        return imsProductMapper.insertImsProduct(imsProduct);
    }

    /**
     * 修改产品管理
     * 
     * @param imsProduct 产品管理
     * @return 结果
     */
    @Override
    public int updateImsProduct(ImsProduct imsProduct)
    {
        imsProduct.setUpdateTime(DateUtils.getNowDate());
        return imsProductMapper.updateImsProduct(imsProduct);
    }

    /**
     * 批量删除产品管理
     * 
     * @param ids 需要删除的产品管理主键
     * @return 结果
     */
    @Override
    public int deleteImsProductByIds(Long[] ids)
    {
        return imsProductMapper.deleteImsProductByIds(ids);
    }

    /**
     * 删除产品管理信息
     * 
     * @param id 产品管理主键
     * @return 结果
     */
    @Override
    public int deleteImsProductById(Long id)
    {
        return imsProductMapper.deleteImsProductById(id);
    }
}
