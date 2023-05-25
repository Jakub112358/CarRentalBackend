package com.carrentalbackend.util.factories;

import com.carrentalbackend.features.priceLists.rest.PriceListRequest;
import com.carrentalbackend.model.entity.PriceList;

import static com.carrentalbackend.features.priceLists.rest.PriceListRequest.PriceListRequestBuilder;
import static com.carrentalbackend.model.entity.PriceList.PriceListBuilder;

public class PriceListFactory {
    public static final double simplePriceListPricePerDay = 100.0;
    public static final double simplePriceListPricePerWeek = 95.0;
    public static final double simplePriceListPricePerMonth = 89.99;

    public static PriceListBuilder getSimplePriceListBuilder() {
        return PriceList.builder()
                .pricePerDay(simplePriceListPricePerDay)
                .pricePerWeek(simplePriceListPricePerWeek)
                .pricePerMonth(simplePriceListPricePerMonth);
    }

    public static PriceListRequestBuilder getSimplePriceListRequestBuilder() {
        return PriceListRequest.builder()
                .pricePerDay(simplePriceListPricePerDay)
                .pricePerWeek(simplePriceListPricePerWeek)
                .pricePerMonth(simplePriceListPricePerMonth);
    }
}
