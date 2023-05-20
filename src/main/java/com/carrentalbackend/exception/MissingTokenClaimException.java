package com.carrentalbackend.exception;

public class MissingTokenClaimException extends RuntimeException{
    public MissingTokenClaimException(String missingClaim) {
        super("provided token doesn't contain " + missingClaim + " claim");
    }
}
