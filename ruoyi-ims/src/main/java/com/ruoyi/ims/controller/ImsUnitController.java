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
import com.ruoyi.ims.domain.ImsUnit;
import com.ruoyi.ims.service.IImsUnitService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 计量单位管理Controller
 * 
 * @author tzq
 * @date 2026-05-29
 */
@RestController
@RequestMapping("/ims/ims_unit")
public class ImsUnitController extends BaseController
{
    @Autowired
    private IImsUnitService imsUnitService;

    /**
     * 查询计量单位管理列表
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_unit:list')")
    @GetMapping("/list")
    public TableDataInfo list(ImsUnit imsUnit)
    {
        startPage();
        List<ImsUnit> list = imsUnitService.selectImsUnitList(imsUnit);
        return getDataTable(list);
    }

    /**
     * 导出计量单位管理列表
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_unit:export')")
    @Log(title = "计量单位管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ImsUnit imsUnit)
    {
        List<ImsUnit> list = imsUnitService.selectImsUnitList(imsUnit);
        ExcelUtil<ImsUnit> util = new ExcelUtil<ImsUnit>(ImsUnit.class);
        util.exportExcel(response, list, "计量单位管理数据");
    }

    /**
     * 获取计量单位管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_unit:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(imsUnitService.selectImsUnitById(id));
    }

    /**
     * 新增计量单位管理
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_unit:add')")
    @Log(title = "计量单位管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ImsUnit imsUnit)
    {
        return toAjax(imsUnitService.insertImsUnit(imsUnit));
    }

    /**
     * 修改计量单位管理
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_unit:edit')")
    @Log(title = "计量单位管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ImsUnit imsUnit)
    {
        return toAjax(imsUnitService.updateImsUnit(imsUnit));
    }

    /**
     * 删除计量单位管理
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_unit:remove')")
    @Log(title = "计量单位管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(imsUnitService.deleteImsUnitByIds(ids));
    }
}
