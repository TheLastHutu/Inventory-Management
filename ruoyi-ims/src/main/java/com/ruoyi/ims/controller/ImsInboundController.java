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
import com.ruoyi.ims.domain.ImsInbound;
import com.ruoyi.ims.service.IImsInboundService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 入库单Controller
 * 
 * @author tzq
 * @date 2026-05-29
 */
@RestController
@RequestMapping("/ims/ims_inbound")
public class ImsInboundController extends BaseController
{
    @Autowired
    private IImsInboundService imsInboundService;

    /**
     * 查询入库单列表
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_inbound:list')")
    @GetMapping("/list")
    public TableDataInfo list(ImsInbound imsInbound)
    {
        startPage();
        List<ImsInbound> list = imsInboundService.selectImsInboundList(imsInbound);
        return getDataTable(list);
    }

    /**
     * 导出入库单列表
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_inbound:export')")
    @Log(title = "入库单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ImsInbound imsInbound)
    {
        List<ImsInbound> list = imsInboundService.selectImsInboundList(imsInbound);
        ExcelUtil<ImsInbound> util = new ExcelUtil<ImsInbound>(ImsInbound.class);
        util.exportExcel(response, list, "入库单数据");
    }

    /**
     * 获取入库单详细信息
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_inbound:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(imsInboundService.selectImsInboundById(id));
    }

    /**
     * 新增入库单
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_inbound:add')")
    @Log(title = "入库单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ImsInbound imsInbound)
    {
        return toAjax(imsInboundService.insertImsInbound(imsInbound));
    }

    /**
     * 修改入库单
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_inbound:edit')")
    @Log(title = "入库单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ImsInbound imsInbound)
    {
        return toAjax(imsInboundService.updateImsInbound(imsInbound));
    }

    /**
     * 删除入库单
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_inbound:remove')")
    @Log(title = "入库单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(imsInboundService.deleteImsInboundByIds(ids));
    }
}
