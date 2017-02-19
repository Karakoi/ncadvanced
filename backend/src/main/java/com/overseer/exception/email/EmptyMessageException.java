package com.overseer.exception.email;

/**
 * Exception for email service.
 */
public class EmptyMessageException extends Exception{

    /**
     * Constructs a new exception with {@code null} as its detail message.
     */
    public EmptyMessageException() {
        super();
    }

    /**
     *  Constructs a new exception with the specified detail message.
     *
     * @param message the detail message.
     */
    public EmptyMessageException(String message) {
        super(message);
    }
}
