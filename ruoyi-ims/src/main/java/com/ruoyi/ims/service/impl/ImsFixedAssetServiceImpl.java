package com.ruoyi.ims.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.ims.mapper.ImsFixedAssetMapper;
import com.ruoyi.ims.domain.ImsFixedAsset;
import com.ruoyi.ims.service.IImsFixedAssetService;

/**
 * 固定资产Service业务层处理
 * 
 * @author tzq
 * @date 2026-05-29
 */
@Service
public class ImsFixedAssetServiceImpl implements IImsFixedAssetService 
{
    @Autowired
    private ImsFixedAssetMapper imsFixedAssetMapper;

    /**
     * 查询固定资产
     * 
     * @param id 固定资产主键
     * @return 固定资产
     */
    @Override
    public ImsFixedAsset selectImsFixedAssetById(Long id)
    {
        return imsFixedAssetMapper.selectImsFixedAssetById(id);
    }

    /**
     * 查询固定资产列表
     * 
     * @param imsFixedAsset 固定资产
     * @return 固定资产
     */
    @Override
    public List<ImsFixedAsset> selectImsFixedAssetList(ImsFixedAsset imsFixedAsset)
    {
        return imsFixedAssetMapper.selectImsFixedAssetList(imsFixedAsset);
    }

    /**
     * 新增固定资产
     * 
     * @param imsFixedAsset 固定资产
     * @return 结果
     */
    @Override
    public int insertImsFixedAsset(ImsFixedAsset imsFixedAsset)
    {
        imsFixedAsset.setCreateTime(DateUtils.getNowDate());
        return imsFixedAssetMapper.insertImsFixedAsset(imsFixedAsset);
    }

    /**
     * 修改固定资产
     * 
     * @param imsFixedAsset 固定资产
     * @return 结果
     */
    @Override
    public int updateImsFixedAsset(ImsFixedAsset imsFixedAsset)
    {
        imsFixedAsset.setUpdateTime(DateUtils.getNowDate());
        return imsFixedAssetMapper.updateImsFixedAsset(imsFixedAsset);
    }

    /**
     * 批量删除固定资产
     * 
     * @param ids 需要删除的固定资产主键
     * @return 结果
     */
    @Override
    public int deleteImsFixedAssetByIds(Long[] ids)
    {
        return imsFixedAssetMapper.deleteImsFixedAssetByIds(ids);
    }

    /**
     * 删除固定资产信息
     * 
     * @param id 固定资产主键
     * @return 结果
     */
    @Override
    public int deleteImsFixedAssetById(Long id)
    {
        return imsFixedAssetMapper.deleteImsFixedAssetById(id);
    }
}
