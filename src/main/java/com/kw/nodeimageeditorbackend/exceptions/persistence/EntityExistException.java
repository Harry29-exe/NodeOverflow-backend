package com.kw.nodeimageeditorbackend.exceptions.persistence;

import com.kw.nodeimageeditorbackend.exceptions.ApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EntityExistException extends ApplicationException {

    public EntityExistException() {
    }

    public EntityExistException(String message) {
        super(message);
    }

}
