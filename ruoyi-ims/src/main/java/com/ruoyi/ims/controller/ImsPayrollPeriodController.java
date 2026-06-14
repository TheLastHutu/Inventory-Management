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
import com.ruoyi.ims.domain.ImsPayrollPeriod;
import com.ruoyi.ims.service.IImsPayrollPeriodService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 工资管理Controller
 * 
 * @author tzq
 * @date 2026-06-04
 */
@RestController
@RequestMapping("/ims/ims_payroll_period")
public class ImsPayrollPeriodController extends BaseController
{
    @Autowired
    private IImsPayrollPeriodService imsPayrollPeriodService;

    /**
     * 查询工资管理列表
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_payroll_period:list')")
    @GetMapping("/list")
    public TableDataInfo list(ImsPayrollPeriod imsPayrollPeriod)
    {
        startPage();
        List<ImsPayrollPeriod> list = imsPayrollPeriodService.selectImsPayrollPeriodList(imsPayrollPeriod);
        return getDataTable(list);
    }

    /**
     * 导出工资管理列表
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_payroll_period:export')")
    @Log(title = "工资管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ImsPayrollPeriod imsPayrollPeriod)
    {
        List<ImsPayrollPeriod> list = imsPayrollPeriodService.selectImsPayrollPeriodList(imsPayrollPeriod);
        ExcelUtil<ImsPayrollPeriod> util = new ExcelUtil<ImsPayrollPeriod>(ImsPayrollPeriod.class);
        util.exportExcel(response, list, "工资管理数据");
    }

    /**
     * 获取工资管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_payroll_period:query')")
    @GetMapping(value = "/{periodId}")
    public AjaxResult getInfo(@PathVariable("periodId") Long periodId)
    {
        return success(imsPayrollPeriodService.selectImsPayrollPeriodByPeriodId(periodId));
    }

    /**
     * 新增工资管理
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_payroll_period:add')")
    @Log(title = "工资管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ImsPayrollPeriod imsPayrollPeriod)
    {
        return toAjax(imsPayrollPeriodService.insertImsPayrollPeriod(imsPayrollPeriod));
    }

    /**
     * 修改工资管理
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_payroll_period:edit')")
    @Log(title = "工资管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ImsPayrollPeriod imsPayrollPeriod)
    {
        return toAjax(imsPayrollPeriodService.updateImsPayrollPeriod(imsPayrollPeriod));
    }

    /**
     * 删除工资管理
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_payroll_period:remove')")
    @Log(title = "工资管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{periodIds}")
    public AjaxResult remove(@PathVariable Long[] periodIds)
    {
        return toAjax(imsPayrollPeriodService.deleteImsPayrollPeriodByPeriodIds(periodIds));
    }
}
