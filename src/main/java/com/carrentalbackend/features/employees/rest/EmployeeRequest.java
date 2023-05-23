package com.carrentalbackend.features.employees.rest;

import com.carrentalbackend.features.generics.Request;
import com.carrentalbackend.features.generics.UpdateRequest;
import com.carrentalbackend.model.enumeration.JobPosition;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeRequest implements Request, UpdateRequest {
    private String firstName;
    private String lastName;
    private JobPosition jobPosition;
    private Long branchOfficeId;
    private String email;
    private String password;
}
