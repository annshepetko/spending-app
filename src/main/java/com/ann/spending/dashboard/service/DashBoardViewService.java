package com.ann.spending.dashboard.service;

import com.ann.spending.authorization.entity.User;
import com.ann.spending.calculation.DashboardSpendingCalculator;
import com.ann.spending.calculation.credential.SpendingRange;
import com.ann.spending.calculation.util.PercentCalcUtil;
import com.ann.spending.category.service.CategoryDaoService;
import com.ann.spending.category.view.CategoryDTO;
import com.ann.spending.dashboard.range.Period;
import com.ann.spending.dashboard.view.DashboardView;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class DashBoardViewService {

    private final DashboardSpendingCalculator spendingCalculator;
    private final CategoryDaoService categoryDaoService;

    public DashBoardViewService(DashboardSpendingCalculator spendingCalculator, CategoryDaoService categoryDaoService) {
        this.spendingCalculator = spendingCalculator;
        this.categoryDaoService = categoryDaoService;
    }

    @Transactional
    public DashboardView retrieveDashboard(User user) {

        List<CategoryDTO> categories = categoryDaoService.findUserCategories(user);

        BigDecimal currentSpending = spendingCalculator.calculateSpending(user, Period.CURRENT_MONTH.getNeededDate());
        BigDecimal prevMonthSpending = spendingCalculator.calculateSpending(user, Period.PREV_MONTH.getNeededDate());

        Double percents = PercentCalcUtil.calculatePercents(currentSpending, prevMonthSpending);

        return new DashboardView(categories, currentSpending, prevMonthSpending, percents);

    }


}
