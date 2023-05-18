package com.carrentalbackend.features.renting.reservation;

import com.carrentalbackend.exception.ResourceNotFoundException;
import com.carrentalbackend.features.companyResources.car.CarMapper;
import com.carrentalbackend.features.companyResources.office.OfficeMapper;
import com.carrentalbackend.features.generics.CrudMapper;
import com.carrentalbackend.features.renting.reservation.ReservationUpdateDto;
import com.carrentalbackend.features.generics.UpdateDto;
import com.carrentalbackend.model.entity.*;
import com.carrentalbackend.model.enumeration.RentalActionStatus;
import com.carrentalbackend.model.enumeration.ReservationStatus;
import com.carrentalbackend.features.renting.reservation.ReservationCreateRequest;
import com.carrentalbackend.features.renting.reservation.ReservationUpdateRequest;
import com.carrentalbackend.features.renting.reservation.ReservationClientResponse;
import com.carrentalbackend.features.renting.reservation.ReservationResponse;
import com.carrentalbackend.repository.CarRepository;
import com.carrentalbackend.repository.ClientRepository;
import com.carrentalbackend.repository.OfficeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationMapper implements CrudMapper<Reservation, ReservationUpdateRequest, ReservationCreateRequest> {
    private final ClientRepository clientRepository;
    private final CarRepository carRepository;
    private final OfficeRepository officeRepository;
    private final CarMapper carMapper;
    private final OfficeMapper officeMapper;


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
    public Reservation toNewEntity(ReservationCreateRequest request) {

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

    @Override
    public UpdateDto toUpdateDto(ReservationUpdateRequest request) {

        Client client = request.getClientId() != null ? clientRepository.findById(request.getClientId()).orElse(null) : null;
        Office pickUpOffice = request.getPickUpOfficeId() != null ? officeRepository.findById(request.getPickUpOfficeId()).orElse(null) : null;
        Office returnOffice = request.getReturnOfficeId() != null ? officeRepository.findById(request.getReturnOfficeId()).orElse(null) : null;
        return ReservationUpdateDto.builder()
                .dateFrom(request.getDateFrom())
                .dateTo(request.getDateTo())
                .client(client)
                .pickUpOffice(pickUpOffice)
                .returnOffice(returnOffice)
                .status(request.getStatus())
                .build();
    }
}
