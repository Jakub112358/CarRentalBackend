package com.carrentalbackend.model.mapper;

import com.carrentalbackend.model.dto.crudDto.PickUpDto;
import com.carrentalbackend.model.dto.updateDto.PickUpUpdateDto;
import com.carrentalbackend.model.entity.*;
import com.carrentalbackend.repository.CarRepository;
import com.carrentalbackend.repository.EmployeeRepository;
import com.carrentalbackend.repository.OfficeRepository;
import com.carrentalbackend.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PickUpMapper implements CrudMapper<CarPickUp, PickUpDto> {
    private final EmployeeRepository employeeRepository;
    private final CarRepository carRepository;
    private final ReservationRepository reservationRepository;
    private final OfficeRepository officeRepository;
    //TODO: implement
    @Override
    public CarPickUp toNewEntity(PickUpDto dto) {
        return null;
    }

    @Override
    public PickUpUpdateDto toUpdateEntity(PickUpDto dto) {
        Employee employee = dto.getEmployeeId() != null ? employeeRepository.getReferenceById(dto.getEmployeeId()) : null;
        Reservation reservation = dto.getReservationId() != null ? reservationRepository.getReferenceById(dto.getReservationId()) : null;
        Car car = dto.getCarId() != null ? carRepository.getReferenceById(dto.getId()) : null;
        BranchOffice office = dto.getBranchOfficeId() != null ? officeRepository.getReferenceById(dto.getBranchOfficeId()) : null;

        return PickUpUpdateDto.builder()
                .comments(dto.getComments())
                .pickUpDate(dto.getPickUpDate())
                .plannedPickUpDate(dto.getPlannedPickUpDate())
                .status(dto.getStatus())
                .employee(employee)
                .reservation(reservation)
                .car(car)
                .branchOffice(office)
                .build();
    }

    @Override
    public PickUpDto toDto(CarPickUp entity) {
        Long employeeId = entity.getEmployee() != null ? entity.getEmployee().getId() : null;
        Long reservationId = entity.getReservation() != null ? entity.getReservation().getId() : null;
        Long carId = entity.getCar() != null ? entity.getCar().getId() : null;
        Long branchOfficeId = entity.getBranchOffice() != null ? entity.getBranchOffice().getId() : null;
        return PickUpDto.builder()
                .id(entity.getId())
                .comments(entity.getComments())
                .plannedPickUpDate(entity.getPlannedPickUpDate())
                .pickUpDate(entity.getPickUpDate())
                .status(entity.getStatus())
                .employeeId(employeeId)
                .reservationId(reservationId)
                .carId(carId)
                .branchOfficeId(branchOfficeId)
                .build();
    }
}
