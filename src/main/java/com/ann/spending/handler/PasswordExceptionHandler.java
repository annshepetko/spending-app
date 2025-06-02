package com.ann.spending.handler;

import com.ann.spending.exception.user.PasswordDoesNotMatchException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PasswordExceptionHandler {


    @ExceptionHandler(PasswordDoesNotMatchException.class)
    public ResponseEntity<String> handlePasswordException(PasswordDoesNotMatchException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

}
