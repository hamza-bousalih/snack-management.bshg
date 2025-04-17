package org.bshg.demo.exceptions;

import org.bshg.demo.zutils.exception.ApiException;
import org.springframework.http.HttpStatus;

public class NotFoundException extends ApiException {
    public NotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}

