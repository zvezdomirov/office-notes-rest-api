package com.officenotes.controllers.advices;

import com.officenotes.exceptions.TeamAlreadyHasAManagerException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class TeamAlreadyHasAManagerAdvice {

    @ExceptionHandler(TeamAlreadyHasAManagerException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String teamAlreadyHasAManagerHandler(TeamAlreadyHasAManagerException ex) {
        return ex.getMessage();
    }
}
