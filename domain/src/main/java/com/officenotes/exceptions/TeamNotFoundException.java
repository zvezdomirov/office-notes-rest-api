package com.officenotes.exceptions;

/**
 * Usually thrown when a team
 * is not found in the database.
 */
public class TeamNotFoundException extends RuntimeException {
    public TeamNotFoundException(String teamName) {
        super("Could not find team " + teamName);
    }
}
