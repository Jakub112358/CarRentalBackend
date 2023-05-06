package com.carrentalbackend.model.mapper;

import com.carrentalbackend.model.dto.crudDto.PricelistDto;
import com.carrentalbackend.model.dto.updateDto.UpdateDto;
import com.carrentalbackend.model.entity.Pricelist;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
@Component
public class PricelistMapper implements CrudMapper<Pricelist, PricelistDto> {
    @Override
    public Pricelist toNewEntity(PricelistDto dto) {
        return Pricelist.builder()
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
    public PricelistDto toDto(Pricelist entity) {
        return PricelistDto.builder()
                .id(entity.getId())
                .pricePerDay(entity.getPricePerDay())
                .pricePerWeek(entity.getPricePerWeek())
                .pricePerMonth(entity.getPricePerMonth())
                .build();
    }
}
