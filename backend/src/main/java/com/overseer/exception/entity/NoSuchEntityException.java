package com.overseer.exception.entity;

/**
 * Exception for user service.
 */
public class NoSuchEntityException extends RuntimeException {
    private static final String NO_SUCH_ENTITY_MESSAGE = " Database does not contain user with";

    /**
     * Constructs a new exception with {@code null} as its detail message.
     */
    public NoSuchEntityException() {
        super();
    }

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message the detail message.
     */
    public NoSuchEntityException(String message) {
        super(NO_SUCH_ENTITY_MESSAGE + " " + message);
    }
}
