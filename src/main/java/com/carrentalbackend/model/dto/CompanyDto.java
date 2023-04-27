package com.carrentalbackend.model.dto;

import com.carrentalbackend.model.entity.Address;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDto implements CrudDto {
    private long id;
    private String name;
    private String domain;
    private byte[] logotype;
    private Address address;


}
