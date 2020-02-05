package com.officenotes.exceptions;

/**
 * Thrown when someone attempts to register a user
 * with the username of an existing one.
 */
public class UserAlreadyTakenException extends RuntimeException {
    public UserAlreadyTakenException(String username) {
        super("There is already a user with username: " + username);
    }
}
