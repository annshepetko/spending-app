package com.ann.spending.authorization.service.interfaces;

import com.ann.spending.authorization.dto.AuthenticationResponse;
import com.ann.spending.authorization.entity.User;

public interface AuthenticationResponseBuilder {
    AuthenticationResponse buildResponse(User user);
}
