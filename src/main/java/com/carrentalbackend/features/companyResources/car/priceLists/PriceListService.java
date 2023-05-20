package com.carrentalbackend.features.companyResources.car.priceLists;

import com.carrentalbackend.features.generics.CrudService;
import com.carrentalbackend.model.entity.PriceList;
import com.carrentalbackend.features.companyResources.car.PriceListUpdateRequest;
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