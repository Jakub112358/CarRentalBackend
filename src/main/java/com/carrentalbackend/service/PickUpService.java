package com.carrentalbackend.service;

import com.carrentalbackend.model.dto.crudDto.PickUpDto;
import com.carrentalbackend.model.entity.CarPickUp;
import com.carrentalbackend.model.mapper.PickUpMapper;
import com.carrentalbackend.repository.PickUpRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PickUpService extends CrudService<CarPickUp, PickUpDto> {
    private final PickUpRepository pickUpRepository;
    public PickUpService(PickUpRepository repository, PickUpMapper mapper) {
        super(repository, mapper);
        this.pickUpRepository = repository;
    }

    @Override
    public void deleteById(Long id) {
    }

    public List<PickUpDto> findAllByOfficeId(Long officeId) {
        return pickUpRepository
                .findAllByBranchOffice_Id(officeId)
                .stream()
                .map(mapper::toDto)
                .toList();
    }
}
