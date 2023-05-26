package com.carrentalbackend.features.employees;

import com.carrentalbackend.exception.ResourceNotFoundException;
import com.carrentalbackend.features.employees.rest.EmployeeMapper;
import com.carrentalbackend.features.employees.rest.EmployeeCreateRequest;
import com.carrentalbackend.features.employees.rest.EmployeeUpdateRequest;
import com.carrentalbackend.features.employees.rest.EmployeeUpdateTool;
import com.carrentalbackend.features.generics.CrudService;
import com.carrentalbackend.model.entity.CarReturn;
import com.carrentalbackend.model.entity.Employee;
import com.carrentalbackend.features.generics.Response;
import com.carrentalbackend.model.entity.PickUp;
import com.carrentalbackend.repository.CarReturnRepository;
import com.carrentalbackend.repository.EmployeeRepository;
import com.carrentalbackend.repository.PickUpRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeService extends CrudService<Employee, EmployeeCreateRequest, EmployeeUpdateRequest> {
    private final EmployeeRepository employeeRepository;
    private final PickUpRepository pickUpRepository;
    private final CarReturnRepository carReturnRepository;

    public EmployeeService(EmployeeRepository employeeRepository,
                           EmployeeMapper employeeMapper,
                           EmployeeUpdateTool updateTool,
                           PickUpRepository pickUpRepository,
                           CarReturnRepository carReturnRepository) {
        super(employeeRepository, employeeMapper, updateTool);
        this.employeeRepository = employeeRepository;
        this.pickUpRepository = pickUpRepository;
        this.carReturnRepository = carReturnRepository;
    }

    @Override
    public void deleteById(Long id) {
        throwIfDoesNotExist(id);

        nullEmployeeInPickUps(id);
        nullEmployeeInCarReturns(id);

        employeeRepository.deleteById(id);
    }

    private void nullEmployeeInCarReturns(Long id) {
        List<CarReturn> carReturns = carReturnRepository.findAllByEmployee_id(id);
        carReturns.forEach(cr -> cr.setEmployee(null));
    }

    private void nullEmployeeInPickUps(Long id) {
        List<PickUp> pickups = pickUpRepository.findAllByEmployee_id(id);
        pickups.forEach(pu -> pu.setEmployee(null));
    }

    private void throwIfDoesNotExist(Long id) {
        if(!employeeRepository.existsById(id))
            throw new ResourceNotFoundException(id);
    }

    public Set<Response> findAllByOfficeId(Long id) {
        return employeeRepository.findAllByOffice_Id(id).stream().map(mapper::toResponse).collect(Collectors.toSet());
    }
}
