package com.ann.spending.dashboard.range;

import com.ann.spending.calculation.credential.SpendingRange;

import java.time.LocalDate;
import java.time.LocalDateTime;

public enum Period {

    PREV_MONTH {
        @Override
        public SpendingRange getNeededDate() {
            return new SpendingRange(LocalDateTime.now().minusMonths(1), LocalDateTime.MAX);
        }
    },
    CURRENT_MONTH;

    public SpendingRange getNeededDate() {
        return new SpendingRange(
                LocalDateTime.now(),
                LocalDateTime.MAX
        );
    }


}