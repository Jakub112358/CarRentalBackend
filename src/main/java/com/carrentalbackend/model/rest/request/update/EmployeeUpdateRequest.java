package com.carrentalbackend.model.rest.request.update;

import com.carrentalbackend.model.enumeration.JobPosition;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeUpdateRequest implements UpdateRequest{
    private String firstName;
    private String lastName;
    private JobPosition jobPosition;
    private Long branchOfficeId;
}
