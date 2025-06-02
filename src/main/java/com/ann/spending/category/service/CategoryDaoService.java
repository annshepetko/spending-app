package com.ann.spending.category.service;

import com.ann.spending.authorization.entity.User;
import com.ann.spending.category.CategoryRepository;
import com.ann.spending.category.UserCategoryRepository;
import com.ann.spending.category.entity.Category;
import com.ann.spending.category.entity.UserCategory;
import com.ann.spending.category.mapper.UserCategoryMapper;
import com.ann.spending.category.view.CategoryDTO;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CategoryDaoService {

    private final UserCategoryRepository userCategoryRepository;
    private final CategoryRepository categoryRepository;
    private final UserCategoryMapper userCategoryMapper;

    public CategoryDaoService(UserCategoryRepository userCategoryRepository, CategoryRepository categoryRepository, UserCategoryMapper userCategoryMapper) {
        this.userCategoryRepository = userCategoryRepository;
        this.categoryRepository = categoryRepository;
        this.userCategoryMapper = userCategoryMapper;
    }

    public List<UserCategory> findGeneralCategories(User user){

        List<String> categoryNames = Arrays.asList("Bills", "Transport", "Mobile", "Shopping", "Insurance", "Voucher", "Internet", "Electricity");
        List<Category> categories = categoryRepository.findCategoriesByNameIn(categoryNames);

        return userCategoryMapper.buildUserCategories(categories, user);
    }

    public List<CategoryDTO> findUserCategories(User user){
        return userCategoryRepository.findCategoryViewsByUserId(user.getId());
    }
}
