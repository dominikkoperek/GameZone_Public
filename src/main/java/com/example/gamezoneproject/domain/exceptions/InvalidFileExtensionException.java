package com.example.gamezoneproject.domain.exceptions;

public class InvalidFileExtensionException extends RuntimeException{
    public InvalidFileExtensionException(String message) {
        super(message);
    }
}
