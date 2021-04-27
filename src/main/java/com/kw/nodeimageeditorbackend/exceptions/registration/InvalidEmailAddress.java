package com.kw.nodeimageeditorbackend.exceptions.registration;

import com.kw.nodeimageeditorbackend.exceptions.ApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class InvalidEmailAddress extends ApplicationException {

    public InvalidEmailAddress() {
        super("Given email address is invalid");
    }

    public InvalidEmailAddress(String message) {
        super(message);
    }
}
