package com.ann.spending.authorization.service.interfaces;

import com.ann.spending.authorization.dto.AuthenticationResponse;
import com.ann.spending.authorization.dto.RegistrationRequest;

public interface RegistrationService {
    AuthenticationResponse register(RegistrationRequest request);
}

