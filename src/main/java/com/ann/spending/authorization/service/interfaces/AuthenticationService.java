package com.ann.spending.authorization.service.interfaces;

import com.ann.spending.authorization.dto.AuthenticationRequest;
import com.ann.spending.authorization.dto.AuthenticationResponse;

public interface AuthenticationService<T> {
    AuthenticationResponse authenticate(T request);
}
