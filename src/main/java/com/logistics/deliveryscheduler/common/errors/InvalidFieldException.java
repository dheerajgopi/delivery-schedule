package com.logistics.deliveryscheduler.common.errors;

/**
 * Thrown for invalid data in request.
 */
public class InvalidFieldException extends RuntimeException {

    public InvalidFieldException(final String msg) {
        super(msg);
    }

}
