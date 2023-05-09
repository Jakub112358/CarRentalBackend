package com.carrentalbackend.model.dto.crudDto;

import com.carrentalbackend.model.entity.Address;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyDto implements CrudDto {
    private long id;
    private String name;
    private String domain;
    private byte[] logotype;
    private Address address;
    private double differentOfficesExtraCharge;


}
