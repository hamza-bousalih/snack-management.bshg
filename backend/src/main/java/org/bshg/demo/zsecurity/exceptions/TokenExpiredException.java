package org.bshg.demo.zsecurity.exceptions;

import org.bshg.demo.zutils.exception.ApiException;
import org.springframework.http.HttpStatus;

public class TokenExpiredException extends ApiException {
    public TokenExpiredException() {
        super("Token has expired! new token has been sent.", HttpStatus.BAD_REQUEST);
    }
}
