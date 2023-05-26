package com.carrentalbackend.features.employees.rest;

import com.carrentalbackend.features.generics.UpdateRequest;
import com.carrentalbackend.model.enumeration.JobPosition;
import com.carrentalbackend.validation.existingOfficeId.ExistingOfficeId;
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
public class EmployeeUpdateRequest implements UpdateRequest {
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
    private String email;
    @NotEmpty
    private String password;
}
