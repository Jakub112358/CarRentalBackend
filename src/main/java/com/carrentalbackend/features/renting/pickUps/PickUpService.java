package com.carrentalbackend.features.renting.pickUps;

import com.carrentalbackend.features.generics.CrudService;
import com.carrentalbackend.features.renting.pickUps.rest.PickUpMapper;
import com.carrentalbackend.features.renting.pickUps.rest.PickUpRequest;
import com.carrentalbackend.features.renting.pickUps.rest.PickUpUpdateTool;
import com.carrentalbackend.model.entity.PickUp;
import com.carrentalbackend.features.generics.Response;
import com.carrentalbackend.repository.PickUpRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PickUpService extends CrudService<PickUp, PickUpRequest, PickUpRequest> {
    private final PickUpRepository pickUpRepository;

    public PickUpService(PickUpRepository repository, PickUpMapper mapper, PickUpUpdateTool updateTool) {
        super(repository, mapper, updateTool);
        this.pickUpRepository = repository;
    }

    @Override
    public void deleteById(Long id) {
    }

    public Set<Response> findAllByOfficeId(Long officeId) {
        return pickUpRepository
                .findAllByOffice_Id(officeId)
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toSet());
    }
}
