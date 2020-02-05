package com.officenotes.controllers.advices;

import com.officenotes.exceptions.UserAlreadyTakenException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class UserAlreadyTakenAdvice {

    @ExceptionHandler(UserAlreadyTakenException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    String userAlreadyTakenHandler(UserAlreadyTakenException ex) {
        return ex.getMessage();
    }
}
