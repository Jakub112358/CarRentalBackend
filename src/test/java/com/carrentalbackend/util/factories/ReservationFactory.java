package com.carrentalbackend.util.factories;

import com.carrentalbackend.features.renting.reservations.rest.ReservationCreateRequest;
import com.carrentalbackend.model.entity.Reservation;
import com.carrentalbackend.model.entity.Reservation.ReservationBuilder;
import com.carrentalbackend.model.enumeration.ReservationStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.carrentalbackend.features.renting.reservations.rest.ReservationCreateRequest.ReservationCreateRequestBuilder;

public class ReservationFactory {
    public final static LocalDateTime simpleReservationReservationDate = LocalDateTime.now();
    public final static BigDecimal simpleReservationPrice = BigDecimal.valueOf(300.0);
    public final static LocalDate simpleReservationDateFrom = LocalDate.now().plusDays(1);
    public final static LocalDate simpleReservationDateTo = LocalDate.now().plusDays(3);
    public final static ReservationStatus simpleReservationStatus = ReservationStatus.PLANNED;


    public static ReservationBuilder getSimpleReservationBuilder() {
        return Reservation.builder()
                .reservationDate(simpleReservationReservationDate)
                .price(simpleReservationPrice)
                .dateFrom(simpleReservationDateFrom)
                .dateTo(simpleReservationDateTo)
                .status(simpleReservationStatus);
    }

    public static ReservationCreateRequestBuilder getSimpleReservationCreateRequestBuilder(Long clientId, Long carId, Long pickUpOfficeId, Long returnOfficeId) {
        return ReservationCreateRequest.builder()
                .dateFrom(simpleReservationDateFrom)
                .dateTo(simpleReservationDateTo)
                .price(simpleReservationPrice)
                .clientId(clientId)
                .carId(carId)
                .pickUpOfficeId(pickUpOfficeId)
                .returnOfficeId(returnOfficeId);
    }
}
