package com.logistics.deliveryscheduler.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

/**
 * Location entity.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Location {

    /**
     * Location name.
     */
    private String name;

    /**
     * Address.
     */
    private String address;

    /**
     * Opening time of the location.
     */
    private LocalTime openingTime;

    /**
     * Closing time of the location.
     */
    private LocalTime closingTime;

}
