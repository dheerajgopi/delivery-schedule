package com.logistics.deliveryscheduler.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalTime;

/**
 * Delivery Schedule.
 */
@Getter @Setter @AllArgsConstructor @Builder @ToString
public class DeliverySchedule {

    /**
     * Location of delivery.
     */
    private Location location;

    /**
     * Vehicle for delivery.
     */
    private Vehicle vehicle;

    /**
     * Driver for delivery.
     */
    private Driver driver;

    /**
     * Start time for delivery.
     */
    private LocalTime deliveryStartTime;

    /**
     * End time for delivery.
     */
    private LocalTime deliveryEndTime;

}
