package com.overseer.exception.entity;

/**
 * Exception for user service.
 */
public class EntityAlreadyExistsException extends RuntimeException {
    private static final String ENTITY_ALREADY_EXISTS_MESSAGE = " Database already contains user with ";

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
        super(ENTITY_ALREADY_EXISTS_MESSAGE + " " + message);
    }
}
