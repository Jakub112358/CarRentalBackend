package com.carrentalbackend.validation.existingCarId;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExistingCarIdValidator.class)
@Documented
public @interface ExistingCarId {
    String message() default "Car with given id doesn't exist in database";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default { };
}
