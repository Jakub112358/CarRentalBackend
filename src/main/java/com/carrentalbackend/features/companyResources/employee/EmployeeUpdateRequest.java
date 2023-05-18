package com.carrentalbackend.features.companyResources.employee;

import com.carrentalbackend.model.enumeration.JobPosition;
import com.carrentalbackend.features.generics.UpdateRequest;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeUpdateRequest implements UpdateRequest {
    private String firstName;
    private String lastName;
    private JobPosition jobPosition;
    private Long branchOfficeId;
}
