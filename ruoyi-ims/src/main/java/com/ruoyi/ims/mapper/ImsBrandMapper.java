package com.ruoyi.ims.mapper;

import java.util.List;
import com.ruoyi.ims.domain.ImsBrand;

/**
 * 品牌管理Mapper接口
 * 
 * @author tzq
 * @date 2026-05-29
 */
public interface ImsBrandMapper 
{
    /**
     * 查询品牌管理
     * 
     * @param id 品牌管理主键
     * @return 品牌管理
     */
    public ImsBrand selectImsBrandById(Long id);

    /**
     * 查询品牌管理列表
     * 
     * @param imsBrand 品牌管理
     * @return 品牌管理集合
     */
    public List<ImsBrand> selectImsBrandList(ImsBrand imsBrand);

    /**
     * 新增品牌管理
     * 
     * @param imsBrand 品牌管理
     * @return 结果
     */
    public int insertImsBrand(ImsBrand imsBrand);

    /**
     * 修改品牌管理
     * 
     * @param imsBrand 品牌管理
     * @return 结果
     */
    public int updateImsBrand(ImsBrand imsBrand);

    /**
     * 删除品牌管理
     * 
     * @param id 品牌管理主键
     * @return 结果
     */
    public int deleteImsBrandById(Long id);

    /**
     * 批量删除品牌管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteImsBrandByIds(Long[] ids);
}
