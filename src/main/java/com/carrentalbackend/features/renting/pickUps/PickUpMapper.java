package com.carrentalbackend.features.renting.pickUps;

import com.carrentalbackend.exception.ResourceNotFoundException;
import com.carrentalbackend.features.generics.CrudMapper;
import com.carrentalbackend.features.generics.Response;
import com.carrentalbackend.model.entity.*;
import com.carrentalbackend.repository.CarRepository;
import com.carrentalbackend.repository.EmployeeRepository;
import com.carrentalbackend.repository.OfficeRepository;
import com.carrentalbackend.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PickUpMapper implements CrudMapper<PickUp, PickUpRequest> {
    private final EmployeeRepository employeeRepository;
    private final CarRepository carRepository;
    private final ReservationRepository reservationRepository;
    private final OfficeRepository officeRepository;

    @Override
    public PickUp toNewEntity(PickUpRequest request) {

        Employee employee = findEmployeeById(request.getEmployeeId());
        Reservation reservation = findReservationById(request.getReservationId());
        Car car = findCarById(request.getCarId());
        Office office = findOfficeById(request.getOfficeId());

        return PickUp.builder()
                .comments(request.getComments())
                .pickUpDate(request.getPickUpDate())
                .plannedPickUpDate(request.getPlannedPickUpDate())
                .status(request.getStatus())
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
