package com.carrentalbackend.service;

import com.carrentalbackend.model.dto.crudDto.PricelistDto;
import com.carrentalbackend.model.entity.PriceList;
import com.carrentalbackend.model.mapper.PriceListMapper;
import com.carrentalbackend.repository.PriceListRepository;
import org.springframework.stereotype.Service;

@Service
public class PriceListService extends CrudService<PriceList, PricelistDto> {
    public PriceListService(PriceListRepository repository, PriceListMapper mapper) {
        super(repository, mapper);
    }

    @Override
    public void deleteById(Long id) {

    }
}
