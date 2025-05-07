package com.ann.spending.spending.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record SpendingDTO(

        @Positive(message = "Id should be positive")
        @NotNull(message = "Id should be not null")
        Long id,
        String description,
        Long categoryId,
        LocalDateTime spentAt,
        @Positive(message = "spent amount should be positive")
        BigDecimal amount,
        LocalDateTime updatedAt) {
}
