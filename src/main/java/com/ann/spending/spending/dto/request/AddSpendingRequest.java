package com.ann.spending.spending.dto.request;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record AddSpendingRequest (
        @Positive
        BigDecimal amount,
        Long categoryId,

        @NotNull(message = "Description should be not null")

        String description){

}
