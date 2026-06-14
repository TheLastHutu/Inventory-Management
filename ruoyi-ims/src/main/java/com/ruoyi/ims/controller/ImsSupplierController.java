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
import com.ruoyi.ims.domain.ImsSupplier;
import com.ruoyi.ims.service.IImsSupplierService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 供应商管理Controller
 * 
 * @author tzq
 * @date 2026-05-29
 */
@RestController
@RequestMapping("/ims/ims_supplier")
public class ImsSupplierController extends BaseController
{
    @Autowired
    private IImsSupplierService imsSupplierService;

    /**
     * 查询供应商管理列表
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_supplier:list')")
    @GetMapping("/list")
    public TableDataInfo list(ImsSupplier imsSupplier)
    {
        startPage();
        List<ImsSupplier> list = imsSupplierService.selectImsSupplierList(imsSupplier);
        return getDataTable(list);
    }

    /**
     * 导出供应商管理列表
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_supplier:export')")
    @Log(title = "供应商管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ImsSupplier imsSupplier)
    {
        List<ImsSupplier> list = imsSupplierService.selectImsSupplierList(imsSupplier);
        ExcelUtil<ImsSupplier> util = new ExcelUtil<ImsSupplier>(ImsSupplier.class);
        util.exportExcel(response, list, "供应商管理数据");
    }

    /**
     * 获取供应商管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_supplier:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(imsSupplierService.selectImsSupplierById(id));
    }

    /**
     * 新增供应商管理
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_supplier:add')")
    @Log(title = "供应商管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ImsSupplier imsSupplier)
    {
        return toAjax(imsSupplierService.insertImsSupplier(imsSupplier));
    }

    /**
     * 修改供应商管理
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_supplier:edit')")
    @Log(title = "供应商管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ImsSupplier imsSupplier)
    {
        return toAjax(imsSupplierService.updateImsSupplier(imsSupplier));
    }

    /**
     * 删除供应商管理
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_supplier:remove')")
    @Log(title = "供应商管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(imsSupplierService.deleteImsSupplierByIds(ids));
    }
}
