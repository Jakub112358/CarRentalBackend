package com.carrentalbackend.model.mapper;

import com.carrentalbackend.exception.ResourceNotFoundException;
import com.carrentalbackend.model.dto.CarDto;
import com.carrentalbackend.model.dto.CarUpdateDto;
import com.carrentalbackend.model.dto.UpdateDto;
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
        BranchOffice office = findOfficeById(dto.getCurrentBranchOfficeId());
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
    public UpdateDto toUpdateEntity(CarDto dto) {
        BranchOffice office = findOfficeById(dto.getCurrentBranchOfficeId());
        return CarUpdateDto.builder()
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
        Long officeId = entity.getCurrentBranchOffice() != null ? entity.getCurrentBranchOffice().getId() : null;
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
                .currentBranchOfficeId(officeId)
                .build();
    }

    private BranchOffice findOfficeById(Long id) {
        if (id == null) {
            return null;
        } else {
            return officeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        }
    }

}
