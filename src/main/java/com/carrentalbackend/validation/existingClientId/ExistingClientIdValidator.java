package com.carrentalbackend.validation.existingClientId;

import com.carrentalbackend.repository.ClientRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ExistingClientIdValidator implements ConstraintValidator<ExistingClientId, Long> {
    private final ClientRepository clientRepository;

    @Override
    public boolean isValid(Long clientId, ConstraintValidatorContext context) {
        if (clientId == null)
            return false;
        return clientRepository.existsById(clientId);
    }
}
