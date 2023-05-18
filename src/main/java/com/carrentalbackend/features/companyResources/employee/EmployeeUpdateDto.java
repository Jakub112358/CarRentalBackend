package com.carrentalbackend.features.companyResources.employee;

import com.carrentalbackend.features.generics.UpdateDto;
import com.carrentalbackend.model.entity.Office;
import com.carrentalbackend.model.enumeration.JobPosition;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeUpdateDto implements UpdateDto {
    private String firstName;
    private String lastName;
    private JobPosition jobPosition;
    private Office office;
}
