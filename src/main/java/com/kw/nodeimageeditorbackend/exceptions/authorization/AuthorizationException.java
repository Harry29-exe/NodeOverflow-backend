package com.kw.nodeimageeditorbackend.exceptions.authorization;

import com.kw.nodeimageeditorbackend.exceptions.ApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class AuthorizationException extends ApplicationException {

    public AuthorizationException() {
        super("You have no right to do this");
    }

    public AuthorizationException(String message) {
        super(message);
    }
}
