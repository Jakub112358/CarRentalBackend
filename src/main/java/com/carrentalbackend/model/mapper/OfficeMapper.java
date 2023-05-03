package com.carrentalbackend.model.mapper;

import com.carrentalbackend.exception.ResourceNotFoundException;
import com.carrentalbackend.model.dto.OfficeDto;
import com.carrentalbackend.model.dto.OfficeUpdateDto;
import com.carrentalbackend.model.dto.UpdateDto;
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
        Company company = findCompanyById(dto.getCompanyId());
        return BranchOffice.builder()
                .id(dto.getId())
                .address(dto.getAddress())
                .company(company)
                .build();
    }

    @Override
    public UpdateDto toUpdateEntity(OfficeDto dto) {
        Company company = findCompanyById(dto.getCompanyId());
        return OfficeUpdateDto.builder()
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

    private Company findCompanyById(Long id){
        if (id == null) {
            return null;
        } else {
            return companyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        }

    }

}
