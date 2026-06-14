package com.ruoyi.ims.service;

import java.util.List;
import com.ruoyi.ims.domain.ImsEmployee;

/**
 * 员工管理Service接口
 * 
 * @author tzq
 * @date 2026-05-29
 */
public interface IImsEmployeeService 
{
    /**
     * 查询员工管理
     * 
     * @param id 员工管理主键
     * @return 员工管理
     */
    public ImsEmployee selectImsEmployeeById(Long id);

    /**
     * 查询员工管理列表
     * 
     * @param imsEmployee 员工管理
     * @return 员工管理集合
     */
    public List<ImsEmployee> selectImsEmployeeList(ImsEmployee imsEmployee);

    /**
     * 新增员工管理
     * 
     * @param imsEmployee 员工管理
     * @return 结果
     */
    public int insertImsEmployee(ImsEmployee imsEmployee);

    /**
     * 修改员工管理
     * 
     * @param imsEmployee 员工管理
     * @return 结果
     */
    public int updateImsEmployee(ImsEmployee imsEmployee);

    /**
     * 批量删除员工管理
     * 
     * @param ids 需要删除的员工管理主键集合
     * @return 结果
     */
    public int deleteImsEmployeeByIds(Long[] ids);

    /**
     * 删除员工管理信息
     * 
     * @param id 员工管理主键
     * @return 结果
     */
    public int deleteImsEmployeeById(Long id);
}
