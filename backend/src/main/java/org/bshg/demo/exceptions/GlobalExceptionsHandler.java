package org.bshg.demo.exceptions;

import org.bshg.demo.zutils.exception.ApiError;
import org.bshg.demo.zutils.exception.ApiException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.security.authentication.BadCredentialsException;

@ControllerAdvice
public class GlobalExceptionsHandler {

    @ExceptionHandler(ApiException.class)
    public < T extends ApiException> ResponseEntity< ApiError> handleApiExceptions(T e) {
        return ResponseEntity
            .status(e.getStatus())
            .body(new ApiError(e));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity< ApiError> handleBadCredentialsException(BadCredentialsException e) {
        ApiError apiError = ApiError.status(HttpStatus.BAD_REQUEST).message(e.getMessage());
        return ResponseEntity
            .status(apiError.getStatus())
            .body(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity< ApiError> handleException(Exception e) {
        // e.printStackTrace();
        ApiError body = new ApiError();
        body.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        body.setMessage(e.getMessage());
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(body);
    }

}