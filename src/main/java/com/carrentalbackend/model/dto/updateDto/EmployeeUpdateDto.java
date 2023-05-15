package com.carrentalbackend.model.dto.updateDto;

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
