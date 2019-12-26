package com.logistics.deliveryscheduler.v1.schedule.dto;

import java.util.ArrayList;
import java.util.List;

import com.logistics.deliveryscheduler.common.errors.MissingFieldsException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JSON representation for create delivery API request body.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CreateDeliveryRequestDto {

    /**
     * List of locations.
     */
    private List<String> locations;

    /**
     * List of vehicles.
     */
    private List<String> vehicles;

    /**
     * List of drivers.
     */
    private List<String> drivers;

    /**
     * Validate and throw error if failed.
     */
    public void validate() {
        final List<String> missingFields = new ArrayList<>();

        if (locations.size() == 0) {
            missingFields.add("locations");
        }

        if (vehicles.size() == 0) {
            missingFields.add("vehicles");
        }

        if (drivers.size() == 0) {
            missingFields.add("drivers");
        }

        if (!missingFields.isEmpty()) {
            throw new MissingFieldsException("Mandatory fields are missing", missingFields);
        }
    }

}
