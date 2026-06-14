package com.ruoyi.ims.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

final class ImsAmountUtils
{
    private ImsAmountUtils()
    {
    }

    static BigDecimal qty(BigDecimal value)
    {
        if (value == null || value.compareTo(BigDecimal.ZERO) <= 0)
        {
            return BigDecimal.ZERO;
        }
        return value.setScale(0, RoundingMode.DOWN);
    }

    static BigDecimal money(BigDecimal value)
    {
        if (value == null || value.compareTo(BigDecimal.ZERO) < 0)
        {
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }
        return value.setScale(2, RoundingMode.HALF_UP);
    }

    static BigDecimal amount(BigDecimal qty, BigDecimal price)
    {
        return qty(qty).multiply(money(price)).setScale(2, RoundingMode.HALF_UP);
    }
}
