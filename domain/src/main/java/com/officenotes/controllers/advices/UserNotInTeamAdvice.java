package com.officenotes.controllers.advices;

import com.officenotes.exceptions.UserNotInTeamException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class UserNotInTeamAdvice {

    @ExceptionHandler(UserNotInTeamException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String userNotInTeamHandler(UserNotInTeamException ex) {
        return ex.getMessage();
    }
}
