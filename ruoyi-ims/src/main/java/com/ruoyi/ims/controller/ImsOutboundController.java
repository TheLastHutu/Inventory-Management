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
import com.ruoyi.ims.domain.ImsOutbound;
import com.ruoyi.ims.service.IImsOutboundService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 出库单Controller
 * 
 * @author tzq
 * @date 2026-05-29
 */
@RestController
@RequestMapping("/ims/ims_outbound")
public class ImsOutboundController extends BaseController
{
    @Autowired
    private IImsOutboundService imsOutboundService;

    /**
     * 查询出库单列表
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_outbound:list')")
    @GetMapping("/list")
    public TableDataInfo list(ImsOutbound imsOutbound)
    {
        startPage();
        List<ImsOutbound> list = imsOutboundService.selectImsOutboundList(imsOutbound);
        return getDataTable(list);
    }

    /**
     * 导出出库单列表
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_outbound:export')")
    @Log(title = "出库单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ImsOutbound imsOutbound)
    {
        List<ImsOutbound> list = imsOutboundService.selectImsOutboundList(imsOutbound);
        ExcelUtil<ImsOutbound> util = new ExcelUtil<ImsOutbound>(ImsOutbound.class);
        util.exportExcel(response, list, "出库单数据");
    }

    /**
     * 获取出库单详细信息
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_outbound:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(imsOutboundService.selectImsOutboundById(id));
    }

    /**
     * 新增出库单
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_outbound:add')")
    @Log(title = "出库单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ImsOutbound imsOutbound)
    {
        return toAjax(imsOutboundService.insertImsOutbound(imsOutbound));
    }

    /**
     * 修改出库单
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_outbound:edit')")
    @Log(title = "出库单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ImsOutbound imsOutbound)
    {
        return toAjax(imsOutboundService.updateImsOutbound(imsOutbound));
    }

    /**
     * 删除出库单
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_outbound:remove')")
    @Log(title = "出库单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(imsOutboundService.deleteImsOutboundByIds(ids));
    }
}
