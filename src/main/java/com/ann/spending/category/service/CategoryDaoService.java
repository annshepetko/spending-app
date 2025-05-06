package com.ann.spending.category.service;

import com.ann.spending.authorization.entity.User;
import com.ann.spending.category.CategoryRepository;
import com.ann.spending.category.entity.Category;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CategoryDaoService {

    private final CategoryRepository categoryRepository;

    public CategoryDaoService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findGeneralCategories(){

        List<String> categoryNames = Arrays.asList("Bills", "Transport", "Mobile", "Shopping", "Insurance", "Voucher", "Internet", "Electricity");

        return categoryRepository.findCategoriesByNameIn(categoryNames);
    }

    public List<Category> findUserCategories(User user){
        return categoryRepository.findCategoriesByUser(user);
    }
}
