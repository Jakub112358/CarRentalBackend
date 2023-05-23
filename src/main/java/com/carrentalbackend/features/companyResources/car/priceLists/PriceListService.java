package com.carrentalbackend.features.companyResources.car.priceLists;

import com.carrentalbackend.features.generics.CrudService;
import com.carrentalbackend.model.entity.PriceList;
import com.carrentalbackend.repository.PriceListRepository;
import org.springframework.stereotype.Service;

@Service
public class PriceListService extends CrudService<PriceList, PriceListRequest> {
    public PriceListService(PriceListRepository repository, PriceListMapper mapper, PriceListUpdateTool updateTool) {
        super(repository, mapper, updateTool);
    }

    @Override
    public void deleteById(Long id) {

    }
}
