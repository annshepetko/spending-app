package com.ann.spending.authorization.controller;

import com.ann.spending.authorization.dto.AuthenticationRequest;
import com.ann.spending.authorization.dto.AuthenticationResponse;
import com.ann.spending.authorization.dto.RegistrationRequest;
import com.ann.spending.authorization.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegistrationRequest registrationRequest){
        AuthenticationResponse authResponse = authenticationService.register(registrationRequest);

        ResponseEntity<AuthenticationResponse> response = ResponseEntity.ok(authResponse);
        return response;
    }


    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authRequest){
        AuthenticationResponse authResponse = authenticationService.authenticate(authRequest);

        ResponseEntity<AuthenticationResponse> response = ResponseEntity.ok(authResponse);
        return response;
    }
}
