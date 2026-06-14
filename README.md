# 进销存营销智能体系统

本项目是在 RuoYi-Vue 3.9.2 基础上二次开发的进销存营销管理系统。系统保留了 RuoYi 的后台管理、权限认证、代码生成、日志审计、定时任务、接口文档等通用能力，并在此基础上新增了进销存业务模块和 AI Agent 智能体模块。

项目适合用于中小型企业的库存、采购、销售、客户、供应商、员工、工资、固定资产等业务管理场景。系统内部集成了智能体助手，智能体可以根据当前页面上下文调用只读业务工具，查询数据库中的库存、出入库、销售退货、报损、工资、固定资产、主数据健康等信息，再用自然语言给出分析结果。

## 核心能力

- 权限后台：基于 RuoYi 的用户、角色、菜单、部门、岗位、字典、参数、通知公告、登录日志、操作日志等基础管理能力。
- 进销存管理：支持商品、商品分类、品牌、计量单位、企业、仓库、货位、供应商、客户、员工等主数据维护。
- 单据业务：支持入库单、送货单、出库单、销售退货单、样品单、报损单等业务单据。
- 库存台账：支持现存量、库存流水、低库存查询、库存按仓库汇总等能力。
- 经营辅助：支持固定资产、工资期间和工资明细等管理模块。
- AI Agent：支持会话持久化、SSE 流式输出、页面上下文识别、只读工具调用和业务数据分析。
- 开发工具：保留 RuoYi 代码生成器、Druid 数据源监控、SpringDoc 接口文档等开发运维工具。

## 技术栈

后端：

- Java 17
- Spring Boot 4.0.3
- Spring Security
- JWT
- MyBatis
- MySQL
- Redis
- Druid
- Quartz
- Spring AI

前端：

- Vue 2
- Vue CLI
- Vuex
- Vue Router
- Element UI
- Axios
- ECharts

AI：

- Spring AI OpenAI-compatible Chat Model
- 默认配置示例使用 DeepSeek 兼容 OpenAI 的接口
- 支持本地占位回复模式和真实模型模式

## 项目结构

```text
.
├── ruoyi-admin       后端启动入口、Web 控制器、配置文件
├── ruoyi-framework   RuoYi 框架层、安全认证、Redis、MyBatis、全局配置
├── ruoyi-system      系统管理模块
├── ruoyi-quartz      定时任务模块
├── ruoyi-generator   代码生成模块
├── ruoyi-common      通用工具和公共组件
├── ruoyi-ims         进销存业务模块
├── ruoyi-ai          AI Agent、会话、消息、工具调用模块
├── ruoyi-ui          Vue 前端项目
└── sql               数据库脚本
```

## 启动前必须修改的配置

开源仓库中的敏感配置已经替换为占位符。首次运行前，请根据自己的本地环境修改下面这些文件。不要把真实密码、真实 API key、服务器 IP 提交到 GitHub。

### 1. 数据库配置

文件：`ruoyi-admin/src/main/resources/application-druid.yml`

需要修改：

```yaml
spring:
  datasource:
    druid:
      master:
        url: jdbc:mysql://<在这里填入你的数据库地址>:<在这里填入你的数据库端口>/<在这里填入你的数据库名称>?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8
        username: <在这里填入你的数据库用户名>
        password: <在这里填入你的数据库密码>
      statViewServlet:
        login-username: <在这里填入你的 Druid 控制台用户名>
        login-password: <在这里填入你的 Druid 控制台密码>
```

本地开发示例：

```yaml
url: jdbc:mysql://localhost:3306/ry_zf?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8
username: root
password: your_password
```

### 2. Redis、Token、AI 配置

文件：`ruoyi-admin/src/main/resources/application.yml`

需要修改：

```yaml
spring:
  ai:
    openai:
      api-key: <在这里填入你的 API 密钥>
      base-url: https://api.deepseek.com
      chat:
        options:
          model: deepseek-v4-flash
  data:
    redis:
      host: <在这里填入你的 Redis 地址>
      port: 6379
      password: <在这里填入你的 Redis 密码>

ai:
  chat:
    provider: spring
    model: deepseek-v4-flash
    max-history: 20
```

说明：

- 如果 Redis 没有密码，可以把 `password` 留空。
- `token.secret` 请改成足够复杂的随机字符串。
- 如果暂时没有 AI API key，可以把 `ai.chat.provider` 改成 `local`，系统会使用本地占位回复，不会真正调用大模型，也不会调用业务工具。
- 如果要启用真正的智能体分析，请保持 `ai.chat.provider: spring`，并配置可用的 `spring.ai.openai.api-key`、`base-url` 和 `model`。

### 3. 文件上传目录

文件：`ruoyi-admin/src/main/resources/application.yml`

```yaml
ruoyi:
  profile: D:/ruoyi/uploadPath
```

请改成你本机或服务器上真实存在、且后端进程有读写权限的目录。例如 Linux 可以改成：

```yaml
profile: /home/ruoyi/uploadPath
```

### 4. 前端接口代理

文件：`ruoyi-ui/vue.config.js`

默认前端会把 `/dev-api` 代理到 `http://localhost:8080`：

```js
const baseUrl = 'http://localhost:8080'
const port = process.env.port || process.env.npm_config_port || 80
```

如果后端端口不是 `8080`，请修改 `baseUrl`。如果本机 `80` 端口被占用或没有权限，请用其他端口启动前端。

### 5. 代码生成配置

文件：`ruoyi-generator/src/main/resources/generator.yml`

如果你要使用 RuoYi 的代码生成器，请按自己的包名和作者信息修改：

```yaml
gen:
  author: your_name
  packageName: com.ruoyi.ims
  tablePrefix: ims_
```

## 数据库初始化

推荐使用 MySQL 8.x。脚本在 `sql` 目录下：

- `sql/ry_zf.sql`：完整业务库脚本，包含 RuoYi 基础表、进销存业务表、菜单、示例数据等。
- `sql/ai_chat.sql`：AI 会话和消息表。
- `sql/ry.sql`：RuoYi 基础脚本参考，不建议和 `ry_zf.sql` 盲目重复导入。

初始化方式：

```bash
mysql -u root -p < sql/ry_zf.sql
mysql -u root -p ry_zf < sql/ai_chat.sql
```

如果使用 Navicat、DataGrip、DBeaver 等工具，可以先执行 `ry_zf.sql`，再选中 `ry_zf` 数据库执行 `ai_chat.sql`。

默认脚本中通常包含 RuoYi 管理员账号：

```text
账号：admin
密码：admin123
```

如果无法登录，请检查 `sys_user` 表中的用户状态、密码字段和数据库脚本是否完整导入。

## 后端启动

### 1. 准备环境

请先安装：

- JDK 17 或更高版本
- Maven 3.8+
- MySQL 8.x
- Redis

注意：根项目 `pom.xml` 使用 Java 17。当前 `ruoyi-ims/pom.xml` 中单独声明了 `maven.compiler.source` 和 `maven.compiler.target` 为 `25`，如果你使用 JDK 17 构建时报 Java release 25 相关错误，可以把该文件中的两个值改为 `17`，或者使用支持 Java 25 的 JDK。

### 2. 启动 Redis

确保 Redis 已启动，并且 `application.yml` 中的 Redis 地址、端口、密码正确。

### 3. 编译后端

在项目根目录执行：

```bash
mvn clean install -DskipTests
```

### 4. 运行后端

方式一：使用 Maven 启动。

```bash
mvn -pl ruoyi-admin -am spring-boot:run
```

方式二：使用 IDE 启动。

打开 `ruoyi-admin/src/main/java/com/ruoyi/RuoYiApplication.java`，运行 `main` 方法。

后端默认地址：

```text
http://localhost:8080
```

接口文档地址：

```text
http://localhost:8080/swagger-ui.html
```

Druid 监控地址：

```text
http://localhost:8080/druid
```

## 前端启动

### 1. 准备环境

推荐使用 Node.js 16 LTS。项目是 Vue 2 + Vue CLI 4，过新的 Node 版本可能遇到 OpenSSL 兼容问题。

### 2. 安装依赖

```bash
cd ruoyi-ui
npm install
```

如果想严格按 `package-lock.json` 安装，也可以使用：

```bash
npm ci
```

### 3. 启动前端

```bash
npm run dev
```

默认访问地址：

```text
http://localhost
```

如果 80 端口被占用，可以指定其他端口。

Windows PowerShell：

```powershell
$env:port=81; npm run dev
```

macOS / Linux：

```bash
port=81 npm run dev
```

然后访问：

```text
http://localhost:81
```

## 完整启动流程

1. 克隆项目到本地。
2. 安装并启动 MySQL、Redis。
3. 导入数据库脚本：先导入 `sql/ry_zf.sql`，再导入 `sql/ai_chat.sql`。
4. 修改 `application-druid.yml` 中的数据库连接、用户名、密码、Druid 账号密码。
5. 修改 `application.yml` 中的 Redis、token secret、上传目录、AI 配置。
6. 如需真实 AI 能力，配置可用的大模型 API key；如暂时不用，把 `ai.chat.provider` 改为 `local`。
7. 在项目根目录执行 `mvn clean install -DskipTests`。
8. 启动后端：`mvn -pl ruoyi-admin -am spring-boot:run`，确认 `http://localhost:8080` 可访问。
9. 进入 `ruoyi-ui`，执行 `npm install`。
10. 启动前端：`npm run dev`。
11. 打开前端页面，使用 `admin/admin123` 登录。
12. 进入进销存相关菜单，验证商品、仓库、库存、单据和 AI 助手功能。

## AI Agent 使用说明

AI 助手位于前端页面中，会把当前页面路由、页面标题、用户问题和历史会话一起传给后端。后端根据 `ai.chat.provider` 决定使用本地回复还是真实模型。

真实模型模式下，智能体会先判断是否需要查询业务数据。如果需要，它会根据当前页面允许的工具生成工具调用请求，后端只执行白名单中的只读查询工具，然后把查询结果交给模型生成最终回答。

当前已接入的工具能力包括：

- 库存汇总、按仓库汇总、低库存列表
- 库存流水趋势、商品出入库排行
- 报损汇总、报损商品分析
- 入库、送货、出库、销售退货、样品单月度汇总和商品排行
- 固定资产汇总、部门分布、折旧趋势、风险资产
- 工资月度汇总、部门对比、员工排行、异常检查
- 商品、分类、品牌、单位、仓库、供应商、企业、员工、客户等主数据健康检查

安全边界：

- 工具只做查询和分析，不新增、不修改、不删除业务数据。
- 每个页面只允许调用与当前页面相关的工具。
- AI 会话和消息会保存到 `ai_conversation`、`ai_message` 表。

## 生产部署建议

后端打包：

```bash
mvn clean package -DskipTests
java -jar ruoyi-admin/target/ruoyi-admin.jar
```

前端打包：

```bash
cd ruoyi-ui
npm run build:prod
```

构建产物在：

```text
ruoyi-ui/dist
```

生产环境建议：

- 使用 Nginx 部署 `ruoyi-ui/dist`。
- 将前端 `/prod-api` 代理到后端服务。
- 不要把真实配置提交到仓库，生产环境建议使用环境变量、独立配置文件或服务器密钥管理方案。
- 修改默认管理员密码。
- 修改 `token.secret`。
- 限制 Druid 监控访问来源或关闭公网访问。
- Redis、MySQL 不要暴露到公网。

## 常见问题

### 后端启动失败，提示数据库连接失败

检查 `application-druid.yml` 中的数据库地址、端口、库名、用户名、密码是否正确，并确认 MySQL 服务已启动。

### 后端启动失败，提示 Redis 连接失败

检查 `application.yml` 中的 Redis 地址、端口、密码是否正确，并确认 Redis 服务已启动。

### Maven 构建提示 release 25 不支持

检查当前 JDK 版本。如果使用 JDK 17，可以把 `ruoyi-ims/pom.xml` 中的：

```xml
<maven.compiler.source>25</maven.compiler.source>
<maven.compiler.target>25</maven.compiler.target>
```

改为：

```xml
<maven.compiler.source>17</maven.compiler.source>
<maven.compiler.target>17</maven.compiler.target>
```

### 前端无法请求后端接口

检查 `ruoyi-ui/vue.config.js` 中的 `baseUrl` 是否指向后端地址，后端是否已启动，浏览器控制台是否有代理或跨域错误。

### 前端 80 端口无法启动

使用其他端口启动：

```powershell
$env:port=81; npm run dev
```

### AI 不回复或调用模型失败

检查：

- `ai.chat.provider` 是否为 `spring`
- `spring.ai.openai.api-key` 是否有效
- `spring.ai.openai.base-url` 是否可访问
- `spring.ai.openai.chat.options.model` 是否填写了模型供应商支持的模型名

如果暂时不使用真实模型，请把 `ai.chat.provider` 改为 `local`。
