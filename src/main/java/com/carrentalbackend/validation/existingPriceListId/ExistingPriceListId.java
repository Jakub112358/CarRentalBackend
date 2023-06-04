package com.carrentalbackend.validation.existingPriceListId;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExistingPriceListIdValidator.class)
@Documented
public @interface ExistingPriceListId {
    String message() default "PriceList with given id doesn't exist in database";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default { };
}
