package com.carrentalbackend.model.mapper;

import com.carrentalbackend.exception.ResourceNotFoundException;
import com.carrentalbackend.model.dto.updateDto.OfficeUpdateDto;
import com.carrentalbackend.model.dto.updateDto.UpdateDto;
import com.carrentalbackend.model.entity.Company;
import com.carrentalbackend.model.entity.Office;
import com.carrentalbackend.model.rest.request.create.CreateRequest;
import com.carrentalbackend.model.rest.request.create.OfficeCreateRequest;
import com.carrentalbackend.model.rest.request.update.UpdateRequest;
import com.carrentalbackend.model.rest.response.OfficeResponse;
import com.carrentalbackend.repository.CompanyRepository;
import com.carrentalbackend.service.util.ServiceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OfficeMapper implements CrudMapper<Office> {
    private final CompanyRepository companyRepository;

    private Company findCompanyById(Long id) {
        if (id == null) {
            return null;
        } else {
            return companyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        }
    }

    @Override
    public Office toNewEntity(CreateRequest request) {
        ServiceUtil.checkIfInstance(request, OfficeCreateRequest.class);
        OfficeCreateRequest officeRequest = (OfficeCreateRequest) request;

        Company company = findCompanyById(officeRequest.getCompanyId());
        return Office.builder()
                .address(officeRequest.getAddress())
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
    public UpdateDto toUpdateDto(UpdateRequest request) {
        ServiceUtil.checkIfInstance(request, OfficeCreateRequest.class);
        OfficeCreateRequest officeRequest = (OfficeCreateRequest) request;

        Company company = findCompanyById(officeRequest.getCompanyId());
        return OfficeUpdateDto.builder()
                .address(officeRequest.getAddress())
                .company(company)
                .build();
    }
}
