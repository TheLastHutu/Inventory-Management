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
import com.ruoyi.ims.domain.ImsInventory;
import com.ruoyi.ims.service.IImsInventoryService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 库存(现存量)Controller
 * 
 * @author tzq
 * @date 2026-05-29
 */
@RestController
@RequestMapping("/ims/ims_inventory")
public class ImsInventoryController extends BaseController
{
    @Autowired
    private IImsInventoryService imsInventoryService;

    /**
     * 查询库存(现存量)列表
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_inventory:list')")
    @GetMapping("/list")
    public TableDataInfo list(ImsInventory imsInventory)
    {
        startPage();
        List<ImsInventory> list = imsInventoryService.selectImsInventoryList(imsInventory);
        return getDataTable(list);
    }

    /**
     * 导出库存(现存量)列表
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_inventory:export')")
    @Log(title = "库存(现存量)", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ImsInventory imsInventory)
    {
        List<ImsInventory> list = imsInventoryService.selectImsInventoryList(imsInventory);
        ExcelUtil<ImsInventory> util = new ExcelUtil<ImsInventory>(ImsInventory.class);
        util.exportExcel(response, list, "库存(现存量)数据");
    }

    /**
     * 获取库存(现存量)详细信息
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_inventory:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(imsInventoryService.selectImsInventoryById(id));
    }

    /**
     * 新增库存(现存量)
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_inventory:add')")
    @Log(title = "库存(现存量)", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ImsInventory imsInventory)
    {
        return toAjax(imsInventoryService.insertImsInventory(imsInventory));
    }

    /**
     * 修改库存(现存量)
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_inventory:edit')")
    @Log(title = "库存(现存量)", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ImsInventory imsInventory)
    {
        return toAjax(imsInventoryService.updateImsInventory(imsInventory));
    }

    /**
     * 删除库存(现存量)
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_inventory:remove')")
    @Log(title = "库存(现存量)", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(imsInventoryService.deleteImsInventoryByIds(ids));
    }
}
