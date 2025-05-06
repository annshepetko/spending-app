package com.ann.spending.dashboard.view;

import com.ann.spending.category.view.CategoryDTO;

import java.math.BigDecimal;
import java.util.List;

public record DashboardView (
    List<CategoryDTO> categories,
    BigDecimal spentAtThisMonth,
    BigDecimal spentAtPrevMonth,
    Double percents
){

}
