package com.ruoyi.ims.service;

import java.util.List;
import com.ruoyi.ims.domain.ImsProductCategory;

/**
 * 产品分类Service接口
 * 
 * @author tzq
 * @date 2026-05-29
 */
public interface IImsProductCategoryService 
{
    /**
     * 查询产品分类
     * 
     * @param id 产品分类主键
     * @return 产品分类
     */
    public ImsProductCategory selectImsProductCategoryById(Long id);

    /**
     * 查询产品分类列表
     * 
     * @param imsProductCategory 产品分类
     * @return 产品分类集合
     */
    public List<ImsProductCategory> selectImsProductCategoryList(ImsProductCategory imsProductCategory);

    /**
     * 新增产品分类
     * 
     * @param imsProductCategory 产品分类
     * @return 结果
     */
    public int insertImsProductCategory(ImsProductCategory imsProductCategory);

    /**
     * 修改产品分类
     * 
     * @param imsProductCategory 产品分类
     * @return 结果
     */
    public int updateImsProductCategory(ImsProductCategory imsProductCategory);

    /**
     * 批量删除产品分类
     * 
     * @param ids 需要删除的产品分类主键集合
     * @return 结果
     */
    public int deleteImsProductCategoryByIds(Long[] ids);

    /**
     * 删除产品分类信息
     * 
     * @param id 产品分类主键
     * @return 结果
     */
    public int deleteImsProductCategoryById(Long id);
}
