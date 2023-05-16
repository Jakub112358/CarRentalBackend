package com.carrentalbackend.model.rest.request.create;

import com.carrentalbackend.model.enumeration.JobPosition;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeCreateRequest implements CreateRequest{
    private String firstName;
    private String lastName;
    private JobPosition jobPosition;
    private Long branchOfficeId;
    private String email;
    private String password;
}
