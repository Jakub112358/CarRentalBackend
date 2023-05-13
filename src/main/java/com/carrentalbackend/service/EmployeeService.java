package com.carrentalbackend.service;

import com.carrentalbackend.model.entity.Employee;
import com.carrentalbackend.model.mapper.EmployeeMapper;
import com.carrentalbackend.model.rest.response.Response;
import com.carrentalbackend.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeService extends CrudService<Employee> {
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
