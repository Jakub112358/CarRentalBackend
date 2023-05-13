package com.carrentalbackend.model.mapper;

import com.carrentalbackend.model.dto.updateDto.CompanyUpdateDto;
import com.carrentalbackend.model.dto.updateDto.UpdateDto;
import com.carrentalbackend.model.entity.Company;
import com.carrentalbackend.model.entity.Finances;
import com.carrentalbackend.model.rest.request.create.CompanyCreateRequest;
import com.carrentalbackend.model.rest.request.create.CreateRequest;
import com.carrentalbackend.model.rest.request.update.UpdateRequest;
import com.carrentalbackend.model.rest.response.CompanyResponse;
import com.carrentalbackend.model.rest.response.Response;
import com.carrentalbackend.service.util.ServiceUtil;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper implements CrudMapper<Company> {

    @Override
    public Company toNewEntity(CreateRequest request) {
        ServiceUtil.checkIfInstance(request, CompanyCreateRequest.class);
        CompanyCreateRequest companyRequest = (CompanyCreateRequest) request;

        return Company.builder()
                .name(companyRequest.getName())
                .domain(companyRequest.getDomain())
                .logotype(companyRequest.getLogotype())
                .address(companyRequest.getAddress())
                .finances(new Finances())
                .differentOfficesExtraCharge(companyRequest.getDifferentOfficesExtraCharge())
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
    public UpdateDto toUpdateDto(UpdateRequest request) {
        ServiceUtil.checkIfInstance(request, CompanyCreateRequest.class);
        CompanyCreateRequest companyRequest = (CompanyCreateRequest) request;

        return CompanyUpdateDto.builder()
                .name(companyRequest.getName())
                .domain(companyRequest.getDomain())
                .logotype(companyRequest.getLogotype())
                .address(companyRequest.getAddress())
                .build();
    }
}
