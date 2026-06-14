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
import com.ruoyi.ims.domain.ImsWarehouseLocation;
import com.ruoyi.ims.service.IImsWarehouseLocationService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 货位管理Controller
 * 
 * @author tzq
 * @date 2026-05-29
 */
@RestController
@RequestMapping("/ims/ims_warehouse_location")
public class ImsWarehouseLocationController extends BaseController
{
    @Autowired
    private IImsWarehouseLocationService imsWarehouseLocationService;

    /**
     * 查询货位管理列表
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_warehouse_location:list')")
    @GetMapping("/list")
    public TableDataInfo list(ImsWarehouseLocation imsWarehouseLocation)
    {
        startPage();
        List<ImsWarehouseLocation> list = imsWarehouseLocationService.selectImsWarehouseLocationList(imsWarehouseLocation);
        return getDataTable(list);
    }

    /**
     * 导出货位管理列表
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_warehouse_location:export')")
    @Log(title = "货位管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ImsWarehouseLocation imsWarehouseLocation)
    {
        List<ImsWarehouseLocation> list = imsWarehouseLocationService.selectImsWarehouseLocationList(imsWarehouseLocation);
        ExcelUtil<ImsWarehouseLocation> util = new ExcelUtil<ImsWarehouseLocation>(ImsWarehouseLocation.class);
        util.exportExcel(response, list, "货位管理数据");
    }

    /**
     * 获取货位管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_warehouse_location:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(imsWarehouseLocationService.selectImsWarehouseLocationById(id));
    }

    /**
     * 新增货位管理
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_warehouse_location:add')")
    @Log(title = "货位管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ImsWarehouseLocation imsWarehouseLocation)
    {
        return toAjax(imsWarehouseLocationService.insertImsWarehouseLocation(imsWarehouseLocation));
    }

    /**
     * 修改货位管理
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_warehouse_location:edit')")
    @Log(title = "货位管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ImsWarehouseLocation imsWarehouseLocation)
    {
        return toAjax(imsWarehouseLocationService.updateImsWarehouseLocation(imsWarehouseLocation));
    }

    /**
     * 删除货位管理
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_warehouse_location:remove')")
    @Log(title = "货位管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(imsWarehouseLocationService.deleteImsWarehouseLocationByIds(ids));
    }
}
