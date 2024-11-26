package com.projetjava.Model.building;

/**
 * Exception thrown when trying to create a building of an unknown type.
 */
public class UnknownBuildingTypeException extends RuntimeException {
    public UnknownBuildingTypeException(String message) {
        super(message);
    }
}
