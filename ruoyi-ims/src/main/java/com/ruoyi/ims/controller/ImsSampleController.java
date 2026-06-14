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
import com.ruoyi.ims.domain.ImsSample;
import com.ruoyi.ims.service.IImsSampleService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 样品单Controller
 * 
 * @author tzq
 * @date 2026-05-29
 */
@RestController
@RequestMapping("/ims/ims_sample")
public class ImsSampleController extends BaseController
{
    @Autowired
    private IImsSampleService imsSampleService;

    /**
     * 查询样品单列表
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_sample:list')")
    @GetMapping("/list")
    public TableDataInfo list(ImsSample imsSample)
    {
        startPage();
        List<ImsSample> list = imsSampleService.selectImsSampleList(imsSample);
        return getDataTable(list);
    }

    /**
     * 导出样品单列表
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_sample:export')")
    @Log(title = "样品单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ImsSample imsSample)
    {
        List<ImsSample> list = imsSampleService.selectImsSampleList(imsSample);
        ExcelUtil<ImsSample> util = new ExcelUtil<ImsSample>(ImsSample.class);
        util.exportExcel(response, list, "样品单数据");
    }

    /**
     * 获取样品单详细信息
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_sample:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(imsSampleService.selectImsSampleById(id));
    }

    /**
     * 新增样品单
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_sample:add')")
    @Log(title = "样品单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ImsSample imsSample)
    {
        return toAjax(imsSampleService.insertImsSample(imsSample));
    }

    /**
     * 修改样品单
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_sample:edit')")
    @Log(title = "样品单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ImsSample imsSample)
    {
        return toAjax(imsSampleService.updateImsSample(imsSample));
    }

    /**
     * 删除样品单
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_sample:remove')")
    @Log(title = "样品单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(imsSampleService.deleteImsSampleByIds(ids));
    }
}
