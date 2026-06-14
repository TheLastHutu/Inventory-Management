package com.ruoyi.ai.service.impl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import com.alibaba.fastjson2.JSON;
import com.ruoyi.ai.domain.AiMessage;
import com.ruoyi.ai.domain.bo.AiChatRequest;
import com.ruoyi.ai.domain.vo.AiChatEvent;
import com.ruoyi.ai.domain.vo.AiChatExchange;
import com.ruoyi.ai.service.AiChatProvider;
import com.ruoyi.ai.tool.AiToolCall;
import com.ruoyi.ai.tool.AiToolCallParser;
import com.ruoyi.ai.tool.AiToolExecutionResult;
import com.ruoyi.ai.tool.AiToolExecutor;
import com.ruoyi.ai.tool.AiToolProtocol;
import com.ruoyi.ai.tool.AiToolRouteRegistry;
import com.ruoyi.common.utils.StringUtils;

/**
 * Spring AI provider backed by the OpenAI-compatible chat model.
 */
@Service
@ConditionalOnProperty(prefix = "ai.chat", name = "provider", havingValue = "spring")
public class SpringAiChatProvider implements AiChatProvider
{
    private static final String PROMPT_PATH = "prompt/agent_prompt.txt";
    private static final ZoneId DEFAULT_ZONE = ZoneId.of("Asia/Shanghai");
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final ChatClient chatClient;
    private final String systemPrompt;

    @Autowired
    private AiToolRouteRegistry toolRouteRegistry;

    @Autowired
    private AiToolCallParser toolCallParser;

    @Autowired
    private AiToolExecutor toolExecutor;

    public SpringAiChatProvider(OpenAiChatModel chatModel)
    {
        this.chatClient = ChatClient.builder(chatModel).build();
        this.systemPrompt = loadSystemPrompt();
    }

    @Override
    public String chat(AiChatRequest request, List<AiMessage> history)
    {
        return exchange(request, history).getContent();
    }

    @Override
    public AiChatExchange exchange(AiChatRequest request, List<AiMessage> history)
    {
        String firstAnswer = chatClient.prompt().user(buildPrompt(request, history, null)).call().content();
        Optional<AiToolCall> toolCall = toolCallParser.parse(firstAnswer);
        if (!toolCall.isPresent())
        {
            return new AiChatExchange(firstAnswer);
        }

        AiChatExchange exchange = new AiChatExchange();
        AiToolCall call = toolCall.get();
        exchange.addEvent(new AiChatEvent("tool_start", call.getTool(),
                StringUtils.nvl(call.getReason(), "正在查询业务数据")));

        AiToolExecutionResult toolResult = toolExecutor.execute(request.getRoute(), call);
        exchange.addEvent(new AiChatEvent("tool_result", toolResult.getToolName(), toolResult.getSummary()));

        String finalAnswer = chatClient.prompt().user(buildPrompt(request, history, toolResult)).call().content();
        exchange.setContent(finalAnswer);
        return exchange;
    }

    @Override
    public AiChatExchange streamExchange(AiChatRequest request, List<AiMessage> history,
            Consumer<String> contentConsumer, Consumer<AiChatEvent> eventConsumer)
    {
        if (toolRouteRegistry.getAllowedTools(request.getRoute()).isEmpty())
        {
            StringBuilder answer = new StringBuilder();
            chatClient.prompt().user(buildPrompt(request, history, null)).stream().content()
                    .doOnNext(chunk -> {
                        if (chunk != null)
                        {
                            answer.append(chunk);
                            contentConsumer.accept(chunk);
                        }
                    }).blockLast();
            return new AiChatExchange(answer.toString());
        }

        String firstAnswer = chatClient.prompt().user(buildPrompt(request, history, null)).call().content();
        Optional<AiToolCall> toolCall = toolCallParser.parse(firstAnswer);
        if (!toolCall.isPresent())
        {
            if (StringUtils.isNotEmpty(firstAnswer))
            {
                contentConsumer.accept(firstAnswer);
            }
            return new AiChatExchange(firstAnswer);
        }

        AiChatExchange exchange = new AiChatExchange();
        AiToolCall call = toolCall.get();
        AiChatEvent startEvent = new AiChatEvent("tool_start", call.getTool(),
                StringUtils.nvl(call.getReason(), "姝ｅ湪鏌ヨ涓氬姟鏁版嵁"));
        exchange.addEvent(startEvent);
        eventConsumer.accept(startEvent);

        AiToolExecutionResult toolResult = toolExecutor.execute(request.getRoute(), call);
        AiChatEvent resultEvent = new AiChatEvent("tool_result", toolResult.getToolName(), toolResult.getSummary());
        exchange.addEvent(resultEvent);
        eventConsumer.accept(resultEvent);

        StringBuilder finalAnswer = new StringBuilder();
        chatClient.prompt().user(buildPrompt(request, history, toolResult)).stream().content()
                .doOnNext(chunk -> {
                    if (chunk != null)
                    {
                        finalAnswer.append(chunk);
                        contentConsumer.accept(chunk);
                    }
                }).blockLast();
        exchange.setContent(finalAnswer.toString());
        return exchange;
    }

    private String buildPrompt(AiChatRequest request, List<AiMessage> history, AiToolExecutionResult toolResult)
    {
        StringBuilder prompt = new StringBuilder();
        prompt.append(systemPrompt).append("\n\n");

        if (StringUtils.isNotEmpty(request.getPageTitle()) || StringUtils.isNotEmpty(request.getRoute()))
        {
            prompt.append("当前页面：").append(StringUtils.nvl(request.getPageTitle(), "")).append(" ");
            prompt.append(StringUtils.nvl(request.getRoute(), "")).append("\n\n");
        }

        appendDateContext(prompt);

        prompt.append(toolRouteRegistry.describeAllowedTools(request.getRoute())).append("\n");
        prompt.append("工具调用协议：\n");
        prompt.append("- 如果需要查询当前页面业务数据，只能输出一次工具调用 JSON，不要输出其他文字。\n");
        prompt.append("- 工具调用 JSON 必须被以下特殊标记包裹：\n");
        prompt.append(AiToolProtocol.START).append("\n");
        prompt.append("{\"type\":\"tool_call\",\"tool\":\"toolName\",\"arguments\":{\"beginDate\":\"yyyy-MM-dd\",\"endDate\":\"yyyy-MM-dd\",\"beginPeriod\":\"yyyy-MM\",\"endPeriod\":\"yyyy-MM\",\"periodCode\":\"yyyy-MM\",\"enterpriseId\":null,\"warehouseId\":null,\"deptId\":null,\"limit\":10},\"reason\":\"call reason\"}\n");
        prompt.append(AiToolProtocol.END).append("\n");
        prompt.append("- 如果不需要查询工具，正常中文回答，不要输出特殊标记。\n");
        prompt.append("- 工具只能查询和分析，不能新增、修改、删除任何业务数据。\n\n");

        prompt.append("历史对话：\n");
        for (AiMessage message : history)
        {
            prompt.append(message.getRole()).append(": ").append(message.getContent()).append("\n");
        }

        if (toolResult != null)
        {
            prompt.append("\n工具查询结果：\n");
            prompt.append(JSON.toJSONString(toolResult)).append("\n");
            prompt.append("请基于工具查询结果，用中文 Markdown 给出最终分析结论。不要再输出工具调用特殊标记。");
        }
        else
        {
            prompt.append("\n用户新问题：").append(request.getMessage());
        }
        return prompt.toString();
    }

    private void appendDateContext(StringBuilder prompt)
    {
        LocalDate today = LocalDate.now(DEFAULT_ZONE);
        YearMonth currentMonth = YearMonth.from(today);
        LocalDate monthStart = currentMonth.atDay(1);
        LocalDate monthEnd = currentMonth.atEndOfMonth();
        prompt.append("当前日期上下文：\n");
        prompt.append("- 当前服务器日期：").append(today.format(DATE_FORMAT)).append("\n");
        prompt.append("- 当前时区：").append(DEFAULT_ZONE.getId()).append("\n");
        prompt.append("- 当前月份：").append(currentMonth).append("\n");
        prompt.append("- 本月开始日期：").append(monthStart.format(DATE_FORMAT)).append("\n");
        prompt.append("- 本月结束日期：").append(monthEnd.format(DATE_FORMAT)).append("\n");
        prompt.append("当用户提到“今天”“当前日期”“本月”“这个月”“最近”等相对时间时，必须优先使用以上日期上下文，不要使用模型训练截止日期推断。\n\n");
    }

    private String loadSystemPrompt()
    {
        try
        {
            ClassPathResource resource = new ClassPathResource(PROMPT_PATH);
            return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
        }
        catch (IOException e)
        {
            throw new IllegalStateException("读取AI提示词文件失败: " + PROMPT_PATH, e);
        }
    }
}
