package com.example;

public class NavigationException extends RuntimeException {
    public NavigationException(String message, Throwable cause) {
        super(message, cause);
    }
}
