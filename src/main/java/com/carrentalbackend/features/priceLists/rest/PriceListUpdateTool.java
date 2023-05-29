package com.carrentalbackend.features.priceLists.rest;

import com.carrentalbackend.features.generics.UpdateTool;
import com.carrentalbackend.model.entity.PriceList;
import org.springframework.stereotype.Component;

@Component
public class PriceListUpdateTool implements UpdateTool<PriceList, PriceListRequest> {
    @Override
    public void updateEntity(PriceList entity, PriceListRequest updateRequest) {
        entity.setShortTermPrice(updateRequest.getShortTermPrice());
        entity.setMediumTermPrice(updateRequest.getMediumTermPrice());
        entity.setLongTermPrice(updateRequest.getLongTermPrice());
    }
}
