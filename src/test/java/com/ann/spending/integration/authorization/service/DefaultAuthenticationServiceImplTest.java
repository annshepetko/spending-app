package com.ann.spending.integration.authorization.service;

import com.ann.spending.data.AuthDataFactory;
import com.ann.spending.authorization.dto.AuthenticationRequest;
import com.ann.spending.authorization.dto.RegistrationRequest;
import com.ann.spending.authorization.service.impl.authentication.DefaultAuthenticationServiceImpl;
import com.ann.spending.authorization.service.impl.registration.DefaultRegistrationService;
import com.ann.spending.integration.TestBeans;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.AuthenticationException;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes = TestBeans.class)
@Transactional
class DefaultAuthenticationServiceImplTest {

    @Autowired
    DefaultAuthenticationServiceImpl authService;

    @Autowired
    DefaultRegistrationService defaultRegistrationService;

    AuthenticationRequest authenticationRequest;
    RegistrationRequest registrationRequest;

    @BeforeEach
    void setUp() {
        this.authenticationRequest = AuthDataFactory.createAuthenticationRequest();
        this.registrationRequest = AuthDataFactory.createRegistrationRequest();
    }

    @Test
    void authenticate() {
        defaultRegistrationService.register(registrationRequest);

        authService.authenticate(authenticationRequest);
    }

    @Test
    void authenticateWithWrongCredentials() {
        defaultRegistrationService.register(registrationRequest);

        Assertions.assertThrows(AuthenticationException.class, () -> authService.authenticate(new AuthenticationRequest("Wrong email", "wrong password")));
    }
}