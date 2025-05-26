package com.ann.spending.unit.dashboard;

import com.ann.spending.authorization.entity.User;
;
import com.ann.spending.calculation.DashboardSpendingCalculator;
import com.ann.spending.calculation.credential.SpendingRange;
import com.ann.spending.calculation.util.PercentCalcUtil;
import com.ann.spending.category.service.CategoryDaoService;
import com.ann.spending.category.view.CategoryDTO;
import com.ann.spending.dashboard.range.Period;
import com.ann.spending.dashboard.service.DashBoardViewService;
import com.ann.spending.dashboard.view.DashboardView;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DashBoardViewServiceTest {

    @Mock
    private DashboardSpendingCalculator spendingCalculator;

    @Mock
    private CategoryDaoService categoryDaoService;

    @InjectMocks
    private DashBoardViewService dashBoardViewService;

    private User mockUser;

    @BeforeEach
    void setUp() {

        mockUser = new User();
        mockUser.setId(1L);
    }

    @Test
    void shouldRetrieveDashboardCorrectly() {

        List<CategoryDTO> mockCategories = List.of(
                new CategoryDTO(1L, "Food", 1L),
                new CategoryDTO(2L, "Transport", 2L)
        );

        BigDecimal currentSpending = new BigDecimal("150");
        BigDecimal prevMonthSpending = new BigDecimal("100");

        ArgumentCaptor<SpendingRange> rangeCaptor = ArgumentCaptor.forClass(SpendingRange.class);

        when(categoryDaoService.findUserCategories(mockUser)).thenReturn(mockCategories);

        when(spendingCalculator.calculateSpending(eq(mockUser), argThat(range ->
                range.start().getMonth() == LocalDateTime.now().getMonth()
        ))).thenReturn(currentSpending);

        when(spendingCalculator.calculateSpending(eq(mockUser), argThat(range ->
                range.start().getMonth() == LocalDateTime.now().minusMonths(1).getMonth()
        ))).thenReturn(prevMonthSpending);

        DashboardView result = dashBoardViewService.retrieveDashboard(mockUser);

        Assertions.assertEquals(currentSpending, result.spentAtThisMonth());

        verify(categoryDaoService).findUserCategories(mockUser);
        verify(spendingCalculator, times(2)).calculateSpending(eq(mockUser), any());

        verify(spendingCalculator, times(2)).calculateSpending(eq(mockUser), rangeCaptor.capture());

        List<SpendingRange> capturedRanges = rangeCaptor.getAllValues();

        boolean containsCurrentMonth = capturedRanges.stream()
                .anyMatch(range -> range.start().getMonth() == LocalDateTime.now().getMonth());

        boolean containsPrevMonth = capturedRanges.stream()
                .anyMatch(range -> range.start().getMonth() == LocalDateTime.now().minusMonths(1).getMonth());

        Assertions.assertTrue(containsCurrentMonth);
        Assertions.assertTrue(containsPrevMonth);
    }


}
