package com.carrentalbackend.features.clients.register.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueEmailValidator.class)
@Documented
public @interface UniqueEmail {
    String message() default "Client with given email already exists in database";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default { };
}
