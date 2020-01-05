package com.logistics.deliveryscheduler.v1.schedule;

import java.util.List;

import com.logistics.deliveryscheduler.common.ApiResponse;
import com.logistics.deliveryscheduler.entity.Driver;
import com.logistics.deliveryscheduler.entity.Location;
import com.logistics.deliveryscheduler.entity.Vehicle;
import com.logistics.deliveryscheduler.v1.schedule.dto.CreateDeliveryRequestDto;
import com.logistics.deliveryscheduler.v1.schedule.dto.DeliveryScheduleDto;
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
    public ResponseEntity<ApiResponse<DeliveryScheduleDto>> createDeliverySchedule(
            @RequestBody final CreateDeliveryRequestDto requestBody
    ) {
        requestBody.validate();

        final List<Location> locations = requestBody.getLocations();
        final List<Vehicle> vehicles = requestBody.getVehicles();
        final List<Driver> drivers = requestBody.getDrivers();

        final DeliveryScheduleDto deliveryScheduleDto = service.create(locations, vehicles, drivers);
        final List<Location> noDeliveryLocations = deliveryScheduleDto.getNoDeliveryLocations();

        final ApiResponse<DeliveryScheduleDto> apiResponse = new ApiResponse
                .Builder<DeliveryScheduleDto>()
                .status(noDeliveryLocations.size() > 0 ? HttpStatus.MULTI_STATUS.value() : HttpStatus.OK.value())
                .data(deliveryScheduleDto)
                .build();

        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

}
