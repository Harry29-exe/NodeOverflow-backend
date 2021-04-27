package com.kw.nodeimageeditorbackend.exceptions.authorization;

import com.kw.nodeimageeditorbackend.exceptions.ApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends ApplicationException {

    public UserNotFoundException() {
        super("Not found user with given credentials");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
