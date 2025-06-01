package com.microservice.loans.exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String s) {
        super(s);
    }
}
