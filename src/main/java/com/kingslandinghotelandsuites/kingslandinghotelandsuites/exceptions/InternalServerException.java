package com.kingslandinghotelandsuites.kingslandinghotelandsuites.exceptions;

public class InternalServerException extends RuntimeException{
    public InternalServerException(String message) {
        super(message);
    }
}
