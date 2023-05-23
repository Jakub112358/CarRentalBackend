package com.carrentalbackend.features.companyResources.cars.rest;

import com.carrentalbackend.exception.ResourceNotFoundException;
import com.carrentalbackend.features.generics.UpdateTool;
import com.carrentalbackend.model.entity.Car;
import com.carrentalbackend.model.entity.Office;
import com.carrentalbackend.model.entity.PriceList;
import com.carrentalbackend.repository.OfficeRepository;
import com.carrentalbackend.repository.PriceListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CarUpdateTool implements UpdateTool<Car, CarRequest> {
    private final OfficeRepository officeRepository;
    private final PriceListRepository priceListRepository;
    @Override
    public void updateEntity(Car entity, CarRequest request) {
        Office office = findOfficeById(request.getCurrentOfficeId());
        PriceList pricelist = findPriceListById(request.getPriceListId());

        entity.setMake(request.getMake());
        entity.setModel(request.getModel());
        entity.setMileage(request.getMileage());
        entity.setMinRentalTime(request.getMinRentalTime());
        entity.setYearOfManufacture(request.getYearOfManufacture());
        entity.setBodyType(request.getBodyType());
        entity.setColor(request.getColor());
        entity.setStatus(request.getStatus());
        entity.setPriceList(pricelist);
        entity.setCurrentOffice(office);
    }

    private Office findOfficeById(Long id) {
            return officeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    private PriceList findPriceListById(Long id) {
            return priceListRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }
}
