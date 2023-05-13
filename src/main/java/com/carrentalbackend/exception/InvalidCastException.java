package com.carrentalbackend.exception;

public class InvalidCastException extends RuntimeException{
    public InvalidCastException(String className) {
        super("Provided object can't be cast to " + className);
    }
}
