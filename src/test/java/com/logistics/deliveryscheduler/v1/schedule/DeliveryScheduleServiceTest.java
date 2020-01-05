package com.logistics.deliveryscheduler.v1.schedule;

import com.logistics.deliveryscheduler.entity.DeliverySchedule;
import com.logistics.deliveryscheduler.entity.Driver;
import com.logistics.deliveryscheduler.entity.Location;
import com.logistics.deliveryscheduler.entity.Vehicle;
import com.logistics.deliveryscheduler.v1.schedule.dto.DeliveryScheduleDto;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DeliveryScheduleServiceTest {

    @Test
    public void testCreate() {
        final DeliveryScheduleService deliveryScheduleService = new DeliveryScheduleService();

        final Location location1 = Location.builder()
                .name("L1")
                .openingTime(LocalTime.of(1, 0, 0))
                .closingTime(LocalTime.of(9, 0, 0))
                .build();
        final Location location2 = Location.builder()
                .name("L2")
                .openingTime(LocalTime.of(1, 0, 0))
                .closingTime(LocalTime.of(9, 0, 0))
                .build();
        final Location location3 = Location.builder()
                .name("L3")
                .openingTime(LocalTime.of(1, 0, 0))
                .closingTime(LocalTime.of(9, 0, 0))
                .build();
        final Vehicle vehicle1 = Vehicle.builder().registrationNumber("V1").build();
        final Vehicle vehicle2 = Vehicle.builder().registrationNumber("V2").build();
        final Vehicle vehicle3 = Vehicle.builder().registrationNumber("V3").build();
        final Driver driver1 = Driver.builder().name("D1").build();
        final Driver driver2 = Driver.builder().name("D2").build();
        final Driver driver3 = Driver.builder().name("D3").build();

        final List<Location> locations = Arrays.asList(location1, location2, location3);
        final List<Vehicle> vehicles = Arrays.asList(vehicle1, vehicle2, vehicle3);
        final List<Driver> drivers = Arrays.asList(driver1, driver2, driver3);

        final DeliveryScheduleDto deliveryScheduleDto = deliveryScheduleService
                .create(locations, vehicles, drivers);
        final List<DeliverySchedule> actualDeliverySchedules = deliveryScheduleDto.getDeliverySchedule();

        assertEquals(3, actualDeliverySchedules.size());

        for (int i = 0; i < 3 ; i++) {
            assertEquals("L".concat(String.valueOf(i + 1)), actualDeliverySchedules.get(i).getLocation().getName());
            assertEquals("V".concat(String.valueOf(i + 1)), actualDeliverySchedules.get(i).getVehicle().getRegistrationNumber());
            assertEquals("D".concat(String.valueOf(i + 1)), actualDeliverySchedules.get(i).getDriver().getName());
            assertEquals(LocalTime.of(1, 0, 0), actualDeliverySchedules.get(i).getDeliveryStartTime());
            assertEquals(LocalTime.of(3, 0, 0), actualDeliverySchedules.get(i).getDeliveryEndTime());
        }
    }

    @Test
    public void testCreateForTooManyDeliveriesForADay() {
        final DeliveryScheduleService deliveryScheduleService = new DeliveryScheduleService();

        final Location location1 = Location.builder().name("L1")
                .openingTime(LocalTime.of(1, 0, 0))
                .closingTime(LocalTime.of(9, 0, 0))
                .build();
        final Location location2 = Location.builder().name("L2")
                .openingTime(LocalTime.of(1, 0, 0))
                .closingTime(LocalTime.of(2, 0, 0))
                .build();
        final Location location3 = Location.builder()
                .name("L3")
                .openingTime(LocalTime.of(1, 0, 0))
                .closingTime(LocalTime.of(2, 0, 0))
                .build();
        final Location location4 = Location.builder()
                .name("L4")
                .openingTime(LocalTime.of(1, 0, 0))
                .closingTime(LocalTime.of(2, 0, 0))
                .build();
        final Location location5 = Location.builder()
                .name("L5")
                .openingTime(LocalTime.of(1, 0, 0))
                .closingTime(LocalTime.of(2, 0, 0))
                .build();
        final Vehicle vehicle1 = Vehicle.builder().registrationNumber("V1").build();
        final Driver driver1 = Driver.builder().name("D1").build();
        final List<Location> locations = Arrays.asList(location1, location2, location3, location4, location5);
        final List<Vehicle> vehicles = Arrays.asList(vehicle1);
        final List<Driver> drivers = Arrays.asList(driver1);

        final DeliveryScheduleDto deliveryScheduleDto = deliveryScheduleService.create(locations, vehicles, drivers);

        assertEquals(location1, deliveryScheduleDto.getDeliverySchedule().get(0).getLocation());
        assertEquals(location2, deliveryScheduleDto.getNoDeliveryLocations().get(0));
        assertEquals(location3, deliveryScheduleDto.getNoDeliveryLocations().get(1));
        assertEquals(location4, deliveryScheduleDto.getNoDeliveryLocations().get(2));
        assertEquals(location5, deliveryScheduleDto.getNoDeliveryLocations().get(3));
    }
}
