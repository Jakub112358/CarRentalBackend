package com.carrentalbackend.features.companyResources.employee;

import com.carrentalbackend.features.generics.UpdateTool;
import com.carrentalbackend.model.entity.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeUpdateTool implements UpdateTool<Employee, EmployeeCreateUpdateRequest> {
    @Override
    public void updateEntity(Employee entity, EmployeeCreateUpdateRequest updateRequest) {
        //TODO: implement
    }
}
