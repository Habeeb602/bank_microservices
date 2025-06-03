package com.microservice.accounts.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resourceName, String field, String value){
        super(String.format("%s not found with the given data '%s': '%s'", resourceName, field, value));
    }
}
