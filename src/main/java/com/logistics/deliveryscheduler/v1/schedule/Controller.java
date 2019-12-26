package com.logistics.deliveryscheduler.v1.schedule;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.logistics.deliveryscheduler.common.ApiResponse;
import com.logistics.deliveryscheduler.entity.DeliverySchedule;
import com.logistics.deliveryscheduler.v1.schedule.dto.CreateDeliveryRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for product related APIs (v1).
 */
@RestController("deliveryScheduleControllerV1")
@RequestMapping("/v1")
public class Controller {

    /**
     * Product service.
     */
    private DeliveryScheduleService service;

    @Autowired
    public Controller(@Qualifier("deliveryScheduleServiceV1") final DeliveryScheduleService service) {
        this.service = service;
    }

    /**
     * API to fetch a product from its ID.
     * @param requestBody request body
     * @return delivery schedule
     */
    @PostMapping("/deliverySchedules")
    public ResponseEntity<ApiResponse<Map<String, List<DeliverySchedule>>>> createDeliverySchedule(
            @RequestBody final CreateDeliveryRequestDto requestBody
    ) {
        requestBody.validate();

        final List<String> locationIds = requestBody.getLocations();
        final List<String> vehicleIds = requestBody.getVehicles();
        final List<String> driverIds = requestBody.getDrivers();

        final List<DeliverySchedule> deliverySchedules = service.create(locationIds, vehicleIds, driverIds);

        final ApiResponse<Map<String, List<DeliverySchedule>>> apiResponse = new ApiResponse
                .Builder<Map<String, List<DeliverySchedule>>>()
                .status(HttpStatus.OK.value())
                .data(Collections.singletonMap("deliverySchedules", deliverySchedules))
                .build();

        return ResponseEntity.ok(apiResponse);
    }

}
