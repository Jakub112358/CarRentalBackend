package com.carrentalbackend.features.companyResources.car.priceLists;

import com.carrentalbackend.features.generics.CrudMapper;
import com.carrentalbackend.features.companyResources.car.priceLists.PriceListUpdateDto;
import com.carrentalbackend.features.generics.UpdateDto;
import com.carrentalbackend.model.entity.PriceList;
import com.carrentalbackend.features.companyResources.car.priceLists.PriceListCreateRequest;
import com.carrentalbackend.features.companyResources.car.PriceListUpdateRequest;
import com.carrentalbackend.features.companyResources.car.priceLists.PriceListResponse;
import com.carrentalbackend.features.generics.Response;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class PriceListMapper implements CrudMapper<PriceList, PriceListUpdateRequest, PriceListCreateRequest> {

    @Override
    public PriceList toNewEntity(PriceListCreateRequest request) {

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

    @Override
    public UpdateDto toUpdateDto(PriceListUpdateRequest request) {

        return PriceListUpdateDto.builder()
                .pricePerDay(request.getPricePerDay())
                .pricePerWeek(request.getPricePerWeek())
                .pricePerMonth(request.getPricePerMonth())
                .build();
    }
}
