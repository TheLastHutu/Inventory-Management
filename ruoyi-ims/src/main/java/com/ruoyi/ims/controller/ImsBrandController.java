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
import com.ruoyi.ims.domain.ImsBrand;
import com.ruoyi.ims.service.IImsBrandService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 品牌管理Controller
 * 
 * @author tzq
 * @date 2026-05-29
 */
@RestController
@RequestMapping("/ims/ims_brand")
public class ImsBrandController extends BaseController
{
    @Autowired
    private IImsBrandService imsBrandService;

    /**
     * 查询品牌管理列表
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_brand:list')")
    @GetMapping("/list")
    public TableDataInfo list(ImsBrand imsBrand)
    {
        startPage();
        List<ImsBrand> list = imsBrandService.selectImsBrandList(imsBrand);
        return getDataTable(list);
    }

    /**
     * 导出品牌管理列表
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_brand:export')")
    @Log(title = "品牌管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ImsBrand imsBrand)
    {
        List<ImsBrand> list = imsBrandService.selectImsBrandList(imsBrand);
        ExcelUtil<ImsBrand> util = new ExcelUtil<ImsBrand>(ImsBrand.class);
        util.exportExcel(response, list, "品牌管理数据");
    }

    /**
     * 获取品牌管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_brand:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(imsBrandService.selectImsBrandById(id));
    }

    /**
     * 新增品牌管理
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_brand:add')")
    @Log(title = "品牌管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ImsBrand imsBrand)
    {
        return toAjax(imsBrandService.insertImsBrand(imsBrand));
    }

    /**
     * 修改品牌管理
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_brand:edit')")
    @Log(title = "品牌管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ImsBrand imsBrand)
    {
        return toAjax(imsBrandService.updateImsBrand(imsBrand));
    }

    /**
     * 删除品牌管理
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_brand:remove')")
    @Log(title = "品牌管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(imsBrandService.deleteImsBrandByIds(ids));
    }
}
