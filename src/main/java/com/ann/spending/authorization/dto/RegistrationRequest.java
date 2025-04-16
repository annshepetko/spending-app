package com.ann.spending.authorization.dto;

public record RegistrationRequest (


        String email,
        String username,
        String phoneNumber,
        String password

) {
}
