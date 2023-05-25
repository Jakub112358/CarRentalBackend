package com.carrentalbackend.features.priceLists;

import com.carrentalbackend.exception.ResourceNotFoundException;
import com.carrentalbackend.features.priceLists.rest.PriceListMapper;
import com.carrentalbackend.features.priceLists.rest.PriceListRequest;
import com.carrentalbackend.features.priceLists.rest.PriceListUpdateTool;
import com.carrentalbackend.features.generics.CrudService;
import com.carrentalbackend.model.entity.PriceList;
import com.carrentalbackend.repository.CarRepository;
import com.carrentalbackend.repository.PriceListRepository;
import org.springframework.stereotype.Service;

@Service
public class PriceListService extends CrudService<PriceList, PriceListRequest> {
    private final PriceListRepository priceListRepository;
    private final CarRepository carRepository;

    public PriceListService(PriceListRepository priceListRepository,
                            PriceListMapper mapper,
                            PriceListUpdateTool updateTool,
                            CarRepository carRepository) {
        super(priceListRepository, mapper, updateTool);
        this.priceListRepository = priceListRepository;
        this.carRepository = carRepository;
    }

    @Override
    public void deleteById(Long id) {
        if(!priceListRepository.existsById(id))
            throw new ResourceNotFoundException(id);

        nullPriceListInCars(id);
        priceListRepository.deleteById(id);
    }

    private void nullPriceListInCars(Long id) {
        carRepository.findAllByPriceListId(id).forEach(c -> c.setPriceList(null));
    }
}
