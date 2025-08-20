package com.danielara.ForoHub.domain;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}
