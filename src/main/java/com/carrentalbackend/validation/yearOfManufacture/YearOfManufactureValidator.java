package com.carrentalbackend.validation.yearOfManufacture;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class YearOfManufactureValidator implements ConstraintValidator<YearOfManufacture, Integer> {

    @Override
    public boolean isValid(Integer yearOfManufacture, ConstraintValidatorContext context) {
        if (yearOfManufacture == null)
            return false;
        return (yearOfManufacture >= 1900 && yearOfManufacture <= (LocalDate.now().getYear() + 1));
    }
}
