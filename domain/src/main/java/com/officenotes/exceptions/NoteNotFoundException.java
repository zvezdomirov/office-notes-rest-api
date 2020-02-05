package com.officenotes.exceptions;

/**
 * Usually thrown when a note
 * is not found in the database.
 */
public class NoteNotFoundException extends RuntimeException {
    public NoteNotFoundException(String message) {
        super(message);
    }
}
