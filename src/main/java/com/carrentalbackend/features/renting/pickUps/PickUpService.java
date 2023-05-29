package com.carrentalbackend.features.renting.pickUps;

import com.carrentalbackend.exception.ResourceNotFoundException;
import com.carrentalbackend.features.generics.Response;
import com.carrentalbackend.features.renting.pickUps.rest.PickUpMapper;
import com.carrentalbackend.features.renting.pickUps.rest.PickUpUpdateRequest;
import com.carrentalbackend.features.renting.pickUps.rest.PickUpUpdateTool;
import com.carrentalbackend.model.entity.PickUp;
import com.carrentalbackend.repository.PickUpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PickUpService {
    private final PickUpRepository pickUpRepository;
    private final PickUpMapper pickUpMapper;
    private final PickUpUpdateTool updateTool;

    public Set<Response> findAll() {
        return pickUpRepository.findAll().stream().map(pickUpMapper::toResponse).collect(Collectors.toSet());
    }

    public Response findById(Long id) {
        return pickUpMapper.toResponse(pickUpRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id)));
    }

    public Response update(Long id, PickUpUpdateRequest request) {
        PickUp instance = pickUpRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        updateTool.updateEntity(instance, request);
        return pickUpMapper.toResponse(instance);
    }

    public Set<Response> findAllByOfficeId(Long officeId) {
        return pickUpRepository
                .findAllByOffice_Id(officeId)
                .stream()
                .map(pickUpMapper::toResponse)
                .collect(Collectors.toSet());
    }
}
