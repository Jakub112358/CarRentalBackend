package com.carrentalbackend.features.renting.pickUps.rest;

import com.carrentalbackend.model.entity.PickUp;
import com.carrentalbackend.repository.CarRepository;
import com.carrentalbackend.repository.EmployeeRepository;
import com.carrentalbackend.repository.OfficeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PickUpUpdateTool {
    private final EmployeeRepository employeeRepository;
    private final CarRepository carRepository;
    private final OfficeRepository officeRepository;


    public void updateEntity(PickUp entity, PickUpUpdateRequest updateRequest) {
        var employee = employeeRepository.getReferenceById(updateRequest.getEmployeeId());
        var car = carRepository.getReferenceById(updateRequest.getCarId());
        var office = officeRepository.getReferenceById(updateRequest.getOfficeId());

        entity.setComments(updateRequest.getComments());
        entity.setPickUpDate(updateRequest.getPickUpDate());
        entity.setPlannedPickUpDate(updateRequest.getPlannedPickUpDate());
        entity.setStatus(updateRequest.getStatus());
        entity.setEmployee(employee);
        entity.setCar(car);
        entity.setOffice(office);
    }
}
