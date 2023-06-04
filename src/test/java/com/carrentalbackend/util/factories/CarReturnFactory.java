package com.carrentalbackend.util.factories;

import com.carrentalbackend.features.renting.carReturns.rest.CarReturnUpdateRequest;
import com.carrentalbackend.model.entity.CarReturn;
import com.carrentalbackend.model.enumeration.RentalActionStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.carrentalbackend.model.entity.CarReturn.CarReturnBuilder;
import static com.carrentalbackend.features.renting.carReturns.rest.CarReturnUpdateRequest.CarReturnUpdateRequestBuilder;

public class CarReturnFactory {
    public final static String simpleCarReturnComments = "simple car return comments";
    public final static BigDecimal simpleCarReturnExtraCharge = BigDecimal.TEN;
    public final static LocalDate simpleCarReturnDate = LocalDate.now().minusDays(1);
    public final static LocalDate simpleCarReturnPlanedDate = LocalDate.now().minusDays(3);
    public final static RentalActionStatus simpleCarReturnStatus = RentalActionStatus.REALIZED;
    public static CarReturnBuilder getSimpleCarReturnBuilder() {
        return CarReturn.builder()
                .comments(simpleCarReturnComments)
                .extraCharge(simpleCarReturnExtraCharge)
                .returnDate(simpleCarReturnDate)
                .plannedReturnDate(simpleCarReturnPlanedDate)
                .status(simpleCarReturnStatus);
    }

    public static CarReturnUpdateRequestBuilder getSimpleCarReturnRequestBuilder(Long employeeId, Long carId, Long officeId) {
        return CarReturnUpdateRequest.builder()
                .comments(simpleCarReturnComments)
                .extraCharge(simpleCarReturnExtraCharge)
                .returnDate(simpleCarReturnDate)
                .plannedReturnDate(simpleCarReturnPlanedDate)
                .status(simpleCarReturnStatus)
                .employeeId(employeeId)
                .carId(carId)
                .officeId(officeId);
    }
}
