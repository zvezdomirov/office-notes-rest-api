package com.officenotes.controllers.advices;

import com.officenotes.exceptions.InvalidUserRoleException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class InvalidUserRoleAdvice {

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String invalidUserRoleHandler(InvalidUserRoleException ex) {
        return ex.getMessage();
    }
}
