package com.ruoyi.ims.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.ims.mapper.ImsBrandMapper;
import com.ruoyi.ims.domain.ImsBrand;
import com.ruoyi.ims.service.IImsBrandService;

/**
 * 品牌管理Service业务层处理
 * 
 * @author tzq
 * @date 2026-05-29
 */
@Service
public class ImsBrandServiceImpl implements IImsBrandService 
{
    @Autowired
    private ImsBrandMapper imsBrandMapper;

    /**
     * 查询品牌管理
     * 
     * @param id 品牌管理主键
     * @return 品牌管理
     */
    @Override
    public ImsBrand selectImsBrandById(Long id)
    {
        return imsBrandMapper.selectImsBrandById(id);
    }

    /**
     * 查询品牌管理列表
     * 
     * @param imsBrand 品牌管理
     * @return 品牌管理
     */
    @Override
    public List<ImsBrand> selectImsBrandList(ImsBrand imsBrand)
    {
        return imsBrandMapper.selectImsBrandList(imsBrand);
    }

    /**
     * 新增品牌管理
     * 
     * @param imsBrand 品牌管理
     * @return 结果
     */
    @Override
    public int insertImsBrand(ImsBrand imsBrand)
    {
        imsBrand.setCreateTime(DateUtils.getNowDate());
        return imsBrandMapper.insertImsBrand(imsBrand);
    }

    /**
     * 修改品牌管理
     * 
     * @param imsBrand 品牌管理
     * @return 结果
     */
    @Override
    public int updateImsBrand(ImsBrand imsBrand)
    {
        imsBrand.setUpdateTime(DateUtils.getNowDate());
        return imsBrandMapper.updateImsBrand(imsBrand);
    }

    /**
     * 批量删除品牌管理
     * 
     * @param ids 需要删除的品牌管理主键
     * @return 结果
     */
    @Override
    public int deleteImsBrandByIds(Long[] ids)
    {
        return imsBrandMapper.deleteImsBrandByIds(ids);
    }

    /**
     * 删除品牌管理信息
     * 
     * @param id 品牌管理主键
     * @return 结果
     */
    @Override
    public int deleteImsBrandById(Long id)
    {
        return imsBrandMapper.deleteImsBrandById(id);
    }
}
