package com.carrentalbackend.service;

import com.carrentalbackend.model.entity.PriceList;
import com.carrentalbackend.model.rest.request.create.PriceListCreateRequest;
import com.carrentalbackend.model.rest.request.update.PriceListUpdateRequest;
import com.carrentalbackend.service.mapper.PriceListMapper;
import com.carrentalbackend.repository.PriceListRepository;
import org.springframework.stereotype.Service;

@Service
public class PriceListService extends CrudService<PriceList, PriceListUpdateRequest, PriceListCreateRequest> {
    public PriceListService(PriceListRepository repository, PriceListMapper mapper) {
        super(repository, mapper);
    }

    @Override
    public void deleteById(Long id) {

    }
}
