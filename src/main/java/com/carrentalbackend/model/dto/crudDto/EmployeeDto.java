package com.carrentalbackend.model.dto.crudDto;

import com.carrentalbackend.model.dto.crudDto.CrudDto;
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
