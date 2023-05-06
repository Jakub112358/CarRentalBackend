package com.carrentalbackend.service;

import com.carrentalbackend.model.dto.crudDto.PricelistDto;
import com.carrentalbackend.model.entity.Pricelist;
import com.carrentalbackend.model.mapper.PricelistMapper;
import com.carrentalbackend.repository.PricelistRepository;
import org.springframework.stereotype.Service;

@Service
public class PricelistService extends CrudService<Pricelist, PricelistDto> {
    public PricelistService(PricelistRepository repository, PricelistMapper mapper) {
        super(repository, mapper);
    }

    @Override
    public void deleteById(Long id) {

    }
}
