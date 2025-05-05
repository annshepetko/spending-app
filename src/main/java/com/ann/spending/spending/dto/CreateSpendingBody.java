package com.ann.spending.spending.dto;

import com.ann.spending.authorization.entity.User;
import java.math.BigDecimal;

public record CreateSpendingBody(
        BigDecimal amount,
        Long categoryId,
        String description,
        User user

) {

}
