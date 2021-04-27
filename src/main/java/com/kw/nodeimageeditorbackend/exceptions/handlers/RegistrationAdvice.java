package com.kw.nodeimageeditorbackend.exceptions.handlers;

import com.kw.nodeimageeditorbackend.exceptions.registration.InvalidEmailAddress;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RegistrationAdvice {

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ResponseBody
    @ExceptionHandler(InvalidEmailAddress.class)
    public String handleInvalidEmailAddress(InvalidEmailAddress ex) {
        return ex.getMessage();
    }
}
