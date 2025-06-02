package com.ann.spending.category.mapper;

import com.ann.spending.authorization.entity.User;
import com.ann.spending.category.entity.Category;
import com.ann.spending.category.entity.UserCategory;
import com.ann.spending.category.entity.UserCategoryId;
import com.ann.spending.category.sequence.SequenceRepository;
import com.ann.spending.category.view.AddCategoryRequest;
import com.ann.spending.category.view.CategoryDTO;
import com.ann.spending.category.view.PatchCategoryRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class CategoryMapper {

    public Category prepareCategory(AddCategoryRequest addCategoryRequest) {

        Category category = new Category();
        category.setName(addCategoryRequest.name());
        category.setIconName(addCategoryRequest.iconName());

        return category;
    }

}
