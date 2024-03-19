package com.kingslandinghotelandsuites.kingslandinghotelandsuites.exceptions;

public class InvalidBookingRequestException extends RuntimeException{
    public InvalidBookingRequestException(String message) {
        super(message);
    }
}
