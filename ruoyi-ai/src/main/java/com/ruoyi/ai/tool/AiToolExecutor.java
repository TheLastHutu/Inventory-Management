package com.ruoyi.ai.tool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;

@Component
public class AiToolExecutor
{
    @Autowired
    private AiToolRouteRegistry routeRegistry;

    @Autowired
    private AiInventoryToolExecutor inventoryToolExecutor;

    public AiToolExecutionResult execute(String route, AiToolCall call)
    {
        if (call == null || StringUtils.isEmpty(call.getTool()))
        {
            throw new ServiceException("工具调用内容为空");
        }
        if (!routeRegistry.isAllowed(route, call.getTool()))
        {
            throw new ServiceException("当前页面不允许调用工具：" + call.getTool());
        }
        if (inventoryToolExecutor.supports(call.getTool()))
        {
            return inventoryToolExecutor.execute(call);
        }
        throw new ServiceException("未找到工具执行器：" + call.getTool());
    }
}
