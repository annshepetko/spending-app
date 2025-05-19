package com.ann.spending.search.spending.controller;

import com.ann.spending.filter.page.PageParams;
import com.ann.spending.search.spending.interfaces.SearchMapper;
import com.ann.spending.search.spending.mapper.SpendingSearchMapper;
import com.ann.spending.spending.dto.SpendingDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/search/spending")
@RestController
public class SearchSpendingController {

    private final SearchMapper<SpendingDTO, String> searchMapper;

    public SearchSpendingController(SpendingSearchMapper searchMapper) {
        this.searchMapper = searchMapper;
    }

    @GetMapping
    public ResponseEntity<Page<SpendingDTO>> searchByPrompt(
            @RequestParam String prompt,

            @RequestParam(defaultValue = "0") Integer pageCount,
            @RequestParam(defaultValue = "5") Integer pageSize,
            @RequestParam(defaultValue = "ASC") Sort.Direction sortDirection,
            @RequestParam(defaultValue = "date") String sortBy
    ) {

        PageParams pageParams = new PageParams(pageCount, pageSize, sortDirection, sortBy);

        return ResponseEntity.ok(searchMapper.mapToDtoPage(prompt, pageParams));
    }
}
