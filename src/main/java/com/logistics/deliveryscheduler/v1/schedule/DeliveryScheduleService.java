package com.logistics.deliveryscheduler.v1.schedule;

import com.logistics.deliveryscheduler.common.errors.DeliveriesNotPossibleException;
import com.logistics.deliveryscheduler.entity.DeliverySchedule;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains business logic for delivery schedule related operations.
 */
@Service("deliveryScheduleServiceV1")
public class DeliveryScheduleService {

    /**
     * Start of the working hours.
     */
    private static final LocalTime workStartTime = LocalTime.of(8, 0);

    /**
     * End of the working hours.
     */
    private static final LocalTime workEndTime = LocalTime.of(17, 0);

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
     * @param locationIds location ids
     * @param vehicleIds vehicle ids
     * @param driverIds driver ids
     * @return delivery schedules
     */
    public List<DeliverySchedule> create(
            final List<String> locationIds,
            final List<String> vehicleIds,
            final List<String> driverIds
    ) {
        int locationsRemaining = locationIds.size();
        final int numberOfVehicles = vehicleIds.size();
        final int numberOfDrivers =  driverIds.size();

        final int parallelDeliveryCount = numberOfDrivers <= numberOfVehicles
                ? numberOfDrivers
                : numberOfVehicles;

        LocalTime deliveryStartTime = workStartTime;
        LocalTime breakEndTime = breakTime.plus(breakDuration);
        Duration timeTakenForDelivery = Duration.ofHours(2);
        final List<DeliverySchedule> deliverySchedules = new ArrayList<>();

        for (int loc = 0; loc <= locationsRemaining; loc = loc + parallelDeliveryCount) {
            LocalTime deliveryEndTime = deliveryStartTime.plus(timeTakenForDelivery);

            if ((deliveryStartTime.equals(breakTime) || deliveryStartTime.isAfter(breakTime))
                    && deliveryStartTime.isBefore(breakEndTime)) {
                deliveryEndTime = deliveryEndTime.plus(Duration.between(deliveryStartTime, breakTime));
            } else if (deliveryEndTime.isAfter(breakTime)
                    && (deliveryEndTime.equals(breakEndTime) || deliveryEndTime.isBefore(breakEndTime))) {
                deliveryEndTime = deliveryEndTime.plus(Duration.between(breakTime, deliveryEndTime));
            }

            if (deliveryEndTime.isAfter(workEndTime)) {
                throw new DeliveriesNotPossibleException("Deliveries not possible within the day");
            }

            for (int each = 0; each < parallelDeliveryCount; each++) {
                if (loc + each >= locationsRemaining) {
                    break;
                }

                deliverySchedules.add(DeliverySchedule
                        .builder()
                        .vehicle(vehicleIds.get(each))
                        .driver(driverIds.get(each))
                        .location(locationIds.get(loc + each))
                        .deliveryStartTime(deliveryStartTime)
                        .deliveryEndTime(deliveryEndTime)
                        .build()
                );
            }

            deliveryStartTime = deliveryEndTime;
        }

        return deliverySchedules;
    }

}
