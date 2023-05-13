package com.carrentalbackend.model.mapper;

import com.carrentalbackend.exception.ResourceNotFoundException;
import com.carrentalbackend.model.dto.updateDto.ReservationUpdateDto;
import com.carrentalbackend.model.dto.updateDto.UpdateDto;
import com.carrentalbackend.model.entity.*;
import com.carrentalbackend.model.enumeration.RentalActionStatus;
import com.carrentalbackend.model.enumeration.ReservationStatus;
import com.carrentalbackend.model.rest.request.create.CreateRequest;
import com.carrentalbackend.model.rest.request.create.ReservationCreateRequest;
import com.carrentalbackend.model.rest.request.update.UpdateRequest;
import com.carrentalbackend.model.rest.response.ReservationClientResponse;
import com.carrentalbackend.model.rest.response.ReservationResponse;
import com.carrentalbackend.repository.CarRepository;
import com.carrentalbackend.repository.ClientRepository;
import com.carrentalbackend.repository.OfficeRepository;
import com.carrentalbackend.service.util.ServiceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationMapper implements CrudMapper<Reservation> {
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
    public Reservation toNewEntity(CreateRequest request) {
        ServiceUtil.checkIfInstance(request, ReservationCreateRequest.class);
        ReservationCreateRequest reservationRequest = (ReservationCreateRequest) request;

        //TODO price should be recalculated!
        Client client = clientRepository.findById(reservationRequest.getClientId()).orElseThrow(() -> new ResourceNotFoundException(reservationRequest.getClientId()));
        Car car = carRepository.findById(reservationRequest.getCarId()).orElseThrow(() -> new ResourceNotFoundException(reservationRequest.getCarId()));
        Office pickUpOffice = officeRepository.findById(reservationRequest.getPickUpOfficeId()).orElseThrow(() -> new ResourceNotFoundException(reservationRequest.getPickUpOfficeId()));
        Office returnOffice = officeRepository.findById(reservationRequest.getReturnOfficeId()).orElseThrow(() -> new ResourceNotFoundException(reservationRequest.getReturnOfficeId()));
        PickUp pickUp = createCarPickUp(reservationRequest, car, pickUpOffice);
        CarReturn carReturn = createCarReturn(reservationRequest, car, returnOffice);

        return Reservation.builder()
                .reservationDate(reservationRequest.getReservationDate())
                .price(reservationRequest.getPrice())
                .dateFrom(reservationRequest.getDateFrom())
                .dateTo(reservationRequest.getDateTo())
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
    public UpdateDto toUpdateDto(UpdateRequest request) {
        ServiceUtil.checkIfInstance(request, ReservationCreateRequest.class);
        ReservationCreateRequest reservationRequest = (ReservationCreateRequest) request;

        Client client = reservationRequest.getClientId() != null ? clientRepository.findById(reservationRequest.getClientId()).orElse(null) : null;
        Office pickUpOffice = reservationRequest.getPickUpOfficeId() != null ? officeRepository.findById(reservationRequest.getPickUpOfficeId()).orElse(null) : null;
        Office returnOffice = reservationRequest.getReturnOfficeId() != null ? officeRepository.findById(reservationRequest.getReturnOfficeId()).orElse(null) : null;
        return ReservationUpdateDto.builder()
                .dateFrom(reservationRequest.getDateFrom())
                .dateTo(reservationRequest.getDateTo())
                .client(client)
                .pickUpOffice(pickUpOffice)
                .returnOffice(returnOffice)
                .status(reservationRequest.getStatus())
                .build();
    }
}
