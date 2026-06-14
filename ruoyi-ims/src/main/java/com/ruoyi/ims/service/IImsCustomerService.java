package com.ruoyi.ims.service;

import java.util.List;
import com.ruoyi.ims.domain.ImsCustomer;

/**
 * 客户管理Service接口
 * 
 * @author tzq
 * @date 2026-05-29
 */
public interface IImsCustomerService 
{
    /**
     * 查询客户管理
     * 
     * @param id 客户管理主键
     * @return 客户管理
     */
    public ImsCustomer selectImsCustomerById(Long id);

    /**
     * 查询客户管理列表
     * 
     * @param imsCustomer 客户管理
     * @return 客户管理集合
     */
    public List<ImsCustomer> selectImsCustomerList(ImsCustomer imsCustomer);

    /**
     * 新增客户管理
     * 
     * @param imsCustomer 客户管理
     * @return 结果
     */
    public int insertImsCustomer(ImsCustomer imsCustomer);

    /**
     * 修改客户管理
     * 
     * @param imsCustomer 客户管理
     * @return 结果
     */
    public int updateImsCustomer(ImsCustomer imsCustomer);

    /**
     * 批量删除客户管理
     * 
     * @param ids 需要删除的客户管理主键集合
     * @return 结果
     */
    public int deleteImsCustomerByIds(Long[] ids);

    /**
     * 删除客户管理信息
     * 
     * @param id 客户管理主键
     * @return 结果
     */
    public int deleteImsCustomerById(Long id);
}
