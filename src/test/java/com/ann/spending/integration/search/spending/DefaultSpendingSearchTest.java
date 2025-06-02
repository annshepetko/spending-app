package com.ann.spending.integration.search.spending;


import com.ann.spending.authorization.entity.User;
import com.ann.spending.authorization.repository.UserRepository;
import com.ann.spending.category.CategoryRepository;
import com.ann.spending.category.UserCategoryRepository;
import com.ann.spending.category.entity.Category;
import com.ann.spending.category.entity.UserCategory;
import com.ann.spending.category.mapper.CategoryMapper;
import com.ann.spending.category.mapper.UserCategoryMapper;
import com.ann.spending.data.AuthDataFactory;
import com.ann.spending.data.CategoryServiceDataFactory;
import com.ann.spending.data.SpendingDataFactory;
import com.ann.spending.filter.page.PageParams;
import com.ann.spending.integration.TestBeans;
import com.ann.spending.search.spending.impl.DefaultSpendingSearchService;
import com.ann.spending.spending.SpendingRepository;
import com.ann.spending.spending.entity.Spending;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest(classes = TestBeans.class)
@Transactional
public class DefaultSpendingSearchTest {

    @Autowired
    UserCategoryRepository userCategoryRepository;

    @Autowired
    UserCategoryMapper categoryMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SpendingRepository spendingRepository;

    @Autowired
    DefaultSpendingSearchService searchService;

    User user;

    List<Spending> spending;

    Category category;

    PageParams pageParams = new PageParams();

    @BeforeEach
    void setUp() {
        user = AuthDataFactory.createUser();

        category = CategoryServiceDataFactory.createCategory();

        UserCategory userCategory = categoryMapper.buldUserCategory(category, user);
        user.addCategory(userCategory);

        spending = SpendingDataFactory.createSpendings(user, category);
        spending.forEach(user::addSpending);


        user = userRepository.save(this.user);
        pageParams.setSortBy("date");
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void testSearchSpending() {

        List<String> prompts = spending.stream().map(Spending::getDescription).toList();

        Page<Spending> searchResult = searchService.findByFilter(prompts, pageParams);

        Assertions.assertNotNull(searchResult);
        Assertions.assertFalse(searchResult.isEmpty());
        Assertions.assertEquals(prompts.size(), searchResult.getTotalElements());

    }

    @Test
    void testSearchSpendingWithNoMatchers() {
        List<String> prompts = List.of("invalid", "credentials");

        Page<Spending> notFoundedSpending = searchService.findByFilter(prompts, pageParams);


        Assertions.assertTrue(notFoundedSpending.isEmpty());
    }
}
