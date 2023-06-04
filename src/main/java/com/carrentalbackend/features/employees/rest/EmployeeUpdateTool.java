package com.carrentalbackend.features.employees.rest;

import com.carrentalbackend.exception.ExistingEmailException;
import com.carrentalbackend.features.generics.UpdateTool;
import com.carrentalbackend.model.entity.Employee;
import com.carrentalbackend.model.entity.Office;
import com.carrentalbackend.repository.OfficeRepository;
import com.carrentalbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmployeeUpdateTool implements UpdateTool<Employee, EmployeeUpdateRequest> {
    private final OfficeRepository officeRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void updateEntity(Employee entity, EmployeeUpdateRequest updateRequest) {
        throwIfInvalidEmail(entity, updateRequest);

        Office office = officeRepository.getReferenceById(updateRequest.getOfficeId());
        String password = passwordEncoder.encode(updateRequest.getPassword());

        entity.setFirstName(updateRequest.getFirstName());
        entity.setLastName(updateRequest.getLastName());
        entity.setJobPosition(updateRequest.getJobPosition());
        entity.setOffice(office);
        entity.setEmail(updateRequest.getEmail());
        entity.setPassword(password);
    }

    private void throwIfInvalidEmail(Employee entity, EmployeeUpdateRequest updateRequest) {
        if (entity.getEmail().equals(updateRequest.getEmail()))
            return;
        if (userRepository.existsByEmail(updateRequest.getEmail()))
            throw new ExistingEmailException(updateRequest.getEmail());
    }
}
