package com.carrentalbackend.exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(Long id) {
        super("resource with id:" + id + " not found");
    }
}
