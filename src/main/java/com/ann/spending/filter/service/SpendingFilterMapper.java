package com.ann.spending.filter.service;

import com.ann.spending.filter.dto.SearchSpendingRequest;

import com.ann.spending.filter.interfaces.FilterRepository;
import com.ann.spending.filter.page.OrderPage;
import com.ann.spending.spending.dto.SpendingDTO;
import com.ann.spending.spending.entity.Spending;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class SpendingFilterMapper {

    private final FilterRepository<Spending, SearchSpendingRequest> filterRepository;

    public SpendingFilterMapper(FilterRepository<Spending, SearchSpendingRequest> filterRepository) {
        this.filterRepository = filterRepository;
    }


    public Page<SpendingDTO> findSpendingByFilter(SearchSpendingRequest searchSpendingRequest, OrderPage orderPage){
        Page<Spending> page = filterRepository.findByFilter(searchSpendingRequest, orderPage);

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
