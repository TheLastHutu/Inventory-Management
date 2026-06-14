package com.ruoyi.ims.service;

import java.util.List;
import com.ruoyi.ims.domain.ImsProduct;

/**
 * 产品管理Service接口
 * 
 * @author tzq
 * @date 2026-05-29
 */
public interface IImsProductService 
{
    /**
     * 查询产品管理
     * 
     * @param id 产品管理主键
     * @return 产品管理
     */
    public ImsProduct selectImsProductById(Long id);

    /**
     * 查询产品管理列表
     * 
     * @param imsProduct 产品管理
     * @return 产品管理集合
     */
    public List<ImsProduct> selectImsProductList(ImsProduct imsProduct);

    /**
     * 新增产品管理
     * 
     * @param imsProduct 产品管理
     * @return 结果
     */
    public int insertImsProduct(ImsProduct imsProduct);

    /**
     * 修改产品管理
     * 
     * @param imsProduct 产品管理
     * @return 结果
     */
    public int updateImsProduct(ImsProduct imsProduct);

    /**
     * 批量删除产品管理
     * 
     * @param ids 需要删除的产品管理主键集合
     * @return 结果
     */
    public int deleteImsProductByIds(Long[] ids);

    /**
     * 删除产品管理信息
     * 
     * @param id 产品管理主键
     * @return 结果
     */
    public int deleteImsProductById(Long id);
}
