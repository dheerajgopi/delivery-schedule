package com.logistics.deliveryscheduler.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Vehicle entity.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Vehicle {

    /**
     * Registration number of the vehicle.
     */
    private String registrationNumber;

    /**
     * Type of the vehicle.
     */
    private String type;

}
