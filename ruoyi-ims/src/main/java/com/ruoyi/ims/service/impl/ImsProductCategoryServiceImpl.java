package com.ruoyi.ims.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.ims.mapper.ImsProductCategoryMapper;
import com.ruoyi.ims.domain.ImsProductCategory;
import com.ruoyi.ims.service.IImsProductCategoryService;

/**
 * 产品分类Service业务层处理
 * 
 * @author tzq
 * @date 2026-05-29
 */
@Service
public class ImsProductCategoryServiceImpl implements IImsProductCategoryService 
{
    @Autowired
    private ImsProductCategoryMapper imsProductCategoryMapper;

    /**
     * 查询产品分类
     * 
     * @param id 产品分类主键
     * @return 产品分类
     */
    @Override
    public ImsProductCategory selectImsProductCategoryById(Long id)
    {
        return imsProductCategoryMapper.selectImsProductCategoryById(id);
    }

    /**
     * 查询产品分类列表
     * 
     * @param imsProductCategory 产品分类
     * @return 产品分类
     */
    @Override
    public List<ImsProductCategory> selectImsProductCategoryList(ImsProductCategory imsProductCategory)
    {
        return imsProductCategoryMapper.selectImsProductCategoryList(imsProductCategory);
    }

    /**
     * 新增产品分类
     * 
     * @param imsProductCategory 产品分类
     * @return 结果
     */
    @Override
    public int insertImsProductCategory(ImsProductCategory imsProductCategory)
    {
        imsProductCategory.setCreateTime(DateUtils.getNowDate());
        return imsProductCategoryMapper.insertImsProductCategory(imsProductCategory);
    }

    /**
     * 修改产品分类
     * 
     * @param imsProductCategory 产品分类
     * @return 结果
     */
    @Override
    public int updateImsProductCategory(ImsProductCategory imsProductCategory)
    {
        imsProductCategory.setUpdateTime(DateUtils.getNowDate());
        return imsProductCategoryMapper.updateImsProductCategory(imsProductCategory);
    }

    /**
     * 批量删除产品分类
     * 
     * @param ids 需要删除的产品分类主键
     * @return 结果
     */
    @Override
    public int deleteImsProductCategoryByIds(Long[] ids)
    {
        return imsProductCategoryMapper.deleteImsProductCategoryByIds(ids);
    }

    /**
     * 删除产品分类信息
     * 
     * @param id 产品分类主键
     * @return 结果
     */
    @Override
    public int deleteImsProductCategoryById(Long id)
    {
        return imsProductCategoryMapper.deleteImsProductCategoryById(id);
    }
}
