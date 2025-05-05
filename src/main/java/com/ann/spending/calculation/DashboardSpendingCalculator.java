package com.ann.spending.calculation;

import com.ann.spending.authorization.entity.User;
import com.ann.spending.calculation.credential.SpendingRange;
import com.ann.spending.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.YearMonth;

@Service
public class DashboardSpendingCalculator implements SpendingCalculationService {

    private final UserRepository userRepository;

    public DashboardSpendingCalculator(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    @Override
    public BigDecimal calculateSpending(User user, SpendingRange range) {

        BigDecimal sum = userRepository.findTotalSpendingByUserAndMonth(user, range.start().getYear(), range.start().getMonthValue());
        return sum == null ? BigDecimal.ZERO : sum;
    }

}
