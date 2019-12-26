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
    private String location;

    /**
     * Vehicle for delivery.
     */
    private String vehicle;

    /**
     * Driver for delivery.
     */
    private String driver;

    /**
     * Start time for delivery.
     */
    private LocalTime deliveryStartTime;

    /**
     * End time for delivery.
     */
    private LocalTime deliveryEndTime;

}
