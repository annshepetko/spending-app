package com.ann.spending.search.spending.mapper;

import com.ann.spending.filter.dto.FilterSpendingRequest;
import com.ann.spending.filter.page.OrderPage;
import com.ann.spending.search.spending.interfaces.SearchMapper;
import com.ann.spending.search.spending.interfaces.SearchingSpendingService;
import com.ann.spending.search.spending.util.SearchSpendingUtil;
import com.ann.spending.spending.dto.SpendingDTO;
import com.ann.spending.spending.entity.Spending;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SpendingSearchMapper implements SearchMapper<SpendingDTO, String> {


    private final SearchingSpendingService<List<String>> searchingSpendingService;

    public SpendingSearchMapper(
            @Qualifier("defaultSpendingSearchService")
            SearchingSpendingService<List<String>> searchingSpendingService
    ) {
        this.searchingSpendingService = searchingSpendingService;
    }

    @Override
    public Page<SpendingDTO> mapToDtoPage(String filter, OrderPage orderPage) {

        List<String> keyWords = SearchSpendingUtil.getKeyWordsFromPrompt(filter);

        Page<Spending> page = searchingSpendingService.findByFilter(keyWords, orderPage);

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
