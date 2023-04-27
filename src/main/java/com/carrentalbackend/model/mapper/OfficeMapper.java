package com.carrentalbackend.model.mapper;

import com.carrentalbackend.model.dto.OfficeDto;
import com.carrentalbackend.model.entity.BranchOffice;
import org.springframework.stereotype.Component;

@Component
public class OfficeMapper implements CrudMapper<BranchOffice, OfficeDto> {
    @Override
    public BranchOffice toEntity(OfficeDto dto) {
        return BranchOffice.builder()
                .id(dto.getId())
                .address(dto.getAddress())
                .build();
    }

    @Override
    public OfficeDto toDto(BranchOffice entity) {
        return OfficeDto.builder()
                .id(entity.getId())
                .address(entity.getAddress())
                .build();
    }

}
