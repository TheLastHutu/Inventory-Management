package com.ruoyi.ims.service;

import java.util.List;
import com.ruoyi.ims.domain.ImsEnterprise;

/**
 * 企业管理Service接口
 * 
 * @author tzq
 * @date 2026-05-29
 */
public interface IImsEnterpriseService 
{
    /**
     * 查询企业管理
     * 
     * @param id 企业管理主键
     * @return 企业管理
     */
    public ImsEnterprise selectImsEnterpriseById(Long id);

    /**
     * 查询企业管理列表
     * 
     * @param imsEnterprise 企业管理
     * @return 企业管理集合
     */
    public List<ImsEnterprise> selectImsEnterpriseList(ImsEnterprise imsEnterprise);

    /**
     * 新增企业管理
     * 
     * @param imsEnterprise 企业管理
     * @return 结果
     */
    public int insertImsEnterprise(ImsEnterprise imsEnterprise);

    /**
     * 修改企业管理
     * 
     * @param imsEnterprise 企业管理
     * @return 结果
     */
    public int updateImsEnterprise(ImsEnterprise imsEnterprise);

    /**
     * 批量删除企业管理
     * 
     * @param ids 需要删除的企业管理主键集合
     * @return 结果
     */
    public int deleteImsEnterpriseByIds(Long[] ids);

    /**
     * 删除企业管理信息
     * 
     * @param id 企业管理主键
     * @return 结果
     */
    public int deleteImsEnterpriseById(Long id);
}
