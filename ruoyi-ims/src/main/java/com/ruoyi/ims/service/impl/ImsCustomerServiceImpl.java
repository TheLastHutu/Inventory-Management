package com.ruoyi.ims.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.ims.mapper.ImsCustomerMapper;
import com.ruoyi.ims.domain.ImsCustomer;
import com.ruoyi.ims.service.IImsCustomerService;

/**
 * 客户管理Service业务层处理
 * 
 * @author tzq
 * @date 2026-05-29
 */
@Service
public class ImsCustomerServiceImpl implements IImsCustomerService 
{
    @Autowired
    private ImsCustomerMapper imsCustomerMapper;

    /**
     * 查询客户管理
     * 
     * @param id 客户管理主键
     * @return 客户管理
     */
    @Override
    public ImsCustomer selectImsCustomerById(Long id)
    {
        return imsCustomerMapper.selectImsCustomerById(id);
    }

    /**
     * 查询客户管理列表
     * 
     * @param imsCustomer 客户管理
     * @return 客户管理
     */
    @Override
    public List<ImsCustomer> selectImsCustomerList(ImsCustomer imsCustomer)
    {
        return imsCustomerMapper.selectImsCustomerList(imsCustomer);
    }

    /**
     * 新增客户管理
     * 
     * @param imsCustomer 客户管理
     * @return 结果
     */
    @Override
    public int insertImsCustomer(ImsCustomer imsCustomer)
    {
        imsCustomer.setCreateTime(DateUtils.getNowDate());
        return imsCustomerMapper.insertImsCustomer(imsCustomer);
    }

    /**
     * 修改客户管理
     * 
     * @param imsCustomer 客户管理
     * @return 结果
     */
    @Override
    public int updateImsCustomer(ImsCustomer imsCustomer)
    {
        imsCustomer.setUpdateTime(DateUtils.getNowDate());
        return imsCustomerMapper.updateImsCustomer(imsCustomer);
    }

    /**
     * 批量删除客户管理
     * 
     * @param ids 需要删除的客户管理主键
     * @return 结果
     */
    @Override
    public int deleteImsCustomerByIds(Long[] ids)
    {
        return imsCustomerMapper.deleteImsCustomerByIds(ids);
    }

    /**
     * 删除客户管理信息
     * 
     * @param id 客户管理主键
     * @return 结果
     */
    @Override
    public int deleteImsCustomerById(Long id)
    {
        return imsCustomerMapper.deleteImsCustomerById(id);
    }
}
