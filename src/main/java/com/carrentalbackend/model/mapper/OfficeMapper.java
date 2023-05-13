package com.carrentalbackend.model.mapper;

import com.carrentalbackend.exception.ResourceNotFoundException;
import com.carrentalbackend.model.dto.crudDto.OfficeDto;
import com.carrentalbackend.model.dto.updateDto.OfficeUpdateDto;
import com.carrentalbackend.model.dto.updateDto.UpdateDto;
import com.carrentalbackend.model.entity.Office;
import com.carrentalbackend.model.entity.Company;
import com.carrentalbackend.model.rest.request.create.CreateRequest;
import com.carrentalbackend.model.rest.request.create.OfficeCreateRequest;
import com.carrentalbackend.model.rest.response.Response;
import com.carrentalbackend.model.rest.response.OfficeResponse;
import com.carrentalbackend.repository.CompanyRepository;
import com.carrentalbackend.service.util.ServiceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OfficeMapper implements CrudMapper<Office, OfficeDto> {
    private final CompanyRepository companyRepository;

    @Override
    public Office toNewEntity(OfficeDto dto) {
        Company company = findCompanyById(dto.getCompanyId());
        return Office.builder()
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
    public OfficeDto toDto(Office entity) {
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

    @Override
    public Office toNewEntity(CreateRequest request) {
        ServiceUtil.checkIfInstance(request, OfficeCreateRequest.class);

        OfficeCreateRequest officeRequest = (OfficeCreateRequest) request;
        Company company = findCompanyById(officeRequest.getCompanyId());

        return Office.builder()
                .id(officeRequest.getId())
                .address(officeRequest.getAddress())
                .company(company)
                .build();
    }

    @Override
    public Response toResponse(Office entity) {
        return OfficeResponse.builder()
                .id(entity.getId())
                .address(entity.getAddress())
                .companyId(entity.getCompany().getId())
                .build();
    }
}
