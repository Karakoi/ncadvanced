package com.overseer.exception;

/**
 * Exception for situation when someone try join request that has progress status not 'Free' and has Assignee.
 */
public class InappropriateProgressStatusChange extends RuntimeException {
    /**
     * Constructs a new exception with {@code null} as its detail message.
     */
    public InappropriateProgressStatusChange() {
        super();
    }

    /**
     * Constructs a new exception with the specified detail message.
     * @param message the detail message.
     */
    public InappropriateProgressStatusChange(String message) {
        super(message);
    }
}
