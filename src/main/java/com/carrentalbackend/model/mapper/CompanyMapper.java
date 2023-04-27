package com.carrentalbackend.model.mapper;

import com.carrentalbackend.model.dto.CompanyDto;
import com.carrentalbackend.model.entity.Company;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper implements CrudMapper<Company, CompanyDto> {
    @Override
    public Company toEntity(CompanyDto dto) {
        return Company.builder()
                .id(dto.getId())
                .name(dto.getName())
                .domain(dto.getDomain())
                .logotype(dto.getLogotype())
                .address(dto.getAddress())
                .build();
    }

    @Override
    public CompanyDto toDto(Company entity) {
        return CompanyDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .domain(entity.getDomain())
                .logotype(entity.getLogotype())
                .address(entity.getAddress())
                .build();
    }
}
