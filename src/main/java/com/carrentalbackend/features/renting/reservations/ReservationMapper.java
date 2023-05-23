package com.carrentalbackend.features.renting.reservations;

import com.carrentalbackend.exception.ResourceNotFoundException;
import com.carrentalbackend.features.cars.rest.CarMapper;
import com.carrentalbackend.features.offices.rest.OfficeMapper;
import com.carrentalbackend.features.generics.CrudMapper;
import com.carrentalbackend.features.renting.reservations.rest.ReservationRequest;
import com.carrentalbackend.features.renting.reservations.rest.ReservationResponse;
import com.carrentalbackend.model.entity.*;
import com.carrentalbackend.model.enumeration.RentalActionStatus;
import com.carrentalbackend.model.enumeration.ReservationStatus;
import com.carrentalbackend.repository.CarRepository;
import com.carrentalbackend.repository.ClientRepository;
import com.carrentalbackend.repository.OfficeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationMapper implements CrudMapper<Reservation, ReservationRequest> {
    private final ClientRepository clientRepository;
    private final CarRepository carRepository;
    private final OfficeRepository officeRepository;
    private final CarMapper carMapper;
    private final OfficeMapper officeMapper;


    private CarReturn createCarReturn(ReservationRequest request, Car car, Office office) {
        return CarReturn.builder()
                .plannedReturnDate(request.getDateTo())
                .status(RentalActionStatus.PLANNED)
                .car(car)
                .office(office)
                .build();
    }

    private PickUp createCarPickUp(ReservationRequest request, Car car, Office office) {
        return PickUp.builder()
                .plannedPickUpDate(request.getDateFrom())
                .status(RentalActionStatus.PLANNED)
                .car(car)
                .office(office)
                .build();
    }


    public ReservationClientResponse toReservationClientResponse(Reservation reservation) {

        Long clientId = reservation.getClient() != null ? reservation.getClient().getId() : null;

        return ReservationClientResponse.builder()
                .id(reservation.getId())
                .dateFrom(reservation.getDateFrom())
                .dateTo(reservation.getDateTo())
                .price(reservation.getPrice())
                .status(reservation.getStatus())
                .clientId(clientId)
                .car(carMapper.toResponse(reservation.getCar()))
                .pickUpOffice(officeMapper.toResponse(reservation.getPickUpOffice()))
                .returnOffice(officeMapper.toResponse(reservation.getReturnOffice()))
                .build();
    }

    @Override
    public Reservation toNewEntity(ReservationRequest request) {

        //TODO price should be recalculated!
        Client client = clientRepository.findById(request.getClientId()).orElseThrow(() -> new ResourceNotFoundException(request.getClientId()));
        Car car = carRepository.findById(request.getCarId()).orElseThrow(() -> new ResourceNotFoundException(request.getCarId()));
        Office pickUpOffice = officeRepository.findById(request.getPickUpOfficeId()).orElseThrow(() -> new ResourceNotFoundException(request.getPickUpOfficeId()));
        Office returnOffice = officeRepository.findById(request.getReturnOfficeId()).orElseThrow(() -> new ResourceNotFoundException(request.getReturnOfficeId()));
        PickUp pickUp = createCarPickUp(request, car, pickUpOffice);
        CarReturn carReturn = createCarReturn(request, car, returnOffice);

        return Reservation.builder()
                .reservationDate(request.getReservationDate())
                .price(request.getPrice())
                .dateFrom(request.getDateFrom())
                .dateTo(request.getDateTo())
                .status(ReservationStatus.PLANNED)
                .client(client)
                .car(car)
                .pickUpOffice(pickUpOffice)
                .returnOffice(returnOffice)
                .pickUp(pickUp)
                .carReturn(carReturn)
                .build();
    }

    @Override
    public ReservationResponse toResponse(Reservation entity) {
        Long clientId = entity.getClient() != null ? entity.getClient().getId() : null;
        Long carId = entity.getCar() != null ? entity.getCar().getId() : null;
        Long pickUpOfficeId = entity.getPickUpOffice() != null ? entity.getPickUpOffice().getId() : null;
        Long returnOfficeId = entity.getReturnOffice() != null ? entity.getReturnOffice().getId() : null;

        return ReservationResponse.builder()
                .id(entity.getId())
                .reservationDate(entity.getReservationDate())
                .price(entity.getPrice())
                .status(entity.getStatus())
                .dateFrom(entity.getDateFrom())
                .dateTo(entity.getDateTo())
                .clientId(clientId)
                .carId(carId)
                .pickUpOfficeId(pickUpOfficeId)
                .returnOfficeId(returnOfficeId)
                .build();
    }

}
