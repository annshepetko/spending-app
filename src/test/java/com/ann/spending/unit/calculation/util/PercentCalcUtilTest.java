package com.ann.spending.unit.calculation.util;

import com.ann.spending.calculation.util.PercentCalcUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PercentCalcUtilTest {


    @AfterEach
    void tearDown() {
    }

    @Test
    void calculatePercents() {
        BigDecimal currentMonth = BigDecimal.ONE;
        BigDecimal prevMonth = BigDecimal.TEN;

        Double result = PercentCalcUtil.calculatePercents(currentMonth, prevMonth);

        Assertions.assertEquals(-90.00, result);

    }
}