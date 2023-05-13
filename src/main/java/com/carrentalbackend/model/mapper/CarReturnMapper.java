package com.carrentalbackend.model.mapper;

import com.carrentalbackend.model.dto.updateDto.CarReturnUpdateDto;
import com.carrentalbackend.model.dto.updateDto.UpdateDto;
import com.carrentalbackend.model.entity.*;
import com.carrentalbackend.model.rest.request.create.CarReturnCreateRequest;
import com.carrentalbackend.model.rest.request.create.CreateRequest;
import com.carrentalbackend.model.rest.request.update.CarReturnUpdateRequest;
import com.carrentalbackend.model.rest.request.update.UpdateRequest;
import com.carrentalbackend.model.rest.response.CarReturnResponse;
import com.carrentalbackend.model.rest.response.Response;
import com.carrentalbackend.repository.CarRepository;
import com.carrentalbackend.repository.EmployeeRepository;
import com.carrentalbackend.repository.OfficeRepository;
import com.carrentalbackend.repository.ReservationRepository;
import com.carrentalbackend.service.util.ServiceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CarReturnMapper implements CrudMapper<CarReturn> {

    private final EmployeeRepository employeeRepository;
    private final ReservationRepository reservationRepository;
    private final CarRepository carRepository;
    private final OfficeRepository officeRepository;

    @Override
    public CarReturn toNewEntity(CreateRequest request) {
        ServiceUtil.checkIfInstance(request, CarReturnCreateRequest.class);
        CarReturnCreateRequest carReturnRequest = (CarReturnCreateRequest) request;

        Employee employee = carReturnRequest.getEmployeeId() != null ? employeeRepository.getReferenceById(carReturnRequest.getEmployeeId()) : null;
        Reservation reservation = carReturnRequest.getReservationId() != null ? reservationRepository.getReferenceById(carReturnRequest.getReservationId()) : null;
        Car car = carReturnRequest.getCarId() != null ? carRepository.getReferenceById(carReturnRequest.getCarId()) : null;
        Office office = carReturnRequest.getBranchOfficeId() != null ? officeRepository.getReferenceById(carReturnRequest.getBranchOfficeId()) : null;

        return CarReturn.builder()
                .comments(carReturnRequest.getComments())
                .extraCharge(carReturnRequest.getExtraCharge())
                .returnDate(carReturnRequest.getReturnDate())
                .plannedReturnDate(carReturnRequest.getPlannedReturnDate())
                .status(carReturnRequest.getStatus())
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
    public UpdateDto toUpdateDto(UpdateRequest request) {
        ServiceUtil.checkIfInstance(request, CarReturnUpdateRequest.class);
        CarReturnUpdateRequest carUpdateRequest = (CarReturnUpdateRequest) request;

        Employee employee = carUpdateRequest.getEmployeeId() != null ? employeeRepository.getReferenceById(carUpdateRequest.getEmployeeId()) : null;
        return CarReturnUpdateDto.builder()
                .comments(carUpdateRequest.getComments())
                .extraCharge(carUpdateRequest.getExtraCharge())
                .returnDate(carUpdateRequest.getReturnDate())
                .status(carUpdateRequest.getStatus())
                .employee(employee)
                .build();
    }
}
