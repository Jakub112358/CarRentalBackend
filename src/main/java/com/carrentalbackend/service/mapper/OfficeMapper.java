package com.carrentalbackend.service.mapper;

import com.carrentalbackend.exception.ResourceNotFoundException;
import com.carrentalbackend.features.generics.CrudMapper;
import com.carrentalbackend.model.dto.updateDto.OfficeUpdateDto;
import com.carrentalbackend.model.dto.updateDto.UpdateDto;
import com.carrentalbackend.model.entity.Company;
import com.carrentalbackend.model.entity.Office;
import com.carrentalbackend.model.rest.request.create.OfficeCreateRequest;
import com.carrentalbackend.model.rest.request.update.OfficeUpdateRequest;
import com.carrentalbackend.model.rest.response.OfficeResponse;
import com.carrentalbackend.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OfficeMapper implements CrudMapper<Office, OfficeUpdateRequest, OfficeCreateRequest> {
    private final CompanyRepository companyRepository;

    private Company findCompanyById(Long id) {
        if (id == null) {
            return null;
        } else {
            return companyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        }
    }

    @Override
    public Office toNewEntity(OfficeCreateRequest request) {

        Company company = findCompanyById(request.getCompanyId());
        return Office.builder()
                .address(request.getAddress())
                .company(company)
                .build();
    }

    @Override
    public OfficeResponse toResponse(Office entity) {
        return OfficeResponse.builder()
                .id(entity.getId())
                .address(entity.getAddress())
                .companyId(entity.getCompany().getId())
                .build();
    }

    @Override
    public UpdateDto toUpdateDto(OfficeUpdateRequest request) {

        Company company = findCompanyById(request.getCompanyId());
        return OfficeUpdateDto.builder()
                .address(request.getAddress())
                .company(company)
                .build();
    }
}
