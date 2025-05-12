package com.ann.spending.filter.interfaces;

import com.ann.spending.filter.page.OrderPage;
import org.springframework.data.domain.Page;

/**
 *
 * @param <T> - return type of values
 *
 * @param <V> Filter object with necessary fields
 */
public interface FilterRepository<T, V>{

    Page<T> findByFilter(V f, OrderPage orderPage);

}
