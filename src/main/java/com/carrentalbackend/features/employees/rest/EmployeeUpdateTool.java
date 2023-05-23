package com.carrentalbackend.features.employees.rest;

import com.carrentalbackend.features.generics.UpdateTool;
import com.carrentalbackend.model.entity.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeUpdateTool implements UpdateTool<Employee, EmployeeRequest> {
    @Override
    public void updateEntity(Employee entity, EmployeeRequest updateRequest) {
        //TODO: implement
    }
}
