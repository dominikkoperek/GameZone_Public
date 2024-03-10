package com.example.gamezoneproject.domain.exceptions;

public class GameNotFoundException extends RuntimeException{
    public GameNotFoundException(String message) {
        super(message);
    }
}
