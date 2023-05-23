package com.carrentalbackend.features.companyResources.employees;

import com.carrentalbackend.features.companyResources.employees.rest.EmployeeMapper;
import com.carrentalbackend.features.companyResources.employees.rest.EmployeeRequest;
import com.carrentalbackend.features.companyResources.employees.rest.EmployeeUpdateTool;
import com.carrentalbackend.features.generics.CrudService;
import com.carrentalbackend.model.entity.Employee;
import com.carrentalbackend.features.generics.Response;
import com.carrentalbackend.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeService extends CrudService<Employee, EmployeeRequest> {
    private final EmployeeRepository repository;


    public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper, EmployeeUpdateTool updateTool) {
        super(employeeRepository, employeeMapper, updateTool);
        this.repository = employeeRepository;
    }

    //TODO implement delete method
    @Override
    public void deleteById(Long id) {

    }

    public Set<Response> findAllByOfficeId(Long id) {
        return repository.findAllByOffice_Id(id).stream().map(mapper::toResponse).collect(Collectors.toSet());
    }
}
