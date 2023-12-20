package com.workintech.s17friday.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalErrorHandler {

    @ExceptionHandler
    public ResponseEntity<ApiErrorResponse> handleException(ApiException apiException) {
        log.error("Error occurred here details: " + apiException.getMessage());
        ApiErrorResponse newError = new ApiErrorResponse(apiException.getHttpStatus().value(), apiException.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(newError, apiException.getHttpStatus());
    }

    @ExceptionHandler
    public ResponseEntity<ApiErrorResponse> handleException(Exception exception) {
        log.error("Error occurred here details: " + exception.getMessage());
        ApiErrorResponse newError = new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(newError, HttpStatus.BAD_REQUEST);
    }
}
