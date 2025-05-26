package com.ann.spending.data;

import com.ann.spending.authorization.entity.User;
import com.ann.spending.calculation.credential.SpendingRange;
import com.ann.spending.spending.entity.Spending;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CalculateSpendingDataFactory {

    public static User createUser() {
        User user = new User();

        user.setSpending(buildSpending());

        return user;
    }

    private static List<Spending> buildSpending() {

        List<Spending> spending = new ArrayList<>();

        spending.add(new Spending(BigDecimal.valueOf(100L), LocalDateTime.now()));
        spending.add(new Spending(BigDecimal.valueOf(100L), LocalDateTime.now()));
        return spending;
    }
}
