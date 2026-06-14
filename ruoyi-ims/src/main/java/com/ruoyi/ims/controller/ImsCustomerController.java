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
import com.ruoyi.ims.domain.ImsCustomer;
import com.ruoyi.ims.service.IImsCustomerService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 客户管理Controller
 * 
 * @author tzq
 * @date 2026-05-29
 */
@RestController
@RequestMapping("/ims/ims_customer")
public class ImsCustomerController extends BaseController
{
    @Autowired
    private IImsCustomerService imsCustomerService;

    /**
     * 查询客户管理列表
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_customer:list')")
    @GetMapping("/list")
    public TableDataInfo list(ImsCustomer imsCustomer)
    {
        startPage();
        List<ImsCustomer> list = imsCustomerService.selectImsCustomerList(imsCustomer);
        return getDataTable(list);
    }

    /**
     * 导出客户管理列表
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_customer:export')")
    @Log(title = "客户管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ImsCustomer imsCustomer)
    {
        List<ImsCustomer> list = imsCustomerService.selectImsCustomerList(imsCustomer);
        ExcelUtil<ImsCustomer> util = new ExcelUtil<ImsCustomer>(ImsCustomer.class);
        util.exportExcel(response, list, "客户管理数据");
    }

    /**
     * 获取客户管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_customer:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(imsCustomerService.selectImsCustomerById(id));
    }

    /**
     * 新增客户管理
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_customer:add')")
    @Log(title = "客户管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ImsCustomer imsCustomer)
    {
        return toAjax(imsCustomerService.insertImsCustomer(imsCustomer));
    }

    /**
     * 修改客户管理
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_customer:edit')")
    @Log(title = "客户管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ImsCustomer imsCustomer)
    {
        return toAjax(imsCustomerService.updateImsCustomer(imsCustomer));
    }

    /**
     * 删除客户管理
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_customer:remove')")
    @Log(title = "客户管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(imsCustomerService.deleteImsCustomerByIds(ids));
    }
}
