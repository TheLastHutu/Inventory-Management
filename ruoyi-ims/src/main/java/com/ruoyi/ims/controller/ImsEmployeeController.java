package com.ruoyi.ims.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.ims.domain.ImsEmployee;
import com.ruoyi.ims.service.IImsEmployeeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 员工管理Controller
 * 
 * @author tzq
 * @date 2026-05-29
 */
@RestController
@RequestMapping("/ims/ims_employee")
public class ImsEmployeeController extends BaseController
{
    @Autowired
    private IImsEmployeeService imsEmployeeService;

    /**
     * 查询员工管理列表
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_employee:list')")
    @GetMapping("/list")
    public TableDataInfo list(ImsEmployee imsEmployee)
    {
        startPage();
        List<ImsEmployee> list = imsEmployeeService.selectImsEmployeeList(imsEmployee);
        return getDataTable(list);
    }

    /**
     * 导出员工管理列表
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_employee:export')")
    @Log(title = "员工管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ImsEmployee imsEmployee)
    {
        List<ImsEmployee> list = imsEmployeeService.selectImsEmployeeList(imsEmployee);
        ExcelUtil<ImsEmployee> util = new ExcelUtil<ImsEmployee>(ImsEmployee.class);
        util.exportExcel(response, list, "员工管理数据");
    }

    /**
     * 获取员工管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_employee:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(imsEmployeeService.selectImsEmployeeById(id));
    }

    /**
     * 新增员工管理
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_employee:add')")
    @Log(title = "员工管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ImsEmployee imsEmployee)
    {
        return toAjax(imsEmployeeService.insertImsEmployee(imsEmployee));
    }

    /**
     * 修改员工管理
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_employee:edit')")
    @Log(title = "员工管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ImsEmployee imsEmployee)
    {
        return toAjax(imsEmployeeService.updateImsEmployee(imsEmployee));
    }

    /**
     * 删除员工管理
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_employee:remove')")
    @Log(title = "员工管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(imsEmployeeService.deleteImsEmployeeByIds(ids));
    }
}
