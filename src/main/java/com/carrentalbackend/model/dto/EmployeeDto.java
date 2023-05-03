package com.carrentalbackend.model.dto;

import com.carrentalbackend.model.enumeration.JobPosition;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDto implements CrudDto {
    private long id;
    private String firstName;
    private String lastName;
    private JobPosition jobPosition;
    private Long branchOfficeId;
}
