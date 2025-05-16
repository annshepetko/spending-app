package com.ann.spending.authorization.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthenticationResponse(
        String accessToken,
        String helloMessage,
        @JsonIgnore
        String refreshToken
) {
}
