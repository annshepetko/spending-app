package com.ann.spending.integration.user;

import com.ann.spending.authorization.entity.User;
import com.ann.spending.authorization.repository.UserRepository;
import com.ann.spending.data.AuthDataFactory;
import com.ann.spending.exception.user.PasswordDoesNotMatchException;
import com.ann.spending.integration.TestBeans;
import com.ann.spending.user.dto.ChangePasswordRequest;
import com.ann.spending.user.service.ChangePasswordService;
import com.ann.spending.user.service.validator.PasswordEncodeManager;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = TestBeans.class)
public class UserPasswordTest {

    @Autowired
    ChangePasswordService changePasswordService;

    @Autowired
    PasswordEncodeManager passwordEncodeManager;

    @Autowired
    UserRepository userRepository;


    ChangePasswordRequest passwordRequest;

    User user;

    @BeforeEach
    void setUp() {

        passwordRequest = new ChangePasswordRequest("testPassword", "new-Password");
        user = AuthDataFactory.createUser();
        user.setPassword(passwordEncodeManager.encodeString("testPassword"));


    }

    @AfterEach
    void afterEach() {

        userRepository.deleteAll();

    }

    @Test
    void testPasswordChange() {

        User currentUser = userRepository.save(user);

        changePasswordService.changePassword(currentUser, passwordRequest);

        User updatedUser = userRepository.findByEmail(user.getEmail()).get();

        Assertions.assertNotEquals(updatedUser.getPassword(), currentUser.getPassword());
    }

    @Test
    void testPasswordChangeWithWrongCredentials() {
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest("wrong password", "new password");
        User currentUser = userRepository.save(user);

        Assertions.assertThrows(PasswordDoesNotMatchException.class, () -> changePasswordService.changePassword(currentUser, changePasswordRequest));


    }
}
