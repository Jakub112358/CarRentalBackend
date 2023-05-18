package com.carrentalbackend.service;

import com.carrentalbackend.features.generics.CrudService;
import com.carrentalbackend.model.entity.Employee;
import com.carrentalbackend.model.rest.request.create.EmployeeCreateRequest;
import com.carrentalbackend.model.rest.request.update.EmployeeUpdateRequest;
import com.carrentalbackend.service.mapper.EmployeeMapper;
import com.carrentalbackend.features.generics.Response;
import com.carrentalbackend.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeService extends CrudService<Employee, EmployeeUpdateRequest, EmployeeCreateRequest> {
    private final EmployeeRepository repository;


    public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        super(employeeRepository, employeeMapper);
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
