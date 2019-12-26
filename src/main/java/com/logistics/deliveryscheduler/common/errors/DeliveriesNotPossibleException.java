package com.logistics.deliveryscheduler.common.errors;

import lombok.Getter;

/**
 * Thrown when deliveries are not possible within a single day.
 */
@Getter
public class DeliveriesNotPossibleException extends RuntimeException {

    public DeliveriesNotPossibleException(final String message) {
        super(message);
    }

}
