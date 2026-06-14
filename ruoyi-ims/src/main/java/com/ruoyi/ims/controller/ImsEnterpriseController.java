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
import com.ruoyi.ims.domain.ImsEnterprise;
import com.ruoyi.ims.service.IImsEnterpriseService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 企业管理Controller
 * 
 * @author tzq
 * @date 2026-05-29
 */
@RestController
@RequestMapping("/ims/ims_enterprise")
public class ImsEnterpriseController extends BaseController
{
    @Autowired
    private IImsEnterpriseService imsEnterpriseService;

    /**
     * 查询企业管理列表
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_enterprise:list')")
    @GetMapping("/list")
    public TableDataInfo list(ImsEnterprise imsEnterprise)
    {
        startPage();
        List<ImsEnterprise> list = imsEnterpriseService.selectImsEnterpriseList(imsEnterprise);
        return getDataTable(list);
    }

    /**
     * 导出企业管理列表
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_enterprise:export')")
    @Log(title = "企业管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ImsEnterprise imsEnterprise)
    {
        List<ImsEnterprise> list = imsEnterpriseService.selectImsEnterpriseList(imsEnterprise);
        ExcelUtil<ImsEnterprise> util = new ExcelUtil<ImsEnterprise>(ImsEnterprise.class);
        util.exportExcel(response, list, "企业管理数据");
    }

    /**
     * 获取企业管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_enterprise:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(imsEnterpriseService.selectImsEnterpriseById(id));
    }

    /**
     * 新增企业管理
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_enterprise:add')")
    @Log(title = "企业管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ImsEnterprise imsEnterprise)
    {
        return toAjax(imsEnterpriseService.insertImsEnterprise(imsEnterprise));
    }

    /**
     * 修改企业管理
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_enterprise:edit')")
    @Log(title = "企业管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ImsEnterprise imsEnterprise)
    {
        return toAjax(imsEnterpriseService.updateImsEnterprise(imsEnterprise));
    }

    /**
     * 删除企业管理
     */
    @PreAuthorize("@ss.hasPermi('ims:ims_enterprise:remove')")
    @Log(title = "企业管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(imsEnterpriseService.deleteImsEnterpriseByIds(ids));
    }
}
