package com.projetjava.customexceptions;

public class ErrorInitController extends IllegalStateException {

    public ErrorInitController(String message) {
        super(message);
    }

    public ErrorInitController(String message, Throwable cause) {
        super(message, cause);
    }

}
