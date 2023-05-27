package com.carrentalbackend.util.factories;

import com.carrentalbackend.features.priceLists.rest.PriceListRequest;
import com.carrentalbackend.model.entity.PriceList;

import static com.carrentalbackend.features.priceLists.rest.PriceListRequest.PriceListRequestBuilder;
import static com.carrentalbackend.model.entity.PriceList.PriceListBuilder;

public class PriceListFactory {
    public static final double simplePriceListShortTermPrice = 100.0;
    public static final double simplePriceListMediumTermPrice = 95.0;
    public static final double simplePriceListLongTermPrice = 89.99;

    public static PriceListBuilder getSimplePriceListBuilder() {
        return PriceList.builder()
                .shortTermPrice(simplePriceListShortTermPrice)
                .mediumTermPrice(simplePriceListMediumTermPrice)
                .longTermPrice(simplePriceListLongTermPrice);
    }

    public static PriceListRequestBuilder getSimplePriceListRequestBuilder() {
        return PriceListRequest.builder()
                .shortTermPrice(simplePriceListShortTermPrice)
                .mediumTermPrice(simplePriceListMediumTermPrice)
                .longTermPrice(simplePriceListLongTermPrice);
    }
}
