package com.carrentalbackend.model.mapper;

import com.carrentalbackend.exception.ResourceNotFoundException;
import com.carrentalbackend.model.rest.request.create.CreateRequest;
import com.carrentalbackend.model.rest.response.CarRentResponse;
import com.carrentalbackend.model.dto.crudDto.CarDto;
import com.carrentalbackend.model.dto.updateDto.CarUpdateDto;
import com.carrentalbackend.model.dto.updateDto.UpdateDto;
import com.carrentalbackend.model.entity.Office;
import com.carrentalbackend.model.entity.Car;
import com.carrentalbackend.model.entity.PriceList;
import com.carrentalbackend.model.rest.response.Response;
import com.carrentalbackend.repository.OfficeRepository;
import com.carrentalbackend.repository.PriceListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CarMapper implements CrudMapper<Car, CarDto> {

    @Override
    public Car toNewEntity(CreateRequest request) {
        return null;
    }

    @Override
    public Response toCreateResponse(Car entity) {
        return null;
    }

    private final OfficeRepository officeRepository;
    private final PriceListRepository pricelistRepository;

    @Override
    public Car toNewEntity(CarDto dto) {
        Office office = findOfficeById(dto.getCurrentBranchOfficeId());
        PriceList pricelist = findPricelistByid(dto.getPriceListId());
        return Car.builder()
                .id(dto.getId())
                .make(dto.getMake())
                .model(dto.getModel())
                .mileage(dto.getMileage())
                .minRentalTime(dto.getMinRentalTime())
                .yearOfManufacture(dto.getYearOfManufacture())
                .bodyType(dto.getBodyType())
                .color(dto.getColor())
                .status(dto.getStatus())
                .currentOffice(office)
                .priceList(pricelist)
                .build();
    }



    @Override
    public UpdateDto toUpdateEntity(CarDto dto) {
        Office office = findOfficeById(dto.getCurrentBranchOfficeId());
        PriceList pricelist = findPricelistByid(dto.getPriceListId());
        return CarUpdateDto.builder()
                .make(dto.getMake())
                .model(dto.getModel())
                .mileage(dto.getMileage())
                .minRentalTime(dto.getMinRentalTime())
                .yearOfManufacture(dto.getYearOfManufacture())
                .bodyType(dto.getBodyType())
                .color(dto.getColor())
                .status(dto.getStatus())
                .priceList(pricelist)
                .currentOffice(office)
                .build();
    }


    @Override
    public CarDto toDto(Car entity) {
        Long officeId = entity.getCurrentOffice() != null ? entity.getCurrentOffice().getId() : null;
        Long priceListId = entity.getPriceList() != null ? entity.getPriceList().getId() : null;
        return CarDto.builder()
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

    public CarRentResponse toRentDto(Car entity) {
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

    private PriceList findPricelistByid(Long id) {
        if (id == null) {
            return null;
        } else {
            return pricelistRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        }
    }

}
