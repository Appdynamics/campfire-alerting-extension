/*
 * Copyright 2015. AppDynamics LLC and its affiliates.
 * All Rights Reserved.
 * This is unpublished proprietary source code of AppDynamics LLC and its affiliates.
 * The copyright notice above does not evidence any actual or intended publication of such source code.
 */

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
