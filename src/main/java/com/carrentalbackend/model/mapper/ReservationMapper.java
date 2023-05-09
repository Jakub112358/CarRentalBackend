package com.carrentalbackend.model.mapper;

import com.carrentalbackend.exception.ResourceNotFoundException;
import com.carrentalbackend.model.dto.crudDto.ReservationDto;
import com.carrentalbackend.model.dto.updateDto.ReservationUpdateDto;
import com.carrentalbackend.model.entity.*;
import com.carrentalbackend.model.enumeration.RentalActionStatus;
import com.carrentalbackend.model.enumeration.ReservationStatus;
import com.carrentalbackend.model.rest.ReservationClientResponse;
import com.carrentalbackend.repository.CarRepository;
import com.carrentalbackend.repository.ClientRepository;
import com.carrentalbackend.repository.OfficeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationMapper implements CrudMapper<Reservation, ReservationDto> {
    private final ClientRepository clientRepository;
    private final CarRepository carRepository;
    private final OfficeRepository officeRepository;
    private final CarMapper carMapper;
    private final OfficeMapper officeMapper;

    @Override
    public Reservation toNewEntity(ReservationDto dto) {
        //TODO price should be recalculated!
        Client client = clientRepository.findById(dto.getClientId()).orElseThrow(() -> new ResourceNotFoundException(dto.getClientId()));
        Car car = carRepository.findById(dto.getCarId()).orElseThrow(() -> new ResourceNotFoundException(dto.getCarId()));
        BranchOffice pickUpOffice = officeRepository.findById(dto.getPickUpOfficeId()).orElseThrow(() -> new ResourceNotFoundException(dto.getPickUpOfficeId()));
        BranchOffice returnOffice = officeRepository.findById(dto.getReturnOfficeId()).orElseThrow(() -> new ResourceNotFoundException(dto.getReturnOfficeId()));
        CarPickUp carPickUp = createCarPickUp(dto, car, pickUpOffice);
        CarReturn carReturn = createCarReturn(dto, car, returnOffice);

        return Reservation.builder()
                .reservationDate(dto.getReservationDate())
                .price(dto.getPrice())
                .dateFrom(dto.getDateFrom())
                .dateTo(dto.getDateTo())
                .status(ReservationStatus.PLANNED)
                .client(client)
                .car(car)
                .pickUpOffice(pickUpOffice)
                .returnOffice(returnOffice)
                .carPickUp(carPickUp)
                .carReturn(carReturn)
                .build();
    }

    private CarReturn createCarReturn(ReservationDto dto, Car car, BranchOffice office) {
        return CarReturn.builder()
                .plannedReturnDate(dto.getDateTo())
                .status(RentalActionStatus.PLANNED)
                .car(car)
                .branchOffice(office)
                .build();
    }

    private CarPickUp createCarPickUp(ReservationDto dto, Car car, BranchOffice office) {
        return CarPickUp.builder()
                .plannedPickUpDate(dto.getDateFrom())
                .status(RentalActionStatus.PLANNED)
                .car(car)
                .branchOffice(office)
                .build();
    }

    //TODO implement
    @Override
    public ReservationUpdateDto toUpdateEntity(ReservationDto dto) {
        Client client = dto.getClientId() != null ? clientRepository.findById(dto.getClientId()).orElse(null) : null;
        BranchOffice pickUpOffice = dto.getPickUpOfficeId() != null ? officeRepository.findById(dto.getPickUpOfficeId()).orElse(null) : null;
        BranchOffice returnOffice = dto.getReturnOfficeId() != null ? officeRepository.findById(dto.getReturnOfficeId()).orElse(null) : null;
        return ReservationUpdateDto.builder()
                .dateFrom(dto.getDateFrom())
                .dateTo(dto.getDateTo())
                .client(client)
                .pickUpOffice(pickUpOffice)
                .returnOffice(returnOffice)
                .status(dto.getStatus())
                .build();
    }

    @Override
    public ReservationDto toDto(Reservation entity) {

        Long clientId = entity.getClient() != null ? entity.getClient().getId() : null;
        Long carId = entity.getCar() != null ? entity.getCar().getId() : null;
        Long pickUpOfficeId = entity.getPickUpOffice() != null ? entity.getPickUpOffice().getId() : null;
        Long returnOfficeId = entity.getReturnOffice() != null ? entity.getReturnOffice().getId() : null;

        return ReservationDto.builder()
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

    public ReservationClientResponse toReservationClientResponse(Reservation reservation) {

        Long clientId = reservation.getClient() != null ? reservation.getClient().getId() : null;

        return ReservationClientResponse.builder()
                .id(reservation.getId())
                .dateFrom(reservation.getDateFrom())
                .dateTo(reservation.getDateTo())
                .price(reservation.getPrice())
                .status(reservation.getStatus())
                .clientId(clientId)
                .car(carMapper.toDto(reservation.getCar()))
                .pickUpOffice(officeMapper.toDto(reservation.getPickUpOffice()))
                .returnOffice(officeMapper.toDto(reservation.getReturnOffice()))
                .build();
    }
}
