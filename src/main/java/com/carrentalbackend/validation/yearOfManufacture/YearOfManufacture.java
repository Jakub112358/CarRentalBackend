package com.carrentalbackend.validation.yearOfManufacture;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = YearOfManufactureValidator.class)
@Documented
public @interface YearOfManufacture {
    String message() default "incorrect year of manufacture; Must be between 1900 and current year + 1";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default { };
}
