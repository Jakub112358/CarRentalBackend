package com.carrentalbackend.features.renting.reservations.rest;

import com.carrentalbackend.exception.ResourceNotFoundException;
import com.carrentalbackend.features.cars.rest.CarMapper;
import com.carrentalbackend.features.generics.Response;
import com.carrentalbackend.features.offices.rest.OfficeMapper;
import com.carrentalbackend.features.generics.CrudMapper;

import com.carrentalbackend.model.entity.*;
import com.carrentalbackend.model.enumeration.RentalActionStatus;
import com.carrentalbackend.model.enumeration.ReservationStatus;
import com.carrentalbackend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ReservationMapper implements CrudMapper<Reservation, ReservationCreateRequest> {
    private final ClientRepository clientRepository;
    private final CarRepository carRepository;
    private final OfficeRepository officeRepository;
    private final CarMapper carMapper;
    private final OfficeMapper officeMapper;
    private final ReservationRepository reservationRepository;
    private final CompanyRepository companyRepository;




    @Override
    public ReservationResponse toResponse(Reservation reservation) {
        Long clientId = reservation.getClient() != null ? reservation.getClient().getId() : null;

        return ReservationResponse.builder()
                .id(reservation.getId())
                .reservationDate(reservation.getReservationDate())
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
    public Reservation toNewEntity(ReservationCreateRequest request) {

        Client client = clientRepository.findById(request.getClientId()).orElseThrow(() -> new ResourceNotFoundException(request.getClientId()));
        Car car = carRepository.findById(request.getCarId()).orElseThrow(() -> new ResourceNotFoundException(request.getCarId()));
        Office pickUpOffice = officeRepository.findById(request.getPickUpOfficeId()).orElseThrow(() -> new ResourceNotFoundException(request.getPickUpOfficeId()));
        Office returnOffice = officeRepository.findById(request.getReturnOfficeId()).orElseThrow(() -> new ResourceNotFoundException(request.getReturnOfficeId()));
        PickUp pickUp = createCarPickUp(request, car, pickUpOffice);
        CarReturn carReturn = createCarReturn(request, car, returnOffice);

        return Reservation.builder()
                .reservationDate(LocalDateTime.now())
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


    public Income reservationResponseToIncome(Response response) {

        ReservationResponse reservationResponse = (ReservationResponse) response;

        Reservation reservation = reservationRepository.getReferenceById(response.getId());
        Finances finances = companyRepository.findFirstByIdIsNotNull().orElseThrow().getFinances();
        return Income.builder()
                .incomeValue(reservationResponse.getPrice())
                .reservation(reservation)
                .finances(finances)
                .build();
    }

    private CarReturn createCarReturn(ReservationCreateRequest request, Car car, Office office) {
        return CarReturn.builder()
                .plannedReturnDate(request.getDateTo())
                .status(RentalActionStatus.PLANNED)
                .car(car)
                .office(office)
                .build();
    }

    private PickUp createCarPickUp(ReservationCreateRequest request, Car car, Office office) {
        return PickUp.builder()
                .plannedPickUpDate(request.getDateFrom())
                .status(RentalActionStatus.PLANNED)
                .car(car)
                .office(office)
                .build();
    }

}
