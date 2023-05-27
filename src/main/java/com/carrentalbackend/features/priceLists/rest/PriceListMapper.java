package com.carrentalbackend.features.priceLists.rest;

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
                .shortTermPrice(request.getShortTermPrice())
                .mediumTermPrice(request.getMediumTermPrice())
                .longTermPrice(request.getLongTermPrice())
                .cars(new ArrayList<>())
                .build();
    }

    @Override
    public Response toResponse(PriceList entity) {
        return PriceListResponse.builder()
                .id(entity.getId())
                .shortTermPrice(entity.getShortTermPrice())
                .mediumTermPrice(entity.getMediumTermPrice())
                .longTermPrice(entity.getLongTermPrice())
                .build();
    }

}
