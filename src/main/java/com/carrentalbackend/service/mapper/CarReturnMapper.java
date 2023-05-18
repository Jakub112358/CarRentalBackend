package com.carrentalbackend.service.mapper;

import com.carrentalbackend.features.generics.CrudMapper;
import com.carrentalbackend.model.dto.updateDto.CarReturnUpdateDto;
import com.carrentalbackend.model.dto.updateDto.UpdateDto;
import com.carrentalbackend.model.entity.*;
import com.carrentalbackend.model.rest.request.create.CarReturnCreateRequest;
import com.carrentalbackend.model.rest.request.update.CarReturnUpdateRequest;
import com.carrentalbackend.model.rest.response.CarReturnResponse;
import com.carrentalbackend.features.generics.Response;
import com.carrentalbackend.repository.CarRepository;
import com.carrentalbackend.repository.EmployeeRepository;
import com.carrentalbackend.repository.OfficeRepository;
import com.carrentalbackend.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CarReturnMapper implements CrudMapper<CarReturn, CarReturnUpdateRequest, CarReturnCreateRequest> {

    private final EmployeeRepository employeeRepository;
    private final ReservationRepository reservationRepository;
    private final CarRepository carRepository;
    private final OfficeRepository officeRepository;

    @Override
    public CarReturn toNewEntity(CarReturnCreateRequest request) {

        Employee employee = request.getEmployeeId() != null ? employeeRepository.getReferenceById(request.getEmployeeId()) : null;
        Reservation reservation = request.getReservationId() != null ? reservationRepository.getReferenceById(request.getReservationId()) : null;
        Car car = request.getCarId() != null ? carRepository.getReferenceById(request.getCarId()) : null;
        Office office = request.getBranchOfficeId() != null ? officeRepository.getReferenceById(request.getBranchOfficeId()) : null;

        return CarReturn.builder()
                .comments(request.getComments())
                .extraCharge(request.getExtraCharge())
                .returnDate(request.getReturnDate())
                .plannedReturnDate(request.getPlannedReturnDate())
                .status(request.getStatus())
                .employee(employee)
                .reservation(reservation)
                .car(car)
                .office(office)
                .build();
    }

    @Override
    public Response toResponse(CarReturn entity) {
        Long employeeId = entity.getEmployee() != null ? entity.getEmployee().getId() : null;
        Long reservationId = entity.getReservation() != null ? entity.getReservation().getId() : null;
        Long carId = entity.getCar() != null ? entity.getCar().getId() : null;
        int carMileage = entity.getCar() != null ? entity.getCar().getMileage() : 0;
        Long branchOfficeId = entity.getOffice() != null ? entity.getOffice().getId() : null;
        return CarReturnResponse.builder()
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
    public UpdateDto toUpdateDto(CarReturnUpdateRequest request) {

        Employee employee = request.getEmployeeId() != null ? employeeRepository.getReferenceById(request.getEmployeeId()) : null;
        return CarReturnUpdateDto.builder()
                .comments(request.getComments())
                .extraCharge(request.getExtraCharge())
                .returnDate(request.getReturnDate())
                .status(request.getStatus())
                .employee(employee)
                .build();
    }
}
