package com.ann.spending.authorization.service;

import com.ann.spending.authorization.dto.AuthenticationRequest;
import com.ann.spending.authorization.dto.AuthenticationResponse;
import com.ann.spending.authorization.dto.RegistrationRequest;
import com.ann.spending.authorization.entity.User;
import com.ann.spending.authorization.jwt.JwtService;
import com.ann.spending.authorization.mapper.UserAuthenticationMapper;
import com.ann.spending.authorization.service.repository.UserRepositoryService;
import com.ann.spending.repository.BasicCategoryRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Service
public class AuthenticationService {

    private final UserRepositoryService userRepositoryService;
    private final UserAuthenticationMapper userAuthenticationMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final BasicCategoryRepository basicCategoryRepository;

    public AuthenticationService(
            UserRepositoryService userRepositoryService,
            UserAuthenticationMapper userAuthenticationMapper,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager,
            BasicCategoryRepository basicCategoryRepository) {
        this.userRepositoryService = userRepositoryService;
        this.userAuthenticationMapper = userAuthenticationMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.basicCategoryRepository = basicCategoryRepository;
    }

    @Transactional
    public AuthenticationResponse register(RegistrationRequest registrationRequest) {
        User user = createUserFromRegistrationRequest(registrationRequest);
        user.setBasicCategories(basicCategoryRepository.findAll());
        userRepositoryService.save(user);

        return buildAuthenticationResponse(user);
    }

    private User createUserFromRegistrationRequest(RegistrationRequest registrationRequest) {
        User user = userAuthenticationMapper.mapToUser(registrationRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return user;
    }


    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        Authentication authentication = authenticateUser(authenticationRequest);
        User user = userRepositoryService.findByEmail(authentication.getName());
        return buildAuthenticationResponse(user);
    }

    private Authentication authenticateUser(AuthenticationRequest authenticationRequest) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.email(),
                        authenticationRequest.password()
                )
        );
    }

    private AuthenticationResponse buildAuthenticationResponse(User user) {
        String jwtToken = jwtService.generateAccessToken(new HashMap<>(), user);
        return new AuthenticationResponse(jwtToken);
    }
}
