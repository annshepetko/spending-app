package com.ann.spending.category.service;

import com.ann.spending.authorization.entity.User;
import com.ann.spending.category.entity.Category;
import com.ann.spending.category.view.CategoryDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryCrudService {

    private final CategoryDaoService categoryDaoService;

    public CategoryCrudService(CategoryDaoService categoryDaoService) {
        this.categoryDaoService = categoryDaoService;
    }

    public void reorderCategories(User user, List<CategoryDTO> categoryDTOS){
        List<Category> categories = categoryDaoService.findUserCategories(user);


    }
}
