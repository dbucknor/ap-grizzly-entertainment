package com.grizzly.application.services;

public class AuthException extends Exception {
    private AuthCode errorCode;


    public AuthException(AuthCode code, String message) {
        super(message);
        this.errorCode = code;
    }

    public AuthCode getErrorCode() {
        return errorCode;
    }
}
