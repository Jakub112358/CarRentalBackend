package com.carrentalbackend.features.companyResources.employee;

import com.carrentalbackend.exception.ResourceNotFoundException;
import com.carrentalbackend.features.generics.CrudMapper;
import com.carrentalbackend.features.generics.Response;
import com.carrentalbackend.model.entity.Employee;
import com.carrentalbackend.model.entity.Office;
import com.carrentalbackend.model.enumeration.Role;
import com.carrentalbackend.repository.OfficeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class EmployeeMapper implements CrudMapper<Employee, EmployeeRequest> {
    private final OfficeRepository officeRepository;
    private final PasswordEncoder passwordEncoder;

    private Office findOfficeById(Long id) {
        if (id == null) {
            return null;
        } else {
            return officeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        }
    }

    @Override
    public Employee toNewEntity(EmployeeRequest request) {

        Office office = findOfficeById(request.getBranchOfficeId());
        return Employee.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .jobPosition(request.getJobPosition())
                .office(office)
                .pickUps(new ArrayList<>())
                .email(request.getEmail())
                .role(Role.EMPLOYEE)
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
    }

    @Override
    public Response toResponse(Employee entity) {
        Long officeId = entity.getOffice() != null ? entity.getOffice().getId() : null;
        return EmployeeResponse.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .jobPosition(entity.getJobPosition())
                .officeId(officeId)
                .build();
    }

}
