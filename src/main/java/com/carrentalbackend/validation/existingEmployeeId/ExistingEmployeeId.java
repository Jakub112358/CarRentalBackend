package com.carrentalbackend.validation.existingEmployeeId;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExistingEmployeeIdValidator.class)
@Documented
public @interface ExistingEmployeeId {
    String message() default "Employee with given id doesn't exist in database";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default { };
}
