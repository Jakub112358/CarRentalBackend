package com.carrentalbackend.features.employees.rest;

import com.carrentalbackend.validation.existingOfficeId.ExistingOfficeId;
import com.carrentalbackend.validation.uniqueEmail.UniqueEmail;
import com.carrentalbackend.features.generics.Request;
import com.carrentalbackend.features.generics.UpdateRequest;
import com.carrentalbackend.model.enumeration.JobPosition;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeRequest implements Request, UpdateRequest {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotNull
    private JobPosition jobPosition;
    @NotNull
    @ExistingOfficeId
    private Long officeId;
    @Email
    @UniqueEmail
    private String email;
    @NotEmpty
    private String password;

}
