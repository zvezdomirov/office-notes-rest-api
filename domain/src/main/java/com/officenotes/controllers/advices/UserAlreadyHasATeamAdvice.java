package com.officenotes.controllers.advices;

import com.officenotes.exceptions.UserAlreadyHasATeamException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class UserAlreadyHasATeamAdvice {

    @ExceptionHandler(UserAlreadyHasATeamException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String userAlreadyHasATeam(UserAlreadyHasATeamException ex) {
        return ex.getMessage();
    }
}
