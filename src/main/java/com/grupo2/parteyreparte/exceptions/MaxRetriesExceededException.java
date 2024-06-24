package com.grupo2.parteyreparte.exceptions;

public class MaxRetriesExceededException extends RuntimeException{

    public MaxRetriesExceededException(String message) {
        super(message);
    }
}
