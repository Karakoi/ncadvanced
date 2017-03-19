package com.overseer.exception;

/**
 * Exception for situation when someone try delete himself.
 */
public class RemovingYourselfException extends RuntimeException {
    /**
     * Constructs a new exception with {@code null} as its detail message.
     */
    public RemovingYourselfException() {
        super();
    }

    /**
     * Constructs a new exception with the specified detail message.
     * @param message the detail message.
     */
    public RemovingYourselfException(String message) {
        super(message);
    }
}
