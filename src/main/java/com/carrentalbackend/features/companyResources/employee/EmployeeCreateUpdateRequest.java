package com.carrentalbackend.features.companyResources.employee;

import com.carrentalbackend.features.generics.CreateRequest;
import com.carrentalbackend.features.generics.UpdateRequest;
import com.carrentalbackend.model.enumeration.JobPosition;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeCreateUpdateRequest implements CreateRequest, UpdateRequest {
    private String firstName;
    private String lastName;
    private JobPosition jobPosition;
    private Long branchOfficeId;
    private String email;
    private String password;
}
