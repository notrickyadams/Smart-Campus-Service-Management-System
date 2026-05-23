package org.example.app.exceptions;

public class InvalidRequestException {
    /**
     * Custom runtime exception thrown by the backend system when operations
     * violate business rules, data constraints, or processing requirements.
     */
    public static class ProcessingException extends RuntimeException {

        public ProcessingException(String message) {
            super(message);
        }

        public ProcessingException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
