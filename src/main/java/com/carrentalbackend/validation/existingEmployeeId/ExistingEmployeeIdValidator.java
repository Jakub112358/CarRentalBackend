package com.carrentalbackend.validation.existingEmployeeId;

import com.carrentalbackend.repository.EmployeeRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ExistingEmployeeIdValidator implements ConstraintValidator<ExistingEmployeeId, Long> {
    private final EmployeeRepository employeeRepository;

    @Override
    public boolean isValid(Long employeeId, ConstraintValidatorContext context) {
        if (employeeId == null)
            return false;
        return employeeRepository.existsById(employeeId);
    }
}
