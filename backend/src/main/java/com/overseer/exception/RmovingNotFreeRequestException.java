package com.overseer.exception;

/**
 * Exception for situation when someone try delete request that has progress status not 'Free'.
 */
public class RmovingNotFreeRequestException extends RuntimeException {
    /**
     * Constructs a new exception with {@code null} as its detail message.
     */
    public RmovingNotFreeRequestException() {
        super();
    }

    /**
     * Constructs a new exception with the specified detail message.
     * @param message the detail message.
     */
    public RmovingNotFreeRequestException(String message) {
        super(message);
    }
}
