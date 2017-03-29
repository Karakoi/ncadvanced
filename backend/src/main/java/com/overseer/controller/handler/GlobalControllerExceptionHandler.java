package com.overseer.controller.handler;

import lombok.Data;
import lombok.val;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;

/**
 * Handles all the exceptions in the application and send error response entity object
 * to the client side for further processing.
 */
@ControllerAdvice
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles application exceptions and send response data to client side.
     *
     * @param exception handled exception
     * @param request   input web request
     * @return error response object
     */
   /* @ExceptionHandler(value = RuntimeException.class)
    protected ResponseEntity<Object> handleException(RuntimeException exception, WebRequest request) {
        val message = exception.getMessage();
        val status = HttpStatus.CONFLICT.value();
        val response = new ErrorResponse(message, status);
        return handleExceptionInternal(exception, response, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }*/

    /**
     * Handles application sql exceptions and send response data to client side.
     *
     * @param exception handled exception
     * @param request   input web request
     * @return error response object
     */
    @ExceptionHandler(value = DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleNestedSqlException(SQLException exception, WebRequest request) {
        val message = "Nested application exception. Check logs for more details.";
        val status = HttpStatus.CONFLICT.value();
        val response = new ErrorResponse(message, status);
        return handleExceptionInternal(exception, response, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    /**
     * Error response data wrapper class.
     */
    @SuppressWarnings("PMD.UnusedPrivateField")
    @Data
    private static class ErrorResponse {
        private final String message;
        private final int status;
    }
}