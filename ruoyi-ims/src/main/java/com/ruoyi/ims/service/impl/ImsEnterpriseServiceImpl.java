package com.ruoyi.ims.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.ims.mapper.ImsEnterpriseMapper;
import com.ruoyi.ims.domain.ImsEnterprise;
import com.ruoyi.ims.service.IImsEnterpriseService;

/**
 * 企业管理Service业务层处理
 * 
 * @author tzq
 * @date 2026-05-29
 */
@Service
public class ImsEnterpriseServiceImpl implements IImsEnterpriseService 
{
    @Autowired
    private ImsEnterpriseMapper imsEnterpriseMapper;

    /**
     * 查询企业管理
     * 
     * @param id 企业管理主键
     * @return 企业管理
     */
    @Override
    public ImsEnterprise selectImsEnterpriseById(Long id)
    {
        return imsEnterpriseMapper.selectImsEnterpriseById(id);
    }

    /**
     * 查询企业管理列表
     * 
     * @param imsEnterprise 企业管理
     * @return 企业管理
     */
    @Override
    public List<ImsEnterprise> selectImsEnterpriseList(ImsEnterprise imsEnterprise)
    {
        return imsEnterpriseMapper.selectImsEnterpriseList(imsEnterprise);
    }

    /**
     * 新增企业管理
     * 
     * @param imsEnterprise 企业管理
     * @return 结果
     */
    @Override
    public int insertImsEnterprise(ImsEnterprise imsEnterprise)
    {
        imsEnterprise.setCreateTime(DateUtils.getNowDate());
        return imsEnterpriseMapper.insertImsEnterprise(imsEnterprise);
    }

    /**
     * 修改企业管理
     * 
     * @param imsEnterprise 企业管理
     * @return 结果
     */
    @Override
    public int updateImsEnterprise(ImsEnterprise imsEnterprise)
    {
        imsEnterprise.setUpdateTime(DateUtils.getNowDate());
        return imsEnterpriseMapper.updateImsEnterprise(imsEnterprise);
    }

    /**
     * 批量删除企业管理
     * 
     * @param ids 需要删除的企业管理主键
     * @return 结果
     */
    @Override
    public int deleteImsEnterpriseByIds(Long[] ids)
    {
        return imsEnterpriseMapper.deleteImsEnterpriseByIds(ids);
    }

    /**
     * 删除企业管理信息
     * 
     * @param id 企业管理主键
     * @return 结果
     */
    @Override
    public int deleteImsEnterpriseById(Long id)
    {
        return imsEnterpriseMapper.deleteImsEnterpriseById(id);
    }
}
