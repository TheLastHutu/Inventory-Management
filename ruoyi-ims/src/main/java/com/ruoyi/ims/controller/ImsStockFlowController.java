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
import com.ruoyi.ims.domain.ImsStockFlow;
import com.ruoyi.ims.service.IImsStockFlowService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 库存流水Controller
 * 
 * @author tzq
 * @date 2026-05-29
 */
@RestController
@RequestMapping("/ims/ims_stock_flow")
public class ImsStockFlowController extends BaseController
{
    @Autowired
    private IImsStockFlowService imsStockFlowService;

    /**
     * 查询库存流水列表
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_stock_flow:list')")
    @GetMapping("/list")
    public TableDataInfo list(ImsStockFlow imsStockFlow)
    {
        startPage();
        List<ImsStockFlow> list = imsStockFlowService.selectImsStockFlowList(imsStockFlow);
        return getDataTable(list);
    }

    /**
     * 导出库存流水列表
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_stock_flow:export')")
    @Log(title = "库存流水", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ImsStockFlow imsStockFlow)
    {
        List<ImsStockFlow> list = imsStockFlowService.selectImsStockFlowList(imsStockFlow);
        ExcelUtil<ImsStockFlow> util = new ExcelUtil<ImsStockFlow>(ImsStockFlow.class);
        util.exportExcel(response, list, "库存流水数据");
    }

    /**
     * 获取库存流水详细信息
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_stock_flow:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(imsStockFlowService.selectImsStockFlowById(id));
    }

    /**
     * 新增库存流水
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_stock_flow:add')")
    @Log(title = "库存流水", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ImsStockFlow imsStockFlow)
    {
        return toAjax(imsStockFlowService.insertImsStockFlow(imsStockFlow));
    }

    /**
     * 修改库存流水
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_stock_flow:edit')")
    @Log(title = "库存流水", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ImsStockFlow imsStockFlow)
    {
        return toAjax(imsStockFlowService.updateImsStockFlow(imsStockFlow));
    }

    /**
     * 删除库存流水
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_stock_flow:remove')")
    @Log(title = "库存流水", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(imsStockFlowService.deleteImsStockFlowByIds(ids));
    }
}
