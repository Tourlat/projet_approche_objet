package com.projetjava.customexceptions;

public class InvalidResourceLoadException extends RuntimeException {
    public InvalidResourceLoadException(String message) {
        super(message);
    }

    public InvalidResourceLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}