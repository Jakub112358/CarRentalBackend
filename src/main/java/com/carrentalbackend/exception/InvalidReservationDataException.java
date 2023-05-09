package com.carrentalbackend.exception;

public class InvalidReservationDataException extends RuntimeException{
    public InvalidReservationDataException(String message) {
        super(message);
    }
}
