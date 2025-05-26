package com.ann.spending.integration.authorization.service;

import com.ann.spending.data.AuthDataFactory;
import com.ann.spending.authorization.dto.AuthenticationResponse;
import com.ann.spending.authorization.dto.RegistrationRequest;
import com.ann.spending.authorization.repository.UserRepository;
import com.ann.spending.authorization.service.impl.registration.DefaultRegistrationService;
import com.ann.spending.exception.user.UserIsAlreadyExist;
import com.ann.spending.integration.TestBeans;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = TestBeans.class)
@Transactional
class DefaultRegistrationServiceIntegrateTest {

    @Autowired
    DefaultRegistrationService defaultRegistrationService;

    @Autowired
    UserRepository userRepository;

    RegistrationRequest registrationRequest;

    @BeforeEach
    void setUp() {
        this.registrationRequest = AuthDataFactory.createRegistrationRequest();
    }

    @Test
    void testRegistrationIfUserDoesNotRegisteredYet() {

        AuthenticationResponse authenticationResponse = defaultRegistrationService.register(registrationRequest);

        Assertions.assertNotNull(authenticationResponse);

        Boolean isUserRegistered = userRepository.findByEmail(registrationRequest.email()).isPresent();

        Assertions.assertTrue(isUserRegistered);
    }


    @Test
    void testRegistrationIfUserIsAlreadyRegistered() {

        defaultRegistrationService.register(registrationRequest);

        assertThrows(UserIsAlreadyExist.class, () -> defaultRegistrationService.register(registrationRequest));

    }
}