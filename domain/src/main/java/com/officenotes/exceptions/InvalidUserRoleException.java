package com.officenotes.exceptions;

/**
 * Thrown when a user doesn't have
 * the role, needed to execute a certain request.
 */
public class InvalidUserRoleException extends RuntimeException {
    public InvalidUserRoleException(String userRole) {
        super(String.format(
                "User has role %s, which is inappropriate for this request.%n",
                userRole));
    }
}
