package com.carrentalbackend.model.mapper;

import com.carrentalbackend.model.dto.crudDto.PricelistDto;
import com.carrentalbackend.model.dto.updateDto.UpdateDto;
import com.carrentalbackend.model.entity.PriceList;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
@Component
public class PriceListMapper implements CrudMapper<PriceList, PricelistDto> {
    @Override
    public PriceList toNewEntity(PricelistDto dto) {
        return PriceList.builder()
                .id(dto.getId())
                .pricePerDay(dto.getPricePerDay())
                .pricePerWeek(dto.getPricePerWeek())
                .pricePerMonth(dto.getPricePerMonth())
                .cars(new ArrayList<>())
                .build();
    }
//TODO: implement
    @Override
    public UpdateDto toUpdateEntity(PricelistDto dto) {
        return null;
    }

    @Override
    public PricelistDto toDto(PriceList entity) {
        return PricelistDto.builder()
                .id(entity.getId())
                .pricePerDay(entity.getPricePerDay())
                .pricePerWeek(entity.getPricePerWeek())
                .pricePerMonth(entity.getPricePerMonth())
                .build();
    }
}
