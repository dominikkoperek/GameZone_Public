package com.example.gamezoneproject.domain.exceptions;

public class PlatformNotFoundException extends RuntimeException{
    public PlatformNotFoundException(String message) {
        super(message);
    }
}
