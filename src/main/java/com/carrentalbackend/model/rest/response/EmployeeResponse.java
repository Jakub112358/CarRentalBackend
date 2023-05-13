package com.carrentalbackend.model.rest.response;

import com.carrentalbackend.model.enumeration.JobPosition;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeResponse implements Response{
    private long id;
    private String firstName;
    private String lastName;
    private JobPosition jobPosition;
    private Long branchOfficeId;
}
