package com.carrentalbackend.exception;

public class ExistingEmailException extends RuntimeException{
    public ExistingEmailException(String email) {
        super("email: " + email + " already exists in DB");
    }
}
