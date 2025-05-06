package com.ann.spending.search.spending.interfaces;

import com.ann.spending.filter.page.OrderPage;
import org.springframework.data.domain.Page;

public interface SearchService<T, F> {
    Page<T> findByFilter(F filter, OrderPage orderPage);


}
