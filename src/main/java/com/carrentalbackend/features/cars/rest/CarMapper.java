package com.carrentalbackend.features.cars.rest;

import com.carrentalbackend.exception.ResourceNotFoundException;
import com.carrentalbackend.features.generics.CrudMapper;
import com.carrentalbackend.features.renting.carSearch.CarSearchResponse;
import com.carrentalbackend.model.entity.Car;
import com.carrentalbackend.model.entity.Office;
import com.carrentalbackend.model.entity.PriceList;
import com.carrentalbackend.repository.OfficeRepository;
import com.carrentalbackend.repository.PriceListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CarMapper implements CrudMapper<Car, CarRequest> {

    private final OfficeRepository officeRepository;
    private final PriceListRepository pricelistRepository;

    @Override
    public Car toNewEntity(CarRequest request) {

        Office office = findOfficeById(request.getCurrentOfficeId());
        PriceList pricelist = findPriceListById(request.getPriceListId());
        return Car.builder()
                .make(request.getMake())
                .model(request.getModel())
                .mileage(request.getMileage())
                .minRentalTime(request.getMinRentalTime())
                .yearOfManufacture(request.getYearOfManufacture())
                .bodyType(request.getBodyType())
                .color(request.getColor())
                .status(request.getStatus())
                .currentOffice(office)
                .priceList(pricelist)
                .build();
    }

    @Override
    public CarResponse toResponse(Car entity) {
        Long officeId = entity.getCurrentOffice() != null ? entity.getCurrentOffice().getId() : null;
        Long priceListId = entity.getPriceList() != null ? entity.getPriceList().getId() : null;
        return CarResponse.builder()
                .id(entity.getId())
                .make(entity.getMake())
                .model(entity.getModel())
                .mileage(entity.getMileage())
                .minRentalTime(entity.getMinRentalTime())
                .yearOfManufacture(entity.getYearOfManufacture())
                .bodyType(entity.getBodyType())
                .color(entity.getColor())
                .status(entity.getStatus())
                .priceListId(priceListId)
                .currentBranchOfficeId(officeId)
                .build();
    }

    public CarSearchResponse toCarRentResponse(Car entity) {
        return CarSearchResponse.builder()
                .carResponse(toResponse(entity))
                .build();
    }

    private Office findOfficeById(Long id) {
        if (id == null) {
            return null;
        } else {
            return officeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        }
    }

    private PriceList findPriceListById(Long id) {
        if (id == null) {
            return null;
        } else {
            return pricelistRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        }
    }

}
