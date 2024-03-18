package com.kingslandinghotelandsuites.kingslandinghotelandsuites.exceptions;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
