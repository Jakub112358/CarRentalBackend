package com.carrentalbackend.model.dto;

import com.carrentalbackend.model.entity.Address;
import com.carrentalbackend.model.entity.Company;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDto implements CrudDto<Company>{
    private long id;
    private String name;
    private String domain;
    private byte[] logotype;
    private Address address;


    public static CompanyDto toDTO(Company company){
        return CompanyDto.builder()
                .id(company.getId())
                .name(company.getName())
                .domain(company.getDomain())
                .logotype(company.getLogotype())
                .address(company.getAddress())
                .build();
    }

    @Override
    public Company toEntity() {
        return Company.builder()
                .name(this.name)
                .domain(this.domain)
                .logotype(this.logotype)
                .address(this.address)
                .build();
    }



}
