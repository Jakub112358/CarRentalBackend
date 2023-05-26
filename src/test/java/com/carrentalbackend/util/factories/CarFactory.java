package com.carrentalbackend.util.factories;

import com.carrentalbackend.features.cars.rest.CarRequest;
import com.carrentalbackend.model.entity.Car;
import com.carrentalbackend.model.enumeration.CarBodyType;
import com.carrentalbackend.model.enumeration.CarStatus;
import com.carrentalbackend.model.enumeration.Color;

import static com.carrentalbackend.model.entity.Car.CarBuilder;
import static com.carrentalbackend.features.cars.rest.CarRequest.CarRequestBuilder;

public class CarFactory {
    public final static String simpleCarMake = "Kia";
    public final static String simpleCarModel = "Sportage";
    public final static int simpleCarMileage = 30_000;
    public final static int simpleCarMinRentalTime = 1;
    public final static int simpleCarYearOfManufacture = 2020;
    public final static CarBodyType simpleCarBodyType = CarBodyType.SUV;
    public final static Color simpleCarColor = Color.WHITE;
    public final static CarStatus simpleCarStatus = CarStatus.AVAILABLE;


    public static CarBuilder getSimpleCarBuilder() {
        return Car.builder()
                .make(simpleCarMake)
                .model(simpleCarModel)
                .mileage(simpleCarMileage)
                .minRentalTime(simpleCarMinRentalTime)
                .yearOfManufacture(simpleCarYearOfManufacture)
                .bodyType(simpleCarBodyType)
                .color(simpleCarColor)
                .status(simpleCarStatus);
    }

    public static CarRequestBuilder getSimpleCarRequestBuilder(Long priceListId, Long currentOfficeId) {
        return CarRequest.builder()
                .make(simpleCarMake)
                .model(simpleCarModel)
                .mileage(simpleCarMileage)
                .minRentalTime(simpleCarMinRentalTime)
                .yearOfManufacture(simpleCarYearOfManufacture)
                .bodyType(simpleCarBodyType)
                .color(simpleCarColor)
                .status(simpleCarStatus)
                .priceListId(priceListId)
                .currentOfficeId(currentOfficeId);
    }
}
