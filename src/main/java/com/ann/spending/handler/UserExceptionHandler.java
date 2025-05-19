package com.ann.spending.handler;

import com.ann.spending.exception.user.UserIsAlreadyExist;
import com.ann.spending.exception.user.UserIsNotRegisteredException;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Hidden
public class UserExceptionHandler {

    @ExceptionHandler(UserIsNotRegisteredException.class)
    public ResponseEntity<String> handleException(UserIsNotRegisteredException ex) {

        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(UserIsAlreadyExist.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<String> handleUserExistsException(UserIsAlreadyExist ex) {

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

}
