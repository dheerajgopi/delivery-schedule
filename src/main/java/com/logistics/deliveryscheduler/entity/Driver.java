package com.logistics.deliveryscheduler.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Driver entity.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Driver {

    /**
     * Driver name.
     */
    private String name;

    /**
     * Driver phone number.
     */
    private String phone;

}
