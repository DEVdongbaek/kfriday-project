package com.kfriday.kevin.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import com.kfriday.kevin.common.ApiResponse;
import com.kfriday.kevin.exception.customException.DuplicatedPackageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = {"com.kfriday.kevin"})
public class RestExceptionHandler {

    @ExceptionHandler(DuplicatedPackageException.class)
    public ResponseEntity<ApiResponse<?>> handleDuplicatedUserException(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResponse.createError(exception.getMessage()));
    }

}
