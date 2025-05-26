package com.ann.spending.calculation.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PercentCalcUtil {


    public static Double calculatePercents(BigDecimal currentSpending, BigDecimal prevMonthSpending) {
        if (prevMonthSpending == null || prevMonthSpending.compareTo(BigDecimal.ZERO) == 0) {
            return 100.0; // або можна подумати про NaN/null або інше значення
        }

        BigDecimal difference = currentSpending.subtract(prevMonthSpending);
        BigDecimal percent = difference
                .divide(prevMonthSpending, 4, RoundingMode.HALF_UP) // точність 4 знаки
                .multiply(BigDecimal.valueOf(100));

        return percent.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

}
