package com.tsapiszczak.gitapitask.exception;

public class APIException extends RuntimeException {
    private final int statusCode;

    public APIException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}