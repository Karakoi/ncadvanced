package com.overseer.exception.email;

/**
 * Exception for email service.
 */
public class MessageDestinationException extends Exception{

    /**
     * Constructs a new exception with {@code null} as its detail message.
     */
    public MessageDestinationException() {
        super();
    }

    /**
     *  Constructs a new exception with the specified detail message.
     *
     * @param message the detail message.
     */
    public MessageDestinationException(String message) {
        super(message);
    }
}
