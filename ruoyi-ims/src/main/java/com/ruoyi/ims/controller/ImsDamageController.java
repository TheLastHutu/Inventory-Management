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
import com.ruoyi.ims.domain.ImsDamage;
import com.ruoyi.ims.service.IImsDamageService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 报损单Controller
 * 
 * @author tzq
 * @date 2026-05-29
 */
@RestController
@RequestMapping("/ims/ims_damage")
public class ImsDamageController extends BaseController
{
    @Autowired
    private IImsDamageService imsDamageService;

    /**
     * 查询报损单列表
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_damage:list')")
    @GetMapping("/list")
    public TableDataInfo list(ImsDamage imsDamage)
    {
        startPage();
        List<ImsDamage> list = imsDamageService.selectImsDamageList(imsDamage);
        return getDataTable(list);
    }

    /**
     * 导出报损单列表
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_damage:export')")
    @Log(title = "报损单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ImsDamage imsDamage)
    {
        List<ImsDamage> list = imsDamageService.selectImsDamageList(imsDamage);
        ExcelUtil<ImsDamage> util = new ExcelUtil<ImsDamage>(ImsDamage.class);
        util.exportExcel(response, list, "报损单数据");
    }

    /**
     * 获取报损单详细信息
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_damage:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(imsDamageService.selectImsDamageById(id));
    }

    /**
     * 新增报损单
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_damage:add')")
    @Log(title = "报损单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ImsDamage imsDamage)
    {
        return toAjax(imsDamageService.insertImsDamage(imsDamage));
    }

    /**
     * 修改报损单
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_damage:edit')")
    @Log(title = "报损单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ImsDamage imsDamage)
    {
        return toAjax(imsDamageService.updateImsDamage(imsDamage));
    }

    /**
     * 删除报损单
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_damage:remove')")
    @Log(title = "报损单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(imsDamageService.deleteImsDamageByIds(ids));
    }
}
