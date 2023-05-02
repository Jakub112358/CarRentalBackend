package com.carrentalbackend.model.mapper;

import com.carrentalbackend.exception.ResourceNotFoundException;
import com.carrentalbackend.model.dto.CarDto;
import com.carrentalbackend.model.entity.BranchOffice;
import com.carrentalbackend.model.entity.Car;
import com.carrentalbackend.repository.OfficeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CarMapper implements CrudMapper<Car, CarDto> {
    private final OfficeRepository officeRepository;

    @Override
    public Car toNewEntity(CarDto dto) {
        BranchOffice office = officeRepository.findById(dto.getCurrentBranchOfficeId())
                .orElseThrow(() -> new ResourceNotFoundException(dto.getCurrentBranchOfficeId()));
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
                .currentBranchOffice(office)
                .build();
    }

    @Override
    public Car toUpdateEntity(CarDto dto) {
        BranchOffice office = null;
        if (dto.getCurrentBranchOfficeId() != null) {
            office = officeRepository.findById(dto.getCurrentBranchOfficeId())
                    .orElseThrow(() -> new ResourceNotFoundException(dto.getCurrentBranchOfficeId()));
        }
        return Car.builder()
                .make(dto.getMake())
                .model(dto.getModel())
                .mileage(dto.getMileage())
                .minRentalTime(dto.getMinRentalTime())
                .yearOfManufacture(dto.getYearOfManufacture())
                .bodyType(dto.getBodyType())
                .color(dto.getColor())
                .status(dto.getStatus())
                .currentBranchOffice(office)
                .build();
    }

    @Override
    public CarDto toDto(Car entity) {
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
                .currentBranchOfficeId(entity.getCurrentBranchOffice().getId())
                .build();
    }

}
