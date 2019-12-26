package com.logistics.deliveryscheduler.v1.schedule;

import com.logistics.deliveryscheduler.common.errors.DeliveriesNotPossibleException;
import com.logistics.deliveryscheduler.entity.DeliverySchedule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DeliveryScheduleServiceTest {

    @Test
    public void testCreate() {
        final DeliveryScheduleService deliveryScheduleService = new DeliveryScheduleService();

        final List<String> locationIds = Arrays.asList("L1", "L2", "L3");
        final List<String> vehicleIds = Arrays.asList("V1", "V2", "V3");
        final List<String> driverIds = Arrays.asList("D1", "D2", "D3");

        final List<DeliverySchedule> actualDeliverySchedules = deliveryScheduleService
                .create(locationIds, vehicleIds, driverIds);

        assertEquals(3, actualDeliverySchedules.size());

        for (int i = 0; i < 3 ; i++) {
            assertEquals("L".concat(String.valueOf(i + 1)), actualDeliverySchedules.get(i).getLocation());
            assertEquals("V".concat(String.valueOf(i + 1)), actualDeliverySchedules.get(i).getVehicle());
            assertEquals("D".concat(String.valueOf(i + 1)), actualDeliverySchedules.get(i).getDriver());
            assertEquals(LocalTime.of(8, 0, 0), actualDeliverySchedules.get(i).getDeliveryStartTime());
            assertEquals(LocalTime.of(10, 0, 0), actualDeliverySchedules.get(i).getDeliveryEndTime());
        }
    }

    @Test
    public void testCreateForTooManyDeliveriesForADay() {
        final DeliveryScheduleService deliveryScheduleService = new DeliveryScheduleService();

        final List<String> locationIds = Arrays.asList("L1", "L2", "L3", "L4", "L5");
        final List<String> vehicleIds = Arrays.asList("V1");
        final List<String> driverIds = Arrays.asList("D1");

        assertThrows(DeliveriesNotPossibleException.class, () -> {
            deliveryScheduleService.create(locationIds, vehicleIds, driverIds);
        });
    }
}
