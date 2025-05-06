package com.ann.spending.search.spending.interfaces;

import com.ann.spending.filter.page.OrderPage;
import org.springframework.data.domain.Page;

public interface SearchMapper<T, F> {
    Page<T> mapToDtoPage(F filter, OrderPage orderPage);
}
