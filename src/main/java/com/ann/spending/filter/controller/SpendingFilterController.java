package com.ann.spending.filter.controller;

import com.ann.spending.filter.dto.FilterSpendingRequest;
import com.ann.spending.filter.page.PageParams;
import com.ann.spending.filter.service.SpendingFilterMapper;
import com.ann.spending.search.spending.interfaces.SearchMapper;
import com.ann.spending.spending.dto.SpendingDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/filter/spending")
public class SpendingFilterController {


    private final SearchMapper<SpendingDTO, FilterSpendingRequest> spendingFilterMapper;

    public SpendingFilterController(SpendingFilterMapper spendingFilterMapper) {
        this.spendingFilterMapper = spendingFilterMapper;
    }

    @GetMapping
    public ResponseEntity<Page<SpendingDTO>> filterSpending(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime spentFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime spentTo,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) BigDecimal minAmount,
            @RequestParam(required = false) BigDecimal maxAmount,

            @RequestParam(defaultValue = "0") Integer pageCount,
            @RequestParam(defaultValue = "5") Integer pageSize,
            @RequestParam(defaultValue = "ASC") Sort.Direction sortDirection,
            @RequestParam(defaultValue = "date") String sortBy
    ) {
        FilterSpendingRequest filterSpendingRequest = new FilterSpendingRequest(
                categoryId,
                spentFrom,
                spentTo,
                description,
                minAmount,
                maxAmount
        );

        PageParams pageParams = new PageParams(pageCount, pageSize, sortDirection, sortBy);

        return ResponseEntity.ok(spendingFilterMapper.mapToDtoPage(filterSpendingRequest, pageParams));
    }
}
