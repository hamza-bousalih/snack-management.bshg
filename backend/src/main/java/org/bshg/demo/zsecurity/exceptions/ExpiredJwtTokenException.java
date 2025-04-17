package org.bshg.demo.zsecurity.exceptions;

import org.bshg.demo.zutils.exception.ApiException;
import org.springframework.http.HttpStatus;

public class ExpiredJwtTokenException extends ApiException {
    public ExpiredJwtTokenException() {
        super("Your session has been expired!", HttpStatus.BAD_REQUEST);
    }
}