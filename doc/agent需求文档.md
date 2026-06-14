# 智发 AI Agent 需求分析文档

## 1. 背景与目标

本项目为基于 RuoYi-Vue 的进销存管理系统，已有往来现金、库存管理、基础设置、采购管理、销售管理、系统管理等业务页面。拟新增一个名为“智发 AI”的智能体应用，为用户提供业务数据查询、统计分析、趋势解读、异常提醒和页面上下文问答能力。

本阶段只做需求分析与方案设计，不编写功能代码。

核心目标：

- 在不破坏原项目结构的前提下新增 Agent 能力。
- 前端提供可移动悬浮入口和可缩放聊天窗口。
- 后端使用 Spring AI 承载大模型编排，使用 MyBatis 查询数据库。
- Agent 工具严格只读，只允许查询、统计、分析，不允许新增、修改、删除业务数据。
- 使用 SSE 流式输出，提升回答过程中的实时反馈体验。
- 前端支持 Markdown 内容展示，包括列表、表格、代码块、加粗、链接等常见格式。

## 2. 低耦合可行性分析

### 2.1 后端独立模块

建议新增 Maven 子模块 `ruoyi-ai`，专门放置 Agent 后端代码。

建议目录：

```text
ruoyi-ai/
  pom.xml
  src/main/java/com/ruoyi/ai/
    controller/
    service/
    domain/
    mapper/
    tool/
    memory/
    config/
  src/main/resources/
    mapper/ai/
```

可行性结论：可行，但无法做到绝对零耦合。

原因：

- RuoYi 后端启动入口在 `ruoyi-admin`。
- 若 `ruoyi-ai` 作为后端子模块被启动扫描，需要在父 `pom.xml` 增加 `<module>ruoyi-ai</module>`。
- 需要在 `ruoyi-admin/pom.xml` 增加对 `ruoyi-ai` 的依赖。

这是最小必要耦合。后续删除 Agent 时，只需要移除：

- `ruoyi-ai` 文件夹。
- 父 `pom.xml` 中的 `ruoyi-ai` module。
- `ruoyi-admin/pom.xml` 中的 `ruoyi-ai` 依赖。
- 相关 AI 配置项。

不需要修改原有 `ruoyi-ims` 业务代码，不需要改动原有业务 Controller、Service、Mapper。

### 2.2 前端独立目录

建议在 `ruoyi-ui/src` 下新增 `ai` 文件夹，集中放置前端 Agent 相关代码。

建议目录：

```text
ruoyi-ui/src/ai/
  components/
    AiFloatingBall.vue
    AiChatWindow.vue
    AiMessageList.vue
    AiMessageInput.vue
    AiConversationList.vue
  api/
    chat.js
    conversation.js
  store/
    aiConversation.js
  utils/
    sse.js
    markdown.js
    dragResize.js
  styles/
    ai.scss
```

可行性结论：可行，但需要一个最小挂载入口。

原因：

- 悬浮窗需要出现在系统所有页面上，必须在全局布局或入口处挂载一次。
- 可选挂载点包括 `src/App.vue`、`src/layout/index.vue` 或 `src/main.js`。

推荐方案：

- 将所有 AI 组件、样式、请求、状态管理都放在 `src/ai` 下。
- 只在 `App.vue` 或 `layout/index.vue` 增加一个 `<AiFloatingBall />` 引入。
- 是否显示由配置开关控制，例如 `VUE_APP_AI_ENABLED=true`。

删除 Agent 前端时，只需移除：

- `src/ai` 文件夹。
- 全局挂载处的一行组件引用。
- 可选的环境变量配置。

### 2.3 数据库耦合

建议不修改现有业务表结构。

如果需要保存会话历史，建议新增 AI 专属表，例如：

- `ai_conversation`：会话主表。
- `ai_message`：消息明细表。
- `ai_tool_call_log`：工具调用日志。
- `ai_user_context`：用户最近页面、会话偏好等上下文。

这些表应由 `ruoyi-ai` 独立管理。删除 Agent 时，删除 AI 表即可，不影响业务表。

## 3. 前端 UI 需求

### 3.1 智发 AI 悬浮入口

页面右下角显示一个可拖拽的小球，文案为“智发 AI”或小球内显示“AI”并在旁边显示“智发 AI”。

需求：

- 可拖动位置。
- 位置记忆到 `localStorage`。
- 不遮挡主要按钮，默认在右下角。
- 在小屏幕下自动贴边，避免超出屏幕。
- 支持悬停态、点击态和加载态。
- 当正在回答时，小球显示流式生成状态。

### 3.2 聊天窗口

点击悬浮小球后打开聊天框。

需求：

- 支持拖动。
- 支持自由缩放大小。
- 支持最大化、最小化、关闭。
- 支持新建聊天。
- 支持查看历史会话。
- 支持继续历史会话，不丢失上下文。
- 支持当前页面上下文，例如当前路由、页面标题、查询条件、选中行 ID。
- 支持 Markdown 渲染。
- 支持 SSE 流式输出，逐字或分段显示回答。
- 支持回答中展示工具调用状态，例如“正在查询库存流水”“正在汇总本月工资”。
- 支持复制回答内容。
- 支持停止生成。

### 3.3 Markdown 显示

前端需要正常显示 Markdown 内容。

建议支持：

- 标题。
- 有序列表和无序列表。
- 表格。
- 加粗、斜体。
- 行内代码和代码块。
- 链接。
- 简单引用。

可选增强：

- 表格横向滚动。
- 代码块复制。
- 数字指标高亮。
- 分析结论卡片化展示，但不要影响 Markdown 基础渲染。

### 3.4 上下文保持

上下文分为三类：

- 会话上下文：同一会话内的历史消息。
- 页面上下文：用户当前所在模块、路由、页面查询条件、当前表格选中行。
- 业务上下文：Agent 通过工具查询到的数据结果。

建议策略：

- 前端每次发问携带 `conversationId`、当前路由、页面标题、查询条件。
- 后端按 `conversationId` 加载最近 N 轮消息。
- 工具查询结果不直接全部塞入长期上下文，只保留摘要，避免上下文过长。
- 重要业务结论可写入会话摘要。

## 4. 后端 Agent 需求

### 4.1 技术选型

后端使用：

- Spring AI：负责 ChatClient、Prompt、Tool Calling、模型接入和流式输出。
- MyBatis：负责业务数据查询。
- Spring MVC SSE：返回 `text/event-stream`。
- RuoYi 权限体系：复用登录态、用户身份、权限校验。

### 4.2 接口设计

建议接口全部放在 `/ai` 前缀下。

核心接口：

```text
POST /ai/chat/stream
```

用途：发送用户问题，SSE 流式返回模型回答。

请求参数：

```json
{
  "conversationId": "可为空，为空则新建会话",
  "message": "帮我分析这个月库存流水",
  "route": "/inventory/ims_stock_flow",
  "pageTitle": "库存流水",
  "queryParams": {},
  "selectedRows": []
}
```

SSE 事件建议：

```text
event: message
data: {"content":"正在分析"}

event: tool_start
data: {"toolName":"stockFlowSummary","description":"查询库存流水汇总"}

event: tool_result
data: {"toolName":"stockFlowSummary","summary":"已查询到 20 条流水"}

event: done
data: {"conversationId":"xxx"}

event: error
data: {"message":"查询失败"}
```

其他接口：

```text
GET /ai/conversations
GET /ai/conversations/{conversationId}
POST /ai/conversations
DELETE /ai/conversations/{conversationId}
```

删除会话只删除 AI 会话数据，不删除任何业务数据。

### 4.3 安全边界

Agent 工具必须只读。

禁止能力：

- 禁止 INSERT。
- 禁止 UPDATE。
- 禁止 DELETE。
- 禁止 ALTER。
- 禁止 DROP。
- 禁止执行任意 SQL。
- 禁止帮用户直接修改单据状态、价格、数量、日期、库存、工资等数据。

允许能力：

- 查询列表。
- 查询详情。
- 聚合统计。
- 趋势分析。
- 异常检测。
- 经营分析。
- 生成只读建议。

典型拒答：

- 用户问“帮我把固定资产的购置日期改成今天”。
- 用户问“把这条工资记录删掉”。
- 用户问“帮我审核这个出库单”。

Agent 应回答：当前智能体只能查询和分析，不能修改业务数据。如需修改，请在系统页面中由有权限的用户手动操作。

## 5. 用户问题场景分析与工具设计

### 5.1 固定资产页面

涉及页面：往来现金 / 固定资产。

涉及表：

- `ims_fixed_asset`
- `ims_fixed_asset_depr`
- `ims_enterprise`
- 可关联 `sys_dept` 或 `ims_employee`

用户可能提出的问题：

- “当前固定资产总原值和总净值是多少？”
- “哪些资产净值下降最多？”
- “本月新增了哪些固定资产？”
- “按使用部门统计资产数量和净值。”
- “哪些资产接近报废或已停用？”
- “帮我分析固定资产折旧情况。”

建议工具：

| 工具名 | 作用 | 查询范围 |
| --- | --- | --- |
| `fixedAssetSummary` | 固定资产总览 | 数量、原值、净值、状态分布 |
| `fixedAssetByDept` | 按部门统计资产 | 使用部门、资产数、原值、净值 |
| `fixedAssetDepreciationTrend` | 折旧趋势分析 | 按期间汇总折旧额 |
| `fixedAssetRiskList` | 资产风险清单 | 净值低、停用、报废、长期未更新 |

### 5.2 工资管理页面

涉及页面：往来现金 / 工资管理。

涉及表：

- `ims_payroll_period`
- `ims_payroll_detail`
- `ims_employee`
- `sys_dept`

用户可能提出的问题：

- “这个月工资总额是多少？”
- “哪个部门工资支出最高？”
- “奖金、提成、扣款分别是多少？”
- “有哪些员工扣款异常？”
- “本月工资和上月相比变化如何？”
- “已发放和未发放的工资分别有多少？”

建议工具：

| 工具名 | 作用 | 查询范围 |
| --- | --- | --- |
| `payrollMonthlySummary` | 月度工资汇总 | 实发工资、基础工资、奖金、提成、扣款 |
| `payrollDeptCompare` | 部门工资对比 | 按部门聚合工资 |
| `payrollEmployeeRanking` | 员工工资排行 | 实发工资、奖金、提成排行 |
| `payrollAbnormalCheck` | 工资异常检查 | 扣款偏高、出勤异常、工资波动 |
| `payrollStatusSummary` | 发放状态统计 | 已发放、未发放统计 |

### 5.3 库存管理页面

涉及页面：库存管理 / 报损单、库存流水、库存现存量。

涉及表：

- `ims_damage`
- `ims_damage_item`
- `ims_stock_flow`
- `ims_inventory`
- `ims_product`
- `ims_warehouse`
- `ims_warehouse_location`
- `ims_enterprise`

用户可能提出的问题：

- “当前库存最多的产品是什么？”
- “哪些产品库存不足？”
- “最近一周出库最多的产品有哪些？”
- “本月报损金额是多少？”
- “哪些仓库库存金额最高？”
- “库存流水中入库和出库趋势如何？”
- “哪些产品报损频率比较高？”

建议工具：

| 工具名 | 作用 | 查询范围 |
| --- | --- | --- |
| `inventoryCurrentSummary` | 现存量总览 | 现存量、锁定量、可用量 |
| `inventoryByWarehouse` | 仓库库存统计 | 按仓库统计数量和金额 |
| `inventoryLowStockList` | 低库存分析 | 可用量低或为 0 的产品 |
| `stockFlowTrend` | 库存流水趋势 | 按日、周、月统计入库/出库 |
| `stockFlowProductRanking` | 产品出入库排行 | 高频产品、数量排行、金额排行 |
| `damageMonthlySummary` | 报损汇总 | 报损数量、金额、原因 |
| `damageProductAnalysis` | 产品报损分析 | 按产品统计报损频次和金额 |

### 5.4 基础设置页面

涉及页面：计量单位管理、货位管理、供应商管理、仓库管理、产品管理、产品分类、企业管理、员工管理、客户管理、品牌管理。

涉及表：

- `ims_unit`
- `ims_warehouse`
- `ims_warehouse_location`
- `ims_supplier`
- `ims_product`
- `ims_product_category`
- `ims_enterprise`
- `ims_employee`
- `ims_customer`
- `ims_brand`

用户可能提出的问题：

- “目前有哪些计量单位处于停用状态？”
- “哪些产品没有绑定供应商或品牌？”
- “哪些仓库没有设置货位？”
- “供应商数量和正常状态占比是多少？”
- “客户资料是否有联系方式缺失？”
- “产品分类结构是否完整？”
- “哪些员工状态异常？”

建议工具：

| 工具名 | 作用 | 查询范围 |
| --- | --- | --- |
| `masterDataHealthCheck` | 基础资料健康检查 | 缺失字段、停用数据、未绑定关系 |
| `unitStatusSummary` | 计量单位统计 | 单位数量、状态分布 |
| `warehouseLocationSummary` | 仓库货位统计 | 仓库、货位、启停状态 |
| `supplierHealthCheck` | 供应商资料检查 | 联系方式、状态、地址缺失 |
| `productMasterAnalysis` | 产品资料分析 | 分类、品牌、供应商、价格、单位 |
| `customerHealthCheck` | 客户资料检查 | 联系人、电话、地址缺失 |
| `employeeStatusSummary` | 员工状态统计 | 部门、员工数量、启停状态 |

### 5.5 采购管理页面

涉及页面：采购管理 / 入库单。

涉及表：

- `ims_inbound`
- `ims_inbound_item`
- `ims_supplier`
- `ims_product`
- `ims_warehouse`
- `ims_employee`

用户可能提出的问题：

- “本月采购入库金额是多少？”
- “哪个供应商供货最多？”
- “入库单中有哪些草稿或作废单据？”
- “本月入库产品排行。”
- “某个供应商最近供货趋势如何？”

建议工具：

| 工具名 | 作用 | 查询范围 |
| --- | --- | --- |
| `inboundMonthlySummary` | 入库月度汇总 | 数量、金额、单据数 |
| `inboundSupplierRanking` | 供应商供货排行 | 按供应商统计数量和金额 |
| `inboundProductRanking` | 入库产品排行 | 按产品统计数量和金额 |
| `inboundStatusSummary` | 入库单状态统计 | 草稿、已审核、作废 |

### 5.6 销售管理页面

涉及页面：销售管理 / 送货单、出库单、销售退货单、样品单。

涉及表：

- `ims_delivery`
- `ims_delivery_item`
- `ims_outbound`
- `ims_outbound_item`
- `ims_sale_return`
- `ims_sale_return_item`
- `ims_sample`
- `ims_sample_item`
- `ims_customer`
- `ims_product`
- `ims_warehouse`

用户可能提出的问题：

- “本月销售出库金额是多少？”
- “送货最多的客户是谁？”
- “退货金额占销售金额比例是多少？”
- “哪些产品退货比较多？”
- “样品单本月发出多少片？”
- “销售出库和退货趋势如何？”

建议工具：

| 工具名 | 作用 | 查询范围 |
| --- | --- | --- |
| `salesDeliverySummary` | 送货统计 | 送货数量、金额、客户分布 |
| `outboundMonthlySummary` | 出库统计 | 出库数量、金额、类型分布 |
| `salesReturnSummary` | 销售退货统计 | 退货数量、金额、原因 |
| `salesReturnRateAnalysis` | 退货率分析 | 出库与退货对比 |
| `sampleMonthlySummary` | 样品单统计 | 样品数量、客户、用途 |
| `salesCustomerRanking` | 客户排行 | 送货/出库金额排行 |
| `salesProductRanking` | 产品销售排行 | 出库/送货产品排行 |

### 5.7 系统管理页面

涉及页面：用户管理、角色管理、菜单管理、部门管理、岗位管理、字典管理、参数设置、通知公告、操作日志、登录日志。

涉及表：

- `sys_user`
- `sys_role`
- `sys_menu`
- `sys_dept`
- `sys_post`
- `sys_dict_type`
- `sys_dict_data`
- `sys_config`
- `sys_notice`
- `sys_oper_log`
- `sys_logininfor`

用户可能提出的问题：

- “最近有哪些登录失败记录？”
- “今天操作日志最多的用户是谁？”
- “哪些用户被停用？”
- “哪些菜单权限没有分配？”
- “最近系统有哪些高频操作？”

建议工具：

| 工具名 | 作用 | 查询范围 |
| --- | --- | --- |
| `loginLogAnalysis` | 登录日志分析 | 登录成功/失败、IP、用户 |
| `operationLogAnalysis` | 操作日志分析 | 模块、用户、耗时、错误 |
| `systemUserSummary` | 用户状态统计 | 正常、停用、部门分布 |
| `rolePermissionSummary` | 角色权限概览 | 角色、菜单、权限数量 |

系统管理工具也必须只读，不允许修改用户、角色、菜单、权限。

## 6. Tool Calling 设计原则

### 6.1 工具粒度

工具不建议设计成“万能 SQL 查询工具”。更推荐按业务能力封装，例如：

- 固定资产汇总工具。
- 库存流水趋势工具。
- 工资异常检查工具。
- 销售退货率分析工具。

这样可以避免模型生成危险 SQL，也方便权限控制、参数校验和审计。

### 6.2 参数设计

工具参数应明确、可校验。

示例：

```json
{
  "enterpriseId": 1,
  "startDate": "2026-06-01",
  "endDate": "2026-06-30",
  "warehouseId": null,
  "productId": null,
  "limit": 10
}
```

参数规则：

- 日期范围必须有上限，默认不超过 1 年。
- `limit` 默认 10，最大 100。
- 不允许传入 SQL 片段。
- 所有枚举字段需要白名单校验。
- 当前用户无权限的数据不可查询。

### 6.3 工具结果

工具结果应返回结构化数据和摘要。

示例：

```json
{
  "summary": "本月库存流水共 128 条，入库 5600 片，出库 4300 片。",
  "rows": [],
  "metrics": {
    "inQty": 5600,
    "outQty": 4300
  }
}
```

模型负责把结构化结果转为自然语言分析，前端负责展示 Markdown。

## 7. 会话记忆与历史对话

### 7.1 会话保存

每个会话需要保存：

- 会话 ID。
- 用户 ID。
- 标题。
- 创建时间。
- 更新时间。
- 最近页面路由。
- 消息列表。
- 会话摘要。

### 7.2 上下文策略

为了避免上下文过长：

- 每次请求加载最近 10 到 20 条消息。
- 超出部分生成摘要。
- 工具查询结果只保留摘要和关键指标。
- 原始大结果集不长期进入模型上下文。

### 7.3 新建聊天

用户点击“新建聊天”时：

- 创建新的 `conversationId`。
- 清空当前聊天窗口消息。
- 不清除旧会话。
- 新会话仍可携带当前页面上下文。

## 8. 权限与审计

### 8.1 权限控制

建议复用 RuoYi 登录态和权限系统。

要求：

- 未登录用户不能访问 `/ai/**`。
- 查询业务数据时按当前用户权限过滤。
- 系统管理类查询需要额外权限。
- 工具调用前校验用户是否具备对应模块的查看权限。

### 8.2 审计日志

建议记录：

- 用户 ID。
- 会话 ID。
- 问题内容摘要。
- 调用工具名称。
- 工具参数。
- 查询耗时。
- 返回记录数量。
- 是否失败。

不要记录敏感完整数据，例如密码、Token、完整个人隐私字段。

## 9. SSE 流式输出需求

### 9.1 前端体验

用户发送问题后，应立即看到反馈：

- “正在理解问题”
- “正在查询库存流水”
- “正在生成分析”
- 模型回答逐步输出

### 9.2 后端输出

后端应使用 `text/event-stream`。

输出事件建议：

- `message`：模型增量内容。
- `tool_start`：工具开始调用。
- `tool_result`：工具调用完成。
- `done`：回答结束。
- `error`：异常。

### 9.3 失败处理

如果模型失败：

- 返回错误事件。
- 前端显示错误提示。
- 当前会话保留用户问题。

如果工具失败：

- Agent 可以说明某项数据暂时无法查询。
- 尽量基于其他可用信息继续回答。

## 10. 非功能需求

### 10.1 性能

- 常见统计工具响应时间建议小于 3 秒。
- 大范围统计需要限制日期范围。
- 工具查询必须分页或限制结果数量。
- SSE 连接需要超时控制。

### 10.2 可靠性

- 网络断开时前端提示重试。
- 用户刷新页面后可从历史会话恢复。
- 后端异常不能影响原有业务接口。
- AI 配置缺失时，系统应隐藏或禁用 AI 入口。

### 10.3 可维护性

- Agent 后端代码集中在 `ruoyi-ai`。
- Agent 前端代码集中在 `src/ai`。
- 工具按业务域拆分。
- Prompt、工具说明、权限映射集中配置。

## 11. 推荐实施阶段

### 第一阶段：基础聊天能力

- 新增 `ruoyi-ai` 模块。
- 新增前端悬浮球和聊天窗口。
- 实现 SSE 流式聊天。
- 实现会话历史保存。
- 支持 Markdown 渲染。

### 第二阶段：核心业务只读工具

优先实现：

- 固定资产汇总。
- 工资月度汇总。
- 库存现存量汇总。
- 库存流水趋势。
- 入库统计。
- 出库/送货/退货统计。
- 基础资料健康检查。

### 第三阶段：增强分析能力

- 异常检测。
- 趋势对比。
- 多页面上下文识别。
- 工具调用审计。
- 权限精细化控制。

### 第四阶段：体验优化

- 工具调用过程可视化。
- 表格结果展示。
- 常用问题推荐。
- 页面级快捷提问。
- 回答复制和导出。

## 12. 关键风险与建议

| 风险 | 说明 | 建议 |
| --- | --- | --- |
| 完全零耦合不可实现 | 前后端都需要入口接入 | 采用最小入口引用和独立模块 |
| 模型误调用修改类能力 | 用户可能要求修改数据 | 工具层只提供只读方法，禁止任意 SQL |
| 查询范围过大 | 统计可能拖慢系统 | 限制日期范围、limit、分页 |
| 上下文过长 | 历史会话和工具结果会膨胀 | 使用摘要机制 |
| 权限泄露 | Agent 可能跨模块查询 | 工具调用前做权限校验 |
| Markdown 安全 | 渲染 HTML 可能有 XSS 风险 | 禁用原始 HTML 或使用安全过滤 |

## 13. 总体结论

新增“智发 AI”Agent 在当前项目中可行。推荐采用后端 `ruoyi-ai` 独立 Maven 子模块、前端 `ruoyi-ui/src/ai` 独立目录的方式实现。该方案不能做到绝对零耦合，但可以做到低耦合、可删除、边界清晰。

Agent 的能力边界应明确定位为“只读业务分析助手”，不提供任何直接修改数据库的工具。前端通过可移动悬浮球和可缩放聊天窗承载交互，后端通过 Spring AI + MyBatis + SSE 实现流式问答和业务工具调用。优先围绕固定资产、工资、库存、基础资料、采购、销售等页面设计工具，逐步增强分析深度和用户体验。
