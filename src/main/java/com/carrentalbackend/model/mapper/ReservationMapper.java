package com.carrentalbackend.model.mapper;

import com.carrentalbackend.exception.ResourceNotFoundException;
import com.carrentalbackend.model.dto.ReservationDto;
import com.carrentalbackend.model.dto.UpdateDto;
import com.carrentalbackend.model.entity.*;
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
    @Override
    public Reservation toNewEntity(ReservationDto dto) {
        Client client = clientRepository.findById(dto.getClientId()).orElseThrow(()-> new ResourceNotFoundException(dto.getClientId()));
        Car car = carRepository.findById(dto.getCarId()).orElseThrow(()-> new ResourceNotFoundException(dto.getCarId()));
        BranchOffice pickUpOffice = officeRepository.findById(dto.getPickUpOfficeId()).orElseThrow(()-> new ResourceNotFoundException(dto.getPickUpOfficeId()));
        BranchOffice returnOffice = officeRepository.findById(dto.getReturnOfficeId()).orElseThrow(()-> new ResourceNotFoundException(dto.getReturnOfficeId()));

        return Reservation.builder()
                .id(dto.getId())
                .reservationDate(dto.getReservationDate())
                .price(dto.getPrice())
                .status(dto.getStatus())
                .dateTo(dto.getDateTo())
                .dateFrom(dto.getDateFrom())
                .client(client)
                .car(car)
                .pickUpOffice(pickUpOffice)
                .returnOffice(returnOffice)
                .carPickUp(new CarPickUp())
                .carReturn(new CarReturn())
                .build();
    }
//TODO implement
    @Override
    public UpdateDto toUpdateEntity(ReservationDto dto) {
        return null;
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
}
