package com.logistics.deliveryscheduler.v1.schedule.dto;

import com.logistics.deliveryscheduler.entity.DeliverySchedule;
import com.logistics.deliveryscheduler.entity.Location;
import lombok.Getter;

import java.util.List;

/**
 * Contains delivery schedule and locations where delivery is not possible.
 */
@Getter
public class DeliveryScheduleDto {

    /**
     * Delivery schedule.
     */
    private List<DeliverySchedule> deliverySchedule;

    /**
     * Locations where delivery is not possible.
     */
    private List<Location> noDeliveryLocations;

    public DeliveryScheduleDto(
            final List<DeliverySchedule> deliverySchedule,
            final List<Location> noDeliveryLocations
    ) {
        this.deliverySchedule = deliverySchedule;
        this.noDeliveryLocations = noDeliveryLocations;
    }
}
