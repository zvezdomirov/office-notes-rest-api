package com.officenotes.controllers.advices;

import com.officenotes.exceptions.TeamAlreadyPresentException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class TeamAlreadyPresentAdvice {

    @ExceptionHandler(TeamAlreadyPresentException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String teamAlreadyPresentHandler(TeamAlreadyPresentException ex) {
        return ex.getMessage();
    }
}
