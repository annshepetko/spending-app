package com.ann.spending.calculation;

import com.ann.spending.authorization.entity.User;
import com.ann.spending.calculation.credential.SpendingRange;
import com.ann.spending.dashboard.service.DashBoardViewService;

import java.math.BigDecimal;

public interface SpendingCalculationService {
    BigDecimal calculateSpending(User user, SpendingRange range);
}
