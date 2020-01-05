package com.logistics.deliveryscheduler.v1.schedule;

import com.logistics.deliveryscheduler.entity.DeliverySchedule;
import com.logistics.deliveryscheduler.entity.Driver;
import com.logistics.deliveryscheduler.entity.Location;
import com.logistics.deliveryscheduler.entity.Vehicle;
import com.logistics.deliveryscheduler.v1.schedule.dto.DeliveryScheduleDto;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Contains business logic for delivery schedule related operations.
 */
@Service("deliveryScheduleServiceV1")
public class DeliveryScheduleService {

    /**
     * Start of the break time.
     */
    private static final LocalTime breakTime = LocalTime.of(13, 0);

    /**
     * Break duration.
     */
    private static final Duration breakDuration = Duration.ofHours(1);

    /**
     * Create a delivery schedule.
     * @param locations location ids
     * @param vehicles vehicle ids
     * @param drivers driver ids
     * @return delivery schedules
     */
    public DeliveryScheduleDto create(
            final List<Location> locations,
            final List<Vehicle> vehicles,
            final List<Driver> drivers
    ) {
        int locationsRemaining = locations.size();
        final int numberOfVehicles = vehicles.size();
        final int numberOfDrivers =  drivers.size();

        final int parallelDeliveryCount = numberOfDrivers <= numberOfVehicles
                ? numberOfDrivers
                : numberOfVehicles;

        List<Location> sortedLocations = locations
                .stream()
                .sorted(Comparator.comparing(Location::getOpeningTime))
                .collect(Collectors.toList());
        LocalTime deliveryStartTime = locations.get(0).getOpeningTime();
        LocalTime breakEndTime = breakTime.plus(breakDuration);
        Duration timeTakenForDelivery = Duration.ofHours(2);
        final List<DeliverySchedule> deliverySchedules = new ArrayList<>();
        final List<Location> noDeliveryLocations = new ArrayList<>();

        for (int loc = 0; loc <= locationsRemaining; loc = loc + parallelDeliveryCount) {

            LocalTime deliveryEndTime = deliveryStartTime.plus(timeTakenForDelivery);

            for (int each = 0; each < parallelDeliveryCount; each++) {
                if (loc + each >= locationsRemaining) {
                    break;
                }

                if ((deliveryStartTime.equals(breakTime) || deliveryStartTime.isAfter(breakTime))
                        && deliveryStartTime.isBefore(breakEndTime)) {
                    deliveryEndTime = deliveryEndTime.plus(Duration.between(deliveryStartTime, breakTime));
                } else if (deliveryEndTime.isAfter(breakTime)
                        && (deliveryEndTime.equals(breakEndTime) || deliveryEndTime.isBefore(breakEndTime))) {
                    deliveryEndTime = deliveryEndTime.plus(Duration.between(breakTime, deliveryEndTime));
                }

                if (deliveryEndTime.isAfter(sortedLocations.get(loc).getClosingTime())) {
                    noDeliveryLocations.add(sortedLocations.get(loc));
                    continue;
                }

                deliverySchedules.add(DeliverySchedule
                        .builder()
                        .vehicle(vehicles.get(each))
                        .driver(drivers.get(each))
                        .location(sortedLocations.get(loc + each))
                        .deliveryStartTime(deliveryStartTime)
                        .deliveryEndTime(deliveryEndTime)
                        .build()
                );
            }

            deliveryStartTime = deliveryEndTime;
        }

        return new DeliveryScheduleDto(deliverySchedules, noDeliveryLocations);
    }

}
