package com.carrentalbackend.service.mapper;

import com.carrentalbackend.exception.ResourceNotFoundException;
import com.carrentalbackend.features.generics.CrudMapper;
import com.carrentalbackend.model.dto.updateDto.CarUpdateDto;
import com.carrentalbackend.model.dto.updateDto.UpdateDto;
import com.carrentalbackend.model.entity.Car;
import com.carrentalbackend.model.entity.Office;
import com.carrentalbackend.model.entity.PriceList;
import com.carrentalbackend.model.rest.request.create.CarCreateRequest;
import com.carrentalbackend.model.rest.request.update.CarUpdateRequest;
import com.carrentalbackend.model.rest.response.CarRentResponse;
import com.carrentalbackend.model.rest.response.CarResponse;
import com.carrentalbackend.repository.OfficeRepository;
import com.carrentalbackend.repository.PriceListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CarMapper implements CrudMapper<Car, CarUpdateRequest, CarCreateRequest> {

    private final OfficeRepository officeRepository;
    private final PriceListRepository pricelistRepository;

    @Override
    public UpdateDto toUpdateDto(CarUpdateRequest request) {

        Office office = findOfficeById(request.getCurrentBranchOfficeId());
        PriceList pricelist = findPriceListById(request.getPriceListId());
        return CarUpdateDto.builder()
                .make(request.getMake())
                .model(request.getModel())
                .mileage(request.getMileage())
                .minRentalTime(request.getMinRentalTime())
                .yearOfManufacture(request.getYearOfManufacture())
                .bodyType(request.getBodyType())
                .color(request.getColor())
                .status(request.getStatus())
                .priceList(pricelist)
                .currentOffice(office)
                .build();
    }

    @Override
    public Car toNewEntity(CarCreateRequest request) {

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

    public CarRentResponse toCarRentResponse(Car entity) {
        Long officeId = entity.getCurrentOffice() != null ? entity.getCurrentOffice().getId() : null;
        Long priceListId = entity.getPriceList() != null ? entity.getPriceList().getId() : null;
        return CarRentResponse.builder()
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
