package com.appdynamics.extensions.campfire.exception;


public class CampFireAlertException extends RuntimeException {

    private String message;
    private Throwable exception;

    public CampFireAlertException(String message) {
        this.message = message;
    }

    public CampFireAlertException(String message, Throwable exception) {
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
