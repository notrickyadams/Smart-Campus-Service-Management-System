package org.example.app.exceptions;

public class ProcessingException extends RuntimeException {

    // This constructor accepts a custom message string
    public ProcessingException(String message) {
        super(message);
    }

    // This constructor handles a message alongside a root cause error
    public ProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}