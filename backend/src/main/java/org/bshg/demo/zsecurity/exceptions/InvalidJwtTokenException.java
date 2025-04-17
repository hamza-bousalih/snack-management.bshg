package org.bshg.demo.zsecurity.exceptions;

import org.bshg.demo.zutils.exception.ApiException;
import org.springframework.http.HttpStatus;

public class InvalidJwtTokenException extends ApiException {
    public InvalidJwtTokenException() {
        super("Invalid JWT token!", HttpStatus.BAD_REQUEST);
    }
}
