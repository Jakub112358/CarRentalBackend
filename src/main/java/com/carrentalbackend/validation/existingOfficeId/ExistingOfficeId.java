package com.carrentalbackend.validation.existingOfficeId;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExistingOfficeIdValidator.class)
@Documented
public @interface ExistingOfficeId {
    String message() default "Office with given id doesn't exist in database";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default { };
}
