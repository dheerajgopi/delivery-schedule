package com.logistics.deliveryscheduler.v1.schedule;

import com.logistics.deliveryscheduler.common.ApiResponse;
import com.logistics.deliveryscheduler.entity.DeliverySchedule;
import com.logistics.deliveryscheduler.entity.Driver;
import com.logistics.deliveryscheduler.entity.Location;
import com.logistics.deliveryscheduler.entity.Vehicle;
import com.logistics.deliveryscheduler.v1.schedule.dto.CreateDeliveryRequestDto;
import com.logistics.deliveryscheduler.v1.schedule.dto.DeliveryScheduleDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;

class ControllerTest {

    @Mock
    private DeliveryScheduleService deliveryScheduleService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateDeliverySchedule() {
        final Controller controller = new Controller(deliveryScheduleService);

        final Location location = Location.builder()
                .name("L1")
                .address("address")
                .openingTime(LocalTime.of(1, 0, 0))
                .closingTime(LocalTime.of(9, 0, 0))
                .build();
        final Vehicle vehicle = Vehicle.builder().registrationNumber("V1").type("truck").build();
        final Driver driver = Driver.builder().name("D1").phone("123").build();

        final List<Location> locations = Arrays.asList(location);
        final List<Vehicle> vehicles = Arrays.asList(vehicle);
        final List<Driver> drivers = Arrays.asList(driver);

        final CreateDeliveryRequestDto requestBody = CreateDeliveryRequestDto
                .builder()
                .locations(locations)
                .vehicles(vehicles)
                .drivers(drivers)
                .build();

        final LocalTime now = LocalTime.now();
        final List<DeliverySchedule> deliverySchedules = Arrays.asList(DeliverySchedule
                .builder()
                .location(location)
                .vehicle(vehicle)
                .driver(driver)
                .deliveryStartTime(now)
                .deliveryEndTime(now)
                .build()
        );

        final DeliveryScheduleDto deliveryScheduleDto = new DeliveryScheduleDto(deliverySchedules, new ArrayList<>());

        Mockito.when(deliveryScheduleService.create(anyList(), anyList(), anyList()))
                .thenReturn(deliveryScheduleDto);

        ResponseEntity<ApiResponse<DeliveryScheduleDto>> actualResponse = controller
                .createDeliverySchedule(requestBody);

        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals(deliverySchedules, actualResponse.getBody().getData().getDeliverySchedule());
    }
}
