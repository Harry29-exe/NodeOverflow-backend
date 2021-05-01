package com.kw.nodeimageeditorbackend.exceptions.general;

import com.kw.nodeimageeditorbackend.exceptions.ApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends ApplicationException {

    public BadRequestException() {
        super("Received request is not valid");
    }

    public BadRequestException(String message) {
        super(message);
    }
}
