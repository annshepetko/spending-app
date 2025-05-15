package com.ann.spending.filter.service;

import com.ann.spending.filter.dto.FilterSpendingRequest;

import com.ann.spending.filter.page.PageParams;
import com.ann.spending.search.spending.interfaces.SearchMapper;
import com.ann.spending.search.spending.interfaces.SearchingSpendingService;
import com.ann.spending.spending.dto.SpendingDTO;
import com.ann.spending.spending.entity.Spending;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class SpendingFilterMapper implements SearchMapper<SpendingDTO, FilterSpendingRequest> {


    private final SearchingSpendingService<FilterSpendingRequest> searchingSpendingService;

    public SpendingFilterMapper(
            @Qualifier("spendingFilterService")
            SearchingSpendingService<FilterSpendingRequest> searchingSpendingService
    ) {
        this.searchingSpendingService = searchingSpendingService;
    }


    @Override
    public Page<SpendingDTO> mapToDtoPage(FilterSpendingRequest filter, PageParams pageParams) {
        Page<Spending> page = searchingSpendingService.findByFilter(filter, pageParams);

        return page.map((s -> {
            return new SpendingDTO(
                    s.getId(),
                    s.getDescription(),
                    s.getCategory().getId(),
                    s.getDate(),
                    s.getAmount(),
                    s.getUpdatedAt()
            );
        }));

    }
}
