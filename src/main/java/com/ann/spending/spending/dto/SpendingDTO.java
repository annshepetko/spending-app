package com.ann.spending.spending.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record SpendingDTO(
        Long id,
        String description,
        Long categoryId,
        LocalDateTime spentAt,
        BigDecimal amount,
        LocalDateTime updatedAt) {
}
