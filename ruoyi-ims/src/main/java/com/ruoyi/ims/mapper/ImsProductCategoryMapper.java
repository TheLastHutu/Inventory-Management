package com.ruoyi.ims.mapper;

import java.util.List;
import com.ruoyi.ims.domain.ImsProductCategory;

/**
 * 产品分类Mapper接口
 * 
 * @author tzq
 * @date 2026-05-29
 */
public interface ImsProductCategoryMapper 
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
     * 删除产品分类
     * 
     * @param id 产品分类主键
     * @return 结果
     */
    public int deleteImsProductCategoryById(Long id);

    /**
     * 批量删除产品分类
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteImsProductCategoryByIds(Long[] ids);
}
