package com.officenotes.controllers.advices;

import com.officenotes.exceptions.PasswordMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class WrongConfirmPasswordAdvice {

    @ExceptionHandler(PasswordMismatchException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String wrongConfirmPasswordHandler(PasswordMismatchException ex) {
        return ex.getMessage();
    }
}
