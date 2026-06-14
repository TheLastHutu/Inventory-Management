package com.ruoyi.ims.service;

import java.util.List;
import com.ruoyi.ims.domain.ImsFixedAsset;

/**
 * 固定资产Service接口
 * 
 * @author tzq
 * @date 2026-05-29
 */
public interface IImsFixedAssetService 
{
    /**
     * 查询固定资产
     * 
     * @param id 固定资产主键
     * @return 固定资产
     */
    public ImsFixedAsset selectImsFixedAssetById(Long id);

    /**
     * 查询固定资产列表
     * 
     * @param imsFixedAsset 固定资产
     * @return 固定资产集合
     */
    public List<ImsFixedAsset> selectImsFixedAssetList(ImsFixedAsset imsFixedAsset);

    /**
     * 新增固定资产
     * 
     * @param imsFixedAsset 固定资产
     * @return 结果
     */
    public int insertImsFixedAsset(ImsFixedAsset imsFixedAsset);

    /**
     * 修改固定资产
     * 
     * @param imsFixedAsset 固定资产
     * @return 结果
     */
    public int updateImsFixedAsset(ImsFixedAsset imsFixedAsset);

    /**
     * 批量删除固定资产
     * 
     * @param ids 需要删除的固定资产主键集合
     * @return 结果
     */
    public int deleteImsFixedAssetByIds(Long[] ids);

    /**
     * 删除固定资产信息
     * 
     * @param id 固定资产主键
     * @return 结果
     */
    public int deleteImsFixedAssetById(Long id);
}
