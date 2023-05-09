package com.carrentalbackend.model.mapper;

import com.carrentalbackend.model.dto.crudDto.CompanyDto;
import com.carrentalbackend.model.dto.updateDto.CompanyUpdateDto;
import com.carrentalbackend.model.dto.updateDto.UpdateDto;
import com.carrentalbackend.model.entity.Company;
import com.carrentalbackend.model.entity.Finances;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper implements CrudMapper<Company, CompanyDto> {

    @Override
    public Company toNewEntity(CompanyDto dto) {
        return Company.builder()
                .id(dto.getId())
                .name(dto.getName())
                .domain(dto.getDomain())
                .logotype(dto.getLogotype())
                .address(dto.getAddress())
                .finances(new Finances())
                .differentOfficesExtraCharge(dto.getDifferentOfficesExtraCharge())
                .build();
    }

    @Override
    public UpdateDto toUpdateEntity(CompanyDto dto) {
        return CompanyUpdateDto.builder()
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
