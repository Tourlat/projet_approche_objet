package com.projetjava.customexceptions;

/**
 * Exception thrown when an unknown resource type is encountered.
 */
public class UnknownResourceTypeException extends RuntimeException {
    public UnknownResourceTypeException(String message) {
        super(message);
    }

}
