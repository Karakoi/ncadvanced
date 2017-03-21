package com.overseer.exception;

/**
 * Exception for situation when someone try join request that has progress status not 'Free' and has Assignee.
 */
public class InappropriateProgressStatusException extends RuntimeException {
    /**
     * Constructs a new exception with {@code null} as its detail message.
     */
    public InappropriateProgressStatusException() {
        super();
    }

    /**
     * Constructs a new exception with the specified detail message.
     * @param message the detail message.
     */
    public InappropriateProgressStatusException(String message) {
        super(message);
    }
}
