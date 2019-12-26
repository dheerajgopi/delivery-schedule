package com.logistics.deliveryscheduler.v1.schedule;

import com.logistics.deliveryscheduler.common.ApiResponse;
import com.logistics.deliveryscheduler.entity.DeliverySchedule;
import com.logistics.deliveryscheduler.v1.schedule.dto.CreateDeliveryRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

        final List<String> locationIds = Arrays.asList("L1");
        final List<String> vehicleIds = Arrays.asList("V1");
        final List<String> driverIds = Arrays.asList("D1");

        final CreateDeliveryRequestDto requestBody = CreateDeliveryRequestDto
                .builder()
                .locations(locationIds)
                .vehicles(vehicleIds)
                .drivers(driverIds)
                .build();

        final LocalTime now = LocalTime.now();
        final List<DeliverySchedule> deliverySchedules = Arrays.asList(DeliverySchedule
                .builder()
                .location("L1")
                .vehicle("V1")
                .driver("D1")
                .deliveryStartTime(now)
                .deliveryEndTime(now)
                .build()
        );

        Mockito.when(deliveryScheduleService.create(anyList(), anyList(), anyList()))
                .thenReturn(deliverySchedules);

        ResponseEntity<ApiResponse<Map<String, List<DeliverySchedule>>>> actualResponse = controller
                .createDeliverySchedule(requestBody);

        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertTrue(actualResponse.getBody().getData().containsKey("deliverySchedules"));
        assertEquals(deliverySchedules, actualResponse.getBody().getData().get("deliverySchedules"));
    }
}
