package com.projetjava.customexceptions;

public class UnknownPopulationTypeException extends RuntimeException {

  public UnknownPopulationTypeException(String message) {
    super(message);
  }
}
