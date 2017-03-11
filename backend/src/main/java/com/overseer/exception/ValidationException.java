package com.overseer.exception;

/**
 * Exception for situation when some one send incorrect entity.
 */
public class ValidationException extends RuntimeException {

    /**
     * Constructs a new exception with {@code null} as its detail message.
     */
    public ValidationException() {
        super();
    }

    /**
     * Constructs a new exception with the specified detail message.
     * @param message the detail message.
     */
    public ValidationException(String message) {
        super(message);
    }

}
