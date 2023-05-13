package com.carrentalbackend.model.mapper;

import com.carrentalbackend.exception.ResourceNotFoundException;
import com.carrentalbackend.model.dto.updateDto.PickUpUpdateDto;
import com.carrentalbackend.model.dto.updateDto.UpdateDto;
import com.carrentalbackend.model.entity.*;
import com.carrentalbackend.model.rest.request.create.CreateRequest;
import com.carrentalbackend.model.rest.request.create.PickUpCreateRequest;
import com.carrentalbackend.model.rest.request.update.UpdateRequest;
import com.carrentalbackend.model.rest.response.PickUpResponse;
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
public class PickUpMapper implements CrudMapper<PickUp> {
    private final EmployeeRepository employeeRepository;
    private final CarRepository carRepository;
    private final ReservationRepository reservationRepository;
    private final OfficeRepository officeRepository;

    @Override
    public PickUp toNewEntity(CreateRequest request) {
        ServiceUtil.checkIfInstance(request, PickUpCreateRequest.class);
        PickUpCreateRequest pickUpRequest = (PickUpCreateRequest) request;

        Employee employee = findEmployeeById(pickUpRequest.getEmployeeId());
        Reservation reservation = findReservationById(pickUpRequest.getReservationId());
        Car car = findCarById(pickUpRequest.getCarId());
        Office office = findOfficeById(pickUpRequest.getOfficeId());

        return PickUp.builder()
                .comments(pickUpRequest.getComments())
                .pickUpDate(pickUpRequest.getPickUpDate())
                .plannedPickUpDate(pickUpRequest.getPlannedPickUpDate())
                .status(pickUpRequest.getStatus())
                .employee(employee)
                .reservation(reservation)
                .car(car)
                .office(office)
                .build();
    }

    @Override
    public Response toResponse(PickUp entity) {
        Long employeeId = entity.getEmployee() != null ? entity.getEmployee().getId() : null;
        Long reservationId = entity.getReservation() != null ? entity.getReservation().getId() : null;
        Long carId = entity.getCar() != null ? entity.getCar().getId() : null;
        Long branchOfficeId = entity.getOffice() != null ? entity.getOffice().getId() : null;
        return PickUpResponse.builder()
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

    @Override
    public UpdateDto toUpdateDto(UpdateRequest request) {
        ServiceUtil.checkIfInstance(request, PickUpCreateRequest.class);
        PickUpCreateRequest pickUpRequest = (PickUpCreateRequest) request;

        Employee employee = pickUpRequest.getEmployeeId() != null ? employeeRepository.getReferenceById(pickUpRequest.getEmployeeId()) : null;
        Reservation reservation = pickUpRequest.getReservationId() != null ? reservationRepository.getReferenceById(pickUpRequest.getReservationId()) : null;
        Car car = pickUpRequest.getCarId() != null ? carRepository.getReferenceById(pickUpRequest.getCarId()) : null;
        Office office = pickUpRequest.getOfficeId() != null ? officeRepository.getReferenceById(pickUpRequest.getOfficeId()) : null;

        return PickUpUpdateDto.builder()
                .comments(pickUpRequest.getComments())
                .pickUpDate(pickUpRequest.getPickUpDate())
                .plannedPickUpDate(pickUpRequest.getPlannedPickUpDate())
                .status(pickUpRequest.getStatus())
                .employee(employee)
                .reservation(reservation)
                .car(car)
                .office(office)
                .build();
    }

    private Office findOfficeById(Long id) {
        if (id == null) {
            return null;
        } else {
            return officeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        }
    }

    private Car findCarById(Long id) {
        if (id == null) {
            return null;
        } else {
            return carRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        }
    }

    private Reservation findReservationById(Long id) {
        if (id == null) {
            return null;
        } else {
            return reservationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        }
    }

    private Employee findEmployeeById(Long id) {
        if (id == null) {
            return null;
        } else {
            return employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        }
    }
}
