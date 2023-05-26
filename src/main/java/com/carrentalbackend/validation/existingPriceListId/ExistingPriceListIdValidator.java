package com.carrentalbackend.validation.existingPriceListId;

import com.carrentalbackend.repository.PriceListRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ExistingPriceListIdValidator implements ConstraintValidator<ExistingPriceListId, Long> {
    private final PriceListRepository priceListRepository;

    @Override
    public boolean isValid(Long priceListId, ConstraintValidatorContext context) {
        if (priceListId == null)
            return false;
        return priceListRepository.existsById(priceListId);
    }
}
