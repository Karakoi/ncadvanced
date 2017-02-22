package com.overseer.exception.entity;

/**
 * Exception for user service.
 */
public class EntityAlreadyExistsException extends Exception {
    /**
     * Constructs a new exception with {@code null} as its detail message.
     */
    public EntityAlreadyExistsException() {
        super();
    }

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message the detail message.
     */
    public EntityAlreadyExistsException(String message) {
        super(message);
    }
}
