package com.ann.spending.integration.category.service;

import com.ann.spending.authorization.entity.User;
import com.ann.spending.authorization.repository.UserRepository;
import com.ann.spending.category.CategoryRepository;
import com.ann.spending.category.UserCategoryRepository;
import com.ann.spending.category.view.AddCategoryRequest;
import com.ann.spending.data.AuthDataFactory;
import com.ann.spending.data.CategoryServiceDataFactory;
import com.ann.spending.category.entity.Category;
import com.ann.spending.category.entity.UserCategory;
import com.ann.spending.category.service.CategoryCrudService;
import com.ann.spending.integration.TestBeans;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest(classes = TestBeans.class)
@Transactional
class CategoryCrudServiceTest {

    @Autowired
    CategoryCrudService categoryCrudService;

    @Autowired
    UserCategoryRepository userCategoryRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;

    AddCategoryRequest addCategoryRequest;

    User user;

    @BeforeEach
    void setUp() {
        this.user = AuthDataFactory.createUser();
        this.addCategoryRequest = CategoryServiceDataFactory.createCategoryRequest();
    }

    @Test
    void createCategoryTest() {

        User savedUser = userRepository.save(user);
        categoryCrudService.createCategory(addCategoryRequest, savedUser);

        List<UserCategory> userCategories = userCategoryRepository.findCategoriesByUser(savedUser);

        Assertions.assertFalse(userCategories.isEmpty());
        Assertions.assertEquals("tech", userCategories.get(0).getCategory().getName());
    }

    @Test
    void reorderCategories() {
    }


    @Test
    void deleteCategory() {

        User savedUser = userRepository.save(user);
        Category savedCategory = categoryRepository.save(new Category("tech", "techIcon"));

        UserCategory userCategory = new UserCategory(savedUser, savedCategory);
        userCategoryRepository.save(userCategory);

        categoryCrudService.deleteCategory(savedCategory.getId(), savedUser.getId());

        List<UserCategory> userCategories = userCategoryRepository.findCategoriesByUser(savedUser);
        Assertions.assertTrue(userCategories.isEmpty());
        Assertions.assertFalse(categoryRepository.findAll().isEmpty());

    }
}