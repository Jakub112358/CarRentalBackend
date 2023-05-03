package com.carrentalbackend.service;

import com.carrentalbackend.model.dto.EmployeeDto;
import com.carrentalbackend.model.entity.Employee;
import com.carrentalbackend.model.mapper.EmployeeMapper;
import com.carrentalbackend.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService extends CrudService<Employee, EmployeeDto> {
    private final EmployeeRepository employeeRepository;


    public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        super(employeeRepository, employeeMapper);
        this.employeeRepository = employeeRepository;
    }

    //TODO implement delete method
    @Override
    public void deleteById(Long id) {

    }
}
