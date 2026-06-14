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
import com.ruoyi.ims.domain.ImsDelivery;
import com.ruoyi.ims.service.IImsDeliveryService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 送货单Controller
 * 
 * @author tzq
 * @date 2026-06-02
 */
@RestController
@RequestMapping("/ims/ims_delivery")
public class ImsDeliveryController extends BaseController
{
    @Autowired
    private IImsDeliveryService imsDeliveryService;

    /**
     * 查询送货单列表
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_delivery:list')")
    @GetMapping("/list")
    public TableDataInfo list(ImsDelivery imsDelivery)
    {
        startPage();
        List<ImsDelivery> list = imsDeliveryService.selectImsDeliveryList(imsDelivery);
        return getDataTable(list);
    }

    /**
     * 导出送货单列表
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_delivery:export')")
    @Log(title = "送货单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ImsDelivery imsDelivery)
    {
        List<ImsDelivery> list = imsDeliveryService.selectImsDeliveryList(imsDelivery);
        ExcelUtil<ImsDelivery> util = new ExcelUtil<ImsDelivery>(ImsDelivery.class);
        util.exportExcel(response, list, "送货单数据");
    }

    /**
     * 获取送货单详细信息
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_delivery:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(imsDeliveryService.selectImsDeliveryById(id));
    }

    /**
     * 新增送货单
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_delivery:add')")
    @Log(title = "送货单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ImsDelivery imsDelivery)
    {
        return toAjax(imsDeliveryService.insertImsDelivery(imsDelivery));
    }

    /**
     * 修改送货单
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_delivery:edit')")
    @Log(title = "送货单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ImsDelivery imsDelivery)
    {
        return toAjax(imsDeliveryService.updateImsDelivery(imsDelivery));
    }

    /**
     * 删除送货单
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_delivery:remove')")
    @Log(title = "送货单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(imsDeliveryService.deleteImsDeliveryByIds(ids));
    }
}
