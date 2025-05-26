package com.ann.spending.data;

import com.ann.spending.authorization.entity.User;
import com.ann.spending.category.entity.Category;
import com.ann.spending.spending.dto.CreateSpendingBody;
import com.ann.spending.spending.entity.Spending;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SpendingDataFactory {

    public static CreateSpendingBody createSpendingBody(Long categoryId, User user) {

        return new CreateSpendingBody(BigDecimal.TEN, categoryId, "some desc", user);
    }

    public static Spending createSpending(User user, Category category) {
        Spending spending = build(user, category);

        return spending;
    }

    public static List<Spending> createSpendings(User user, Category category) {
        Spending spending = build(user, category, "купив кофту");
        Spending spending1 = build(user, category, "купив ноут");

        return List.of(spending, spending1);
    }

    @NotNull
    private static Spending build(User user, Category category, String description) {
        Spending spending = new Spending();

        spending.setDate(LocalDateTime.now());
        spending.setUser(user);
        spending.setCategory(category);
        spending.setDescription(description);
        spending.setAmount(BigDecimal.valueOf(100));
        return spending;
    }

    @NotNull
    private static Spending build(User user, Category category) {
        Spending spending = new Spending();

        spending.setDate(LocalDateTime.now());
        spending.setUser(user);
        spending.setCategory(category);
        spending.setDescription("desc");
        spending.setAmount(BigDecimal.valueOf(100));
        return spending;
    }

}
