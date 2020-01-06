package com.logistics.deliveryscheduler.v1.schedule.dto;

import com.logistics.deliveryscheduler.common.errors.InvalidFieldException;
import com.logistics.deliveryscheduler.common.errors.MissingFieldsException;
import com.logistics.deliveryscheduler.entity.Driver;
import com.logistics.deliveryscheduler.entity.Location;
import com.logistics.deliveryscheduler.entity.Vehicle;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class CreateDeliveryRequestDtoTest {

    @Test
    void testValidateForMissingFields() {
        final CreateDeliveryRequestDto requestDto = CreateDeliveryRequestDto.builder().build();

        assertThrows(MissingFieldsException.class, () -> {
            requestDto.validate();
        });
    }

    @Test
    void testValidateForMissingNameInLocations() {
        final Location location = Location.builder()
                .address("address")
                .openingTime(LocalTime.of(1, 0, 0))
                .closingTime(LocalTime.of(9, 0, 0))
                .build();
        final Vehicle vehicle = Vehicle.builder().registrationNumber("V1").type("test").build();
        final Driver driver = Driver.builder().name("D1").phone("123").build();
        final CreateDeliveryRequestDto requestDto = CreateDeliveryRequestDto.builder()
                .locations(Arrays.asList(location))
                .vehicles(Arrays.asList(vehicle))
                .drivers(Arrays.asList(driver))
                .build();

        assertThrows(MissingFieldsException.class, () -> {
            requestDto.validate();
        });
    }

    @Test
    void testValidateForMissingAddressInLocations() {
        final Location location = Location.builder()
                .name("L1")
                .openingTime(LocalTime.of(1, 0, 0))
                .closingTime(LocalTime.of(9, 0, 0))
                .build();
        final Vehicle vehicle = Vehicle.builder().registrationNumber("V1").type("test").build();
        final Driver driver = Driver.builder().name("D1").phone("123").build();
        final CreateDeliveryRequestDto requestDto = CreateDeliveryRequestDto.builder()
                .locations(Arrays.asList(location))
                .vehicles(Arrays.asList(vehicle))
                .drivers(Arrays.asList(driver))
                .build();

        assertThrows(MissingFieldsException.class, () -> {
            requestDto.validate();
        });
    }

    @Test
    void testValidateForMissingOpeningTimeInLocations() {
        final Location location = Location.builder()
                .name("name")
                .address("address")
                .closingTime(LocalTime.of(9, 0, 0))
                .build();
        final Vehicle vehicle = Vehicle.builder().registrationNumber("V1").type("test").build();
        final Driver driver = Driver.builder().name("D1").phone("123").build();
        final CreateDeliveryRequestDto requestDto = CreateDeliveryRequestDto.builder()
                .locations(Arrays.asList(location))
                .vehicles(Arrays.asList(vehicle))
                .drivers(Arrays.asList(driver))
                .build();

        assertThrows(MissingFieldsException.class, () -> {
            requestDto.validate();
        });
    }

    @Test
    void testValidateForMissingClosingTimeInLocations() {
        final Location location = Location.builder()
                .name("name")
                .address("address")
                .openingTime(LocalTime.of(1, 0, 0))
                .build();
        final Vehicle vehicle = Vehicle.builder().registrationNumber("V1").type("test").build();
        final Driver driver = Driver.builder().name("D1").phone("123").build();
        final CreateDeliveryRequestDto requestDto = CreateDeliveryRequestDto.builder()
                .locations(Arrays.asList(location))
                .vehicles(Arrays.asList(vehicle))
                .drivers(Arrays.asList(driver))
                .build();

        assertThrows(MissingFieldsException.class, () -> {
            requestDto.validate();
        });
    }

    @Test
    void testValidateForInvalidWorkTimingsInLocations() {
        final Location location = Location.builder()
                .name("name")
                .address("address")
                .openingTime(LocalTime.of(10, 0, 0))
                .closingTime(LocalTime.of(9, 0, 0))
                .build();
        final Vehicle vehicle = Vehicle.builder().registrationNumber("V1").type("test").build();
        final Driver driver = Driver.builder().name("D1").phone("123").build();
        final CreateDeliveryRequestDto requestDto = CreateDeliveryRequestDto.builder()
                .locations(Arrays.asList(location))
                .vehicles(Arrays.asList(vehicle))
                .drivers(Arrays.asList(driver))
                .build();

        assertThrows(InvalidFieldException.class, () -> {
            requestDto.validate();
        });
    }

    @Test
    void testValidateForExcessWorkingHoursInLocations() {
        final Location location = Location.builder()
                .name("name")
                .address("address")
                .openingTime(LocalTime.of(1, 0, 0))
                .closingTime(LocalTime.of(19, 0, 0))
                .build();
        final Vehicle vehicle = Vehicle.builder().registrationNumber("V1").type("test").build();
        final Driver driver = Driver.builder().name("D1").phone("123").build();
        final CreateDeliveryRequestDto requestDto = CreateDeliveryRequestDto.builder()
                .locations(Arrays.asList(location))
                .vehicles(Arrays.asList(vehicle))
                .drivers(Arrays.asList(driver))
                .build();

        assertThrows(InvalidFieldException.class, () -> {
            requestDto.validate();
        });
    }

    @Test
    void testValidateForMissingRegistrationNumberInVehicles() {
        final Location location = Location.builder()
                .name("name")
                .address("address")
                .openingTime(LocalTime.of(1, 0, 0))
                .closingTime(LocalTime.of(9, 0, 0))
                .build();
        final Vehicle vehicle = Vehicle.builder().type("test").build();
        final Driver driver = Driver.builder().name("D1").phone("123").build();
        final CreateDeliveryRequestDto requestDto = CreateDeliveryRequestDto.builder()
                .locations(Arrays.asList(location))
                .vehicles(Arrays.asList(vehicle))
                .drivers(Arrays.asList(driver))
                .build();

        assertThrows(MissingFieldsException.class, () -> {
            requestDto.validate();
        });
    }

    @Test
    void testValidateForMissingTypeInVehicles() {
        final Location location = Location.builder()
                .name("name")
                .address("address")
                .openingTime(LocalTime.of(1, 0, 0))
                .closingTime(LocalTime.of(9, 0, 0))
                .build();
        final Vehicle vehicle = Vehicle.builder().registrationNumber("V1").build();
        final Driver driver = Driver.builder().name("D1").phone("123").build();
        final CreateDeliveryRequestDto requestDto = CreateDeliveryRequestDto.builder()
                .locations(Arrays.asList(location))
                .vehicles(Arrays.asList(vehicle))
                .drivers(Arrays.asList(driver))
                .build();

        assertThrows(MissingFieldsException.class, () -> {
            requestDto.validate();
        });
    }

    @Test
    void testValidateForNameInDrivers() {
        final Location location = Location.builder()
                .name("name")
                .address("address")
                .openingTime(LocalTime.of(1, 0, 0))
                .closingTime(LocalTime.of(9, 0, 0))
                .build();
        final Vehicle vehicle = Vehicle.builder().registrationNumber("V1").type("test").build();
        final Driver driver = Driver.builder().phone("123").build();
        final CreateDeliveryRequestDto requestDto = CreateDeliveryRequestDto.builder()
                .locations(Arrays.asList(location))
                .vehicles(Arrays.asList(vehicle))
                .drivers(Arrays.asList(driver))
                .build();

        assertThrows(MissingFieldsException.class, () -> {
            requestDto.validate();
        });
    }

    @Test
    void testValidateForPhoneInDrivers() {
        final Location location = Location.builder()
                .name("name")
                .address("address")
                .openingTime(LocalTime.of(1, 0, 0))
                .closingTime(LocalTime.of(9, 0, 0))
                .build();
        final Vehicle vehicle = Vehicle.builder().registrationNumber("V1").type("test").build();
        final Driver driver = Driver.builder().name("D1").build();
        final CreateDeliveryRequestDto requestDto = CreateDeliveryRequestDto.builder()
                .locations(Arrays.asList(location))
                .vehicles(Arrays.asList(vehicle))
                .drivers(Arrays.asList(driver))
                .build();

        assertThrows(MissingFieldsException.class, () -> {
            requestDto.validate();
        });
    }
}
