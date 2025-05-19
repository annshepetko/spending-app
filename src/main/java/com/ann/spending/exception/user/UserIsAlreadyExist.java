package com.ann.spending.exception.user;

public class UserIsAlreadyExist extends RuntimeException {
    public UserIsAlreadyExist(String message) {
        super(message);
    }
}
