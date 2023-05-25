package com.carrentalbackend.validation.existingOfficeId;

import com.carrentalbackend.repository.OfficeRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ExistingOfficeIdValidator implements ConstraintValidator<ExistingOfficeId, Long> {
    private final OfficeRepository officeRepository;
    @Override
    public boolean isValid(Long officeId, ConstraintValidatorContext context) {
        return officeRepository.existsById(officeId);
    }
}
