package com.kw.nodeimageeditorbackend.exceptions.persistence;

import com.kw.nodeimageeditorbackend.exceptions.ApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotExistException extends ApplicationException {

    public EntityNotExistException() {
        super("Requested entity doesn't exist.");
    }

    public EntityNotExistException(String message) {
        super(message);
    }
}
