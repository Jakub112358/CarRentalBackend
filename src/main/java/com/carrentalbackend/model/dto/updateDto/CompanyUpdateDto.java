package com.carrentalbackend.model.dto.updateDto;

import com.carrentalbackend.model.entity.Address;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyUpdateDto implements UpdateDto {
    private String name;
    private String domain;
    private byte[] logotype;
    private Address address;
}
