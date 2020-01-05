package com.logistics.deliveryscheduler.v1.schedule.dto;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.logistics.deliveryscheduler.common.errors.InvalidFieldException;
import com.logistics.deliveryscheduler.common.errors.MissingFieldsException;
import com.logistics.deliveryscheduler.entity.Driver;
import com.logistics.deliveryscheduler.entity.Location;
import com.logistics.deliveryscheduler.entity.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * JSON representation for create delivery API request body.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CreateDeliveryRequestDto {

    /**
     * working hours.
     */
    private static final Duration workingHours = Duration.ofHours(9);

    /**
     * List of locations.
     */
    private List<Location> locations;

    /**
     * List of vehicles.
     */
    private List<Vehicle> vehicles;

    /**
     * List of drivers.
     */
    private List<Driver> drivers;

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

        validateLocations();
        validateVehicles();
        validateDrivers();
    }

    /**
     * Validate location data.
     */
    public void validateLocations() {
        locations.forEach(location -> {
            final List<String> missingFields = new ArrayList<>();
            final LocalTime openingTime = location.getOpeningTime();
            final LocalTime closingTime = location.getClosingTime();

            if (StringUtils.isBlank(location.getName())) {
                missingFields.add("location.name");
            }

            if (StringUtils.isBlank(location.getAddress())) {
                missingFields.add("location.address");
            }

            if (openingTime == null) {
                missingFields.add("location.openingTime");
            }

            if (closingTime == null) {
                missingFields.add("location.closingTime");
            }

            if (!missingFields.isEmpty()) {
                throw new MissingFieldsException("Mandatory fields are missing for location", missingFields);
            }

            if (openingTime.equals(closingTime) || openingTime.isAfter(closingTime)) {
                throw new InvalidFieldException("closing time should be after opening time");
            }

            if (Duration.between(openingTime, closingTime).compareTo(workingHours) > 0) {
                throw new InvalidFieldException("working hours cannot be greater than 9");
            }
        });
    }

    /**
     * Validate vehicle data.
     */
    public void validateVehicles() {
        vehicles.forEach(vehicle -> {
            final List<String> missingFields = new ArrayList<>();

            if (StringUtils.isBlank(vehicle.getRegistrationNumber())) {
                missingFields.add("vehicle.registrationNumber");
            }

            if (StringUtils.isBlank(vehicle.getType())) {
                missingFields.add("vehicle.type");
            }

            if (!missingFields.isEmpty()) {
                throw new MissingFieldsException("Mandatory fields are missing for vehicle", missingFields);
            }
        });
    }

    /**
     * Validate driver data.
     */
    public void validateDrivers() {
        drivers.forEach(driver -> {
            final List<String> missingFields = new ArrayList<>();

            if (StringUtils.isBlank(driver.getName())) {
                missingFields.add("driver.name");
            }

            if (StringUtils.isBlank(driver.getPhone())) {
                missingFields.add("driver.phone");
            }

            if (!missingFields.isEmpty()) {
                throw new MissingFieldsException("Mandatory fields are missing for driver", missingFields);
            }
        });
    }

}
