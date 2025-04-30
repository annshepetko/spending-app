package com.ann.spending.authorization.controller;

import com.ann.spending.authorization.cookie.facrory.CookieFactory;
import com.ann.spending.authorization.dto.AuthenticationRequest;
import com.ann.spending.authorization.dto.AuthenticationResponse;
import com.ann.spending.authorization.dto.RegistrationRequest;
import com.ann.spending.authorization.service.interfaces.AuthenticationService;
import com.ann.spending.authorization.service.interfaces.RegistrationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final CookieFactory<String> authCookieFactory;
    private final AuthenticationService authenticationService;
    private final RegistrationService registrationService;

    public AuthenticationController(
            @Qualifier("cookieAuthFactory")
            CookieFactory<String> authCookieFactory,
            AuthenticationService authenticationService,
            RegistrationService registrationService
    ) {
        this.authCookieFactory = authCookieFactory;
        this.authenticationService = authenticationService;
        this.registrationService = registrationService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegistrationRequest request,
            HttpServletResponse httpResponse
    ){

        AuthenticationResponse authResponse = registrationService.register(request);

        addRefreshCookie(httpResponse, authResponse);

        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest authRequest,
            HttpServletResponse httpResponse
    )
    {
        AuthenticationResponse authResponse = authenticationService.authenticate(authRequest);

        addRefreshCookie(httpResponse, authResponse);

        return ResponseEntity.ok(authResponse);
    }


    private void addRefreshCookie(HttpServletResponse httpResponse, AuthenticationResponse authResponse) {
        Cookie refreshTokenCookie = authCookieFactory.createCookie(authResponse.refreshToken());
        httpResponse.addCookie(refreshTokenCookie);
    }

}
