package com.appdynamics.extensions.campfire.exception;


public class IllegalConfigException extends RuntimeException {
    private String message;
    private Throwable exception;

    public IllegalConfigException(String message) {
        this.message = message;
    }

    public IllegalConfigException(String message, Throwable exception) {
        this.message = message;
        this.exception = exception;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Throwable getException() {
        return exception;
    }
}
