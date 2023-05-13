package com.carrentalbackend.model.mapper;

import com.carrentalbackend.model.dto.updateDto.PriceListUpdateDto;
import com.carrentalbackend.model.dto.updateDto.UpdateDto;
import com.carrentalbackend.model.entity.PriceList;
import com.carrentalbackend.model.rest.request.create.CreateRequest;
import com.carrentalbackend.model.rest.request.create.PriceListCreateRequest;
import com.carrentalbackend.model.rest.request.update.UpdateRequest;
import com.carrentalbackend.model.rest.response.PriceListResponse;
import com.carrentalbackend.model.rest.response.Response;
import com.carrentalbackend.service.util.ServiceUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class PriceListMapper implements CrudMapper<PriceList> {

    @Override
    public PriceList toNewEntity(CreateRequest request) {
        ServiceUtil.checkIfInstance(request, PriceListCreateRequest.class);
        PriceListCreateRequest priceListRequest = (PriceListCreateRequest) request;

        return PriceList.builder()
                .pricePerDay(priceListRequest.getPricePerDay())
                .pricePerWeek(priceListRequest.getPricePerWeek())
                .pricePerMonth(priceListRequest.getPricePerMonth())
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
    public UpdateDto toUpdateDto(UpdateRequest request) {
        ServiceUtil.checkIfInstance(request, PriceListCreateRequest.class);
        PriceListCreateRequest priceListRequest = (PriceListCreateRequest) request;

        return PriceListUpdateDto.builder()
                .pricePerDay(priceListRequest.getPricePerDay())
                .pricePerWeek(priceListRequest.getPricePerWeek())
                .pricePerMonth(priceListRequest.getPricePerMonth())
                .build();
    }
}
