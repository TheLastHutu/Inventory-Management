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
import com.ruoyi.ims.domain.ImsSaleReturn;
import com.ruoyi.ims.service.IImsSaleReturnService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 销售退货单Controller
 * 
 * @author tzq
 * @date 2026-05-29
 */
@RestController
@RequestMapping("/ims/ims_sale_return")
public class ImsSaleReturnController extends BaseController
{
    @Autowired
    private IImsSaleReturnService imsSaleReturnService;

    /**
     * 查询销售退货单列表
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_sale_return:list')")
    @GetMapping("/list")
    public TableDataInfo list(ImsSaleReturn imsSaleReturn)
    {
        startPage();
        List<ImsSaleReturn> list = imsSaleReturnService.selectImsSaleReturnList(imsSaleReturn);
        return getDataTable(list);
    }

    /**
     * 导出销售退货单列表
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_sale_return:export')")
    @Log(title = "销售退货单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ImsSaleReturn imsSaleReturn)
    {
        List<ImsSaleReturn> list = imsSaleReturnService.selectImsSaleReturnList(imsSaleReturn);
        ExcelUtil<ImsSaleReturn> util = new ExcelUtil<ImsSaleReturn>(ImsSaleReturn.class);
        util.exportExcel(response, list, "销售退货单数据");
    }

    /**
     * 获取销售退货单详细信息
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_sale_return:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(imsSaleReturnService.selectImsSaleReturnById(id));
    }

    /**
     * 新增销售退货单
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_sale_return:add')")
    @Log(title = "销售退货单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ImsSaleReturn imsSaleReturn)
    {
        return toAjax(imsSaleReturnService.insertImsSaleReturn(imsSaleReturn));
    }

    /**
     * 修改销售退货单
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_sale_return:edit')")
    @Log(title = "销售退货单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ImsSaleReturn imsSaleReturn)
    {
        return toAjax(imsSaleReturnService.updateImsSaleReturn(imsSaleReturn));
    }

    /**
     * 删除销售退货单
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_sale_return:remove')")
    @Log(title = "销售退货单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(imsSaleReturnService.deleteImsSaleReturnByIds(ids));
    }
}
