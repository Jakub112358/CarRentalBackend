package com.carrentalbackend.service.mapper;

import com.carrentalbackend.model.dto.updateDto.PriceListUpdateDto;
import com.carrentalbackend.model.dto.updateDto.UpdateDto;
import com.carrentalbackend.model.entity.PriceList;
import com.carrentalbackend.model.rest.request.create.PriceListCreateRequest;
import com.carrentalbackend.model.rest.request.update.PriceListUpdateRequest;
import com.carrentalbackend.model.rest.response.PriceListResponse;
import com.carrentalbackend.model.rest.response.Response;
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
