package com.ann.spending.exception.user;

public class UserIsNotRegisteredException extends RuntimeException {
    public UserIsNotRegisteredException(String message) {
        super(message);
    }
}
