package com.ann.spending.unit.registration;

import com.ann.spending.data.AuthDataFactory;
import com.ann.spending.authorization.dto.AuthenticationResponse;
import com.ann.spending.authorization.dto.RegistrationRequest;
import com.ann.spending.authorization.entity.User;
import com.ann.spending.authorization.mapper.UserAuthenticationMapper;
import com.ann.spending.authorization.service.impl.registration.DefaultRegistrationService;
import com.ann.spending.authorization.service.interfaces.AuthenticationResponseBuilder;
import com.ann.spending.authorization.service.repository.UserRepositoryService;
import com.ann.spending.category.entity.UserCategory;
import com.ann.spending.category.service.CategoryDaoService;
import com.ann.spending.exception.user.UserIsAlreadyExist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultRegistrationServiceTest {


    @Mock
    UserRepositoryService userRepositoryService;

    @Mock
    UserAuthenticationMapper userAuthenticationMapper;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    AuthenticationResponseBuilder authResponseBuilder;

    @Mock
    CategoryDaoService categoryService;

    @InjectMocks
    DefaultRegistrationService registrationService;

    RegistrationRequest registrationRequest;


    @BeforeEach
    void setUp() {
        this.registrationRequest = AuthDataFactory.createRegistrationRequest();
    }

    @Test
    void shouldRegisterNewUser_whenUserDoesNotExist() {
        User user = new User("test@example.com", "raw-password");

        List<UserCategory> categories = List.of(new UserCategory());

        when(userAuthenticationMapper.mapToUser(registrationRequest)).thenReturn(user);
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encoded-password");
        when(categoryService.findGeneralCategories(user)).thenReturn(categories);
        when(userRepositoryService.findByEmail("test@example.com")).thenReturn(Optional.empty());
        when(authResponseBuilder.buildResponse(user)).thenReturn(new AuthenticationResponse("token", "refresh token"));

        AuthenticationResponse response = registrationService.register(registrationRequest);

        assertEquals("token", response.accessToken());
        verify(userRepositoryService).save(user);
    }

    @Test
    void shouldThrowException_whenUserAlreadyExists() {
        User user = new User("test@example.com", "raw-password");

        when(userAuthenticationMapper.mapToUser(registrationRequest)).thenReturn(user);
        when(userRepositoryService.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        assertThrows(UserIsAlreadyExist.class, () -> registrationService.register(registrationRequest));
        verify(userRepositoryService, never()).save(any());
        verify(authResponseBuilder, never()).buildResponse(any());
    }
}