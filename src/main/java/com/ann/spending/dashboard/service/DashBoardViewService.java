package com.ann.spending.dashboard.service;

import com.ann.spending.authorization.entity.User;
import com.ann.spending.calculation.DashboardSpendingCalculator;
import com.ann.spending.calculation.util.PercentCalcUtil;
import com.ann.spending.calculation.credential.SpendingRange;
import com.ann.spending.category.mapper.CategoryMapper;
import com.ann.spending.category.view.CategoryDTO;
import com.ann.spending.dashboard.view.DashboardView;
import com.ann.spending.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class DashBoardViewService {

    private final DashboardSpendingCalculator spendingCalculator;
    private final CategoryMapper categoryMapper;

    public DashBoardViewService(CategoryMapper categoryMapper, UserRepository userRepository, DashboardSpendingCalculator spendingCalculator, CategoryMapper categoryMapper1) {
        this.spendingCalculator = spendingCalculator;
        this.categoryMapper = categoryMapper1;
    }

    @Transactional
    public DashboardView retrieveDashboard(User user) {

        List<CategoryDTO> categories = categoryMapper.findCategoriesByUserId(user);

        BigDecimal currentSpending = spendingCalculator.calculateSpending(user, Period.CURRENT_MONTH.getNeededDate());
        BigDecimal prevMonthSpending = spendingCalculator.calculateSpending(user, Period.PREV_MONTH.getNeededDate());

        Double percents = PercentCalcUtil.calculatePercents(currentSpending, prevMonthSpending);

        return new DashboardView(categories, currentSpending, prevMonthSpending, percents);

    }

    enum Period {

        PREV_MONTH {
            @Override
            public SpendingRange getNeededDate() {
                return new SpendingRange(LocalDateTime.now().minusMonths(1), LocalDateTime.MAX);
            }
        },
        CURRENT_MONTH;

        public SpendingRange getNeededDate(){
            return new SpendingRange(LocalDateTime.now(), LocalDateTime.MAX);
        };
    }

}
