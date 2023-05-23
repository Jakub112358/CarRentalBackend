package com.carrentalbackend.features.companies.rest;

import com.carrentalbackend.features.generics.CrudMapper;
import com.carrentalbackend.features.generics.Response;
import com.carrentalbackend.model.entity.Company;
import com.carrentalbackend.model.entity.Finances;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper implements CrudMapper<Company, CompanyRequest> {

    @Override
    public Company toNewEntity(CompanyRequest request) {
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

}
