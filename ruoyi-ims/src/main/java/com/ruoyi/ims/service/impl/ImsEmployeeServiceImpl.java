package com.ruoyi.ims.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.ims.mapper.ImsEmployeeMapper;
import com.ruoyi.ims.domain.ImsEmployee;
import com.ruoyi.ims.service.IImsEmployeeService;

/**
 * 员工管理Service业务层处理
 * 
 * @author tzq
 * @date 2026-05-29
 */
@Service
public class ImsEmployeeServiceImpl implements IImsEmployeeService 
{
    @Autowired
    private ImsEmployeeMapper imsEmployeeMapper;

    /**
     * 查询员工管理
     * 
     * @param id 员工管理主键
     * @return 员工管理
     */
    @Override
    public ImsEmployee selectImsEmployeeById(Long id)
    {
        return imsEmployeeMapper.selectImsEmployeeById(id);
    }

    /**
     * 查询员工管理列表
     * 
     * @param imsEmployee 员工管理
     * @return 员工管理
     */
    @Override
    public List<ImsEmployee> selectImsEmployeeList(ImsEmployee imsEmployee)
    {
        return imsEmployeeMapper.selectImsEmployeeList(imsEmployee);
    }

    /**
     * 新增员工管理
     * 
     * @param imsEmployee 员工管理
     * @return 结果
     */
    @Override
    public int insertImsEmployee(ImsEmployee imsEmployee)
    {
        imsEmployee.setCreateTime(DateUtils.getNowDate());
        return imsEmployeeMapper.insertImsEmployee(imsEmployee);
    }

    /**
     * 修改员工管理
     * 
     * @param imsEmployee 员工管理
     * @return 结果
     */
    @Override
    public int updateImsEmployee(ImsEmployee imsEmployee)
    {
        imsEmployee.setUpdateTime(DateUtils.getNowDate());
        return imsEmployeeMapper.updateImsEmployee(imsEmployee);
    }

    /**
     * 批量删除员工管理
     * 
     * @param ids 需要删除的员工管理主键
     * @return 结果
     */
    @Override
    public int deleteImsEmployeeByIds(Long[] ids)
    {
        return imsEmployeeMapper.deleteImsEmployeeByIds(ids);
    }

    /**
     * 删除员工管理信息
     * 
     * @param id 员工管理主键
     * @return 结果
     */
    @Override
    public int deleteImsEmployeeById(Long id)
    {
        return imsEmployeeMapper.deleteImsEmployeeById(id);
    }
}
