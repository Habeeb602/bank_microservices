package com.microservice.loans.exception;

public class DataAlreadyExistsException extends RuntimeException{

    public DataAlreadyExistsException(String s){
        super(s);
    }
}
