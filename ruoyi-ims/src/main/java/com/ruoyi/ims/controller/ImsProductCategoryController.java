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
import com.ruoyi.ims.domain.ImsProductCategory;
import com.ruoyi.ims.service.IImsProductCategoryService;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 产品分类Controller
 * 
 * @author tzq
 * @date 2026-05-29
 */
@RestController
@RequestMapping("/ims/ims_product_category")
public class ImsProductCategoryController extends BaseController
{
    @Autowired
    private IImsProductCategoryService imsProductCategoryService;

    /**
     * 查询产品分类列表
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_product_category:list')")
    @GetMapping("/list")
    public AjaxResult list(ImsProductCategory imsProductCategory)
    {
        List<ImsProductCategory> list = imsProductCategoryService.selectImsProductCategoryList(imsProductCategory);
        return success(list);
    }

    /**
     * 导出产品分类列表
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_product_category:export')")
    @Log(title = "产品分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ImsProductCategory imsProductCategory)
    {
        List<ImsProductCategory> list = imsProductCategoryService.selectImsProductCategoryList(imsProductCategory);
        ExcelUtil<ImsProductCategory> util = new ExcelUtil<ImsProductCategory>(ImsProductCategory.class);
        util.exportExcel(response, list, "产品分类数据");
    }

    /**
     * 获取产品分类详细信息
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_product_category:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(imsProductCategoryService.selectImsProductCategoryById(id));
    }

    /**
     * 新增产品分类
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_product_category:add')")
    @Log(title = "产品分类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ImsProductCategory imsProductCategory)
    {
        return toAjax(imsProductCategoryService.insertImsProductCategory(imsProductCategory));
    }

    /**
     * 修改产品分类
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_product_category:edit')")
    @Log(title = "产品分类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ImsProductCategory imsProductCategory)
    {
        return toAjax(imsProductCategoryService.updateImsProductCategory(imsProductCategory));
    }

    /**
     * 删除产品分类
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_product_category:remove')")
    @Log(title = "产品分类", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(imsProductCategoryService.deleteImsProductCategoryByIds(ids));
    }
}
