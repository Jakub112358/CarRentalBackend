package com.carrentalbackend.features.employees.rest;

import com.carrentalbackend.features.generics.Response;
import com.carrentalbackend.model.enumeration.JobPosition;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeResponse implements Response {
    private long id;
    private String email;
    private String firstName;
    private String lastName;
    private JobPosition jobPosition;
    private Long officeId;
}
