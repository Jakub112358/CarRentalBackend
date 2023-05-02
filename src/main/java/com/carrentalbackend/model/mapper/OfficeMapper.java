package com.carrentalbackend.model.mapper;

import com.carrentalbackend.exception.ResourceNotFoundException;
import com.carrentalbackend.model.dto.OfficeDto;
import com.carrentalbackend.model.entity.BranchOffice;
import com.carrentalbackend.model.entity.Company;
import com.carrentalbackend.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OfficeMapper implements CrudMapper<BranchOffice, OfficeDto> {
    private final CompanyRepository companyRepository;

    @Override
    public BranchOffice toNewEntity(OfficeDto dto) {
        Company company = companyRepository.findById(dto.getCompanyId()).orElseThrow(() -> new ResourceNotFoundException(dto.getCompanyId()));
        return BranchOffice.builder()
                .id(dto.getId())
                .address(dto.getAddress())
                .company(company)
                .build();
    }

    @Override
    public BranchOffice toUpdateEntity(OfficeDto dto) {
        Company company = null;
        if (dto.getCompanyId() != null){
            company = companyRepository.findById(dto.getCompanyId()).orElseThrow(() -> new ResourceNotFoundException(dto.getCompanyId()));
        }
        return BranchOffice.builder()
                .address(dto.getAddress())
                .company(company)
                .build();
    }

    @Override
    public OfficeDto toDto(BranchOffice entity) {
        return OfficeDto.builder()
                .id(entity.getId())
                .address(entity.getAddress())
                .companyId(entity.getCompany().getId())
                .build();
    }

}
