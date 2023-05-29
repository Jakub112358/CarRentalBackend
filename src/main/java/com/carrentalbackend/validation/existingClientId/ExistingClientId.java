package com.carrentalbackend.validation.existingClientId;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExistingClientIdValidator.class)
@Documented
public @interface ExistingClientId {
    String message() default "Client with given id doesn't exist in database";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default { };
}
