package com.kw.nodeimageeditorbackend.exceptions.authorization;

import com.kw.nodeimageeditorbackend.exceptions.ApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class BadCredentialsException extends ApplicationException {

    public BadCredentialsException() {
        super("Passed password is incorrect");
    }

    public BadCredentialsException(String message) {
        super(message);
    }
}
