package com.ann.spending.category.mapper;

import com.ann.spending.authorization.entity.User;
import com.ann.spending.category.CategoryRepository;
import com.ann.spending.category.view.CategoryDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryMapper {
    private final CategoryRepository categoryRepository;

    public CategoryMapper(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDTO> findCategoriesByUserId(User user){
        return categoryRepository.findCategoryViewsByUserId(user.getId());
    }
}
