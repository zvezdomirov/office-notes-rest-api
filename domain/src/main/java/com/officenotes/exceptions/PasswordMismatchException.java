package com.officenotes.exceptions;

/**
 * Thrown when a registration form is filled
 * with mismatching "password" and "confirmPassword" fields.
 */
public class PasswordMismatchException extends RuntimeException {
    public PasswordMismatchException() {
        super("Passwords doesn't match");
    }

    public PasswordMismatchException(String message) {
        super(message);
    }
}
