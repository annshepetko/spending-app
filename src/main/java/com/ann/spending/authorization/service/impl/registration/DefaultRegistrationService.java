package com.ann.spending.authorization.service.impl.registration;

import com.ann.spending.authorization.dto.AuthenticationResponse;
import com.ann.spending.authorization.dto.RegistrationRequest;
import com.ann.spending.authorization.entity.User;
import com.ann.spending.authorization.mapper.UserAuthenticationMapper;
import com.ann.spending.authorization.service.interfaces.AuthenticationResponseBuilder;
import com.ann.spending.authorization.service.interfaces.RegistrationService;
import com.ann.spending.authorization.service.repository.UserRepositoryService;
import com.ann.spending.category.service.CategoryDaoService;
import com.ann.spending.exception.user.UserIsAlreadyExist;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefaultRegistrationService implements RegistrationService {

    private final UserRepositoryService userRepositoryService;
    private final UserAuthenticationMapper userAuthenticationMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationResponseBuilder authResponseBuilder;
    private final CategoryDaoService categoryService;

    public DefaultRegistrationService(
            UserRepositoryService userRepositoryService,
            UserAuthenticationMapper userAuthenticationMapper,
            PasswordEncoder passwordEncoder,
            AuthenticationResponseBuilder authResponseBuilder,
            CategoryDaoService categoryService ) {
        this.authResponseBuilder = authResponseBuilder;
        this.passwordEncoder = passwordEncoder;
        this.userRepositoryService = userRepositoryService;
        this.userAuthenticationMapper = userAuthenticationMapper;
        this.categoryService = categoryService;
    }

    @Override
    @Transactional
    public AuthenticationResponse register(RegistrationRequest registrationRequest) {

        User user = createUserFromRegistrationRequest(registrationRequest);

        if (isUserAlreadyExist(user)) {
            throw new UserIsAlreadyExist("You are already registered");
        }
        userRepositoryService.save(user);

        return authResponseBuilder.buildResponse(user);
    }

    private boolean isUserAlreadyExist(User user) {
        return userRepositoryService.findByEmail(user.getEmail()).isPresent();
    }

    private User createUserFromRegistrationRequest(RegistrationRequest registrationRequest) {
        User user = userAuthenticationMapper.mapToUser(registrationRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCategories(categoryService.findGeneralCategories(user));
        return user;
    }
}
