package com.ann.spending.unit.authentication;

import com.ann.spending.data.AuthDataFactory;
import com.ann.spending.authorization.service.impl.authentication.DefaultAuthenticationServiceImpl;
import com.ann.spending.authorization.service.interfaces.AuthenticationResponseBuilder;
import com.ann.spending.authorization.service.repository.UserRepositoryService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;

import static org.junit.jupiter.api.Assertions.*;

import com.ann.spending.authorization.dto.AuthenticationRequest;
import com.ann.spending.authorization.dto.AuthenticationResponse;
import com.ann.spending.authorization.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultAuthenticationServiceImplTest {

    @Mock
    UserRepositoryService userRepositoryService;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    AuthenticationResponseBuilder authResponseBuilder;

    @InjectMocks
    DefaultAuthenticationServiceImpl defaultAuthenticationService;

    AuthenticationRequest authenticationRequest;
    User user;

    @BeforeEach
    void setUp() {
        authenticationRequest = AuthDataFactory.createAuthenticationRequest();
        user = AuthDataFactory.createUser();
    }

    @Test
    void shouldAuthenticateUserSuccessfully() {
        // given
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                authenticationRequest.email(),
                authenticationRequest.password()
        );

        AuthenticationResponse expectedResponse = new AuthenticationResponse("access-token", "refresh-token");

        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(userRepositoryService.findByEmailIfPresent("test@example.com")).thenReturn(user);
        when(authResponseBuilder.buildResponse(user)).thenReturn(expectedResponse);

        // when
        AuthenticationResponse actualResponse = defaultAuthenticationService.authenticate(authenticationRequest);

        // then
        assertNotNull(actualResponse);
        assertEquals("access-token", actualResponse.accessToken());
        assertEquals("refresh-token", actualResponse.refreshToken());

        verify(authenticationManager).authenticate(any());
        verify(userRepositoryService).findByEmailIfPresent("test@example.com");
        verify(authResponseBuilder).buildResponse(user);
    }
}
