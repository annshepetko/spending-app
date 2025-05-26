package com.ann.spending.integration.user;

import com.ann.spending.authorization.entity.User;
import com.ann.spending.authorization.service.repository.UserRepositoryService;
import com.ann.spending.data.AuthDataFactory;
import com.ann.spending.integration.TestBeans;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest(classes = TestBeans.class)
@Transactional
class UserRepositoryTest {

    @Autowired
    UserRepositoryService userRepositoryService;


    @Test
    void findByEmail() {

        User user = AuthDataFactory.createUser();

        userRepositoryService.save(user);

        User foundedUser = userRepositoryService.findByEmailIfPresent(user.getEmail());

        Assertions.assertEquals(foundedUser.getEmail(), user.getEmail());
        Assertions.assertEquals(foundedUser.getName(), user.getName());

    }

}