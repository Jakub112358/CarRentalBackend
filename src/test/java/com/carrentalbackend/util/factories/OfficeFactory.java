package com.carrentalbackend.util.factories;

import com.carrentalbackend.features.offices.rest.OfficeRequest;
import com.carrentalbackend.model.entity.Office;

import static com.carrentalbackend.features.offices.rest.OfficeRequest.OfficeRequestBuilder;
import static com.carrentalbackend.model.entity.Office.OfficeBuilder;

public class OfficeFactory {
    public static OfficeRequestBuilder getSimpleOfficeRequestBuilder() {
        return OfficeRequest.builder()
                .address(AddressFactory.getSimpleAddress());
    }

    public static OfficeBuilder getSimpleOfficeBuilder() {
        return Office.builder()
                .address(AddressFactory.getSimpleAddress());
    }
}
