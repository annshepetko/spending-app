package com.ann.spending.authorization.service.impl.registration;

import com.ann.spending.authorization.dto.AuthenticationResponse;
import com.ann.spending.authorization.dto.RegistrationRequest;
import com.ann.spending.authorization.entity.User;
import com.ann.spending.authorization.mapper.UserAuthenticationMapper;
import com.ann.spending.authorization.service.interfaces.AuthenticationResponseBuilder;
import com.ann.spending.authorization.service.interfaces.RegistrationService;
import com.ann.spending.authorization.service.repository.UserRepositoryService;
import com.ann.spending.jwt.abstraction.JwtTokenGenerator;
import com.ann.spending.repository.BasicCategoryRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefaultRegistrationService implements RegistrationService {

    private final UserRepositoryService userRepositoryService;
    private final UserAuthenticationMapper userAuthenticationMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationResponseBuilder authResponseBuilder;
    private final BasicCategoryRepository basicCategoryRepository;

    public DefaultRegistrationService(
            UserRepositoryService userRepositoryService,
            UserAuthenticationMapper userAuthenticationMapper,
            PasswordEncoder passwordEncoder,
            AuthenticationResponseBuilder authResponseBuilder,
            BasicCategoryRepository basicCategoryRepository
    ) {
        this.authResponseBuilder = authResponseBuilder;
        this.passwordEncoder = passwordEncoder;
        this.userRepositoryService = userRepositoryService;
        this.userAuthenticationMapper = userAuthenticationMapper;
        this.basicCategoryRepository = basicCategoryRepository;
    }

    @Override
    @Transactional
    public AuthenticationResponse register(RegistrationRequest registrationRequest) {
        User user = createUserFromRegistrationRequest(registrationRequest);
        user.setBasicCategories(basicCategoryRepository.findAll());
        userRepositoryService.save(user);

        return authResponseBuilder.buildResponse(user);
    }

    private User createUserFromRegistrationRequest(RegistrationRequest registrationRequest) {
        User user = userAuthenticationMapper.mapToUser(registrationRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return user;
    }



}
