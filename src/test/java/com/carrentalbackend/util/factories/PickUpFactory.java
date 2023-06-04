package com.carrentalbackend.util.factories;

import com.carrentalbackend.features.renting.pickUps.rest.PickUpUpdateRequest;
import com.carrentalbackend.model.entity.PickUp;
import com.carrentalbackend.model.enumeration.RentalActionStatus;

import java.time.LocalDate;

import static com.carrentalbackend.model.entity.PickUp.PickUpBuilder;
import static com.carrentalbackend.features.renting.pickUps.rest.PickUpUpdateRequest.PickUpUpdateRequestBuilder;

public class PickUpFactory {
    public final static String simplePickUpComments = "simple pick-up comments";
    public final static LocalDate simplePickUpDate = LocalDate.now().minusDays(5);
    public final static LocalDate simplePickUpPlannedDate = LocalDate.now().minusDays(6);
    public final static RentalActionStatus simplePickUpStatus = RentalActionStatus.REALIZED;

    public static PickUpBuilder getSimplePickUpBuilder() {
        return PickUp.builder()
                .comments(simplePickUpComments)
                .pickUpDate(simplePickUpDate)
                .plannedPickUpDate(simplePickUpPlannedDate)
                .status(simplePickUpStatus);
    }

    public static PickUpUpdateRequestBuilder getSimplePickUpRequestBuilder(Long employeeId, Long carId, Long officeId) {
        return PickUpUpdateRequest.builder()
                .pickUpDate(simplePickUpDate)
                .plannedPickUpDate(simplePickUpPlannedDate)
                .status(simplePickUpStatus)
                .employeeId(employeeId)
                .carId(carId)
                .officeId(officeId);
    }
}
