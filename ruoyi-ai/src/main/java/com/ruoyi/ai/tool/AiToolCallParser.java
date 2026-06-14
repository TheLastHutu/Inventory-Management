package com.ruoyi.ai.tool;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.utils.StringUtils;

@Component
public class AiToolCallParser
{
    private static final Pattern TOOL_CALL_PATTERN = Pattern.compile(
            Pattern.quote(AiToolProtocol.START) + "([\\s\\S]*?)" + Pattern.quote(AiToolProtocol.END));

    public Optional<AiToolCall> parse(String content)
    {
        if (StringUtils.isEmpty(content))
        {
            return Optional.empty();
        }
        Matcher matcher = TOOL_CALL_PATTERN.matcher(content);
        if (!matcher.find())
        {
            return Optional.empty();
        }
        String json = matcher.group(1);
        if (StringUtils.isEmpty(json))
        {
            return Optional.empty();
        }
        return Optional.of(JSON.parseObject(json.trim(), AiToolCall.class));
    }
}
