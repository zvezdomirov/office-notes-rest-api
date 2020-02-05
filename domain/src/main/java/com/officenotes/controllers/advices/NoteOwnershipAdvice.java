package com.officenotes.controllers.advices;

import com.officenotes.exceptions.NoteOwnershipException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class NoteOwnershipAdvice {

    @ExceptionHandler(NoteOwnershipException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String noteOwnershipHandler(NoteOwnershipException ex) {
        return ex.getMessage();
    }
}
