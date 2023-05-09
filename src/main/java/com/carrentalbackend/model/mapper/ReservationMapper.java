package com.carrentalbackend.model.mapper;

import com.carrentalbackend.exception.ResourceNotFoundException;
import com.carrentalbackend.model.dto.crudDto.ReservationDto;
import com.carrentalbackend.model.dto.updateDto.UpdateDto;
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
public class ReservationMapper implements CrudMapper<Reservation, ReservationDto> {
    private final ClientRepository clientRepository;
    private final CarRepository carRepository;
    private final OfficeRepository officeRepository;
    @Override
    public Reservation toNewEntity(ReservationDto dto) {
        //TODO price should be recalculated!
        Client client = clientRepository.findById(dto.getClientId()).orElseThrow(()-> new ResourceNotFoundException(dto.getClientId()));
        Car car = carRepository.findById(dto.getCarId()).orElseThrow(()-> new ResourceNotFoundException(dto.getCarId()));
        BranchOffice pickUpOffice = officeRepository.findById(dto.getPickUpOfficeId()).orElseThrow(()-> new ResourceNotFoundException(dto.getPickUpOfficeId()));
        BranchOffice returnOffice = officeRepository.findById(dto.getReturnOfficeId()).orElseThrow(()-> new ResourceNotFoundException(dto.getReturnOfficeId()));
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
