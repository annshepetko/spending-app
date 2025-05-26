package com.ann.spending.unit.calculation;

import com.ann.spending.authorization.entity.User;
import com.ann.spending.authorization.repository.UserRepository;
import com.ann.spending.calculation.DashboardSpendingCalculator;
import com.ann.spending.calculation.credential.SpendingRange;
import com.ann.spending.data.CalculateSpendingDataFactory;
import com.ann.spending.dashboard.range.Period;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class DashboardSpendingCalculatorTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    DashboardSpendingCalculator dashboardSpendingCalculator;

    User user;

    SpendingRange spendingRange;


    @BeforeEach
    void setUp() {
        user = CalculateSpendingDataFactory.createUser();
        this.spendingRange = Period.CURRENT_MONTH.getNeededDate();
    }


    @Test
    void testCalculation() {

        Mockito.when(userRepository.findTotalSpendingByUserAndMonth(user, LocalDateTime.now().getYear(), LocalDateTime.now().getMonthValue())).thenReturn(BigDecimal.valueOf(200.00));

        BigDecimal result = dashboardSpendingCalculator.calculateSpending(user, spendingRange);

        assertEquals(BigDecimal.valueOf(200.00), result);
    }
}