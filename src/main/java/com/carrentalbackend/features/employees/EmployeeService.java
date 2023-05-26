package com.carrentalbackend.features.employees;

import com.carrentalbackend.features.employees.rest.EmployeeMapper;
import com.carrentalbackend.features.employees.rest.EmployeeCreateRequest;
import com.carrentalbackend.features.employees.rest.EmployeeUpdateRequest;
import com.carrentalbackend.features.employees.rest.EmployeeUpdateTool;
import com.carrentalbackend.features.generics.CrudService;
import com.carrentalbackend.model.entity.Employee;
import com.carrentalbackend.features.generics.Response;
import com.carrentalbackend.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeService extends CrudService<Employee, EmployeeCreateRequest, EmployeeUpdateRequest> {
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
