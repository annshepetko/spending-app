package com.ann.spending.integration.spending.service;

import com.ann.spending.authorization.entity.User;
import com.ann.spending.authorization.repository.UserRepository;
import com.ann.spending.category.CategoryRepository;
import com.ann.spending.category.UserCategoryRepository;
import com.ann.spending.category.entity.Category;
import com.ann.spending.data.AuthDataFactory;
import com.ann.spending.data.CategoryServiceDataFactory;
import com.ann.spending.integration.TestBeans;
import com.ann.spending.data.SpendingDataFactory;
import com.ann.spending.spending.SpendingRepository;
import com.ann.spending.spending.dto.CreateSpendingBody;
import com.ann.spending.spending.entity.Spending;
import com.ann.spending.spending.service.SpendingCrudServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@SpringBootTest(classes = TestBeans.class)
@Transactional
class SpendingCrudServiceImplTest {

    @Autowired
    UserCategoryRepository userCategoryRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    SpendingCrudServiceImpl spendingService;

    @Autowired
    SpendingRepository spendingRepository;

    User user;

    Category category;

    CreateSpendingBody createSpendingBody;

    @BeforeEach
    void setUp() {
        user = AuthDataFactory.createUser();

        Category category = CategoryServiceDataFactory.createCategory();
        this.category = categoryRepository.save(category);
        this.createSpendingBody = SpendingDataFactory.createSpendingBody(category.getId(), user);
        user = userRepository.save(this.user);
    }

    @Test
    void createSpending() {

        spendingService.createSpending(createSpendingBody);

        User refreshedUser = userRepository.findByEmail(user.getEmail()).get();


        Spending spending = spendingRepository.findAll().get(0);

        Assertions.assertFalse(refreshedUser.getSpending().isEmpty());
        Assertions.assertFalse(spendingRepository.findAll().isEmpty());
        Assertions.assertEquals(spending.getCategory().getId(), category.getId());
        Assertions.assertEquals(spending.getAmount(), createSpendingBody.amount());
    }

    @Test
    void deleteSpending() {

        user = userRepository.save(this.user);

        Spending spending = new Spending();
        spending.setAmount(BigDecimal.TEN);
        spending.setUser(user);
        spending.setCategory(category);
        spending.setDate(LocalDateTime.now());

        Spending savedSpending = spendingRepository.save(spending);

        spendingService.deleteSpending(savedSpending.getId());


        User userWithNoSpending = userRepository.findByEmail(user.getEmail()).get();

        Assertions.assertTrue(userWithNoSpending.getSpending().isEmpty());
        Assertions.assertFalse(spendingRepository.existsById(savedSpending.getId()));
    }

    @Test
    void update() {


    }
}