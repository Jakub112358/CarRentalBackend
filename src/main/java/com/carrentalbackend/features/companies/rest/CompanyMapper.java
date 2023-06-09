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
                .mediumTermRentMinDays(request.getMediumTermRentMinDays())
                .longTermRentMinDays(request.getLongTermRentMinDays())
                .freeCancellationDaysLimit(request.getFreeCancellationDaysLimit())
                .lateCancellationRatio(request.getLateCancellationRatio())
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
                .differentOfficesExtraCharge(entity.getDifferentOfficesExtraCharge())
                .mediumTermRentMinDays(entity.getMediumTermRentMinDays())
                .longTermRentMinDays(entity.getLongTermRentMinDays())
                .freeCancellationDaysLimit(entity.getFreeCancellationDaysLimit())
                .lateCancellationRatio(entity.getLateCancellationRatio())
                .build();
    }

}
