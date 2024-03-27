package com.kingslandinghotelandsuites.kingslandinghotelandsuites.exceptions;

public class UserAlreadyExistsException extends RuntimeException{
    public UserAlreadyExistsException(String message) {
       super(message);
    }
}
