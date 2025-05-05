package com.ann.spending.spending.dto.request;


import java.math.BigDecimal;

public record AddSpendingRequest (BigDecimal amount, Long categoryId, String description){

}
