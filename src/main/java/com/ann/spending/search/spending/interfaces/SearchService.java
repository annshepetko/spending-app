package com.ann.spending.search.spending.interfaces;

import com.ann.spending.filter.page.PageParams;
import org.springframework.data.domain.Page;

// todo: add comment about types

public interface SearchService<T, F> {
    Page<T> findByFilter(F filter, PageParams pageParams);


}
