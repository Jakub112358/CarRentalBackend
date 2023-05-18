package com.carrentalbackend.features.companyResources.company;

import com.carrentalbackend.features.generics.CrudMapper;
import com.carrentalbackend.features.companyResources.company.CompanyUpdateDto;
import com.carrentalbackend.features.generics.UpdateDto;
import com.carrentalbackend.model.entity.Company;
import com.carrentalbackend.model.entity.Finances;
import com.carrentalbackend.features.companyResources.company.CompanyCreateRequest;
import com.carrentalbackend.features.companyResources.company.CompanyUpdateRequest;
import com.carrentalbackend.features.companyResources.company.CompanyResponse;
import com.carrentalbackend.features.generics.Response;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper implements CrudMapper<Company, CompanyUpdateRequest, CompanyCreateRequest> {

    @Override
    public Company toNewEntity(CompanyCreateRequest request) {

        return Company.builder()
                .name(request.getName())
                .domain(request.getDomain())
                .logotype(request.getLogotype())
                .address(request.getAddress())
                .finances(new Finances())
                .differentOfficesExtraCharge(request.getDifferentOfficesExtraCharge())
                .build();
    }

    @Override
    public Response toResponse(Company entity) {
        return CompanyResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .domain(entity.getDomain())
                .logotype(entity.getLogotype())
                .address(entity.getAddress())
                .build();
    }

    @Override
    public UpdateDto toUpdateDto(CompanyUpdateRequest request) {

        return CompanyUpdateDto.builder()
                .name(request.getName())
                .domain(request.getDomain())
                .logotype(request.getLogotype())
                .address(request.getAddress())
                .build();
    }
}
