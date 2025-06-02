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
import com.ann.spending.user.UserCrudService;
import com.ann.spending.user.service.validator.PasswordEncodeManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefaultRegistrationService implements RegistrationService {

    private final UserAuthenticationMapper userAuthenticationMapper;
    private final PasswordEncodeManager passwordEncoder;
    private final AuthenticationResponseBuilder authResponseBuilder;
    private final CategoryDaoService categoryService;
    private final UserCrudService userCrudService;
    private final UserRepositoryService userRepositoryService;

    public DefaultRegistrationService(
            UserAuthenticationMapper userAuthenticationMapper,
            PasswordEncodeManager passwordEncoder,
            AuthenticationResponseBuilder authResponseBuilder,
            CategoryDaoService categoryService,
            UserCrudService userCrudService,
            UserRepositoryService userRepositoryService) {
        this.authResponseBuilder = authResponseBuilder;
        this.passwordEncoder = passwordEncoder;
        this.userAuthenticationMapper = userAuthenticationMapper;
        this.categoryService = categoryService;
        this.userCrudService = userCrudService;
        this.userRepositoryService = userRepositoryService;
    }

    @Override
    @Transactional
    public AuthenticationResponse register(RegistrationRequest registrationRequest) {

        User user = createUserFromRegistrationRequest(registrationRequest);

        if (isUserAlreadyExist(user)) {
            throw new UserIsAlreadyExist("You are already registered");
        }

        userCrudService.createUser(user);
        return authResponseBuilder.buildResponse(user);
    }

    private User createUserFromRegistrationRequest(RegistrationRequest registrationRequest) {

        User user = userAuthenticationMapper.mapToUser(registrationRequest);
        user.setPassword(passwordEncoder.encodeString(user.getPassword()));
        user.setCategories(categoryService.findGeneralCategories(user));

        return user;
    }

    private boolean isUserAlreadyExist(User user) {
        return userRepositoryService.isUserAlreadyExist(user);
    }


}
