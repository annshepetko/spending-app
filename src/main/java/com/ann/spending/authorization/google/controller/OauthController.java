package com.ann.spending.authorization.google.controller;

import com.ann.spending.authorization.cookie.facrory.CookieFactory;
import com.ann.spending.authorization.cookie.impl.CookieAuthFactory;
import com.ann.spending.authorization.dto.AuthenticationResponse;
import com.ann.spending.authorization.dto.GoogleAuthToken;
import com.ann.spending.authorization.google.service.GoogleTokenFilter;
import com.ann.spending.authorization.service.interfaces.AuthenticationService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/oauth/v1")
public class OauthController {

    private final CookieFactory<String> authCookieFactory;
    private final AuthenticationService<GoogleIdToken.Payload> googleAuthService;
    private final GoogleTokenFilter googleTokenFilter;

    public OauthController(CookieAuthFactory authCookieFactory, AuthenticationService<GoogleIdToken.Payload> googleAuthService, GoogleTokenFilter googleTokenFilter) {
        this.authCookieFactory = authCookieFactory;
        this.googleAuthService = googleAuthService;
        this.googleTokenFilter = googleTokenFilter;

    }

    @PostMapping("/authorize")
    public ResponseEntity<AuthenticationResponse> authenticateViaGoogle(@RequestBody GoogleAuthToken googleAuthToken, HttpServletResponse httpServletResponse) {
        GoogleIdToken.Payload payload = googleTokenFilter.handlePayload(googleAuthToken);

        AuthenticationResponse authResponse = googleAuthService.authenticate(payload);
        addRefreshCookie(httpServletResponse, authResponse);

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    private void addRefreshCookie(HttpServletResponse httpResponse, AuthenticationResponse authResponse) {
        Cookie refreshTokenCookie = authCookieFactory.createCookie(authResponse.refreshToken());
        httpResponse.addCookie(refreshTokenCookie);
    }
}
