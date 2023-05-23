package com.carrentalbackend.features.companyResources.car.priceLists;

import com.carrentalbackend.features.generics.UpdateTool;
import com.carrentalbackend.model.entity.PriceList;
import org.springframework.stereotype.Component;

@Component
public class PriceListUpdateTool implements UpdateTool<PriceList, PriceListRequest> {
    @Override
    public void updateEntity(PriceList entity, PriceListRequest updateRequest) {
        entity.setPricePerDay(updateRequest.getPricePerDay());
        entity.setPricePerWeek(updateRequest.getPricePerWeek());
        entity.setPricePerMonth(updateRequest.getPricePerMonth());
    }
}
