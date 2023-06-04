package com.carrentalbackend.features.renting.carReturns.rest;

import com.carrentalbackend.features.generics.UpdateTool;
import com.carrentalbackend.model.entity.CarReturn;
import com.carrentalbackend.repository.CarRepository;
import com.carrentalbackend.repository.EmployeeRepository;
import com.carrentalbackend.repository.OfficeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CarReturnUpdateTool implements UpdateTool<CarReturn, CarReturnUpdateRequest> {
    private final EmployeeRepository employeeRepository;
    private final CarRepository carRepository;
    private final OfficeRepository officeRepository;

    @Override
    public void updateEntity(CarReturn entity, CarReturnUpdateRequest request) {
        var employee = employeeRepository.getReferenceById(request.getEmployeeId());
        var car = carRepository.getReferenceById(request.getCarId());
        var office = officeRepository.getReferenceById(request.getOfficeId());

        entity.setComments(request.getComments());
        entity.setExtraCharge(request.getExtraCharge());
        entity.setReturnDate(request.getReturnDate());
        entity.setPlannedReturnDate(request.getPlannedReturnDate());
        entity.setStatus(request.getStatus());
        entity.setEmployee(employee);
        entity.setCar(car);
        entity.setOffice(office);

    }
}
