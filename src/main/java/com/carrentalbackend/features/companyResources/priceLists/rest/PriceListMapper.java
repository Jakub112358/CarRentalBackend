package com.carrentalbackend.features.companyResources.priceLists.rest;

import com.carrentalbackend.features.generics.CrudMapper;
import com.carrentalbackend.features.generics.Response;
import com.carrentalbackend.model.entity.PriceList;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class PriceListMapper implements CrudMapper<PriceList, PriceListRequest> {

    @Override
    public PriceList toNewEntity(PriceListRequest request) {

        return PriceList.builder()
                .pricePerDay(request.getPricePerDay())
                .pricePerWeek(request.getPricePerWeek())
                .pricePerMonth(request.getPricePerMonth())
                .cars(new ArrayList<>())
                .build();
    }

    @Override
    public Response toResponse(PriceList entity) {
        return PriceListResponse.builder()
                .id(entity.getId())
                .pricePerDay(entity.getPricePerDay())
                .pricePerWeek(entity.getPricePerWeek())
                .pricePerMonth(entity.getPricePerMonth())
                .build();
    }

}
