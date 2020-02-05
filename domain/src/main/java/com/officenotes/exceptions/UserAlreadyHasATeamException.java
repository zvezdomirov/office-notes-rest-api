package com.officenotes.exceptions;

/**
 * Thrown when a user is already in a team
 * and someone tries to enroll him in another one.
 */
public class UserAlreadyHasATeamException extends RuntimeException {
    public UserAlreadyHasATeamException(String username, String otherTeams) {
        super(String.format("User %s is already a team member in: %s",
                username, otherTeams));
    }
}
