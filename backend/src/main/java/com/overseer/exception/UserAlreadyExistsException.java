package com.overseer.exception;

/**
 * Exception for situation when someone try register new account with the same email or id.
 */
public class UserAlreadyExistsException extends RuntimeException {
    /**
     * Constructs a new exception with {@code null} as its detail message.
     */
    public UserAlreadyExistsException() {
        super();
    }

    /**
     * Constructs a new exception with the specified detail message.
     * @param message the detail message.
     */
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
