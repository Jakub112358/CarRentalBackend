package com.carrentalbackend.model.dto.crudDto;

import com.carrentalbackend.model.dto.crudDto.CrudDto;
import com.carrentalbackend.model.entity.Address;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfficeDto implements CrudDto {
    private long id;
    private Address address;
    private Long companyId;
}