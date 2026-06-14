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
import com.ruoyi.ims.domain.ImsFixedAsset;
import com.ruoyi.ims.service.IImsFixedAssetService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 固定资产Controller
 * 
 * @author tzq
 * @date 2026-05-29
 */
@RestController
@RequestMapping("/ims/ims_fixed_asset")
public class ImsFixedAssetController extends BaseController
{
    @Autowired
    private IImsFixedAssetService imsFixedAssetService;

    /**
     * 查询固定资产列表
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_fixed_asset:list')")
    @GetMapping("/list")
    public TableDataInfo list(ImsFixedAsset imsFixedAsset)
    {
        startPage();
        List<ImsFixedAsset> list = imsFixedAssetService.selectImsFixedAssetList(imsFixedAsset);
        return getDataTable(list);
    }

    /**
     * 导出固定资产列表
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_fixed_asset:export')")
    @Log(title = "固定资产", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ImsFixedAsset imsFixedAsset)
    {
        List<ImsFixedAsset> list = imsFixedAssetService.selectImsFixedAssetList(imsFixedAsset);
        ExcelUtil<ImsFixedAsset> util = new ExcelUtil<ImsFixedAsset>(ImsFixedAsset.class);
        util.exportExcel(response, list, "固定资产数据");
    }

    /**
     * 获取固定资产详细信息
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_fixed_asset:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(imsFixedAssetService.selectImsFixedAssetById(id));
    }

    /**
     * 新增固定资产
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_fixed_asset:add')")
    @Log(title = "固定资产", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ImsFixedAsset imsFixedAsset)
    {
        return toAjax(imsFixedAssetService.insertImsFixedAsset(imsFixedAsset));
    }

    /**
     * 修改固定资产
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_fixed_asset:edit')")
    @Log(title = "固定资产", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ImsFixedAsset imsFixedAsset)
    {
        return toAjax(imsFixedAssetService.updateImsFixedAsset(imsFixedAsset));
    }

    /**
     * 删除固定资产
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_fixed_asset:remove')")
    @Log(title = "固定资产", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(imsFixedAssetService.deleteImsFixedAssetByIds(ids));
    }
}
