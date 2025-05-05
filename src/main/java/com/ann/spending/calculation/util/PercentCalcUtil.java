package com.ann.spending.calculation.util;

import java.math.BigDecimal;

public class PercentCalcUtil {


    public static Double calculatePercents(BigDecimal currentSpending, BigDecimal prevMonthSpending) {
        if (prevMonthSpending == null || prevMonthSpending.compareTo(BigDecimal.ZERO) == 0) {
            return 100.0;
        }

        return currentSpending
                .divide(prevMonthSpending, 2, BigDecimal.ROUND_HALF_UP)
                .multiply(BigDecimal.valueOf(100))
                .doubleValue();
    }
}
