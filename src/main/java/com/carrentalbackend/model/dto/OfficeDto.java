package com.carrentalbackend.model.dto;

import com.carrentalbackend.model.entity.Address;
import com.carrentalbackend.model.entity.BranchOffice;
import com.carrentalbackend.model.entity.Company;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfficeDto implements CrudDto<BranchOffice>{
private long id;
private Address address;
private Company company;

    @Override
    public BranchOffice toEntity() {
        return BranchOffice.builder()
                .build();
    }

}
