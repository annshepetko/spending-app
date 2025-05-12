package com.ann.spending.filter.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record SearchSpendingRequest(
        Long categoryId,
        LocalDateTime spentFrom,
        LocalDateTime spentTo,
        String description,
        BigDecimal minAmount,
        BigDecimal maxAmount

) {
}
