package com.carrentalbackend.model.mapper;

import com.carrentalbackend.model.dto.crudDto.CarReturnDto;
import com.carrentalbackend.model.dto.updateDto.CarReturnUpdateDto;
import com.carrentalbackend.model.entity.*;
import com.carrentalbackend.model.rest.request.create.CreateRequest;
import com.carrentalbackend.model.rest.response.Response;
import com.carrentalbackend.repository.CarRepository;
import com.carrentalbackend.repository.EmployeeRepository;
import com.carrentalbackend.repository.OfficeRepository;
import com.carrentalbackend.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CarReturnMapper implements CrudMapper<CarReturn, CarReturnDto> {

    private final EmployeeRepository employeeRepository;
    private final ReservationRepository reservationRepository;
    private final CarRepository carRepository;
    private final OfficeRepository officeRepository;

    @Override
    public CarReturn toNewEntity(CarReturnDto dto) {
        Employee employee = dto.getEmployeeId() != null ? employeeRepository.getReferenceById(dto.getEmployeeId()) : null;
        Reservation reservation = dto.getReservationId() != null ? reservationRepository.getReferenceById(dto.getReservationId()) : null;
        Car car = dto.getCarId() != null ? carRepository.getReferenceById(dto.getCarId()) : null;
        Office office = dto.getBranchOfficeId() != null ? officeRepository.getReferenceById(dto.getBranchOfficeId()) : null;

        return CarReturn.builder()
                .comments(dto.getComments())
                .extraCharge(dto.getExtraCharge())
                .returnDate(dto.getReturnDate())
                .plannedReturnDate(dto.getPlannedReturnDate())
                .status(dto.getStatus())
                .employee(employee)
                .reservation(reservation)
                .car(car)
                .office(office)
                .build();
    }

    @Override
    public CarReturnUpdateDto toUpdateEntity(CarReturnDto dto) {
        Employee employee = dto.getEmployeeId() != null ? employeeRepository.getReferenceById(dto.getEmployeeId()) : null;
        return CarReturnUpdateDto.builder()
                .comments(dto.getComments())
                .extraCharge(dto.getExtraCharge())
                .returnDate(dto.getReturnDate())
                .status(dto.getStatus())
                .employee(employee)
                .build();
    }

    @Override
    public CarReturnDto toDto(CarReturn entity) {
        Long employeeId = entity.getEmployee() != null ? entity.getEmployee().getId() : null;
        Long reservationId = entity.getReservation() != null ? entity.getReservation().getId() : null;
        Long carId = entity.getCar() != null ? entity.getCar().getId() : null;
        int carMileage = entity.getCar() != null ? entity.getCar().getMileage() : 0;
        Long branchOfficeId = entity.getOffice() != null ? entity.getOffice().getId() : null;
        return CarReturnDto.builder()
                .id(entity.getId())
                .comments(entity.getComments())
                .extraCharge(entity.getExtraCharge())
                .returnDate(entity.getReturnDate())
                .plannedReturnDate(entity.getPlannedReturnDate())
                .status(entity.getStatus())
                .employeeId(employeeId)
                .reservationId(reservationId)
                .carId(carId)
                .branchOfficeId(branchOfficeId)
                .mileage(carMileage)
                .build();
    }

    @Override
    public CarReturn toNewEntity(CreateRequest request) {
        return null;
    }

    @Override
    public Response toResponse(CarReturn entity) {
        return null;
    }
}
