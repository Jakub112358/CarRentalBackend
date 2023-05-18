package com.carrentalbackend.features.renting.pickUps;

import com.carrentalbackend.features.generics.CrudService;
import com.carrentalbackend.model.entity.PickUp;
import com.carrentalbackend.features.generics.Response;
import com.carrentalbackend.repository.PickUpRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PickUpService extends CrudService<PickUp, PickUpUpdateRequest, PickUpCreateRequest> {
    private final PickUpRepository pickUpRepository;

    public PickUpService(PickUpRepository repository, PickUpMapper mapper) {
        super(repository, mapper);
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
