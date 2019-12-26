package com.logistics.deliveryscheduler.common.errors;

import lombok.Getter;

import java.util.List;

/**
 * Thrown when mandatory fields are missing in request.
 */
@Getter
public class MissingFieldsException extends RuntimeException {

    /**
     * Missing fields.
     */
    private final List<String> missingFields;

    public MissingFieldsException(final String message, final List<String> missingFields) {
        super(message);
        this.missingFields = missingFields;
    }

}
