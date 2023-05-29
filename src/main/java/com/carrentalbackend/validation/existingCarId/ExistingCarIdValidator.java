package com.carrentalbackend.validation.existingCarId;

import com.carrentalbackend.repository.CarRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ExistingCarIdValidator implements ConstraintValidator<ExistingCarId, Long> {
    private final CarRepository carRepository;

    @Override
    public boolean isValid(Long carId, ConstraintValidatorContext context) {
        if (carId == null)
            return false;
        return carRepository.existsById(carId);
    }
}
