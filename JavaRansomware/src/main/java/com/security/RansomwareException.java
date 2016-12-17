package com.security;


public class RansomwareException extends Exception {

    public RansomwareException(String message) {
        super(message);
    }

    public RansomwareException(String message, Throwable throwable) {
        super(message, throwable);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}