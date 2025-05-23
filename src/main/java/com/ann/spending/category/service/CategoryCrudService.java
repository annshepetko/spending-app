package com.ann.spending.category.service;

import com.ann.spending.authorization.entity.User;
import com.ann.spending.category.CategoryRepository;
import com.ann.spending.category.UserCategoryRepository;
import com.ann.spending.category.entity.Category;
import com.ann.spending.category.entity.UserCategory;
import com.ann.spending.category.entity.UserCategoryId;
import com.ann.spending.category.mapper.CategoryMapper;
import com.ann.spending.category.view.CategoryDTO;
import com.ann.spending.category.view.PatchCategoryRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryCrudService {

    private final CategoryMapper categoryMapper;
    private final UserCategoryRepository userCategoryRepository;

    public CategoryCrudService(CategoryMapper categoryMapper, UserCategoryRepository categoryRepository) {
        this.categoryMapper = categoryMapper;
        this.userCategoryRepository = categoryRepository;
    }

    @Transactional
    public void reorderCategories(User user, List<CategoryDTO> categoryDTOS){

        List<UserCategory> categories = categoryMapper.mapToUserCategoryList(categoryDTOS, user);

        userCategoryRepository.saveAll(categories);
    }

    @Transactional
    public void createCategory(String name, User user){

        Category category = prepareCategory(name);

        UserCategory userCategory = categoryMapper.buldUserCategory(category, user);
        userCategoryRepository.save(userCategory);
    }

    private Category prepareCategory(String name) {

        Category category = new Category();
        category.setName(name);

        return category;
    }

    public void deleteCategory(Long id, Long userId){
        userCategoryRepository.deleteById(new UserCategoryId(userId, id));
    }

}
