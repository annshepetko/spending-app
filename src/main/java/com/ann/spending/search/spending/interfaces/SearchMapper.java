package com.ann.spending.search.spending.interfaces;

import com.ann.spending.filter.page.PageParams;
import org.springframework.data.domain.Page;

public interface SearchMapper<T, F> {
    Page<T> mapToDtoPage(F filter, PageParams pageParams);
}
