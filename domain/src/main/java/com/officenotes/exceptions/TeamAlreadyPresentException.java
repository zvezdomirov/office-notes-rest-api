package com.officenotes.exceptions;

/**
 * Thrown when someone tries to add a new team
 * with the name of an already existing one.
 */
public class TeamAlreadyPresentException extends RuntimeException {
    public TeamAlreadyPresentException(String teamName) {
        super("There is already a team with name " + teamName);
    }
}
