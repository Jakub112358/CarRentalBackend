package com.carrentalbackend.features.employees.rest;

import com.carrentalbackend.features.generics.CreateRequest;
import com.carrentalbackend.model.enumeration.JobPosition;
import com.carrentalbackend.validation.existingOfficeId.ExistingOfficeId;
import com.carrentalbackend.validation.uniqueEmail.UniqueEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeCreateRequest implements CreateRequest {
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
