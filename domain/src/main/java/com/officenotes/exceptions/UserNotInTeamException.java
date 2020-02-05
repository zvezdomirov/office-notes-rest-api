package com.officenotes.exceptions;

/**
 * Usually thrown when a non-team member tries to access/edit/delete
 * team's internal data.
 */
public class UserNotInTeamException extends RuntimeException {
    public UserNotInTeamException(String username, String teamName) {
        super(String.format("User %s is not in team %s.",
                username, teamName));
    }

    public UserNotInTeamException(String username) {
        super(String.format("User %s is not in any team.",
                username));
    }
}
