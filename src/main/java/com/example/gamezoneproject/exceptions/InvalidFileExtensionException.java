package com.example.gamezoneproject.exceptions;
/**
 * Exception class thrown when a file contain invalid extension.
 * This exception should be caught and handled by returning an appropriate
 * error message to the user.
 */
public class InvalidFileExtensionException extends RuntimeException{
    public InvalidFileExtensionException(String message) {
        super(message);
    }
}
