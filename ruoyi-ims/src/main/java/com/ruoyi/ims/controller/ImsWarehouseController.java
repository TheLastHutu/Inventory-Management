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
import com.ruoyi.ims.domain.ImsWarehouse;
import com.ruoyi.ims.service.IImsWarehouseService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 仓库管理Controller
 * 
 * @author tzq
 * @date 2026-05-29
 */
@RestController
@RequestMapping("/ims/ims_warehouse")
public class ImsWarehouseController extends BaseController
{
    @Autowired
    private IImsWarehouseService imsWarehouseService;

    /**
     * 查询仓库管理列表
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_warehouse:list')")
    @GetMapping("/list")
    public TableDataInfo list(ImsWarehouse imsWarehouse)
    {
        startPage();
        List<ImsWarehouse> list = imsWarehouseService.selectImsWarehouseList(imsWarehouse);
        return getDataTable(list);
    }

    /**
     * 导出仓库管理列表
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_warehouse:export')")
    @Log(title = "仓库管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ImsWarehouse imsWarehouse)
    {
        List<ImsWarehouse> list = imsWarehouseService.selectImsWarehouseList(imsWarehouse);
        ExcelUtil<ImsWarehouse> util = new ExcelUtil<ImsWarehouse>(ImsWarehouse.class);
        util.exportExcel(response, list, "仓库管理数据");
    }

    /**
     * 获取仓库管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_warehouse:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(imsWarehouseService.selectImsWarehouseById(id));
    }

    /**
     * 新增仓库管理
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_warehouse:add')")
    @Log(title = "仓库管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ImsWarehouse imsWarehouse)
    {
        return toAjax(imsWarehouseService.insertImsWarehouse(imsWarehouse));
    }

    /**
     * 修改仓库管理
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_warehouse:edit')")
    @Log(title = "仓库管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ImsWarehouse imsWarehouse)
    {
        return toAjax(imsWarehouseService.updateImsWarehouse(imsWarehouse));
    }

    /**
     * 删除仓库管理
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_warehouse:remove')")
    @Log(title = "仓库管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(imsWarehouseService.deleteImsWarehouseByIds(ids));
    }
}
