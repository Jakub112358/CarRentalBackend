package com.carrentalbackend.service;

import com.carrentalbackend.model.dto.crudDto.EmployeeDto;
import com.carrentalbackend.model.entity.Employee;
import com.carrentalbackend.model.mapper.EmployeeMapper;
import com.carrentalbackend.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService extends CrudService<Employee, EmployeeDto> {
    private final EmployeeRepository repository;


    public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        super(employeeRepository, employeeMapper);
        this.repository = employeeRepository;
    }

    //TODO implement delete method
    @Override
    public void deleteById(Long id) {

    }

    public List<EmployeeDto> findAllByBranchOfficeId(Long id) {
        return repository.findAllByOffice_Id(id).stream().map(mapper::toDto).toList();
    }
}
