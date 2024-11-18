package com.example.demo.exception;

public class ScooterDataAccessException extends RuntimeException {
    public ScooterDataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}

