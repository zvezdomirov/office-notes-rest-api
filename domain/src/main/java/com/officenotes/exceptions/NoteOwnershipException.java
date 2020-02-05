package com.officenotes.exceptions;

/**
 * Usually thrown when a user tries to edit/delete a note
 * and doesn't have the required permission for that.
 */
public class NoteOwnershipException extends RuntimeException {
    public NoteOwnershipException(String message) {
        super(message);
    }
}
