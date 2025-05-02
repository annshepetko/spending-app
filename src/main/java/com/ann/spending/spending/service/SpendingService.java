package com.ann.spending.spending.service;

import com.ann.spending.category.CategoryRepository;
import com.ann.spending.category.CustomCategoryRepository;
import com.ann.spending.spending.SpendingRepository;
import com.ann.spending.spending.dto.CreateSpendingBody;
import org.springframework.stereotype.Service;

@Service
public class SpendingService {

    private final CategoryRepository categoryRepository;
    private final CustomCategoryRepository customCategoryRepository;
    private final SpendingRepository spendingRepository;

    public SpendingService(
            CategoryRepository categoryRepository,
            CustomCategoryRepository customCategoryRepository,
            SpendingRepository spendingRepository
    ) {
        this.categoryRepository = categoryRepository;
        this.customCategoryRepository = customCategoryRepository;
        this.spendingRepository = spendingRepository;
    }

    public void createSpending(CreateSpendingBody createSpendingBody) {



    }
}
