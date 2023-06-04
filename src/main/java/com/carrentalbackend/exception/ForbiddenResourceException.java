package com.carrentalbackend.exception;

public class ForbiddenResourceException extends RuntimeException{
    public ForbiddenResourceException(String resource, long id) {
        super("access to " + resource +" with id: " + id + " is forbidden for request sender");
    }
}
