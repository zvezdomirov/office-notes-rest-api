package com.officenotes.exceptions;

/**
 * Thrown when someone tries to add a manager to a team
 * that already has one.
 */
public class TeamAlreadyHasAManagerException extends RuntimeException {
    public TeamAlreadyHasAManagerException(String teamName, String teamManager) {
        super(String.format("Team %s already has %s as a manager.",
                teamName, teamManager
        ));
    }
}
