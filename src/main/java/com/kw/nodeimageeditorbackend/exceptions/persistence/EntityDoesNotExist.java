package com.kw.nodeimageeditorbackend.exceptions.persistence;

import com.kw.nodeimageeditorbackend.exceptions.ApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityDoesNotExist extends ApplicationException {

    public EntityDoesNotExist() {
        super("Requested entity doesn't exist.");
    }

    public EntityDoesNotExist(String message) {
        super(message);
    }
}
